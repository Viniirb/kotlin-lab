package lab.lessons

import lab.shared.Outcome
import lab.shared.failure
import lab.shared.flatMap
import lab.shared.fold
import lab.shared.map
import lab.shared.success

/**
 * LIÇÃO 4: tratamento de erros.
 * Ideia central: erro esperado vira VALOR no tipo de retorno; exceção fica para o inesperado.
 */
object Lesson04Errors : Lesson {
    override val number = 4
    override val title = "Erros: exceções, require/check, Result, Outcome"

    override fun run() {
        exceptions()
        preconditions()
        stdlibResult()
        outcomeChain()
    }

    private fun exceptions() {
        section("try/catch é expressão; Kotlin não tem checked exceptions")
        val parsed = try {
            "abc".toInt()
        } catch (e: NumberFormatException) {
            -1
        }
        println("parsed=$parsed")
        // Para parse, prefira a variante segura que devolve null em vez de lançar:
        println("abc".toIntOrNull() ?: -1)
    }

    private fun preconditions() {
        section("require / check / error: contratos explícitos")
        // require -> valida ARGUMENTO (quem chamou errou)  -> IllegalArgumentException
        // check   -> valida ESTADO interno                  -> IllegalStateException
        // error   -> falha incondicional                    -> IllegalStateException
        println(runCatching { withdraw(balance = 10, amount = 50) }.exceptionOrNull()?.message)
        println(runCatching { withdraw(balance = 100, amount = 30) }.getOrNull())
    }

    private fun withdraw(balance: Int, amount: Int): Int {
        require(amount > 0) { "valor precisa ser positivo" }
        check(balance >= amount) { "saldo insuficiente: $balance < $amount" }
        return balance - amount
    }

    private fun stdlibResult() {
        section("kotlin.Result + runCatching: captura exceção de código que você não controla")
        val value = runCatching { riskyLegacyCall(fail = true) }
            .recover { -1 }              // transforma a falha em valor
            .getOrThrow()
        println("recuperado=$value")
        // Use runCatching nas BORDAS (I/O, bibliotecas). Cuidado: ele também engole CancellationException
        // de coroutines, então não use dentro de código suspend sem relançá-la.
    }

    private fun riskyLegacyCall(fail: Boolean): Int =
        if (fail) throw IllegalStateException("biblioteca antiga explodiu") else 1

    private fun outcomeChain() {
        section("Outcome: erro de negócio como valor, encadeado com flatMap")
        listOf("30", "abc", "200", "-3").forEach { raw ->
            val message = parseAge(raw)
                .flatMap(::ensureAdult)
                .map { "$it anos, maior de idade" }
                .fold(onSuccess = { it }, onFailure = { "erro: $it" })
            println("'$raw' -> $message")
        }
    }

    private fun parseAge(raw: String): Outcome<Int, String> {
        val number = raw.toIntOrNull() ?: return "'$raw' não é número".failure()
        if (number !in 0..150) return "idade fora do intervalo: $number".failure()
        return number.success()
    }

    private fun ensureAdult(age: Int): Outcome<Int, String> =
        if (age >= 18) age.success() else "menor de idade ($age)".failure()
}
