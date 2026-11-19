package lab.compose

import androidx.compose.ui.window.ComposeUIViewController

// iOS: o Swift chama esta função (`MainViewControllerKt.MainViewController()`) e embute o resultado numa tela SwiftUI.
@Suppress("ktlint:standard:function-naming", "FunctionName")
fun MainViewController() = ComposeUIViewController { App() }
