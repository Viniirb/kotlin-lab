package lab.core

/**
 * expect/actual: o código comum DECLARA o que precisa (`expect`); cada plataforma ENTREGA (`actual`).
 * É assim que o código compartilhado acessa APIs específicas do sistema sem conhecê-las.
 * Veja as implementações em jvmMain, androidMain, iosMain e wasmJsMain.
 */
expect fun platformName(): String
