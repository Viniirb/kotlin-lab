package lab.exercises

import lab.shared.Outcome

/**
 * EXERCÍCIOS. Implemente cada função no lugar do `TODO()`.
 * Os testes estão em src/test/kotlin/lab/exercises/ExercisesTest.kt, todos com `@Ignore`.
 * Fluxo: remova o `@Ignore` de UMA classe, rode `./gradlew test --tests "*Exercise1*"`, faça passar, avance.
 */

/** 1 (lição 1). Devolve 1..n; múltiplo de 3 vira "Fizz", de 5 vira "Buzz", de ambos "FizzBuzz". */
fun fizzBuzz(n: Int): List<String> = TODO("implemente")

/** 2 (lição 1). Primeiro valor que não é nulo nem em branco; null se não houver. */
fun firstNonBlank(vararg values: String?): String? = TODO("implemente")

/** 3 (lição 3). Frequência de palavras, ignorando maiúsculas e pontuação. Ordem: mais frequente, depois alfabética. */
fun wordFrequency(text: String): List<Pair<String, Int>> = TODO("implemente")

/** 4 (lição 2). Hierarquia fechada de formas. Complete `perimeter` com `when` SEM `else`. */
sealed interface Figure {
    data class Square(val side: Double) : Figure
    data class Rectangle(val width: Double, val height: Double) : Figure
    data class Circle(val radius: Double) : Figure
}

fun perimeter(figure: Figure): Double = TODO("implemente")

/** 5 (lição 4). Número inteiro positivo, ou um Failure com mensagem clara (não-número, zero, negativo). */
fun parsePositive(raw: String): Outcome<Int, String> = TODO("implemente")

/**
 * 6 (lição 3/4). Executa `block` até `times` vezes. Sucesso: devolve o valor. Todas falharam: devolve a ÚLTIMA falha.
 * `times` precisa ser >= 1 (use `require`).
 */
fun <T> retry(times: Int, block: () -> T): Result<T> = TODO("implemente")

/** 7 (lição 6). Aplica `transform` a cada item EM PARALELO e devolve os resultados na ordem original. */
suspend fun <A, B> parallelMap(items: List<A>, transform: suspend (A) -> B): List<B> = TODO("implemente")
