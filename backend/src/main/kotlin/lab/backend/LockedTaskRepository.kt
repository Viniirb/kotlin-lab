package lab.backend

import lab.taskmanager.domain.Task
import lab.taskmanager.domain.TaskId
import lab.taskmanager.domain.TaskRepository

/**
 * M12. Netty atende requisições em várias threads, mas o `InMemoryTaskRepository` do :core não é thread-safe.
 * Decorator: embrulha qualquer repositório e serializa o acesso com `synchronized(lock)`.
 *
 * Limitação para você pensar: cada método é atômico, mas "ler e depois salvar" em dois passos
 * (como `CompleteTask`) ainda pode intercalar. Como resolver? (Desafio extra do módulo.)
 */
class LockedTaskRepository(private val delegate: TaskRepository) : TaskRepository {
    override fun save(task: Task): Unit = TODO("M12: sincronize e delegue")

    override fun findById(id: TaskId): Task? = TODO("M12: sincronize e delegue")

    override fun findAll(): List<Task> = TODO("M12: sincronize e delegue")
}
