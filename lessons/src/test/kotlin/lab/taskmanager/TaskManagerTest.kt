package lab.taskmanager

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import lab.taskmanager.application.CompleteTask
import lab.taskmanager.application.CreateTask
import lab.taskmanager.application.ListTasks
import lab.taskmanager.infrastructure.InMemoryTaskRepository
import lab.taskmanager.infrastructure.SequentialIdGenerator
import lab.taskmanager.presentation.Cli
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue

private val fixedClock = object : Clock {
    override fun now(): Instant = Instant.parse("2026-01-01T10:00:00Z")
}

// A lógica de domínio e casos de uso é testada em :core (commonTest). Aqui testamos só a camada de apresentação.
class CliTest {
    private fun session(vararg lines: String): String {
        val repository = InMemoryTaskRepository()
        val buffer = ByteArrayOutputStream()
        Cli(
            createTask = CreateTask(repository, SequentialIdGenerator(), fixedClock),
            completeTask = CompleteTask(repository, fixedClock),
            listTasks = ListTasks(repository),
            input = lines.joinToString("\n").reader().buffered(),
            out = PrintStream(buffer, true, "UTF-8"),
        ).run()
        return buffer.toString("UTF-8")
    }

    @Test
    fun `fluxo completo`() {
        val output = session("add high estudar Kotlin", "list", "done t1", "done t1", "list done", "quit")
        assertTrue("Criada: [ ] t1 (high) estudar Kotlin" in output, output)
        assertTrue("Concluída: [x] t1 (high) estudar Kotlin" in output, output)
        assertTrue("Erro: a tarefa 't1' já foi concluída" in output, output)
    }

    @Test
    fun `mensagens de erro de entrada`() {
        val output = session("add", "add high  ", "done", "foo", "list nada")
        assertTrue("Uso: add <low|medium|high> <título>" in output, output)
        assertTrue("Erro: o título não pode ser vazio" in output, output)
        assertTrue("Uso: done <id>" in output, output)
        assertTrue("Comando desconhecido: 'foo'" in output, output)
        assertTrue("Uso: list [open|done]" in output, output)
    }

    @Test
    fun `termina no fim da entrada`() {
        assertIs<String>(session())
    }
}
