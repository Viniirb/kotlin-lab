**Nível:** Avançado  
**Validação automática:** `scripts/validate-module.sh 14` (o mesmo que o GitHub Actions roda no seu PR)

## Objetivo

Concluir o módulo 14: **UI com Compose**, um passo do Task Manager que você constrói do zero.

## Leia antes

- docs/PLATAFORMAS.md
- docs/CLEAN_CODE.md (seção 1)

## Conceitos

- Compose declarativo e recomposição
- `remember`, `mutableStateOf`, `by`
- State holder separado da UI (`TaskStore`)
- `LazyColumn` com `key`
- Estado levantado (state hoisting)

## Tarefas

- [ ] `TaskStore` (o `TaskStoreTest` é a especificação)
- [ ] `App()`: a tela completa descrita no KDoc

## Arquivos

- `composeApp/src/commonMain/kotlin/lab/compose/TaskStore.kt`
- `composeApp/src/commonMain/kotlin/lab/compose/App.kt`

## Critério de pronto

- [ ] `scripts/validate-module.sh 14` termina com "✔ Módulo 14 concluído."
- [ ] Nenhum `TODO("M14...")` restante nos arquivos do módulo
- [ ] PR aberto com `Closes #<número desta issue>` e check **Validar módulo** verde

## Desafio extra (opcional)

- Adicione um contador "N abertas" no topo, calculado a partir do estado.

## Nota

Para VER a tela, faça o desktop do módulo 15 (`Main.kt`) ainda neste PR de estudo local, ou no módulo seguinte.

## Como entregar

```bash
git switch -c modulo/14-compose
# implemente, rode os testes do módulo até ficarem verdes
scripts/validate-module.sh 14
git push -u origin modulo/14-compose
# abra o PR para a main; ao fazer merge, a issue fecha sozinha
```

> Travou? Dica em `docs/FLUXO.md`. A solução completa está na branch `reference` (consulte só depois de tentar).
