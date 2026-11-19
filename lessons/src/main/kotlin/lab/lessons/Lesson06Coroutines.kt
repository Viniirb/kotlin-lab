package lab.lessons

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withTimeout
import kotlin.system.measureTimeMillis

/**
 * LIÇÃO 6: coroutines.
 * Ideia central: concorrência ESTRUTURADA. Toda coroutine pertence a um escopo; o escopo só termina quando
 * todas as filhas terminam, e cancelar o pai cancela as filhas. Sem tarefa "perdida" por aí.
 */
object Lesson06Coroutines : Lesson {
    override val number = 6
    override val title = "Coroutines: suspend, async/await, escopo estruturado, Flow"

    // runBlocking só existe nas BORDAS (main, testes): ele bloqueia a thread até terminar.
    override fun run() = runBlocking {
        sequentialVsConcurrent()
        structuredCancellation()
        failureIsolation()
        flows()
    }

    // `suspend` = pode pausar sem bloquear a thread (delay devolve a thread para outro trabalho).
    private suspend fun fetch(name: String, millis: Long): String {
        delay(millis)
        return "dados de $name"
    }

    private suspend fun sequentialVsConcurrent() {
        section("Sequencial vs concorrente (async/await)")
        val sequential = measureTimeMillis {
            fetch("A", 200)
            fetch("B", 200)
        }
        val concurrent = measureTimeMillis {
            coroutineScope {
                val a = async { fetch("A", 200) }
                val b = async { fetch("B", 200) }
                println("${a.await()} + ${b.await()}")
            }
        }
        println("sequencial ≥ 400ms? ${sequential >= 400} | concorrente < 350ms? ${concurrent < 350}")
    }

    private suspend fun structuredCancellation() {
        section("Cancelamento e timeout")
        try {
            withTimeout(100) { fetch("lento", 1_000) }
        } catch (e: TimeoutCancellationException) {
            println("estourou o tempo: ${e.message}")
        }
        // Cancelamento é COOPERATIVO: funções suspend (delay, await...) verificam e lançam
        // CancellationException. Nunca a engula com `catch (e: Exception)` sem relançar.
    }

    private suspend fun failureIsolation() {
        section("coroutineScope vs supervisorScope")
        // coroutineScope: uma filha falha -> as irmãs são canceladas e o erro sobe.
        // supervisorScope: filhas são independentes; trate o erro de cada uma.
        supervisorScope {
            val ok = launch {
                delay(50)
                println("tarefa boa terminou")
            }
            val bad = async<String> { error("tarefa ruim falhou") }
            println("erro isolado: ${runCatching { bad.await() }.exceptionOrNull()?.message}")
            ok.join()
        }
    }

    private suspend fun flows() {
        section("Flow: fluxo assíncrono e preguiçoso de valores")
        // `flow {}` é "frio": só executa quando alguém coleta. Operadores parecem os de coleções.
        flow {
            for (i in 1..6) {
                delay(10)
                emit(i)
            }
        }
            .filter { it % 2 == 0 }
            .map { "par $it" }
            .collect { println(it) }
    }
}
