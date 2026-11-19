package lab.compose

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

// Desktop: o ponto de entrada é uma janela (Swing/Skia por baixo). `./gradlew :composeApp:run`
fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Kotlin Lab - Task Manager") {
        App()
    }
}
