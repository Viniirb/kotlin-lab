package lab.compose

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

// Web: o Compose desenha num canvas dentro do <body> da página. `./gradlew :composeApp:wasmJsBrowserDevelopmentRun`
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) { App() }
}
