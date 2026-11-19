package lab.backend

import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import lab.taskmanager.TaskModule
import lab.taskmanager.infrastructure.InMemoryTaskRepository

/** Ponto de entrada (já pronto): `./gradlew :backend:run` sobe em http://localhost:8080 */
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = { module() }).start(wait = true)
}

/**
 * M12. Configuração da aplicação como função de extensão (assim o teste chama `application { module() }`
 * sem subir servidor). Faça:
 *  1. `install(ContentNegotiation) { json() }`
 *  2. `install(StatusPages)`: `BadRequestException` -> 400 com `ErrorResponse("invalid_json", ...)`;
 *     `Throwable` -> 500 com `ErrorResponse("internal_error", "erro interno")` (nunca vaze o stack trace)
 *  3. `routing { taskRoutes(tasks) }`
 * Leia antes: docs/PLATAFORMAS.md (seção Backend) e a issue do módulo.
 */
fun Application.module(
    tasks: TaskModule = TaskModule(repository = LockedTaskRepository(InMemoryTaskRepository())),
) {
    TODO("M12: instale plugins e rotas")
}
