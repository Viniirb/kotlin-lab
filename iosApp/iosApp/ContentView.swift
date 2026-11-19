import UIKit
import SwiftUI
import ComposeApp // o framework gerado pelo Kotlin (baseName = "ComposeApp")

// Ponte SwiftUI <-> Kotlin: `MainViewControllerKt.MainViewController()` é a função do iosMain.
// Kotlin vira Objective-C/Swift: o arquivo MainViewController.kt gera a classe `MainViewControllerKt`.
struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.keyboard)
    }
}
