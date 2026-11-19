package lab.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import lab.core.platformName
import lab.taskmanager.TaskModule
import lab.taskmanager.application.TaskFilter
import lab.taskmanager.domain.Priority
import lab.taskmanager.domain.Task

/**
 * A UI inteira, escrita UMA vez. Roda em Android, iOS, Desktop e Web.
 * Compose é declarativo: a tela é uma FUNÇÃO do estado. Mudou o estado, a função roda de novo.
 */
@Composable
fun App(store: TaskStore = remember { TaskStore(TaskModule()) }) {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text("Task Manager", style = MaterialTheme.typography.headlineMedium)
                // Código comum chamando `expect fun platformName()`: cada plataforma responde diferente.
                Text("Rodando em: ${platformName()}", style = MaterialTheme.typography.bodySmall)

                NewTaskForm(onAdd = store::add, error = store.error)
                FilterRow(selected = store.filter, onSelect = store::select)
                TaskList(tasks = store.tasks, onComplete = { store.complete(it.id) })
            }
        }
    }
}

@Composable
private fun NewTaskForm(onAdd: (String, Priority) -> Unit, error: String?) {
    // `remember` guarda estado local entre recomposições; `by` desembrulha o valor.
    var title by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(Priority.MEDIUM) }

    OutlinedTextField(
        value = title,
        onValueChange = { title = it },
        label = { Text("Nova tarefa") },
        isError = error != null,
        supportingText = { error?.let { Text(it) } },
        modifier = Modifier.fillMaxWidth(),
    )
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        Priority.entries.forEach { option ->
            FilterChip(
                selected = priority == option,
                onClick = { priority = option },
                label = { Text(option.name.lowercase()) },
            )
        }
        Button(onClick = {
            onAdd(title, priority)
            title = ""
        }) { Text("Adicionar") }
    }
}

@Composable
private fun FilterRow(selected: TaskFilter, onSelect: (TaskFilter) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        TaskFilter.entries.forEach { filter ->
            FilterChip(
                selected = selected == filter,
                onClick = { onSelect(filter) },
                label = { Text(filter.name.lowercase()) },
            )
        }
    }
}

@Composable
private fun TaskList(tasks: List<Task>, onComplete: (Task) -> Unit) {
    if (tasks.isEmpty()) {
        Text("Nenhuma tarefa.")
        return
    }
    // LazyColumn só compõe o que está visível: listas grandes continuam rápidas.
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(tasks, key = { it.id.value }) { task ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text(task.title.value, style = MaterialTheme.typography.titleMedium)
                        Text(
                            "${task.id} · ${task.priority.name.lowercase()}" + if (task.isDone) " · concluída" else "",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                    if (!task.isDone) TextButton(onClick = { onComplete(task) }) { Text("Concluir") }
                }
            }
        }
    }
}
