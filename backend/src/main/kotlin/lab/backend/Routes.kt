package lab.backend

import io.ktor.server.routing.Route
import lab.taskmanager.TaskModule

/**
 * M12. Camada de apresentação do backend: traduz HTTP para casos de uso. SEM regra de negócio aqui.
 *
 *   GET  /health               -> 200 {"status":"ok"}
 *   GET  /tasks?filter=...     -> 200 lista de TaskDto; filter: all (padrão) | open | done; outro valor -> 400 "invalid_filter"
 *   POST /tasks                -> corpo CreateTaskRequest; 201 TaskDto; prioridade inválida -> 400 "invalid_priority"
 *   POST /tasks/{id}/done      -> 200 TaskDto
 *
 * Erro de domínio vira ErrorResponse(code(), userMessage()) com o status certo, decidido por um `when`
 * EXAUSTIVO sobre DomainError (sem `else`):
 *   BlankTitle, TitleTooLong -> 400 | TaskNotFound -> 404 | AlreadyCompleted -> 409
 * Dicas: `route("/tasks") { get { } post { } post("/{id}/done") { } }`, `call.receive<...>()`,
 * `call.respond(status, body)`, `call.parameters["id"]`, `Outcome.fold`.
 * O ServerTest é a especificação exata.
 */
fun Route.taskRoutes(tasks: TaskModule) {
    TODO("M12: declare as rotas")
}
