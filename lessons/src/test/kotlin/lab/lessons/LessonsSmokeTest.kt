package lab.lessons

import kotlin.test.Test
import kotlin.test.assertEquals

class LessonsSmokeTest {
    @Test
    fun `numeros das licoes sao sequenciais`() {
        assertEquals((1..allLessons.size).toList(), allLessons.map { it.number })
    }

    @Test
    fun `todas as licoes executam sem lancar`() {
        allLessons.forEach { lesson ->
            try {
                lesson.run()
            } catch (_: NotImplementedError) {
                // Algumas lições usam o SEU código dos módulos (Outcome, casos de uso). Enquanto o módulo não
                // estiver pronto, o TODO() dispara e isso é esperado. Qualquer outro erro derruba o teste.
            }
        }
    }
}
