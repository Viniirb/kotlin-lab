package lab.backend

import lab.taskmanager.domain.Task
import lab.taskmanager.domain.TaskId
import lab.taskmanager.domain.TaskRepository

/**
 * Netty atende requisições em várias threads, mas o `InMemoryTaskRepository` do :core não é thread-safe.
 * Decorator: embrulha qualquer repositório e serializa o acesso com `synchronized`.
 *
 * Limitação conhecida (de propósito, para você pensar): cada método é atômico, mas "ler e depois salvar"
 * em dois passos (como `CompleteTask`) ainda pode intercalar. Com banco real, resolva com transação.
 * Desafio: como você tornaria `CompleteTask` atômico?
 */
class LockedTaskRepository(private val delegate: TaskRepository) : TaskRepository {
    private val lock = Any()

    override fun save(task: Task) = synchronized(lock) { delegate.save(task) }

    override fun findById(id: TaskId): Task? = synchronized(lock) { delegate.findById(id) }

    override fun findAll(): List<Task> = synchronized(lock) { delegate.findAll() }
}
