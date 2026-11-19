# kotlin-lab

Aprenda Kotlin construindo um produto real, do básico ao avançado, em todas as plataformas:
terminal, backend, Android, iOS, desktop e web. O **Task Manager** começa incompleto e cresce a cada módulo.
Você escreve o código; testes automáticos dizem se o módulo está certo.

## Como funciona

- **17 módulos**, cada um é uma **issue** deste repositório (objetivo, tarefas, critério de pronto).
- Cada módulo tem **testes que já existem e começam falhando**. Seu trabalho: trocar os `TODO("MNN: ...")` por código até os testes ficarem verdes.
- Você entrega cada módulo por **branch + PR**. O **GitHub Actions** valida o módulo; a issue fecha ao mesclar.
- As **lições** (`lessons/`) são a teoria: arquivos comentados que você lê e executa. Cada módulo diz qual ler antes.
- A solução completa fica na branch **`reference`**. Consulte só depois de tentar.

Detalhes do fluxo: [`docs/FLUXO.md`](docs/FLUXO.md).

## Trilha

| # | Módulo | Nível | Aprende |
|---|--------|-------|---------|
| 00 | Setup e primeira execução | Básico | Ambiente, Gradle, fluxo de PR |
| 01 | Fundamentos | Básico | `val/var`, null safety, `when`, funções |
| 02 | Coleções e lambdas | Básico | `filter/map/groupBy`, `Sequence` |
| 03 | Classes, data, enum e value class | Básico | Modelar o domínio |
| 04 | Tipos fechados e `when` | Básico | `sealed`, exaustividade, imutabilidade |
| 05 | Erros como valor | Intermediário | `Outcome`, `map/flatMap/fold`, `inline` |
| 06 | Funcional e extensões | Intermediário | Extensões, ordem superior, `Result` |
| 07 | Casos de uso e arquitetura limpa | Intermediário | Ports/adapters, SOLID, injeção |
| 08 | Terminal e testes | Intermediário | Apresentação, testes sem mocks |
| 09 | Genéricos, delegação e DSL | Intermediário | Variância, `by`, lambda com receptor |
| 10 | Coroutines e Flow | Intermediário | Concorrência estruturada, `Flow` |
| 11 | Serialização e persistência | Intermediário | `kotlinx.serialization`, JSON em arquivo |
| 12 | Backend com Ktor | Avançado | API REST, status HTTP, testes |
| 13 | Multiplatform com expect/actual | Avançado | Source sets, 4 alvos |
| 14 | UI com Compose | Avançado | Estado, recomposição, state holder |
| 15 | Android, iOS, desktop e web | Avançado | Ponto de entrada de cada plataforma |
| 16 | Avançado e projeto livre | Avançado | Interop, rede, banco, Gradle |

## Estrutura

```
kotlin-lab
├── core/          KMP: domínio, casos de uso, contrato JSON, expect/actual (módulos 03 a 11, 13)
├── lessons/       JVM: lições (teoria), exercícios e terminal (módulos 01, 02, 04 a 06, 08, 10)
├── backend/       JVM: API Ktor (módulo 12)
├── composeApp/    Compose Multiplatform: Android, desktop, iOS, web (módulos 14 e 15)
├── iosApp/        Projeto SwiftUI gerado com XcodeGen
├── scripts/       validate-module.sh, progress.sh
├── docs/          FLUXO, ARQUITETURA, CLEAN_CODE, PLATAFORMAS, issues/
└── .github/       Workflow que valida cada módulo
```

## Comece aqui

1. JDK 17 (`java -version`). Veja `docs/PLATAFORMAS.md` para Android SDK e Xcode.
2. `./gradlew :lessons:run --args="lesson 1"` (deve imprimir a lição 1).
3. `scripts/validate-module.sh 00` (deve terminar com ✔).
4. Abra a issue do **Módulo 00** e siga as tarefas.

## Comandos úteis

```bash
./gradlew :lessons:run --args="lessons"     # lista as lições
./gradlew :lessons:run --args="lesson 3"    # roda uma lição
scripts/validate-module.sh 03               # valida um módulo
scripts/progress.sh                         # quadro de progresso (todos os módulos)
./gradlew :lessons:run                      # terminal do Task Manager (após o módulo 08)
./gradlew :backend:run                      # API (após o módulo 12)
./gradlew :composeApp:run                   # desktop (após os módulos 14 e 15)
```

## Referências

- https://kotlinlang.org/docs/home.html e https://kotlinlang.org/docs/multiplatform.html
- Compose Multiplatform: https://www.jetbrains.com/help/kotlin-multiplatform-dev/
- Ktor: https://ktor.io/docs/
- Livros: "Kotlin in Action" (Jemerov/Isakova), "Effective Kotlin" (Moskała)
- Estilo oficial: https://kotlinlang.org/docs/coding-conventions.html
