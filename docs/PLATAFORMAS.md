# Plataformas: como cada alvo funciona

Ideia central: o código vive em **source sets**. `commonMain` compila para todos os alvos; `jvmMain`, `androidMain`,
`iosMain`, `wasmJsMain`, `desktopMain` só para o seu. O código comum chama o específico via `expect/actual`.

```
                       commonMain (regras, UI Compose, contratos)
                      /        |         |          \
               jvmMain   androidMain   iosMain   wasmJsMain      <- actual + ponto de entrada
                  |          |           |           |
              backend    APK Android   framework   página web
              desktop                  p/ Xcode
```

## Setup

JDK 17 e Android SDK são obrigatórios. Variáveis:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)   # ou o caminho do seu JDK 17
export ANDROID_HOME=~/Library/Android/sdk
echo "sdk.dir=$ANDROID_HOME" > local.properties
```

Pacotes do Android SDK: `platforms;android-35`, `build-tools;35.0.0`, `platform-tools`
(instale com `sdkmanager` depois de aceitar as licenças: `sdkmanager --licenses`).

## Backend (Ktor)

```bash
./gradlew :backend:run                       # http://localhost:8080
curl localhost:8080/health
curl -X POST localhost:8080/tasks -H 'Content-Type: application/json' -d '{"title":"estudar KMP","priority":"HIGH"}'
curl localhost:8080/tasks
curl -X POST localhost:8080/tasks/t1/done
./gradlew :backend:test
```

Aprenda: `Application.module()` como função de extensão, `ContentNegotiation`, `StatusPages`, mapeamento
`DomainError` para status HTTP (`when` exaustivo), `testApplication` sem rede.

## Desktop

```bash
./gradlew :composeApp:run
./gradlew :composeApp:packageDmg    # instalador (macOS)
```

## Web (Wasm)

```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun   # abre no navegador (precisa de um navegador com WasmGC: Chrome/Firefox atuais)
./gradlew :composeApp:wasmJsBrowserDistribution     # gera arquivos estáticos para hospedar
```

## Android

```bash
./gradlew :composeApp:assembleDebug     # gera o APK
./gradlew :composeApp:installDebug      # instala em emulador/aparelho conectado
```

Emulador: `sdkmanager "system-images;android-35;google_apis;arm64-v8a"` e
`avdmanager create avd -n lab -k "system-images;android-35;google_apis;arm64-v8a"`; depois `emulator -avd lab`.
Ou use o Android Studio (Device Manager), que faz isso por você.

## iOS

Pré-requisitos: Xcode instalado, um runtime de simulador (Xcode > Settings > Platforms) e XcodeGen.

```bash
brew install xcodegen
cd iosApp && xcodegen && open iosApp.xcodeproj
```

No Xcode, escolha um simulador e rode (Cmd+R). A fase "Compilar Kotlin (Gradle)" chama
`./gradlew :composeApp:embedAndSignAppleFrameworkForXcode` e gera `ComposeApp.framework`.

Se o Xcode não achar o Java: crie `~/.gradle/gradle.properties` com
`org.gradle.java.home=/caminho/do/jdk17`.

Só para verificar que o Kotlin compila para iOS (sem Xcode aberto):
`./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64`.

## Testes por plataforma

| Comando | O que roda |
|---------|-----------|
| `./gradlew :core:jvmTest` | testes comuns do core, na JVM |
| `./gradlew :lessons:test` | lições, exercícios (ignorados), CLI |
| `./gradlew :backend:test` | API com `testApplication` |
| `./gradlew :composeApp:desktopTest` | `TaskStore` (estado da UI) |
| `./gradlew :core:iosSimulatorArm64Test` | testes comuns em Kotlin/Native (exige runtime de simulador iOS) |

## Problemas comuns

- `Unsupported class file major version`: o Gradle está rodando em JDK errado. Confira `JAVA_HOME` (use 17).
- `SDK location not found`: falta `local.properties` com `sdk.dir`.
- `Failed to install the following Android SDK packages as some licences have not been accepted`:
  rode `sdkmanager --licenses`.
- `Xcode does not support simulator tests`: instale um runtime de simulador no Xcode.
- `JvmInline` não resolvido no `commonMain`: importe `kotlin.jvm.JvmInline`.
