package lab.shared

/**
 * Resultado de uma operação que pode falhar de forma ESPERADA (regra de negócio, entrada inválida).
 *
 * Os dois tipos abaixo já vêm prontos. O que você implementa são as funções de extensão:
 *  - Módulo 05: `map`, `flatMap`, `fold`
 *  - Módulo 09: `getOrElse`, `split` (genéricos)
 *
 * Leia antes: lessons/.../Lesson04Errors.kt e a issue do módulo.
 */
sealed interface Outcome<out V, out E> {
    data class Success<out V>(val value: V) : Outcome<V, Nothing>
    data class Failure<out E>(val error: E) : Outcome<Nothing, E>
}

/** M05. Transforma o valor de sucesso; uma falha passa direto, sem chamar `transform`. */
inline fun <V, E, R> Outcome<V, E>.map(transform: (V) -> R): Outcome<R, E> = TODO("M05: implemente map")

/** M05. Encadeia outra operação que também pode falhar. Falhou antes? Para aqui e devolve a falha. */
inline fun <V, E, R> Outcome<V, E>.flatMap(transform: (V) -> Outcome<R, E>): Outcome<R, E> =
    TODO("M05: implemente flatMap")

/** M05. Colapsa os dois casos em um único valor. */
inline fun <V, E, R> Outcome<V, E>.fold(onSuccess: (V) -> R, onFailure: (E) -> R): R = TODO("M05: implemente fold")

/** M09. Valor de sucesso, ou o que `onFailure` produzir a partir do erro. */
inline fun <V, E> Outcome<V, E>.getOrElse(onFailure: (E) -> V): V = TODO("M09: implemente getOrElse")

/** M09. Separa uma lista de resultados em (todos os valores de sucesso, todos os erros), mantendo a ordem. */
fun <V, E> List<Outcome<V, E>>.split(): Pair<List<V>, List<E>> = TODO("M09: implemente split")

fun <V> V.success(): Outcome<V, Nothing> = Outcome.Success(this)

fun <E> E.failure(): Outcome<Nothing, E> = Outcome.Failure(this)
