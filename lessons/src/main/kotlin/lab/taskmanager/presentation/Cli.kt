package lab.taskmanager.presentation

import lab.shared.fold
import lab.taskmanager.userMessage
import lab.taskmanager.application.CompleteTask
import lab.taskmanager.application.CreateTask
import lab.taskmanager.application.ListTasks
import lab.taskmanager.application.TaskFilter
import lab.taskmanager.domain.DomainError
import lab.taskmanager.domain.Priority
import lab.taskmanager.domain.Task
import lab.taskmanager.domain.TaskId
import lab.taskmanager.domain.TaskStatus
import java.io.BufferedReader
import java.io.PrintStream

/**
 * CAMADA PRESENTATION: traduz texto do usuário em chamadas de caso de uso, e resultados em texto.
 * Entrada e saída são parâmetros (não `System.in/out` fixos), então o teste usa strings em vez do terminal.
 */
class Cli(
    private val createTask: CreateTask,
    private val completeTask: CompleteTask,
    private val listTasks: ListTasks,
    private val input: BufferedReader,
    private val out: PrintStream,
) {
    fun run() {
        out.println("Task Manager. Digite `help` para ver os comandos.")
        while (true) {
            out.print("> ")
            out.flush()
            val line = input.readLine() ?: break
            if (!handle(line.trim())) break
        }
        out.println("Até mais!")
    }

    /** Devolve `false` quando deve encerrar. */
    private fun handle(line: String): Boolean {
        val command = line.substringBefore(' ').lowercase()
        val args = line.substringAfter(' ', missingDelimiterValue = "").trim()
        when (command) {
            "" -> Unit
            "help" -> printHelp()
            "add" -> add(args)
            "list" -> list(args)
            "done" -> done(args)
            "quit", "exit" -> return false
            else -> out.println("Comando desconhecido: '$command'. Digite `help`.")
        }
        return true
    }

    private fun add(args: String) {
        val priorityWord = args.substringBefore(' ')
        val priority = Priority.parse(priorityWord)
        if (priority == null) {
            out.println("Uso: add <low|medium|high> <título>")
            return
        }
        createTask(args.substringAfter(' ', missingDelimiterValue = ""), priority).fold(
            onSuccess = { out.println("Criada: ${format(it)}") },
            onFailure = { out.println("Erro: ${message(it)}") },
        )
    }

    private fun list(args: String) {
        val filter = when (args.lowercase()) {
            "" -> TaskFilter.ALL
            "open" -> TaskFilter.OPEN
            "done" -> TaskFilter.DONE
            else -> {
                out.println("Uso: list [open|done]")
                return
            }
        }
        val tasks = listTasks(filter)
        if (tasks.isEmpty()) out.println("(nenhuma tarefa)") else tasks.forEach { out.println(format(it)) }
    }

    private fun done(args: String) {
        if (args.isBlank()) {
            out.println("Uso: done <id>")
            return
        }
        completeTask(TaskId(args)).fold(
            onSuccess = { out.println("Concluída: ${format(it)}") },
            onFailure = { out.println("Erro: ${message(it)}") },
        )
    }

    private fun printHelp() {
        out.println(
            """
            add <low|medium|high> <título>   cria tarefa
            list [open|done]                 lista tarefas
            done <id>                        conclui tarefa
            quit                             sai
            """.trimIndent(),
        )
    }

    private fun format(task: Task): String {
        val mark = if (task.status is TaskStatus.Done) "x" else " "
        return "[$mark] ${task.id} (${task.priority.name.lowercase()}) ${task.title}"
    }

    // A tradução de erro vive no :core (`userMessage`), compartilhada com API e telas. Sem duplicação.
    private fun message(error: DomainError): String = error.userMessage()
}
