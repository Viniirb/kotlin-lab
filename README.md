> **Branch `reference`: solução completa de todos os módulos.**
> Não leia antes de tentar. O caminho de estudo está na `main` (esqueleto com testes falhando). Aqui tudo passa.
> Consulte um arquivo só quando travar, e depois refaça de memória.

# kotlin-lab

Laboratório para aprender Kotlin em todas as plataformas: lógica, arquitetura, clean code, backend, Android, iOS,
desktop e web. Um único núcleo de regras de negócio (`:core`) é reaproveitado por todos os alvos.

## Mapa dos módulos

| Módulo | Plataforma | O que aprender | Rodar |
|--------|-----------|----------------|-------|
| `:core` | KMP (JVM, Android, iOS, Wasm) | Regras de negócio compartilhadas, `expect/actual`, serialização | `./gradlew :core:jvmTest` |
| `:lessons` | JVM / terminal | 7 lições, 7 exercícios, Task Manager no terminal | `./gradlew :lessons:run --args="lesson 1"` |
| `:backend` | JVM / servidor | API REST com Ktor, status HTTP, testes de integração | `./gradlew :backend:run` |
| `:composeApp` | Android | App nativo com Compose | `./gradlew :composeApp:installDebug` |
| `:composeApp` | Desktop (macOS/Win/Linux) | Janela com Compose | `./gradlew :composeApp:run` |
| `:composeApp` | Web (Wasm) | Compose no navegador | `./gradlew :composeApp:wasmJsBrowserDevelopmentRun` |
| `iosApp/` | iOS | Compose dentro de SwiftUI | ver `docs/PLATAFORMAS.md` |

## Setup (uma vez)

1. JDK 17 (o Gradle e o Android exigem). Aponte `JAVA_HOME` para ele: `export JAVA_HOME=$(/usr/libexec/java_home -v 17)`.
2. Android SDK. O arquivo `local.properties` (não versionado) precisa de `sdk.dir=/caminho/do/sdk`.
3. iOS: Xcode instalado e `brew install xcodegen`.

Detalhes e solução de problemas: `docs/PLATAFORMAS.md`.

## Trilha de estudo (siga a ordem)

| Etapa | O que fazer | Onde |
|-------|-------------|------|
| 1 | Lições 1 a 3: fundamentos, tipos, funcional | `lessons/.../lessons/Lesson01..03` |
| 2 | Lições 4 a 6: erros, genéricos/delegação, coroutines | `lessons/.../lessons/Lesson04..06` |
| 3 | Exercícios 1 a 7 (um por vez, removendo `@Ignore`) | `lessons/.../exercises/` e `ExercisesTest.kt` |
| 4 | Ler o app em camadas: domain, application, infrastructure | `core/src/commonMain/.../taskmanager/` + `docs/ARQUITETURA.md` |
| 5 | Clean code e princípios | `docs/CLEAN_CODE.md` |
| 6 | Lição 7: multiplatform e serialização | `Lesson07Multiplatform.kt` |
| 7 | Backend: rotas, erros HTTP, testes | `backend/` |
| 8 | UI declarativa: Compose, state holder, recomposição | `composeApp/src/commonMain/` |
| 9 | Cada plataforma: ponto de entrada nativo | `androidMain`, `desktopMain`, `iosMain` + `iosApp/`, `wasmJsMain` |
| 10 | Desafios (abaixo) | em todo o projeto |

### Como estudar cada lição

1. Leia o arquivo inteiro antes de rodar.
2. Rode e compare a saída com o que previu.
3. Mude algo (adicione uma variante `sealed`, tire um `?`) e leia o erro do compilador. Erro de compilação é professor.
4. Só então avance.

### Exercícios

Implemente o `TODO()` em `lessons/.../exercises/Exercises.kt`. Cada classe de `ExercisesTest.kt` começa com `@Ignore`.
Remova o `@Ignore` de uma classe, rode `./gradlew :lessons:test --tests "*Exercise1*"`, faça passar, passe para a próxima.

### Desafios

Fáceis:
1. Comando `rename <id> <título>` no terminal: novo caso de uso, método na entidade, erro novo tratado nos 3 lugares
   (`userMessage`, `code`, `status` HTTP). Repare como o compilador guia você.
2. Campo `dueDate` opcional, filtro `overdue`. Injete o `Clock` e teste com data fixa.

Médios:
3. Endpoint `DELETE /tasks/{id}` no backend e botão "excluir" na UI. Mude o domínio uma vez, veja todos os clientes usarem.
4. Persistência em arquivo: `FileTaskRepository` (JVM) sem alterar domínio nem casos de uso.
5. Tornar `CompleteTask` atômico no backend (ver o comentário em `LockedTaskRepository`).

Difíceis:
6. Cliente HTTP no `composeApp` (Ktor Client) para usar o backend em vez do repositório local. Troque só o adaptador.
7. Persistência multiplataforma com SQLDelight ou Room KMP.
8. Autenticação JWT no backend e tela de login nos clientes.
9. Interop: exponha uma função do `:core` para o Swift e chame-a de uma tela SwiftUI nativa (sem Compose).

## Estrutura

```
kotlin-lab
├── core/                      KMP: domínio, casos de uso, contratos (DTO), expect/actual
│   └── src/{commonMain,commonTest,jvmMain,androidMain,iosMain,wasmJsMain}
├── lessons/                   JVM: lições, exercícios, Task Manager no terminal
├── backend/                   JVM: API Ktor (usa o :core)
├── composeApp/                Compose Multiplatform: Android, Desktop, iOS (framework), Web
├── iosApp/                    Projeto SwiftUI (gerado com XcodeGen) que hospeda o composeApp
├── docs/                      ARQUITETURA, CLEAN_CODE, PLATAFORMAS
└── gradle/libs.versions.toml  versões de TODAS as dependências em um só lugar
```

## Referências

- Documentação: https://kotlinlang.org/docs/home.html, https://kotlinlang.org/docs/multiplatform.html
- Compose Multiplatform: https://www.jetbrains.com/help/kotlin-multiplatform-dev/
- Ktor: https://ktor.io/docs/
- Livros: "Kotlin in Action" (Jemerov/Isakova), "Effective Kotlin" (Moskała)
- Estilo oficial: https://kotlinlang.org/docs/coding-conventions.html
