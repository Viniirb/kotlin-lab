package lab.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import lab.taskmanager.TaskModule

/**
 * M14. A UI inteira, escrita UMA vez. Roda em Android, iOS, Desktop e Web.
 * Compose é declarativo: a tela é uma FUNÇÃO do estado. Mudou o estado, a função roda de novo.
 *
 * Monte uma tela com (use `MaterialTheme` > `Surface` > `Column`):
 *  - título "Task Manager" e `Text("Rodando em: ${platformName()}")`
 *  - formulário: `OutlinedTextField` (título), chips de prioridade (`FilterChip`), botão "Adicionar"
 *    (mostre `store.error` como `supportingText` e use `isError`)
 *  - chips de filtro (`TaskFilter.entries`)
 *  - `LazyColumn` com um `Card` por tarefa (título, id, prioridade) e botão "Concluir" nas abertas
 * Leia antes: docs/PLATAFORMAS.md e a issue do módulo. O estado vem do `TaskStore`.
 */
@Composable
fun App(store: TaskStore = remember { TaskStore(TaskModule()) }) {
    TODO("M14: monte a tela")
}
