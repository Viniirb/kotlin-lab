package lab.lessons

import kotlin.properties.Delegates

/**
 * LIÇÃO 5: genéricos, delegação e DSLs.
 * Ideia central: composição em vez de herança; deixe o compilador provar o que for possível.
 */
object Lesson05Generics : Lesson {
    override val number = 5
    override val title = "Genéricos, variância, delegação (by), DSL"

    override fun run() {
        generics()
        variance()
        delegation()
        dsl()
    }

    private fun generics() {
        section("Genéricos com restrição e reified")
        println(biggest(3, 9))
        println(biggest("kiwi", "abacaxi"))
        // reified: o tipo T existe em runtime (só funciona em funções inline).
        val mixed = listOf(1, "dois", 3.0, "quatro")
        println(mixed.firstOfType<String>())
        println(mixed.firstOfType<Double>())
    }

    // T: Comparable<T> -> só aceita tipos que sabem se comparar.
    private fun <T : Comparable<T>> biggest(a: T, b: T): T = if (a >= b) a else b

    private inline fun <reified T> List<Any>.firstOfType(): T? = filterIsInstance<T>().firstOrNull()

    private fun variance() {
        section("Variância: out (produz) e in (consome)")
        // List<out E> é covariante: uma List<String> é uma List<Any>. Seguro porque List só LÊ.
        val strings: List<String> = listOf("a", "b")
        val anything: List<Any> = strings
        println(anything.size)

        // MutableList<E> é invariante: aceitar `add(Any)` numa lista de String quebraria o tipo.
        val sink: Sink<Any> = Sink { println("recebi $it") }
        val stringSink: Sink<String> = sink // `in`: quem aceita Any aceita String (contravariância)
        stringSink.accept("olá")
        // Mnemônico: PECS do Java vira `out` = Producer, `in` = Consumer.
    }

    private fun delegation() {
        section("Delegação: `by lazy`, observable, e delegação de interface")
        val expensive by lazy {
            println("calculando uma vez só...")
            42
        }
        println(expensive) // calcula
        println(expensive) // reaproveita

        var name: String by Delegates.observable("inicial") { _, old, new -> println("name: $old -> $new") }
        name = "Ana"
        name = "Bia"

        // `by inner`: o compilador gera todos os métodos repassando para `inner`. Só sobrescrevemos o que muda.
        val log = mutableListOf<String>()
        val list = LessonLoggingList(mutableListOf<String>(), log)
        list.add("x")
        list.add("y")
        println("lista=$list log=$log")
    }

    private fun dsl() {
        section("DSL: lambda com receptor")
        val menu = menu("Almoço") {
            item("Arroz")
            item("Feijão", extra = "com bacon")
            item("Salada")
        }
        println(menu.render())
        // `Menu.() -> Unit`: dentro da lambda, `this` é o Menu. É assim que Gradle Kotlin DSL funciona.
    }
}

fun interface Sink<in T> {
    fun accept(item: T)
}

class LessonLoggingList<T>(
    private val inner: MutableList<T>,
    private val log: MutableList<String>,
) : MutableList<T> by inner {
    override fun add(element: T): Boolean {
        log += "add($element)"
        return inner.add(element)
    }

    override fun toString() = inner.toString()
}

class LessonMenu(private val title: String) {
    private val lines = mutableListOf<String>()

    fun item(name: String, extra: String? = null) {
        lines += if (extra == null) name else "$name ($extra)"
    }

    fun render(): String = buildString {
        appendLine("== $title ==")
        lines.forEachIndexed { index, line -> appendLine("${index + 1}. $line") }
    }.trimEnd()
}

fun menu(title: String, build: LessonMenu.() -> Unit): LessonMenu = LessonMenu(title).apply(build)
