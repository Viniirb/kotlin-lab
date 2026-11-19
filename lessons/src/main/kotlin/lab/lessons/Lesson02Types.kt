package lab.lessons

/**
 * LIÇÃO 2: tipos e objetos.
 * Ideia central: modele o domínio com tipos que tornam estados inválidos IMPOSSÍVEIS de representar.
 */
object Lesson02Types : Lesson {
    override val number = 2
    override val title = "Tipos: classes, data, sealed, enum, object, value class"

    override fun run() {
        dataClasses()
        sealedHierarchies()
        enumsAndObjects()
        valueClasses()
        regularClasses()
    }

    private fun dataClasses() {
        section("data class: valor com equals/hashCode/toString/copy de graça")
        val a = LessonPoint(1, 2)
        val b = a.copy(y = 9) // cópia alterando um campo: o original NÃO muda (imutabilidade)
        println("a=$a b=$b iguais=${a == LessonPoint(1, 2)}") // == compara conteúdo
        val (x, y) = a // desestruturação
        println("x=$x y=$y")
    }

    private fun sealedHierarchies() {
        section("sealed: conjunto FECHADO de variantes, `when` exaustivo")
        val shapes = listOf(LessonShape.Circle(1.0), LessonShape.Rect(2.0, 3.0), LessonShape.Empty)
        shapes.forEach { println("$it -> área ${"%.2f".format(area(it))}") }
        // Adicione uma variante nova em LessonShape e veja o compilador apontar todo `when` incompleto.
    }

    private fun area(shape: LessonShape): Double = when (shape) {
        is LessonShape.Circle -> Math.PI * shape.radius * shape.radius // smart cast em `shape`
        is LessonShape.Rect -> shape.width * shape.height
        LessonShape.Empty -> 0.0
        // sem `else`: de propósito. `else` esconderia variantes novas.
    }

    private fun enumsAndObjects() {
        section("enum com dados, object (singleton), companion object")
        LessonLevel.entries.forEach { println("${it.name} peso=${it.weight}") }
        println(LessonClock.describe())                // object = instância única, sem `new`
        println(LessonTemperature.fromFahrenheit(212.0)) // companion = "método estático" com fábrica nomeada
    }

    private fun valueClasses() {
        section("value class: tipo próprio SEM custo em runtime")
        // Evita "primitive obsession": ambos são Long, mas o compilador não deixa trocar um pelo outro.
        val user = LessonUserId(7)
        val order = LessonOrderId(7)
        println("user=$user order=$order")
        // findOrder(user)  <- não compila: UserId não é OrderId
        println(findOrderLabel(order))
    }

    private fun findOrderLabel(id: LessonOrderId) = "pedido #${id.value}"

    private fun regularClasses() {
        section("class comum: construtor primário, propriedades, interface")
        val account = LessonAccount(owner = "Ana", initial = 100)
        account.deposit(50)
        println("${account.owner}: ${account.balance} | rica=${account.isRich}")
        val result = runCatching { account.deposit(-1) } // require lança IllegalArgumentException
        println("depósito inválido -> ${result.exceptionOrNull()?.message}")
        println(account.describe()) // método com implementação padrão na interface
    }
}

// Tipos de apoio ficam no nível do arquivo. O prefixo Lesson evita colisão de nomes no pacote.

data class LessonPoint(val x: Int, val y: Int)

sealed interface LessonShape {
    data class Circle(val radius: Double) : LessonShape
    data class Rect(val width: Double, val height: Double) : LessonShape
    data object Empty : LessonShape
}

enum class LessonLevel(val weight: Int) {
    LOW(1), MEDIUM(2), HIGH(3)
}

object LessonClock {
    fun describe() = "sou um singleton: ${this::class.simpleName}"
}

class LessonTemperature private constructor(val celsius: Double) {
    // Construtor privado + fábricas nomeadas: o nome da fábrica documenta a unidade de entrada.
    companion object {
        fun fromCelsius(value: Double) = LessonTemperature(value)
        fun fromFahrenheit(value: Double) = LessonTemperature((value - 32) * 5 / 9)
    }

    override fun toString() = "%.1f°C".format(celsius)
}

@JvmInline
value class LessonUserId(val value: Long)

@JvmInline
value class LessonOrderId(val value: Long)

interface LessonDescribable {
    val label: String
    fun describe(): String = "[$label]" // implementação padrão
}

class LessonAccount(val owner: String, initial: Int) : LessonDescribable {
    // `private set`: lê-se de fora, só a própria classe altera. Encapsulamento sem getter/setter manual.
    var balance: Int = initial
        private set

    // Propriedade calculada: sem campo, recalculada a cada leitura.
    val isRich: Boolean get() = balance > 1_000

    override val label: String get() = "conta de $owner"

    init {
        require(initial >= 0) { "saldo inicial não pode ser negativo" }
    }

    fun deposit(amount: Int) {
        require(amount > 0) { "depósito precisa ser positivo, recebi $amount" }
        balance += amount
    }
}
