package lab.taskmanager.infrastructure

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import lab.taskmanager.TaskDto
import lab.taskmanager.domain.Task
import lab.taskmanager.domain.TaskId
import lab.taskmanager.domain.TaskRepository
import lab.taskmanager.toDto
import lab.taskmanager.toTask
import java.io.File

/**
 * Persiste as tarefas num arquivo JSON. Só JVM (usa `java.io.File`), por isso mora em `jvmMain`.
 * Outra implementação da MESMA porta: domínio e casos de uso não sabem a diferença.
 */
class FileTaskRepository(private val file: File) : TaskRepository {
    private val json = Json { prettyPrint = true }
    private val serializer = ListSerializer(TaskDto.serializer())

    override fun save(task: Task) {
        val current = findAll()
        val updated = if (current.any { it.id == task.id }) {
            current.map { if (it.id == task.id) task else it }
        } else {
            current + task
        }
        file.writeText(json.encodeToString(serializer, updated.map { it.toDto() }))
    }

    override fun findById(id: TaskId): Task? = findAll().firstOrNull { it.id == id }

    override fun findAll(): List<Task> {
        if (!file.exists()) return emptyList()
        return json.decodeFromString(serializer, file.readText()).mapNotNull { it.toTask() }
    }
}
