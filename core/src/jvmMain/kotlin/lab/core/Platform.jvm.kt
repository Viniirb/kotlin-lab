package lab.core

actual fun platformName(): String = "JVM ${System.getProperty("java.version")}"
