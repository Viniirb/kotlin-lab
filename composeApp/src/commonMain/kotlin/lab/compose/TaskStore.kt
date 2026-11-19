package lab.compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import lab.shared.fold
import lab.taskmanager.TaskModule
import lab.taskmanager.application.TaskFilter
import lab.taskmanager.domain.Priority
import lab.taskmanager.domain.Task
import lab.taskmanager.domain.TaskId
import lab.taskmanager.userMessage

/**
 * Estado da tela, separado da UI (padrão "state holder"). A UI só LÊ estas propriedades e CHAMA estas funções.
 * Por não depender de widgets, é testável sem emulador (ver TaskStoreTest).
 *
 * `by mutableStateOf`: quando o valor muda, o Compose redesenha só o que o lê (recomposição).
 */
class TaskStore(private val module: TaskModule) {
    var tasks by mutableStateOf<List<Task>>(emptyList())
        private set
    var filter by mutableStateOf(TaskFilter.ALL)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    init {
        refresh()
    }

    fun add(title: String, priority: Priority) {
        module.createTask(title, priority).fold(
            onSuccess = { error = null },
            onFailure = { error = it.userMessage() },
        )
        refresh()
    }

    fun complete(id: TaskId) {
        module.completeTask(id).fold(
            onSuccess = { error = null },
            onFailure = { error = it.userMessage() },
        )
        refresh()
    }

    fun select(filter: TaskFilter) {
        this.filter = filter
        refresh()
    }

    private fun refresh() {
        tasks = module.listTasks(filter)
    }
}
