package lab.backend

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import lab.shared.fold
import lab.taskmanager.CreateTaskRequest
import lab.taskmanager.ErrorResponse
import lab.taskmanager.TaskModule
import lab.taskmanager.application.TaskFilter
import lab.taskmanager.code
import lab.taskmanager.domain.DomainError
import lab.taskmanager.domain.Priority
import lab.taskmanager.domain.TaskId
import lab.taskmanager.toDto
import lab.taskmanager.userMessage

/**
 * CAMADA DE APRESENTAÇÃO do backend: traduz HTTP <-> casos de uso. Sem regra de negócio aqui.
 *
 *   GET  /health
 *   GET  /tasks?filter=all|open|done
 *   POST /tasks            {"title": "...", "priority": "HIGH"}
 *   POST /tasks/{id}/done
 */
fun Route.taskRoutes(tasks: TaskModule) {
    get("/health") { call.respond(mapOf("status" to "ok")) }

    route("/tasks") {
        get {
            val filter = when (call.request.queryParameters["filter"]?.lowercase()) {
                null, "all" -> TaskFilter.ALL
                "open" -> TaskFilter.OPEN
                "done" -> TaskFilter.DONE
                else -> return@get call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse("invalid_filter", "use all, open ou done"),
                )
            }
            call.respond(tasks.listTasks(filter).map { it.toDto() })
        }

        post {
            val body = call.receive<CreateTaskRequest>()
            val priority = Priority.parse(body.priority)
                ?: return@post call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse("invalid_priority", "use LOW, MEDIUM ou HIGH"),
                )
            val result = tasks.createTask(body.title, priority)
            result.fold(
                onSuccess = { call.respond(HttpStatusCode.Created, it.toDto()) },
                onFailure = { call.respond(it.status(), it.toResponse()) },
            )
        }

        post("/{id}/done") {
            val id = TaskId(call.parameters["id"].orEmpty())
            tasks.completeTask(id).fold(
                onSuccess = { call.respond(it.toDto()) },
                onFailure = { call.respond(it.status(), it.toResponse()) },
            )
        }
    }
}

private fun DomainError.toResponse() = ErrorResponse(code(), userMessage())

// `when` exaustivo: erro de domínio novo exige decidir o status HTTP, senão não compila.
private fun DomainError.status(): HttpStatusCode = when (this) {
    DomainError.BlankTitle, is DomainError.TitleTooLong -> HttpStatusCode.BadRequest
    is DomainError.TaskNotFound -> HttpStatusCode.NotFound
    is DomainError.AlreadyCompleted -> HttpStatusCode.Conflict
}
