package lab.taskmanager.domain

import lab.shared.Outcome
import lab.shared.failure
import lab.shared.success
import kotlinx.datetime.Instant
import kotlin.jvm.JvmInline

/**
 * CAMADA DOMAIN: regras de negócio puras.
 * Regra de dependência: este pacote NÃO importa application, infrastructure nem presentation.
 * Não conhece banco, terminal nem framework. Por isso é a parte mais fácil de testar e a mais estável.
 */

@JvmInline
value class TaskId(val value: String) {
    override fun toString() = value
}

/** Título válido por construção: se você tem um `Title`, ele já passou na validação. */
@JvmInline
value class Title private constructor(val value: String) {
    companion object {
        const val MAX_LENGTH = 80

        fun of(raw: String): Outcome<Title, DomainError> {
            val trimmed = raw.trim()
            return when {
                trimmed.isEmpty() -> DomainError.BlankTitle.failure()
                trimmed.length > MAX_LENGTH -> DomainError.TitleTooLong(MAX_LENGTH).failure()
                else -> Title(trimmed).success()
            }
        }
    }

    override fun toString() = value
}

enum class Priority(val weight: Int) {
    LOW(1), MEDIUM(2), HIGH(3);

    companion object {
        fun parse(raw: String): Priority? = entries.firstOrNull { it.name.equals(raw.trim(), ignoreCase = true) }
    }
}

/** Estados possíveis, e só eles. `Done` carrega o dado que só existe nesse estado. */
sealed interface TaskStatus {
    data object Open : TaskStatus
    data class Done(val completedAt: Instant) : TaskStatus
}

/**
 * Entidade imutável: mudar estado = devolver uma NOVA `Task`.
 * Isso evita mutação escondida e torna o comportamento previsível (e thread-safe).
 */
data class Task(
    val id: TaskId,
    val title: Title,
    val priority: Priority,
    val createdAt: Instant,
    val status: TaskStatus = TaskStatus.Open,
) {
    val isDone: Boolean get() = status is TaskStatus.Done

    /** A regra "não se conclui duas vezes" mora AQUI, na entidade, não espalhada pelos casos de uso. */
    fun complete(at: Instant): Outcome<Task, DomainError> = when (status) {
        is TaskStatus.Done -> DomainError.AlreadyCompleted(id).failure()
        TaskStatus.Open -> copy(status = TaskStatus.Done(at)).success()
    }
}

/** Todos os erros de negócio, enumerados. Quem trata é obrigado pelo compilador a cobrir cada um. */
sealed interface DomainError {
    data object BlankTitle : DomainError
    data class TitleTooLong(val max: Int) : DomainError
    data class TaskNotFound(val id: TaskId) : DomainError
    data class AlreadyCompleted(val id: TaskId) : DomainError
}
