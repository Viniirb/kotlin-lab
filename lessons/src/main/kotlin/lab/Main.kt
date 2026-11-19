package lab

import lab.lessons.allLessons
import lab.taskmanager.runTaskManager

private val usage = """
    kotlin-lab
      ./gradlew :lessons:run --args="lessons"      lista as lições
      ./gradlew :lessons:run --args="lesson 3"     executa a lição 3
      ./gradlew :lessons:run --args="all"          executa todas
      ./gradlew :lessons:run                       (sem args) abre o Task Manager no terminal
      ./gradlew :lessons:test                      roda os testes
""".trimIndent()

fun main(args: Array<String>) {
    when (args.firstOrNull()) {
        null -> runTaskManager()
        "lessons" -> allLessons.forEach { println("${it.number}. ${it.title}") }
        "lesson" -> runLesson(args.getOrNull(1))
        "all" -> allLessons.forEach(::runWithHeader)
        else -> println(usage)
    }
}

private fun runLesson(rawNumber: String?) {
    val lesson = allLessons.firstOrNull { it.number == rawNumber?.toIntOrNull() }
    if (lesson == null) println("Lição inválida. Use um número de 1 a ${allLessons.size}.") else runWithHeader(lesson)
}

private fun runWithHeader(lesson: lab.lessons.Lesson) {
    println("\n════ Lição ${lesson.number}: ${lesson.title} ════")
    lesson.run()
}
