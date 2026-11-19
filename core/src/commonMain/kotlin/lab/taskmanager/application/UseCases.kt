package lab.taskmanager.application

import kotlinx.datetime.Clock
import lab.shared.Outcome
import lab.taskmanager.domain.DomainError
import lab.taskmanager.domain.Priority
import lab.taskmanager.domain.Task
import lab.taskmanager.domain.TaskId
import lab.taskmanager.domain.TaskRepository

/**
 * CAMADA APPLICATION (Módulo 07): orquestra o domínio para cumprir uma intenção do usuário.
 * Um caso de uso = uma classe com uma responsabilidade. Não tem regra de negócio própria:
 * só coordena entidades e portas. Tempo (`Clock`) e id (`IdGenerator`) entram pelo construtor.
 *
 * Leia antes: docs/ARQUITETURA.md e docs/CLEAN_CODE.md (seção 5, SOLID).
 */

/** `fun interface` permite criar a implementação com lambda: `IdGenerator { TaskId("x") }`. */
fun interface IdGenerator {
    fun next(): TaskId
}

class CreateTask(
    private val repository: TaskRepository,
    private val ids: IdGenerator,
    private val clock: Clock,
) {
    /**
     * M07. Valida o título (`Title.of`), cria a `Task` com id novo e `clock.now()`, SALVA e devolve.
     * Título inválido: devolve a falha e NÃO salva nada. Dica: `map` de Outcome + `also`.
     */
    operator fun invoke(rawTitle: String, priority: Priority): Outcome<Task, DomainError> =
        TODO("M07: crie e salve a tarefa")
}

class CompleteTask(
    private val repository: TaskRepository,
    private val clock: Clock,
) {
    /**
     * M07. Busca a tarefa (não achou: Failure(TaskNotFound(id))), conclui com `clock.now()` via `Task.complete`,
     * SALVA a nova versão e devolve.
     */
    operator fun invoke(id: TaskId): Outcome<Task, DomainError> = TODO("M07: conclua e salve")
}

enum class TaskFilter { ALL, OPEN, DONE }

class ListTasks(private val repository: TaskRepository) {
    /** M07. Aplica o filtro e devolve ordenado por urgência (use sua extensão do módulo 06). */
    operator fun invoke(filter: TaskFilter = TaskFilter.ALL): List<Task> = TODO("M07: filtre e ordene")
}
