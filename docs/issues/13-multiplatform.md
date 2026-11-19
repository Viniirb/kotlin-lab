**Nível:** Avançado  
**Validação automática:** `scripts/validate-module.sh 13` (o mesmo que o GitHub Actions roda no seu PR)

## Objetivo

Concluir o módulo 13: **Multiplatform com expect/actual**, um passo do Task Manager que você constrói do zero.

## Leia antes

- Lesson07Multiplatform.kt
- docs/PLATAFORMAS.md (diagrama de source sets)

## Conceitos

- Source sets (`commonMain`, `jvmMain`, `androidMain`, `iosMain`, `wasmJsMain`)
- `expect`/`actual`
- Um mesmo `:core` em 4 alvos
- O que é código comum e o que não é

## Tarefas

- [ ] Os quatro `actual fun platformName()` (JVM, Android, iOS, Wasm)
- [ ] Compilar o core para TODOS os alvos (o script faz isso)

## Arquivos

- `core/src/jvmMain/.../Platform.jvm.kt`
- `core/src/androidMain/.../Platform.android.kt`
- `core/src/iosMain/.../Platform.ios.kt`
- `core/src/wasmJsMain/.../Platform.wasmJs.kt`

## Critério de pronto

- [ ] `scripts/validate-module.sh 13` termina com "✔ Módulo 13 concluído."
- [ ] Nenhum `TODO("M13...")` restante nos arquivos do módulo
- [ ] PR aberto com `Closes #<número desta issue>` e check **Validar módulo** verde

## Desafio extra (opcional)

- Crie um novo par `expect fun platformCores(): Int` com `actual` em cada plataforma e use-o na tela.

## Nota

No macOS o script também compila o alvo iOS; no CI (Linux) ele é ignorado.

## Como entregar

```bash
git switch -c modulo/13-multiplatform
# implemente, rode os testes do módulo até ficarem verdes
scripts/validate-module.sh 13
git push -u origin modulo/13-multiplatform
# abra o PR para a main; ao fazer merge, a issue fecha sozinha
```

> Travou? Dica em `docs/FLUXO.md`. A solução completa está na branch `reference` (consulte só depois de tentar).
