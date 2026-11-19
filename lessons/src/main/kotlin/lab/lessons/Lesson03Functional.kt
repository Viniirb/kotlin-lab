package lab.lessons

/**
 * LIÇÃO 3: funções como valores, coleções e extensões.
 * Ideia central: descreva O QUE quer (filtrar, agrupar), não COMO (índices, loops, variáveis temporárias).
 */
object Lesson03Functional : Lesson {
    override val number = 3
    override val title = "Funcional: lambdas, coleções, sequences, scope functions, extensões"

    override fun run() {
        collections()
        sequences()
        higherOrder()
        scopeFunctions()
        extensions()
    }

    private val people = listOf(
        LessonPerson("Ana", 31, "dev"),
        LessonPerson("Bia", 25, "design"),
        LessonPerson("Caio", 42, "dev"),
        LessonPerson("Duda", 19, "design"),
        LessonPerson("Edu", 35, "ops"),
    )

    private fun collections() {
        section("Coleções: pipeline declarativo")
        // `it` = parâmetro único implícito da lambda.
        println(people.filter { it.age > 30 }.map { it.name })
        println(people.groupBy { it.role }.mapValues { (_, group) -> group.size })
        println(people.sumOf { it.age })
        println(people.maxByOrNull { it.age }?.name)
        println(people.sortedWith(compareBy({ it.role }, { -it.age })).map { it.name })

        val (adults, young) = people.partition { it.age >= 30 }
        println("adultos=${adults.size} jovens=${young.size}")
        println(people.associate { it.name to it.age }) // List -> Map
        println(listOf(1, 2, 3, 4, 5).windowed(size = 2).map { (a, b) -> b - a })
        println(listOf(1, 2, 3).zip(listOf("a", "b", "c")))
        println((1..5).fold(1) { acc, n -> acc * n }) // 120

        // Coleções somente-leitura (List) vs mutáveis (MutableList): exponha a primeira, mude a segunda por dentro.
        val readOnly: List<Int> = mutableListOf(1, 2)
        println(readOnly)
    }

    private fun sequences() {
        section("Sequence: avaliação preguiçosa, elemento a elemento")
        // List: cada etapa cria uma lista intermediária. Sequence: um elemento passa por todas as etapas.
        val result = generateSequence(1) { it * 2 } // infinita!
            .filter { it % 3 != 0 }
            .map { it + 1 }
            .take(5)                                // só aqui a conta de fato acontece
            .toList()
        println(result)
        // Regra prática: Sequence para coleções grandes ou encadeamentos longos; List nos demais casos.
    }

    private fun higherOrder() {
        section("Funções de ordem superior: funções que recebem/retornam funções")
        println(measured("soma") { (1..1000).sum() })

        val isEven: (Int) -> Boolean = { it % 2 == 0 }
        println(listOf(1, 2, 3, 4).filter(isEven))
        println(listOf(1, 2, 3, 4).filter(::isOdd))   // referência a função

        val double = multiplier(2)                     // closure: lembra o `factor`
        println(double(21))
    }

    private fun isOdd(n: Int) = n % 2 != 0

    private fun multiplier(factor: Int): (Int) -> Int = { it * factor }

    // `inline` copia o corpo da função (e da lambda) no ponto de chamada: sem custo de objeto lambda.
    private inline fun <T> measured(label: String, block: () -> T): T {
        val start = System.nanoTime()
        val value = block()
        val micros = (System.nanoTime() - start) / 1_000
        println("[$label] terminou (${if (micros >= 0) "medido" else "?"})")
        return value
    }

    private fun scopeFunctions() {
        section("Scope functions: let, run, apply, also, with")
        // let   : transforma o objeto (it) e devolve o resultado da lambda. Ótimo com `?.`.
        val length = "kotlin".let { it.length }
        // apply : configura o objeto (this) e devolve o PRÓPRIO objeto. Ótimo para inicializar.
        val builder = StringBuilder().apply {
            append("a")
            append("b")
        }
        // also  : efeito colateral (log) e devolve o próprio objeto (it).
        val doubled = (length * 2).also { println("calculei $it") }
        // run/with : usa o objeto como `this` e devolve o resultado.
        val description = with(people.first()) { "$name tem $age anos" }
        println("$length $builder $doubled | $description")
        // Dica: se estiver aninhando 3 scope functions, pare e extraia uma função com nome.
    }

    private fun extensions() {
        section("Extensões: adicione funções a tipos que você não controla")
        println("arara".isPalindrome())
        println("kotlin".isPalindrome())
        println(listOf(3, 9, 6).secondLargest())
        println("kotlin é demais".wordCount)
        // Extensão é resolvida em tempo de compilação e não acessa membros privados:
        // é açúcar sintático para uma função estática, não herança.
    }
}

data class LessonPerson(val name: String, val age: Int, val role: String)

fun String.isPalindrome(): Boolean {
    val cleaned = lowercase().filter { it.isLetterOrDigit() }
    return cleaned == cleaned.reversed()
}

fun List<Int>.secondLargest(): Int? = distinct().sortedDescending().getOrNull(1)

val String.wordCount: Int get() = trim().split(Regex("\\s+")).count { it.isNotEmpty() }
