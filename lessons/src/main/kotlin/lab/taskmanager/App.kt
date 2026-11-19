package lab.taskmanager

import kotlinx.datetime.Clock
import lab.taskmanager.application.CompleteTask
import lab.taskmanager.application.CreateTask
import lab.taskmanager.application.ListTasks
import lab.taskmanager.infrastructure.InMemoryTaskRepository
import lab.taskmanager.infrastructure.SequentialIdGenerator
import lab.taskmanager.presentation.Cli

/**
 * COMPOSITION ROOT do terminal: o ÚNICO lugar que conhece todas as camadas e escolhe as implementações.
 * Injeção de dependência manual: sem framework, só construtores. (Os outros módulos usam `TaskModule`,
 * que faz essa mesma montagem de forma reaproveitável.)
 */
fun runTaskManager() {
    val repository = InMemoryTaskRepository()
    val clock = Clock.System

    Cli(
        createTask = CreateTask(repository, SequentialIdGenerator(), clock),
        completeTask = CompleteTask(repository, clock),
        listTasks = ListTasks(repository),
        input = System.`in`.bufferedReader(),
        out = System.out,
    ).run()
}
