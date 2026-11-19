package lab.taskmanager.domain

/**
 * PORTA (princípio da inversão de dependência): o domínio DEFINE o contrato de que precisa;
 * a infraestrutura IMPLEMENTA. A seta de dependência aponta para dentro, nunca para fora.
 */
interface TaskRepository {
    fun save(task: Task)
    fun findById(id: TaskId): Task?
    fun findAll(): List<Task>
}
