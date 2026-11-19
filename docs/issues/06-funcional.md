**Nível:** Intermediário  
**Validação automática:** `scripts/validate-module.sh 06` (o mesmo que o GitHub Actions roda no seu PR)

## Objetivo

Concluir o módulo 06: **Funcional e extensões**, um passo do Task Manager que você constrói do zero.

## Leia antes

- Lesson03Functional.kt: funções de ordem superior, scope functions e extensões

## Conceitos

- Funções de extensão
- `compareByDescending` + `thenBy`
- `associateWith`
- Lambdas, `runCatching` e `Result`

## Tarefas

- [ ] `List<Task>.sortedByUrgency()` e `List<Task>.countByPriority()`
- [ ] `retry(times, block)` (Exercício 6)

## Arquivos

- `core/src/commonMain/kotlin/lab/taskmanager/domain/TaskExtensions.kt`
- `lessons/src/main/kotlin/lab/exercises/Exercises.kt`

## Critério de pronto

- [ ] `scripts/validate-module.sh 06` termina com "✔ Módulo 06 concluído."
- [ ] Nenhum `TODO("M06...")` restante nos arquivos do módulo
- [ ] PR aberto com `Closes #<número desta issue>` e check **Validar módulo** verde

## Desafio extra (opcional)

- Faça `retry` aceitar um `delayMillis` e uma lista de exceções que NÃO devem ser repetidas.

## Nota

`countByPriority` precisa incluir prioridades com zero tarefas.

## Como entregar

```bash
git switch -c modulo/06-funcional
# implemente, rode os testes do módulo até ficarem verdes
scripts/validate-module.sh 06
git push -u origin modulo/06-funcional
# abra o PR para a main; ao fazer merge, a issue fecha sozinha
```

> Travou? Dica em `docs/FLUXO.md`. A solução completa está na branch `reference` (consulte só depois de tentar).
