package lab.taskmanager.infrastructure

import lab.taskmanager.application.IdGenerator
import lab.taskmanager.domain.Task
import lab.taskmanager.domain.TaskId
import lab.taskmanager.domain.TaskRepository

/**
 * CAMADA INFRASTRUCTURE (Módulo 07): adaptadores concretos das portas.
 * Código comum não tem `ConcurrentHashMap` nem `AtomicInteger` (são do Java): use só a stdlib do Kotlin.
 */
class InMemoryTaskRepository : TaskRepository {
    // M07. Guarde as tarefas num mapa por TaskId (ex.: LinkedHashMap) e implemente os três métodos.
    override fun save(task: Task): Unit = TODO("M07: guarde a tarefa")

    override fun findById(id: TaskId): Task? = TODO("M07: busque por id")

    override fun findAll(): List<Task> = TODO("M07: devolva todas")
}

/** M07. Ids curtos e legíveis para digitar no terminal: "t1", "t2", "t3"... (um contador interno). */
class SequentialIdGenerator : IdGenerator {
    override fun next(): TaskId = TODO("M07: gere t1, t2, t3...")
}
