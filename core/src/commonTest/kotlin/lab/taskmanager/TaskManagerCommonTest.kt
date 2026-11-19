package lab.taskmanager

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import lab.shared.Outcome
import lab.taskmanager.application.CompleteTask
import lab.taskmanager.application.CreateTask
import lab.taskmanager.application.IdGenerator
import lab.taskmanager.application.ListTasks
import lab.taskmanager.application.TaskFilter
import lab.taskmanager.domain.DomainError
import lab.taskmanager.domain.Priority
import lab.taskmanager.domain.Task
import lab.taskmanager.domain.TaskId
import lab.taskmanager.domain.TaskStatus
import lab.taskmanager.domain.Title
import lab.taskmanager.infrastructure.InMemoryTaskRepository
import lab.taskmanager.infrastructure.SequentialIdGenerator
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

// Estes testes ficam em commonTest: rodam em CADA plataforma que o core suporta (aqui, via JVM).

private val now: Instant = Instant.parse("2026-01-01T10:00:00Z")

/** Relógio de mentira: o tempo para. Testes determinísticos. */
private val fixedClock = object : Clock {
    override fun now(): Instant = now
}

private fun <V, E> Outcome<V, E>.successValue(): V =
    (this as? Outcome.Success<V>)?.value ?: error("esperava Success, veio $this")

private fun <V, E> Outcome<V, E>.failureError(): E =
    (this as? Outcome.Failure<E>)?.error ?: error("esperava Failure, veio $this")

class TitleTest {
    @Test
    fun `aceita e apara espacos`() = assertEquals("estudar", Title.of("  estudar ").successValue().value)

    @Test
    fun `rejeita vazio`() = assertEquals(DomainError.BlankTitle, Title.of("   ").failureError())

    @Test
    fun `rejeita titulo longo`() =
        assertEquals(DomainError.TitleTooLong(Title.MAX_LENGTH), Title.of("a".repeat(81)).failureError())
}

class TaskTest {
    private val open = Task(TaskId("t1"), Title.of("x").successValue(), Priority.LOW, now)

    @Test
    fun `concluir devolve nova tarefa e preserva a original`() {
        val done = open.complete(now).successValue()
        assertEquals(TaskStatus.Done(now), done.status)
        assertEquals(TaskStatus.Open, open.status)
    }

    @Test
    fun `nao conclui duas vezes`() {
        val done = open.complete(now).successValue()
        assertEquals(DomainError.AlreadyCompleted(TaskId("t1")), done.complete(now).failureError())
    }
}

class UseCaseTest {
    private val repository = InMemoryTaskRepository()
    private val create = CreateTask(repository, SequentialIdGenerator(), fixedClock)
    private val complete = CompleteTask(repository, fixedClock)
    private val list = ListTasks(repository)

    @Test
    fun `criar persiste a tarefa`() {
        val task = create("estudar", Priority.HIGH).successValue()
        assertEquals(task, repository.findById(task.id))
    }

    @Test
    fun `criar com titulo invalido nao persiste`() {
        assertEquals(DomainError.BlankTitle, create(" ", Priority.LOW).failureError())
        assertTrue(repository.findAll().isEmpty())
    }

    @Test
    fun `usa o gerador de id injetado`() {
        val fixedId = IdGenerator { TaskId("fixo") }
        val task = CreateTask(repository, fixedId, fixedClock)("a", Priority.LOW).successValue()
        assertEquals(TaskId("fixo"), task.id)
    }

    @Test
    fun `concluir tarefa inexistente`() {
        assertEquals(DomainError.TaskNotFound(TaskId("zz")), complete(TaskId("zz")).failureError())
    }

    @Test
    fun `concluir persiste o novo estado`() {
        val task = create("a", Priority.LOW).successValue()
        complete(task.id).successValue()
        assertTrue(repository.findById(task.id)!!.isDone)
    }

    @Test
    fun `lista ordena por prioridade e filtra por estado`() {
        val low = create("baixa", Priority.LOW).successValue()
        create("alta", Priority.HIGH).successValue()
        create("media", Priority.MEDIUM).successValue()
        complete(low.id).successValue()

        assertEquals(listOf("alta", "media"), list(TaskFilter.OPEN).map { it.title.value })
        assertEquals(listOf("baixa"), list(TaskFilter.DONE).map { it.title.value })
        assertEquals(listOf("alta", "media", "baixa"), list().map { it.title.value })
    }
}

class PriorityTest {
    @Test
    fun `pesos crescem com a urgencia`() {
        assertEquals(listOf(1, 2, 3), Priority.entries.map { it.weight })
    }

    @Test
    fun `parse ignora caixa e espacos`() {
        assertEquals(Priority.HIGH, Priority.parse("high"))
        assertEquals(Priority.MEDIUM, Priority.parse("  Medium "))
    }

    @Test
    fun `parse de valor desconhecido devolve null`() = assertNull(Priority.parse("urgente"))
}

class ContractTest {
    @Test
    fun `dto reflete a tarefa`() {
        val module = TaskModule(clock = fixedClock)
        val dto = module.createTask("estudar", Priority.HIGH).successValue().toDto()
        assertEquals(TaskDto("t1", "estudar", "HIGH", done = false, createdAt = "2026-01-01T10:00:00Z"), dto)
    }

    @Test
    fun `dto de tarefa concluida carrega a data`() {
        val module = TaskModule(clock = fixedClock)
        val task = module.createTask("a", Priority.LOW).successValue()
        val done = module.completeTask(task.id).successValue().toDto()
        assertTrue(done.done)
        assertEquals("2026-01-01T10:00:00Z", done.completedAt)
    }

    @Test
    fun `ida e volta preserva a tarefa`() {
        val module = TaskModule(clock = fixedClock)
        val open = module.createTask("a", Priority.LOW).successValue()
        val done = module.completeTask(module.createTask("b", Priority.HIGH).successValue().id).successValue()
        assertEquals(open, open.toDto().toTask())
        assertEquals(done, done.toDto().toTask())
    }

    @Test
    fun `dto invalido nao vira tarefa`() {
        val valid = TaskDto("t1", "x", "LOW", done = false, createdAt = "2026-01-01T10:00:00Z")
        assertNull(valid.copy(title = " ").toTask())
        assertNull(valid.copy(priority = "???").toTask())
        assertNull(valid.copy(createdAt = "ontem").toTask())
        assertNull(valid.copy(done = true, completedAt = null).toTask())
    }
}

class DomainErrorTest {
    @Test
    fun `todo erro de dominio tem codigo e mensagem`() {
        val errors = listOf(
            DomainError.BlankTitle,
            DomainError.TitleTooLong(80),
            DomainError.TaskNotFound(TaskId("x")),
            DomainError.AlreadyCompleted(TaskId("x")),
        )
        assertEquals(errors.size, errors.map { it.code() }.toSet().size)
        assertTrue(errors.all { it.userMessage().isNotBlank() })
    }
}
