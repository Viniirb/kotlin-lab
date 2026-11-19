package lab.taskmanager

import kotlinx.datetime.Clock
import lab.taskmanager.application.CompleteTask
import lab.taskmanager.application.CreateTask
import lab.taskmanager.application.IdGenerator
import lab.taskmanager.application.ListTasks
import lab.taskmanager.domain.TaskRepository
import lab.taskmanager.infrastructure.InMemoryTaskRepository
import lab.taskmanager.infrastructure.SequentialIdGenerator

/**
 * Monta os casos de uso com suas dependências. Cada plataforma (backend, Android, desktop, iOS, web)
 * cria UM `TaskModule` e reaproveita exatamente as mesmas regras de negócio.
 * Os parâmetros têm valor padrão: produção usa o padrão, testes injetam fakes.
 */
class TaskModule(
    repository: TaskRepository = InMemoryTaskRepository(),
    ids: IdGenerator = SequentialIdGenerator(),
    clock: Clock = Clock.System,
) {
    val createTask = CreateTask(repository, ids, clock)
    val completeTask = CompleteTask(repository, clock)
    val listTasks = ListTasks(repository)
}
