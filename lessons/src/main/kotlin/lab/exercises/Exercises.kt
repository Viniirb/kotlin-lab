package lab.exercises

import kotlinx.coroutines.flow.Flow
import lab.shared.Outcome

/**
 * EXERCÍCIOS dos módulos. Implemente cada função no lugar do `TODO()`.
 * Cada um tem teste em src/test/kotlin/lab/exercises/ExercisesTest.kt (NÃO altere os testes).
 * Rode só o do módulo em que está: `./gradlew :lessons:test --tests "*Exercise1*"`.
 */

// ── Módulo 01: Fundamentos ────────────────────────────────────────────────────────────────────

/** Exercício 1. Devolve 1..n; múltiplo de 3 vira "Fizz", de 5 vira "Buzz", de ambos "FizzBuzz". */
fun fizzBuzz(n: Int): List<String> = TODO("M01: implemente")

/** Exercício 2. Primeiro valor que não é nulo nem em branco; null se não houver. */
fun firstNonBlank(vararg values: String?): String? = TODO("M01: implemente")

// ── Módulo 02: Coleções ───────────────────────────────────────────────────────────────────────

/** Exercício 3. Frequência de palavras, ignorando maiúsculas e pontuação. Ordem: mais frequente, depois alfabética. */
fun wordFrequency(text: String): List<Pair<String, Int>> = TODO("M02: implemente")

/**
 * Exercício 3b. Agrupa palavras que são anagramas entre si, ignorando maiúsculas.
 * Cada grupo vem em ordem alfabética; os grupos, ordenados pelo primeiro elemento.
 */
fun groupAnagrams(words: List<String>): List<List<String>> = TODO("M02: implemente")

/** Exercício 3c. Totais acumulados: [1, 2, 3, 4] vira [1, 3, 6, 10]. */
fun runningTotal(numbers: List<Int>): List<Int> = TODO("M02: implemente")

// ── Módulo 04: Tipos fechados (sealed) ────────────────────────────────────────────────────────

/** Exercício 4. Hierarquia fechada de formas. Complete `perimeter` com `when` SEM `else`. */
sealed interface Figure {
    data class Square(val side: Double) : Figure
    data class Rectangle(val width: Double, val height: Double) : Figure
    data class Circle(val radius: Double) : Figure
}

fun perimeter(figure: Figure): Double = TODO("M04: implemente")

// ── Módulo 05: Erros como valor ───────────────────────────────────────────────────────────────

/** Exercício 5. Número inteiro positivo, ou um Failure com mensagem clara (não-número, zero, negativo). */
fun parsePositive(raw: String): Outcome<Int, String> = TODO("M05: implemente")

// ── Módulo 06: Funcional ──────────────────────────────────────────────────────────────────────

/**
 * Exercício 6. Executa `block` até `times` vezes. Sucesso: devolve o valor. Todas falharam: devolve a ÚLTIMA falha.
 * `times` precisa ser >= 1 (use `require`).
 */
fun <T> retry(times: Int, block: () -> T): Result<T> = TODO("M06: implemente")

// ── Módulo 10: Coroutines e Flow ──────────────────────────────────────────────────────────────

/** Exercício 7. Aplica `transform` a cada item EM PARALELO e devolve os resultados na ordem original. */
suspend fun <A, B> parallelMap(items: List<A>, transform: suspend (A) -> B): List<B> = TODO("M10: implemente")

/** Exercício 7b. Emite `from`, `from - 1`, ... até 1, com uma pausa de 1 ms entre os valores. `from <= 0` não emite nada. */
fun countdown(from: Int): Flow<Int> = TODO("M10: implemente")
