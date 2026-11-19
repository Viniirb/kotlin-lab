**Nível:** Avançado  
**Validação automática:** `scripts/validate-module.sh 15` (o mesmo que o GitHub Actions roda no seu PR)

## Objetivo

Concluir o módulo 15: **Cada plataforma: Android, iOS, desktop e web**, um passo do Task Manager que você constrói do zero.

## Leia antes

- docs/PLATAFORMAS.md (inteiro)

## Conceitos

- Ponto de entrada de cada plataforma
- Android: `Activity` + `setContent`
- iOS: `ComposeUIViewController` + SwiftUI + XcodeGen
- Web: `ComposeViewport`
- Desktop: `application { Window }`

## Tarefas

- [ ] Desktop: `Main.kt` e rodar `./gradlew :composeApp:run`
- [ ] Android: `MainActivity` e `./gradlew :composeApp:installDebug` (emulador)
- [ ] iOS: `MainViewController`, `cd iosApp && xcodegen && open iosApp.xcodeproj`
- [ ] Web: `main.kt` e `./gradlew :composeApp:wasmJsBrowserDevelopmentRun`

## Arquivos

- `composeApp/src/desktopMain/.../Main.kt`
- `composeApp/src/androidMain/.../MainActivity.kt`
- `composeApp/src/iosMain/.../MainViewController.kt`
- `composeApp/src/wasmJsMain/.../main.kt`

## Critério de pronto

- [ ] `scripts/validate-module.sh 15` termina com "✔ Módulo 15 concluído."
- [ ] Nenhum `TODO("M15...")` restante nos arquivos do módulo
- [ ] PR aberto com `Closes #<número desta issue>` e check **Validar módulo** verde

## Desafio extra (opcional)

- Anexe ao PR uma captura de tela de cada plataforma rodando.

## Nota

O CI valida compilação (e que não restou `TODO`); ver a tela rodando é com você. No Linux o CI não compila iOS.

## Como entregar

```bash
git switch -c modulo/15-plataformas
# implemente, rode os testes do módulo até ficarem verdes
scripts/validate-module.sh 15
git push -u origin modulo/15-plataformas
# abra o PR para a main; ao fazer merge, a issue fecha sozinha
```

> Travou? Dica em `docs/FLUXO.md`. A solução completa está na branch `reference` (consulte só depois de tentar).
