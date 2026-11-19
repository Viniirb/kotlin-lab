package lab.backend

import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import lab.taskmanager.CreateTaskRequest
import lab.taskmanager.ErrorResponse
import lab.taskmanager.TaskDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

// `testApplication` roda a aplicação em memória, sem porta de rede: rápido e determinístico.
class ServerTest {
    private fun test(block: suspend ApplicationTestBuilder.() -> Unit) = testApplication {
        application { module() }
        block()
    }

    private fun ApplicationTestBuilder.jsonClient() = createClient { install(ContentNegotiation) { json() } }

    @Test
    fun `health responde ok`() = test {
        val response = client.get("/health")
        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue("ok" in response.bodyAsText())
    }

    @Test
    fun `cria e lista tarefas ordenadas por prioridade`() = test {
        val client = jsonClient()
        client.post("/tasks") { contentType(ContentType.Application.Json); setBody(CreateTaskRequest("baixa", "LOW")) }
        val created = client.post("/tasks") {
            contentType(ContentType.Application.Json)
            setBody(CreateTaskRequest("alta", "HIGH"))
        }
        assertEquals(HttpStatusCode.Created, created.status)

        val tasks: List<TaskDto> = client.get("/tasks").body()
        assertEquals(listOf("alta", "baixa"), tasks.map { it.title })
    }

    @Test
    fun `conclui e depois da conflito`() = test {
        val client = jsonClient()
        val task: TaskDto = client.post("/tasks") {
            contentType(ContentType.Application.Json)
            setBody(CreateTaskRequest("x"))
        }.body()

        assertEquals(HttpStatusCode.OK, client.post("/tasks/${task.id}/done").status)

        val again = client.post("/tasks/${task.id}/done")
        assertEquals(HttpStatusCode.Conflict, again.status)
        assertEquals("already_completed", again.body<ErrorResponse>().code)
    }

    @Test
    fun `erros de dominio viram status HTTP`() = test {
        val client = jsonClient()
        assertEquals(HttpStatusCode.NotFound, client.post("/tasks/zz/done").status)

        val blank = client.post("/tasks") { contentType(ContentType.Application.Json); setBody(CreateTaskRequest(" ")) }
        assertEquals(HttpStatusCode.BadRequest, blank.status)
        assertEquals("blank_title", blank.body<ErrorResponse>().code)

        val badPriority = client.post("/tasks") {
            contentType(ContentType.Application.Json)
            setBody(CreateTaskRequest("a", "URGENTE"))
        }
        assertEquals("invalid_priority", badPriority.body<ErrorResponse>().code)
    }

    @Test
    fun `json invalido da 400 e filtro invalido da 400`() = test {
        val bad = client.post("/tasks") { contentType(ContentType.Application.Json); setBody("""{"x":1}""") }
        assertEquals(HttpStatusCode.BadRequest, bad.status)
        assertEquals(HttpStatusCode.BadRequest, client.get("/tasks?filter=zzz").status)
    }
}
