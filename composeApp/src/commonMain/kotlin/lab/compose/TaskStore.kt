package lab.compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import lab.taskmanager.TaskModule
import lab.taskmanager.application.TaskFilter
import lab.taskmanager.domain.Priority
import lab.taskmanager.domain.Task
import lab.taskmanager.domain.TaskId

/**
 * M14. Estado da tela, separado da UI (padrão "state holder"). A UI só LÊ estas propriedades e CHAMA estas funções.
 * Por não depender de widgets, é testável sem emulador (veja o TaskStoreTest, que é a especificação).
 *
 * `var x by mutableStateOf(...)`: quando o valor muda, o Compose redesenha só o que o lê (recomposição).
 *
 * Comportamento:
 *  - `tasks`: lista atual, já filtrada por `filter` (inicialmente carregada no `init`)
 *  - `add`: cria via `module.createTask`; sucesso limpa `error`; falha põe `error = userMessage()`; sempre atualiza a lista
 *  - `complete`: igual, com `module.completeTask`
 *  - `select`: troca o filtro e atualiza a lista
 */
class TaskStore(private val module: TaskModule) {
    var tasks by mutableStateOf<List<Task>>(emptyList())
        private set
    var filter by mutableStateOf(TaskFilter.ALL)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    fun add(title: String, priority: Priority): Unit = TODO("M14: crie a tarefa e atualize o estado")

    fun complete(id: TaskId): Unit = TODO("M14: conclua a tarefa e atualize o estado")

    fun select(filter: TaskFilter): Unit = TODO("M14: troque o filtro e atualize a lista")
}
