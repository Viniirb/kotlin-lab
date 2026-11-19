plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    application
}

dependencies {
    implementation(project(":core"))

    testImplementation(kotlin("test"))
    testImplementation(libs.kotlinx.coroutines.test)
}

application {
    mainClass.set("lab.MainKt")
}

tasks.test {
    useJUnitPlatform()
}

// `./gradlew :lessons:run` precisa repassar o teclado para o app interativo.
tasks.run.configure {
    standardInput = System.`in`
}
