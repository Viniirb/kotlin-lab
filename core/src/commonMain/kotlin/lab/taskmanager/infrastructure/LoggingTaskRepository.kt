package lab.taskmanager.infrastructure

import lab.taskmanager.domain.Task
import lab.taskmanager.domain.TaskId
import lab.taskmanager.domain.TaskRepository

/**
 * Decorator por DELEGAÇÃO: `by delegate` gera os métodos que não mudam; sobrescrevemos só o que importa.
 * Registra no `log` cada `save`, sem alterar o comportamento do repositório embrulhado.
 */
class LoggingTaskRepository(
    private val delegate: TaskRepository,
    private val log: (String) -> Unit,
) : TaskRepository by delegate {
    override fun save(task: Task) {
        log("save ${task.id}")
        delegate.save(task)
    }
}
