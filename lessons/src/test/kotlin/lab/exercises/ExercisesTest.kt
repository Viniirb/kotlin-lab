package lab.exercises

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import lab.shared.Outcome
import kotlin.system.measureTimeMillis
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

// Estes testes são a "régua" dos módulos. Não os altere: o CI recusa PR que modifica testes existentes.

class Exercise1FizzBuzzTest {
    @Test
    fun `fizzbuzz de 15`() {
        val expected = listOf(
            "1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz",
        )
        assertEquals(expected, fizzBuzz(15))
    }

    @Test
    fun `zero devolve lista vazia`() = assertEquals(emptyList(), fizzBuzz(0))
}

class Exercise2FirstNonBlankTest {
    @Test
    fun `pula nulos e brancos`() = assertEquals("ok", firstNonBlank(null, "  ", "ok", "outro"))

    @Test
    fun `sem candidato devolve null`() = assertNull(firstNonBlank(null, ""))
}

class Exercise3WordFrequencyTest {
    @Test
    fun `conta ignorando caixa e pontuacao`() {
        val result = wordFrequency("Kotlin é bom. kotlin, é? Muito bom! Kotlin")
        assertEquals(listOf("kotlin" to 3, "bom" to 2, "é" to 2, "muito" to 1), result)
    }

    @Test
    fun `texto vazio`() = assertEquals(emptyList(), wordFrequency("  ...  "))
}

class Exercise3bGroupAnagramsTest {
    @Test
    fun `agrupa anagramas`() {
        val words = listOf("listen", "silent", "enlist", "google", "gogole", "cat", "act")
        val expected = listOf(
            listOf("act", "cat"),
            listOf("enlist", "listen", "silent"),
            listOf("gogole", "google"),
        )
        assertEquals(expected, groupAnagrams(words))
    }

    @Test
    fun `ignora caixa`() = assertEquals(listOf(listOf("Dog", "god")), groupAnagrams(listOf("god", "Dog")))
}

class Exercise3cRunningTotalTest {
    @Test
    fun `acumula`() = assertEquals(listOf(1, 3, 6, 10), runningTotal(listOf(1, 2, 3, 4)))

    @Test
    fun `vazio`() = assertEquals(emptyList(), runningTotal(emptyList()))
}

class Exercise4PerimeterTest {
    @Test
    fun `perimetros`() {
        assertEquals(8.0, perimeter(Figure.Square(2.0)))
        assertEquals(10.0, perimeter(Figure.Rectangle(2.0, 3.0)))
        assertEquals(2 * Math.PI, perimeter(Figure.Circle(1.0)), 1e-9)
    }
}

class Exercise5ParsePositiveTest {
    @Test
    fun `aceita positivo`() = assertEquals(Outcome.Success(7), parsePositive("7"))

    @Test
    fun `rejeita texto, zero e negativo`() {
        listOf("abc", "0", "-4", "").forEach { assertIs<Outcome.Failure<String>>(parsePositive(it), "entrada '$it'") }
    }
}

class Exercise6RetryTest {
    @Test
    fun `sucesso na terceira tentativa`() {
        var calls = 0
        val result = retry(3) { if (++calls < 3) error("falha $calls") else "ok" }
        assertEquals("ok", result.getOrNull())
        assertEquals(3, calls)
    }

    @Test
    fun `devolve a ultima falha`() {
        var calls = 0
        val result = retry(2) { error("falha ${++calls}") }
        assertEquals("falha 2", result.exceptionOrNull()?.message)
    }

    @Test
    fun `times invalido lanca`() {
        assertTrue(runCatching { retry(0) { 1 } }.exceptionOrNull() is IllegalArgumentException)
    }
}

class Exercise7ParallelMapTest {
    @Test
    fun `mantem ordem e roda em paralelo`() = runBlocking {
        var result: List<Int> = emptyList()
        val elapsed = measureTimeMillis {
            result = parallelMap(listOf(1, 2, 3, 4)) { n ->
                delay(200)
                n * 10
            }
        }
        assertEquals(listOf(10, 20, 30, 40), result)
        assertTrue(elapsed < 500, "levou ${elapsed}ms: deveria rodar em paralelo (~200ms)")
    }
}

class Exercise7bCountdownTest {
    @Test
    fun `conta regressiva`() = runTest { assertEquals(listOf(3, 2, 1), countdown(3).toList()) }

    @Test
    fun `zero nao emite`() = runTest { assertEquals(emptyList(), countdown(0).toList()) }
}
// alterado
