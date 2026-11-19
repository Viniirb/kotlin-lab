package lab.taskmanager

import kotlinx.serialization.Serializable
import lab.taskmanager.domain.DomainError
import lab.taskmanager.domain.Task

/**
 * CONTRATO compartilhado. Backend e clientes usam as MESMAS classes: o JSON que o servidor produz é
 * exatamente o que o cliente espera. `@Serializable` gera, em compilação, o código de (de)serialização.
 * As classes já vêm prontas; as conversões são suas (M11), e o tratamento de erros é do M04.
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

/**
 * M11. Domínio para DTO. `priority` vira o NOME do enum ("HIGH"); datas viram texto ISO (`Instant.toString()`);
 * `completedAt` só existe quando a tarefa está concluída.
 */
fun Task.toDto(): TaskDto = TODO("M11: converta Task em TaskDto")

/**
 * M11. Caminho de volta. Devolve `null` se algum campo não passar nas regras do domínio (título inválido,
 * prioridade desconhecida, data malformada, `done = true` sem `completedAt`).
 * Dicas: `Title.of`, `Priority.parse`, `Instant.parse` dentro de `runCatching`.
 */
fun TaskDto.toTask(): Task? = TODO("M11: converta TaskDto em Task")

/**
 * M04. Código estável (para máquinas) de cada erro. Um código DIFERENTE por erro, em snake_case:
 * blank_title, title_too_long, task_not_found, already_completed.
 * `when` exaustivo, sem `else`: erro novo no domínio exige decisão aqui.
 */
fun DomainError.code(): String = TODO("M04: devolva o código")

/**
 * M04. Mensagem (para humanos), compartilhada por terminal, API e telas. Textos esperados pelo CLI:
 *  - BlankTitle: "o título não pode ser vazio"
 *  - TitleTooLong: "o título passa de {max} caracteres"
 *  - TaskNotFound: "não existe tarefa '{id}'"
 *  - AlreadyCompleted: "a tarefa '{id}' já foi concluída"
 */
fun DomainError.userMessage(): String = TODO("M04: devolva a mensagem")
