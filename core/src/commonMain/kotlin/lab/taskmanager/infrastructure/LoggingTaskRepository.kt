package lab.taskmanager.infrastructure

import lab.taskmanager.domain.Task
import lab.taskmanager.domain.TaskId
import lab.taskmanager.domain.TaskRepository

/**
 * M09. Decorator por DELEGAÇÃO. Registra no `log` cada `save` ("save t1") e grava no repositório embrulhado.
 * `findById` e `findAll` passam direto, sem log.
 *
 * Desafio do módulo: faça isso com `: TaskRepository by delegate` e sobrescreva SÓ o `save`.
 * (Leia antes: Lesson05Generics.kt, seção "Delegação".)
 */
class LoggingTaskRepository(
    private val delegate: TaskRepository,
    private val log: (String) -> Unit,
) : TaskRepository {
    override fun save(task: Task): Unit = TODO("M09: registre e delegue")

    override fun findById(id: TaskId): Task? = TODO("M09: delegue")

    override fun findAll(): List<Task> = TODO("M09: delegue")
}
