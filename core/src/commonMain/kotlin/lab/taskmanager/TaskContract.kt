package lab.taskmanager

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import lab.shared.Outcome
import lab.taskmanager.domain.DomainError
import lab.taskmanager.domain.Priority
import lab.taskmanager.domain.Task
import lab.taskmanager.domain.TaskId
import lab.taskmanager.domain.TaskStatus
import lab.taskmanager.domain.Title

/**
 * CONTRATO compartilhado. Backend e clientes usam as MESMAS classes: o JSON que o servidor produz é
 * exatamente o que o cliente espera, e mudar um campo quebra a compilação dos dois lados (não em produção).
 * `@Serializable` gera, em tempo de compilação, o código de (de)serialização para todas as plataformas.
 */
@Serializable
data class TaskDto(
    val id: String,
    val title: String,
    val priority: String,
    val done: Boolean,
    val createdAt: String,
    val completedAt: String? = null,
)

@Serializable
data class CreateTaskRequest(val title: String, val priority: String = "MEDIUM")

@Serializable
data class ErrorResponse(val code: String, val message: String)

fun Task.toDto(): TaskDto = TaskDto(
    id = id.value,
    title = title.value,
    priority = priority.name,
    done = isDone,
    createdAt = createdAt.toString(),
    completedAt = (status as? TaskStatus.Done)?.completedAt?.toString(),
)

/** Caminho de volta: DTO para domínio. `null` se algum campo não passar nas regras (JSON adulterado, por exemplo). */
fun TaskDto.toTask(): Task? {
    val validTitle = (Title.of(title) as? Outcome.Success)?.value ?: return null
    val validPriority = Priority.parse(priority) ?: return null
    val created = runCatching { Instant.parse(createdAt) }.getOrNull() ?: return null
    val status = if (done) {
        val completed = completedAt?.let { runCatching { Instant.parse(it) }.getOrNull() } ?: return null
        TaskStatus.Done(completed)
    } else {
        TaskStatus.Open
    }
    return Task(TaskId(id), validTitle, validPriority, created, status)
}

/** Código estável (para máquinas) de cada erro de domínio. `when` exaustivo: erro novo exige decisão aqui. */
fun DomainError.code(): String = when (this) {
    DomainError.BlankTitle -> "blank_title"
    is DomainError.TitleTooLong -> "title_too_long"
    is DomainError.TaskNotFound -> "task_not_found"
    is DomainError.AlreadyCompleted -> "already_completed"
}

/** Mensagem (para humanos) compartilhada por terminal, API e telas. */
fun DomainError.userMessage(): String = when (this) {
    DomainError.BlankTitle -> "o título não pode ser vazio"
    is DomainError.TitleTooLong -> "o título passa de $max caracteres"
    is DomainError.TaskNotFound -> "não existe tarefa '$id'"
    is DomainError.AlreadyCompleted -> "a tarefa '$id' já foi concluída"
}
