package lab.taskmanager.application

import lab.shared.Outcome
import lab.shared.failure
import lab.shared.map
import lab.taskmanager.domain.DomainError
import lab.taskmanager.domain.Priority
import lab.taskmanager.domain.Task
import lab.taskmanager.domain.TaskId
import lab.taskmanager.domain.TaskRepository
import lab.taskmanager.domain.Title
import kotlinx.datetime.Clock

/**
 * CAMADA APPLICATION: orquestra o domínio para cumprir uma intenção do usuário. Um caso de uso = uma classe
 * com uma responsabilidade (SRP). Não tem regra de negócio própria, só coordena entidades e portas.
 *
 * Dependências entram pelo construtor (injeção manual). Nada de `Clock.System` ou `Uuid.random()` aqui
 * dentro: tempo e id são portas, então os testes controlam os dois.
 */

/** `fun interface` permite criar a implementação com lambda: `IdGenerator { "x" }`. */
fun interface IdGenerator {
    fun next(): TaskId
}

class CreateTask(
    private val repository: TaskRepository,
    private val ids: IdGenerator,
    private val clock: Clock,
) {
    // `operator fun invoke` deixa chamar como função: `createTask("título", Priority.HIGH)`.
    operator fun invoke(rawTitle: String, priority: Priority): Outcome<Task, DomainError> =
        Title.of(rawTitle).map { title ->
            Task(id = ids.next(), title = title, priority = priority, createdAt = clock.now())
                .also(repository::save)
        }
}

class CompleteTask(
    private val repository: TaskRepository,
    private val clock: Clock,
) {
    operator fun invoke(id: TaskId): Outcome<Task, DomainError> {
        val task = repository.findById(id) ?: return DomainError.TaskNotFound(id).failure()
        return task.complete(clock.now()).map { done -> done.also(repository::save) }
    }
}

enum class TaskFilter { ALL, OPEN, DONE }

class ListTasks(private val repository: TaskRepository) {
    operator fun invoke(filter: TaskFilter = TaskFilter.ALL): List<Task> =
        repository.findAll()
            .filter {
                when (filter) {
                    TaskFilter.ALL -> true
                    TaskFilter.OPEN -> !it.isDone
                    TaskFilter.DONE -> it.isDone
                }
            }
            // Mais urgente primeiro; empate resolve pela ordem de criação.
            .sortedWith(compareByDescending<Task> { it.priority.weight }.thenBy { it.createdAt })
}
