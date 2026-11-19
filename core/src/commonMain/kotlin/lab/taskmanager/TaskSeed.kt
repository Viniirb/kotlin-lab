package lab.taskmanager

import lab.shared.Outcome
import lab.taskmanager.domain.DomainError
import lab.taskmanager.domain.Priority
import lab.taskmanager.domain.Task

/**
 * DSL: lambda com receptor. Uso:
 *
 *     module.seed {
 *         high("pagar conta")
 *         low("regar plantas")
 *     }
 */
class TaskSeed {
    internal val entries = mutableListOf<Pair<String, Priority>>()

    fun low(title: String) {
        entries += title to Priority.LOW
    }

    fun medium(title: String) {
        entries += title to Priority.MEDIUM
    }

    fun high(title: String) {
        entries += title to Priority.HIGH
    }
}

/** Cria todas as tarefas descritas no bloco, na ordem, e devolve o resultado de cada uma. */
fun TaskModule.seed(build: TaskSeed.() -> Unit): List<Outcome<Task, DomainError>> =
    TaskSeed().apply(build).entries.map { (title, priority) -> createTask(title, priority) }
