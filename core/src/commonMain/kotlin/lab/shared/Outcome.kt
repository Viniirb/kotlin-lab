package lab.shared

/**
 * Resultado de uma operação que pode falhar de forma ESPERADA (regra de negócio, entrada inválida).
 *
 * Por que não usar exceções para isso?
 *  - Exceção é para o que é excepcional (disco cheio, bug). Entrada inválida é rotina.
 *  - Com `Outcome`, a assinatura da função já diz "pode falhar, e falha assim".
 *  - O `when` sobre uma `sealed interface` é exaustivo: o compilador obriga a tratar os dois casos.
 *
 * `out V` e `out E` (covariância) deixam `Outcome<Int, Nothing>` ser usado onde se espera `Outcome<Number, Any>`.
 */
sealed interface Outcome<out V, out E> {
    data class Success<out V>(val value: V) : Outcome<V, Nothing>
    data class Failure<out E>(val error: E) : Outcome<Nothing, E>
}

/** Transforma o valor de sucesso; falha passa direto. */
inline fun <V, E, R> Outcome<V, E>.map(transform: (V) -> R): Outcome<R, E> = when (this) {
    is Outcome.Success -> Outcome.Success(transform(value))
    is Outcome.Failure -> this
}

/** Encadeia outra operação que também pode falhar. Falhou antes? Para aqui. */
inline fun <V, E, R> Outcome<V, E>.flatMap(transform: (V) -> Outcome<R, E>): Outcome<R, E> = when (this) {
    is Outcome.Success -> transform(value)
    is Outcome.Failure -> this
}

/** Colapsa os dois casos em um único valor. */
inline fun <V, E, R> Outcome<V, E>.fold(onSuccess: (V) -> R, onFailure: (E) -> R): R = when (this) {
    is Outcome.Success -> onSuccess(value)
    is Outcome.Failure -> onFailure(error)
}

/** Valor de sucesso, ou o que `onFailure` produzir a partir do erro. */
inline fun <V, E> Outcome<V, E>.getOrElse(onFailure: (E) -> V): V = when (this) {
    is Outcome.Success -> value
    is Outcome.Failure -> onFailure(error)
}

/** Separa uma lista de resultados em (todos os valores de sucesso, todos os erros), mantendo a ordem. */
fun <V, E> List<Outcome<V, E>>.split(): Pair<List<V>, List<E>> = Pair(
    mapNotNull { (it as? Outcome.Success<V>)?.value },
    mapNotNull { (it as? Outcome.Failure<E>)?.error },
)

fun <V> V.success(): Outcome<V, Nothing> = Outcome.Success(this)

fun <E> E.failure(): Outcome<Nothing, E> = Outcome.Failure(this)
