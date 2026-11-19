// Raiz: só declara os plugins (sem aplicar) para que todos os módulos usem a MESMA versão do catálogo
// `gradle/libs.versions.toml`. Versões em um lugar só = sem conflito entre módulos.
plugins {
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.kotlinCompose) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
}
