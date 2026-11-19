package lab.taskmanager

import kotlinx.datetime.Instant
import lab.core.platformName
import lab.shared.Outcome
import lab.taskmanager.domain.Priority
import lab.taskmanager.domain.Task
import lab.taskmanager.domain.TaskId
import lab.taskmanager.domain.TaskStatus
import lab.taskmanager.domain.Title
import lab.taskmanager.infrastructure.FileTaskRepository
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

// Módulo 13
class PlatformTest {
    @Test
    fun `jvm informa o proprio nome`() {
        assertTrue(platformName().startsWith("JVM"), platformName())
    }
}

// Módulo 11
class FileTaskRepositoryTest {
    private val file = File.createTempFile("tasks", ".json").also {
        it.delete() // começa sem arquivo
        it.deleteOnExit()
    }
    private val created = Instant.parse("2026-01-01T10:00:00Z")

    private fun task(id: String, status: TaskStatus = TaskStatus.Open) =
        Task(TaskId(id), (Title.of("tarefa $id") as Outcome.Success).value, Priority.MEDIUM, created, status)

    @Test
    fun `sem arquivo a lista e vazia`() {
        assertTrue(FileTaskRepository(file).findAll().isEmpty())
    }

    @Test
    fun `salva e relê de outra instancia`() {
        FileTaskRepository(file).save(task("t1"))
        FileTaskRepository(file).save(task("t2", TaskStatus.Done(created)))

        val reloaded = FileTaskRepository(file)
        assertEquals(listOf("t1", "t2"), reloaded.findAll().map { it.id.value })
        assertEquals(TaskStatus.Done(created), reloaded.findById(TaskId("t2"))?.status)
        assertNull(reloaded.findById(TaskId("zz")))
    }

    @Test
    fun `salvar de novo substitui sem duplicar`() {
        val repository = FileTaskRepository(file)
        repository.save(task("t1"))
        repository.save(task("t1", TaskStatus.Done(created)))

        assertEquals(1, repository.findAll().size)
        assertTrue(repository.findById(TaskId("t1"))!!.isDone)
    }
}
