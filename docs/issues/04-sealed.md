**Nível:** Básico  
**Validação automática:** `scripts/validate-module.sh 04` (o mesmo que o GitHub Actions roda no seu PR)

## Objetivo

Concluir o módulo 04: **Tipos fechados e `when` exaustivo**, um passo do Task Manager que você constrói do zero.

## Leia antes

- Lesson02Types.kt: seção sealed
- docs/ARQUITETURA.md (tabela "Onde cada ideia aparece")

## Conceitos

- `sealed interface`
- `when` exaustivo SEM `else`
- Smart cast
- Estados que carregam dados (`Done(completedAt)`)
- Imutabilidade com `copy`

## Tarefas

- [ ] `Task.complete`: aberta vira `Done`, já concluída falha com `AlreadyCompleted`; a original não muda
- [ ] `DomainError.code()` e `DomainError.userMessage()` em `TaskContract.kt` (um código distinto por erro)
- [ ] `perimeter(figure)` (Exercício 4), `when` sem `else`

## Arquivos

- `core/src/commonMain/kotlin/lab/taskmanager/domain/Task.kt`
- `core/src/commonMain/kotlin/lab/taskmanager/TaskContract.kt`
- `lessons/src/main/kotlin/lab/exercises/Exercises.kt`

## Critério de pronto

- [ ] `scripts/validate-module.sh 04` termina com "✔ Módulo 04 concluído."
- [ ] Nenhum `TODO("M04...")` restante nos arquivos do módulo
- [ ] PR aberto com `Closes #<número desta issue>` e check **Validar módulo** verde

## Desafio extra (opcional)

- Num branch de experimento, adicione um `DomainError` novo e veja o compilador apontar cada `when` incompleto. Depois descarte o experimento.

## Nota

Use `success()` e `failure()` de `lab.shared` para montar o `Outcome`.

## Como entregar

```bash
git switch -c modulo/04-sealed
# implemente, rode os testes do módulo até ficarem verdes
scripts/validate-module.sh 04
git push -u origin modulo/04-sealed
# abra o PR para a main; ao fazer merge, a issue fecha sozinha
```

> Travou? Dica em `docs/FLUXO.md`. A solução completa está na branch `reference` (consulte só depois de tentar).
