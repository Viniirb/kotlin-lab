package lab.core

import platform.UIKit.UIDevice

// Kotlin/Native chama APIs do iOS direto: `platform.UIKit` vem do SDK da Apple, sem ponte manual.
actual fun platformName(): String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
