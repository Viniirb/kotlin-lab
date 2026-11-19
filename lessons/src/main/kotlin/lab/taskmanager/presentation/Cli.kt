package lab.taskmanager.presentation

import lab.taskmanager.application.CompleteTask
import lab.taskmanager.application.CreateTask
import lab.taskmanager.application.ListTasks
import java.io.BufferedReader
import java.io.PrintStream

/**
 * M08. CAMADA PRESENTATION: traduz texto do usuário em chamadas de caso de uso, e resultados em texto.
 * Entrada e saída são parâmetros (não `System.in/out`), então o teste usa strings em vez do terminal.
 *
 * Contrato (veja o CliTest, que é a especificação exata dos textos):
 *   Abertura: "Task Manager. Digite `help` para ver os comandos."
 *   Prompt:   "> " (sem quebra de linha, depois flush) antes de ler cada linha
 *   Comandos: help | add <low|medium|high> <título> | list [open|done] | done <id> | quit/exit
 *   Fim da entrada (readLine == null) ou quit: imprime "Até mais!"
 *   Formato de tarefa: "[ ] t1 (high) estudar Kotlin"  (ou "[x]" quando concluída)
 *   Sucesso: "Criada: <tarefa>" / "Concluída: <tarefa>"; erro de domínio: "Erro: <userMessage>"
 *   Uso incorreto: "Uso: add <low|medium|high> <título>", "Uso: done <id>", "Uso: list [open|done]"
 *   Desconhecido: "Comando desconhecido: '<cmd>'. Digite `help`."   Lista vazia: "(nenhuma tarefa)"
 *
 * Dica de design: um `when (command)` que despacha para funções pequenas, uma por comando.
 */
class Cli(
    private val createTask: CreateTask,
    private val completeTask: CompleteTask,
    private val listTasks: ListTasks,
    private val input: BufferedReader,
    private val out: PrintStream,
) {
    fun run(): Unit = TODO("M08: implemente o laço de comandos")
}
