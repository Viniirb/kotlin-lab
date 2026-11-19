package lab.taskmanager.infrastructure

import lab.taskmanager.application.IdGenerator
import lab.taskmanager.domain.Task
import lab.taskmanager.domain.TaskId
import lab.taskmanager.domain.TaskRepository

/**
 * CAMADA INFRASTRUCTURE: adaptadores concretos das portas. Trocar por um banco de verdade = escrever
 * outra implementação de `TaskRepository`; domínio e casos de uso não mudam uma linha (OCP).
 *
 * Código comum (multiplataforma) não tem `ConcurrentHashMap` nem `AtomicInteger`: esses são do Java.
 * Aqui usamos só a stdlib do Kotlin, SEM garantia de thread-safety. Quem precisa de concorrência
 * (o backend) embrulha o repositório (ver `LockedTaskRepository` no módulo :backend).
 */
class InMemoryTaskRepository : TaskRepository {
    private val store = LinkedHashMap<TaskId, Task>()

    override fun save(task: Task) {
        store[task.id] = task
    }

    override fun findById(id: TaskId): Task? = store[id]

    override fun findAll(): List<Task> = store.values.toList()
}

/** Ids curtos e legíveis para digitar no terminal: t1, t2, t3... */
class SequentialIdGenerator : IdGenerator {
    private var counter = 0

    override fun next(): TaskId {
        counter += 1
        return TaskId("t$counter")
    }
}
