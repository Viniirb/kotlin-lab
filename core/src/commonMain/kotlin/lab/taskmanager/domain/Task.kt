package lab.taskmanager.domain

import kotlinx.datetime.Instant
import lab.shared.Outcome
import kotlin.jvm.JvmInline

/**
 * CAMADA DOMAIN: regras de negócio puras. Não importa application, infrastructure nem presentation.
 *
 * Os TIPOS já estão declarados. Você implementa os comportamentos marcados com TODO:
 *  - Módulo 03: `Title.of`, `Priority.weight`, `Priority.parse`, `Task.isDone`
 *  - Módulo 04: `Task.complete` (e o tratamento de erros em TaskContract.kt)
 *
 * Leia antes: lessons/.../Lesson02Types.kt e docs/CLEAN_CODE.md (seções 1 e 2).
 */

@JvmInline
value class TaskId(val value: String) {
    override fun toString() = value
}

/**
 * Título válido por construção: se você tem um `Title`, ele já passou na validação.
 * Por isso o construtor é privado e a única porta de entrada é `Title.of`.
 */
@JvmInline
value class Title private constructor(val value: String) {
    companion object {
        const val MAX_LENGTH = 80

        /**
         * M03. Apara os espaços e valida:
         *  - vazio depois de aparar -> Failure(DomainError.BlankTitle)
         *  - mais de MAX_LENGTH caracteres -> Failure(DomainError.TitleTooLong(MAX_LENGTH))
         *  - senão Success(Title)
         * Dica: o construtor privado é acessível aqui dentro do companion.
         */
        fun of(raw: String): Outcome<Title, DomainError> = TODO("M03: valide e crie o Title")
    }

    override fun toString() = value
}

enum class Priority {
    LOW, MEDIUM, HIGH;

    /** M03. LOW = 1, MEDIUM = 2, HIGH = 3. Quanto maior, mais urgente. */
    val weight: Int get() = TODO("M03: devolva o peso")

    companion object {
        /** M03. Converte texto em Priority ignorando caixa e espaços nas pontas. Desconhecido vira null. */
        fun parse(raw: String): Priority? = TODO("M03: converta o texto")
    }
}

/** Estados possíveis, e só eles. `Done` carrega o dado que só existe nesse estado. */
sealed interface TaskStatus {
    data object Open : TaskStatus
    data class Done(val completedAt: Instant) : TaskStatus
}

/**
 * Entidade imutável: mudar estado = devolver uma NOVA `Task` (use `copy`).
 */
data class Task(
    val id: TaskId,
    val title: Title,
    val priority: Priority,
    val createdAt: Instant,
    val status: TaskStatus = TaskStatus.Open,
) {
    /** M03. Verdadeiro quando o status é `Done`. */
    val isDone: Boolean get() = TODO("M03: derive do status")

    /**
     * M04. A regra "não se conclui duas vezes" mora AQUI, na entidade.
     *  - já concluída -> Failure(DomainError.AlreadyCompleted(id))
     *  - aberta -> Success(nova Task com status Done(at)); a original NÃO muda
     * Use `when (status)` sem `else`. Ajudam: `success()` e `failure()` de lab.shared.
     */
    fun complete(at: Instant): Outcome<Task, DomainError> = TODO("M04: conclua a tarefa")
}

/** Todos os erros de negócio, enumerados. Quem trata é obrigado pelo compilador a cobrir cada um. */
sealed interface DomainError {
    data object BlankTitle : DomainError
    data class TitleTooLong(val max: Int) : DomainError
    data class TaskNotFound(val id: TaskId) : DomainError
    data class AlreadyCompleted(val id: TaskId) : DomainError
}
