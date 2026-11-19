package lab.lessons

import lab.shared.Outcome
import lab.shared.failure
import lab.shared.flatMap
import lab.shared.map
import lab.shared.success
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LessonsSmokeTest {
    @Test
    fun `numeros das licoes sao sequenciais`() {
        assertEquals((1..allLessons.size).toList(), allLessons.map { it.number })
    }

    @Test
    fun `todas as licoes executam sem lancar`() {
        allLessons.forEach { it.run() }
    }
}

class OutcomeTest {
    @Test
    fun `map transforma so o sucesso`() {
        assertEquals(Outcome.Success(4), 2.success().map { it * 2 })
        val failed: Outcome<Int, String> = "ruim".failure()
        assertEquals(Outcome.Failure("ruim"), failed.map { it * 2 })
    }

    @Test
    fun `flatMap para na primeira falha`() {
        var called = false
        val result: Outcome<Int, String> = "ruim".failure<String>().flatMap { _: Nothing ->
            called = true
            1.success()
        }
        assertTrue(result is Outcome.Failure && !called)
    }
}
