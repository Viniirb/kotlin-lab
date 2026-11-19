package lab.backend

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import lab.taskmanager.ErrorResponse
import lab.taskmanager.TaskModule
import lab.taskmanager.infrastructure.InMemoryTaskRepository

/** Ponto de entrada: `./gradlew :backend:run` sobe em http://localhost:8080 */
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = { module() }).start(wait = true)
}

/**
 * Configuração da aplicação como função de extensão: testes chamam `application { module(fake) }`
 * sem subir um servidor de verdade. O `TaskModule` é o MESMO do app Android, iOS, desktop e web.
 */
fun Application.module(
    tasks: TaskModule = TaskModule(repository = LockedTaskRepository(InMemoryTaskRepository())),
) {
    install(ContentNegotiation) { json() }

    install(StatusPages) {
        // JSON malformado ou com campos faltando: erro do CLIENTE (400), não do servidor.
        // O Ktor embrulha falhas de desserialização em BadRequestException ao fazer `call.receive`.
        exception<BadRequestException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest, ErrorResponse("invalid_json", "corpo da requisição inválido"))
        }
        // Rede de segurança para o inesperado. Nunca vaze stack trace ao cliente.
        exception<Throwable> { call, _ ->
            call.respond(HttpStatusCode.InternalServerError, ErrorResponse("internal_error", "erro interno"))
        }
    }

    routing { taskRoutes(tasks) }
}
