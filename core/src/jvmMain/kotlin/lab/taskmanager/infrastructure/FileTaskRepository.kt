package lab.taskmanager.infrastructure

import lab.taskmanager.domain.Task
import lab.taskmanager.domain.TaskId
import lab.taskmanager.domain.TaskRepository
import java.io.File

/**
 * M11. Persiste as tarefas num arquivo JSON (só JVM, por isso mora em `jvmMain`).
 *  - `save`: lê tudo, troca a tarefa de mesmo id (ou acrescenta no fim) e reescreve o arquivo.
 *  - `findAll`: arquivo inexistente = lista vazia; senão lê o JSON, e descarta o que `toTask()` rejeitar.
 *  - `findById`: derive de `findAll`.
 * Dicas: `Json { prettyPrint = true }`, `ListSerializer(TaskDto.serializer())`, `Task.toDto()`, `TaskDto.toTask()`.
 */
class FileTaskRepository(private val file: File) : TaskRepository {
    override fun save(task: Task): Unit = TODO("M11: grave no arquivo")

    override fun findById(id: TaskId): Task? = TODO("M11: busque por id")

    override fun findAll(): List<Task> = TODO("M11: leia o arquivo")
}
