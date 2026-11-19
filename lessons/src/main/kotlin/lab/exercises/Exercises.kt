package lab.exercises

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import lab.shared.Outcome
import lab.shared.failure
import lab.shared.success

/**
 * EXERCÍCIOS dos módulos. Cada um tem teste em src/test/kotlin/lab/exercises/ExercisesTest.kt.
 * Rode só o do módulo em que está: `./gradlew :lessons:test --tests "*Exercise1*"`.
 */

// ── Módulo 01: Fundamentos ────────────────────────────────────────────────────────────────────

/** Exercício 1. Devolve 1..n; múltiplo de 3 vira "Fizz", de 5 vira "Buzz", de ambos "FizzBuzz". */
fun fizzBuzz(n: Int): List<String> = (1..n).map {
    when {
        it % 15 == 0 -> "FizzBuzz"
        it % 3 == 0 -> "Fizz"
        it % 5 == 0 -> "Buzz"
        else -> it.toString()
    }
}

/** Exercício 2. Primeiro valor que não é nulo nem em branco; null se não houver. */
fun firstNonBlank(vararg values: String?): String? = values.firstOrNull { !it.isNullOrBlank() }

// ── Módulo 02: Coleções ───────────────────────────────────────────────────────────────────────

/** Exercício 3. Frequência de palavras, ignorando maiúsculas e pontuação. Ordem: mais frequente, depois alfabética. */
fun wordFrequency(text: String): List<Pair<String, Int>> =
    text.lowercase()
        .split(Regex("[^\\p{L}]+"))
        .filter { it.isNotEmpty() }
        .groupingBy { it }
        .eachCount()
        .toList()
        .sortedWith(compareByDescending<Pair<String, Int>> { it.second }.thenBy { it.first })

/**
 * Exercício 3b. Agrupa palavras que são anagramas entre si, ignorando maiúsculas.
 * Cada grupo vem em ordem alfabética; os grupos, ordenados pelo primeiro elemento.
 */
fun groupAnagrams(words: List<String>): List<List<String>> =
    words.groupBy { word -> word.lowercase().toList().sorted().joinToString("") }
        .values
        .map { group -> group.sorted() }
        .sortedBy { group -> group.first() }

/** Exercício 3c. Totais acumulados: [1, 2, 3, 4] vira [1, 3, 6, 10]. */
fun runningTotal(numbers: List<Int>): List<Int> = numbers.runningFold(0) { acc, n -> acc + n }.drop(1)

// ── Módulo 04: Tipos fechados (sealed) ────────────────────────────────────────────────────────

/** Exercício 4. Hierarquia fechada de formas. Complete `perimeter` com `when` SEM `else`. */
sealed interface Figure {
    data class Square(val side: Double) : Figure
    data class Rectangle(val width: Double, val height: Double) : Figure
    data class Circle(val radius: Double) : Figure
}

fun perimeter(figure: Figure): Double = when (figure) {
    is Figure.Square -> 4 * figure.side
    is Figure.Rectangle -> 2 * (figure.width + figure.height)
    is Figure.Circle -> 2 * Math.PI * figure.radius
}

// ── Módulo 05: Erros como valor ───────────────────────────────────────────────────────────────

/** Exercício 5. Número inteiro positivo, ou um Failure com mensagem clara (não-número, zero, negativo). */
fun parsePositive(raw: String): Outcome<Int, String> {
    val number = raw.trim().toIntOrNull() ?: return "'$raw' não é um número inteiro".failure()
    if (number <= 0) return "esperava um número positivo, recebi $number".failure()
    return number.success()
}

// ── Módulo 06: Funcional ──────────────────────────────────────────────────────────────────────

/**
 * Exercício 6. Executa `block` até `times` vezes. Sucesso: devolve o valor. Todas falharam: devolve a ÚLTIMA falha.
 * `times` precisa ser >= 1 (use `require`).
 */
fun <T> retry(times: Int, block: () -> T): Result<T> {
    require(times >= 1) { "times precisa ser >= 1, recebi $times" }
    var last: Result<T> = Result.failure(IllegalStateException("nunca executou"))
    repeat(times) {
        last = runCatching(block)
        if (last.isSuccess) return last
    }
    return last
}

// ── Módulo 10: Coroutines e Flow ──────────────────────────────────────────────────────────────

/** Exercício 7. Aplica `transform` a cada item EM PARALELO e devolve os resultados na ordem original. */
suspend fun <A, B> parallelMap(items: List<A>, transform: suspend (A) -> B): List<B> = coroutineScope {
    items.map { item -> async { transform(item) } }.awaitAll()
}

/** Exercício 7b. Emite `from`, `from - 1`, ... até 1, com uma pausa de 1 ms entre os valores. `from <= 0` não emite nada. */
fun countdown(from: Int): Flow<Int> = flow {
    for (value in from downTo 1) {
        emit(value)
        delay(1)
    }
}
