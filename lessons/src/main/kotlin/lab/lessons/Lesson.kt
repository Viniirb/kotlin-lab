package lab.lessons

/**
 * Cada lição é um arquivo que você LÊ, EXECUTA e DEPOIS ALTERA.
 * Rode com: `./gradlew run --args="lesson 1"`
 */
interface Lesson {
    val number: Int
    val title: String
    fun run()
}

internal fun section(name: String) = println("\n── $name")

/** Registro de todas as lições, na ordem de estudo. */
val allLessons: List<Lesson> = listOf(
    Lesson01Basics,
    Lesson02Types,
    Lesson03Functional,
    Lesson04Errors,
    Lesson05Generics,
    Lesson06Coroutines,
    Lesson07Multiplatform,
)
