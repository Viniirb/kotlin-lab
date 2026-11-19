**Nível:** Intermediário  
**Validação automática:** `scripts/validate-module.sh 09` (o mesmo que o GitHub Actions roda no seu PR)

## Objetivo

Concluir o módulo 09: **Genéricos, delegação e DSL**, um passo do Task Manager que você constrói do zero.

## Leia antes

- Lesson05Generics.kt

## Conceitos

- Genéricos e variância (`out`, `in`)
- Delegação de interface (`by`)
- Lambda com receptor (DSL)
- Funções `inline`

## Tarefas

- [ ] `getOrElse` e `split` em `Outcome.kt`
- [ ] `LoggingTaskRepository` (decorator; tente com `by delegate`)
- [ ] `TaskSeed` e `TaskModule.seed { ... }`

## Arquivos

- `core/src/commonMain/kotlin/lab/shared/Outcome.kt`
- `core/src/commonMain/kotlin/lab/taskmanager/infrastructure/LoggingTaskRepository.kt`
- `core/src/commonMain/kotlin/lab/taskmanager/TaskSeed.kt`

## Critério de pronto

- [ ] `scripts/validate-module.sh 09` termina com "✔ Módulo 09 concluído."
- [ ] Nenhum `TODO("M09...")` restante nos arquivos do módulo
- [ ] PR aberto com `Closes #<número desta issue>` e check **Validar módulo** verde

## Desafio extra (opcional)

- Proteja a DSL com `@DslMarker` e explique o que muda.

## Nota

Em `TaskSeed`, `low/medium/high` só ANOTAM; criar de fato é papel de `seed`.

## Como entregar

```bash
git switch -c modulo/09-genericos
# implemente, rode os testes do módulo até ficarem verdes
scripts/validate-module.sh 09
git push -u origin modulo/09-genericos
# abra o PR para a main; ao fazer merge, a issue fecha sozinha
```

> Travou? Dica em `docs/FLUXO.md`. A solução completa está na branch `reference` (consulte só depois de tentar).
