package lab.lessons

/**
 * LIÇÃO 1: fundamentos.
 * Ideia central: Kotlin empurra você para código IMUTÁVEL e SEM NULL surpresa.
 */
object Lesson01Basics : Lesson {
    override val number = 1
    override val title = "Fundamentos: val/var, null safety, when, funções"

    override fun run() {
        immutability()
        nullSafety()
        controlFlow()
        functions()
    }

    private fun immutability() {
        section("val vs var")
        // `val` = referência que não muda (como `final`). Prefira SEMPRE val; use var só se precisar.
        val language = "Kotlin"
        var counter = 0
        counter += 1
        println("language=$language counter=$counter") // $ interpola variáveis (string template)
        println("expressão: ${language.length * 2}")     // ${} interpola expressões

        // Tipos são inferidos, mas conversões numéricas NUNCA são implícitas.
        val small = 42
        val big: Long = small.toLong()
        println("big=$big | 42/5=${small / 5} | 42/5.0=${small / 5.0}")
    }

    private fun nullSafety() {
        section("Null safety: o tipo diz se pode ser null")
        // String  = nunca null.   String? = pode ser null. O compilador fiscaliza.
        println(lengthOrMinusOne("kotlin")) // 6
        println(lengthOrMinusOne(null))     // -1
        println(shout("kotlin"))            // KOTLIN
        println(shout(null))                // (vazio)

        // Operadores:
        //   ?.   chamada segura (retorna null se o receptor for null)
        //   ?:   elvis (valor padrão quando null)
        //   !!   "confie em mim" -> lança NPE. Evite: é um cheiro de código.
    }

    private fun lengthOrMinusOne(text: String?): Int = text?.length ?: -1

    private fun shout(text: String?): String {
        if (text == null) return "(vazio)"
        return text.uppercase() // smart cast: aqui o compilador sabe que text é String
    }

    private fun controlFlow() {
        section("if/when são EXPRESSÕES (retornam valor)")
        listOf(-5, 0, 7, 120).forEach { println("$it é ${classify(it)}") }

        for (i in 1..3) print("$i ")                 // 1..3 inclui o 3
        for (i in 1 until 3) print("| $i ")          // until exclui o 3
        for (i in 10 downTo 0 step 5) print("| $i ") // contagem regressiva
        println()

        val parity = if (4 % 2 == 0) "par" else "ímpar" // if como expressão: sem operador ternário
        println("4 é $parity")
    }

    private fun classify(n: Int): String = when {
        n < 0 -> "negativo"
        n == 0 -> "zero"
        n in 1..9 -> "de um dígito"
        else -> "grande"
    }

    private fun functions() {
        section("Funções: default, nomeados, vararg, locais")
        println(greet("Ana"))
        println(greet("Bia", excited = true))             // argumento nomeado: legível
        println(greet(greeting = "Oi", name = "Caio"))    // ordem livre quando nomeado
        println("soma=${sum(1, 2, 3, 4)}")

        // Função local: só existe aqui dentro. Ótima para quebrar lógica sem poluir a classe.
        fun square(x: Int) = x * x
        println("quadrados=${(1..4).map(::square)}")
    }

    // Corpo de expressão: quando a função é uma expressão só, dispense chaves e `return`.
    private fun greet(name: String, greeting: String = "Olá", excited: Boolean = false): String =
        "$greeting, $name" + if (excited) "!" else "."

    private fun sum(vararg numbers: Int): Int = numbers.sum()
}
