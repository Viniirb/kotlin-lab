package lab.compose

import lab.taskmanager.TaskModule
import lab.taskmanager.application.TaskFilter
import lab.taskmanager.domain.Priority
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

// Estado da tela testado SEM emulador, SEM UI: só lógica.
class TaskStoreTest {
    private val store = TaskStore(TaskModule())

    @Test
    fun `adicionar atualiza a lista`() {
        store.add("estudar", Priority.HIGH)
        assertEquals(listOf("estudar"), store.tasks.map { it.title.value })
        assertNull(store.error)
    }

    @Test
    fun `titulo vazio vira mensagem de erro`() {
        store.add("  ", Priority.LOW)
        assertEquals("o título não pode ser vazio", store.error)
        assertEquals(emptyList(), store.tasks)
    }

    @Test
    fun `concluir e filtrar`() {
        store.add("a", Priority.LOW)
        store.add("b", Priority.LOW)
        store.complete(store.tasks.first().id)

        store.select(TaskFilter.OPEN)
        assertEquals(1, store.tasks.size)
        store.select(TaskFilter.DONE)
        assertEquals(1, store.tasks.size)
    }
}
