package lab.lessons

import kotlinx.datetime.Instant
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lab.core.platformName
import lab.taskmanager.CreateTaskRequest
import lab.taskmanager.TaskModule
import lab.taskmanager.domain.Priority
import lab.taskmanager.toDto
import lab.shared.Outcome

/**
 * LIÇÃO 7: Kotlin Multiplatform e serialização.
 * Ideia central: escreva a regra de negócio UMA vez (módulo :core) e rode em JVM, Android, iOS e Web.
 * Cada plataforma só escreve o que é realmente específico dela (UI, rede, armazenamento).
 */
object Lesson07Multiplatform : Lesson {
    override val number = 7
    override val title = "Multiplatform: expect/actual, :core compartilhado, kotlinx.serialization"

    override fun run() {
        sharedCore()
        serialization()
        map()
    }

    private fun sharedCore() {
        section("O mesmo :core, plataformas diferentes")
        // `platformName()` é `expect` no commonMain; aqui roda o `actual` da JVM.
        // No Android devolve "Android 34", no iOS "iOS 17.x", no navegador "Web (Kotlin/Wasm)".
        println("rodando em: ${platformName()}")

        val module = TaskModule() // casos de uso prontos, as mesmas regras do app inteiro
        val created = module.createTask("aprender KMP", Priority.HIGH)
        println("criada: ${(created as? Outcome.Success)?.value?.title}")
    }

    private fun serialization() {
        section("kotlinx.serialization: JSON sem reflexão, em qualquer plataforma")
        val json = Json { prettyPrint = false }

        // Objeto -> JSON (o mesmo que a API devolve ao app Android, ao iOS e ao navegador)
        val dto = TaskModule().createTask("estudar", Priority.LOW).let { (it as Outcome.Success).value.toDto() }
        val text = json.encodeToString(dto)
        println(text)

        // JSON -> objeto, com valor padrão para campo ausente
        val request = json.decodeFromString<CreateTaskRequest>("""{"title":"ler docs"}""")
        println(request)

        // Entrada inválida vira exceção tipada: trate na BORDA (ex.: StatusPages do Ktor).
        val failure = runCatching { json.decodeFromString<CreateTaskRequest>("""{"prioridade":1}""") }
        println("JSON inválido -> ${failure.exceptionOrNull()?.javaClass?.simpleName}")
    }

    private fun map() {
        section("Mapa mental: onde cada coisa mora")
        println(
            """
            commonMain   regra de negócio, contratos, expect       (compartilhado por todos)
            jvmMain      actual para JVM/backend/desktop
            androidMain  actual para Android (Build, Context...)
            iosMain      actual para iOS (platform.UIKit...)
            wasmJsMain   actual para navegador (DOM, window...)
            """.trimIndent(),
        )
        println("data de referência: ${Instant.parse("2026-01-01T00:00:00Z")}")
    }
}
