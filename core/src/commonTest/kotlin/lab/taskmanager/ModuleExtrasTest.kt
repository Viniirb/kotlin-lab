package lab.taskmanager

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import lab.shared.Outcome
import lab.shared.failure
import lab.shared.flatMap
import lab.shared.fold
import lab.shared.getOrElse
import lab.shared.map
import lab.shared.split
import lab.shared.success
import lab.taskmanager.domain.DomainError
import lab.taskmanager.domain.Priority
import lab.taskmanager.domain.Task
import lab.taskmanager.domain.TaskId
import lab.taskmanager.domain.Title
import lab.taskmanager.domain.countByPriority
import lab.taskmanager.domain.sortedByUrgency
import lab.taskmanager.infrastructure.InMemoryTaskRepository
import lab.taskmanager.infrastructure.LoggingTaskRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

private val t0 = Instant.parse("2026-01-01T10:00:00Z")

private fun task(id: String, priority: Priority, minutes: Long = 0): Task {
    val title = (Title.of("tarefa $id") as Outcome.Success).value
    return Task(TaskId(id), title, priority, Instant.fromEpochSeconds(t0.epochSeconds + minutes * 60))
}

// ── Módulo 05 ───────────────────────────────────────────────────────────────────────────────────

class OutcomeTest {
    @Test
    fun `map transforma so o sucesso`() {
        assertEquals(Outcome.Success(4), 2.success().map { it * 2 })
        val failed: Outcome<Int, String> = "ruim".failure()
        assertEquals(Outcome.Failure("ruim"), failed.map { it * 2 })
    }

    @Test
    fun `flatMap encadeia e para na primeira falha`() {
        val ok = 2.success().flatMap { (it + 1).success() }
        assertEquals(Outcome.Success(3), ok)

        var called = false
        val failed: Outcome<Int, String> = "ruim".failure()
        val result = failed.flatMap { value ->
            called = true
            value.success()
        }
        assertEquals(Outcome.Failure("ruim"), result)
        assertTrue(!called, "a lambda não deve rodar depois de uma falha")
    }

    @Test
    fun `fold colapsa os dois casos`() {
        val ok: Outcome<Int, String> = 5.success()
        val bad: Outcome<Int, String> = "x".failure()
        assertEquals("ok 5", ok.fold(onSuccess = { "ok $it" }, onFailure = { "erro $it" }))
        assertEquals("erro x", bad.fold(onSuccess = { "ok $it" }, onFailure = { "erro $it" }))
    }
}

// ── Módulo 06 ───────────────────────────────────────────────────────────────────────────────────

class TaskExtensionsTest {
    @Test
    fun `ordena por urgencia e depois por criacao`() {
        val tasks = listOf(
            task("a", Priority.LOW, 0),
            task("b", Priority.HIGH, 5),
            task("c", Priority.HIGH, 1),
            task("d", Priority.MEDIUM, 2),
        )
        assertEquals(listOf("c", "b", "d", "a"), tasks.sortedByUrgency().map { it.id.value })
    }

    @Test
    fun `conta por prioridade inclusive zeros`() {
        val tasks = listOf(task("a", Priority.LOW), task("b", Priority.LOW), task("c", Priority.HIGH))
        assertEquals(
            mapOf(Priority.LOW to 2, Priority.MEDIUM to 0, Priority.HIGH to 1),
            tasks.countByPriority(),
        )
    }

    @Test
    fun `lista vazia conta zero em tudo`() {
        assertEquals(Priority.entries.associateWith { 0 }, emptyList<Task>().countByPriority())
    }
}

// ── Módulo 09 ───────────────────────────────────────────────────────────────────────────────────

class OutcomeGenericsTest {
    @Test
    fun `getOrElse devolve o valor ou calcula a partir do erro`() {
        val ok: Outcome<Int, String> = 7.success()
        val bad: Outcome<Int, String> = "abc".failure()
        assertEquals(7, ok.getOrElse { -1 })
        assertEquals(3, bad.getOrElse { it.length })
    }

    @Test
    fun `split separa sucessos e erros mantendo a ordem`() {
        val results: List<Outcome<Int, String>> = listOf(1.success(), "e1".failure(), 2.success(), "e2".failure())
        assertEquals(listOf(1, 2) to listOf("e1", "e2"), results.split())
    }

    @Test
    fun `split de lista vazia`() {
        assertEquals(emptyList<Int>() to emptyList<String>(), emptyList<Outcome<Int, String>>().split())
    }
}

class LoggingTaskRepositoryTest {
    private val log = mutableListOf<String>()
    private val inner = InMemoryTaskRepository()
    private val repository = LoggingTaskRepository(inner) { log += it }

    @Test
    fun `registra cada save e grava no repositorio embrulhado`() {
        val task = task("t1", Priority.LOW)
        repository.save(task)
        assertEquals(listOf("save t1"), log)
        assertEquals(task, inner.findById(TaskId("t1")))
    }

    @Test
    fun `leituras passam direto sem log`() {
        repository.save(task("t1", Priority.LOW))
        log.clear()
        assertEquals(1, repository.findAll().size)
        assertEquals("t1", repository.findById(TaskId("t1"))?.id?.value)
        assertNull(repository.findById(TaskId("zz")))
        assertTrue(log.isEmpty())
    }
}

class TaskSeedTest {
    private val module = TaskModule(clock = object : Clock {
        override fun now(): Instant = t0
    })

    @Test
    fun `cria tarefas na ordem com a prioridade do bloco`() {
        val results = module.seed {
            high("pagar conta")
            low("regar plantas")
            medium("estudar")
        }
        assertEquals(3, results.size)
        val created = results.map { (it as Outcome.Success).value }
        assertEquals(listOf("pagar conta", "regar plantas", "estudar"), created.map { it.title.value })
        assertEquals(listOf(Priority.HIGH, Priority.LOW, Priority.MEDIUM), created.map { it.priority })
    }

    @Test
    fun `titulo invalido vira falha sem derrubar as outras`() {
        val results = module.seed {
            high("ok")
            low("   ")
        }
        assertIs<Outcome.Success<Task>>(results[0])
        assertEquals(Outcome.Failure(DomainError.BlankTitle), results[1])
    }

    @Test
    fun `bloco vazio nao cria nada`() = assertTrue(module.seed { }.isEmpty())
}
