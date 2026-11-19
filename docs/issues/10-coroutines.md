**Nível:** Intermediário  
**Validação automática:** `scripts/validate-module.sh 10` (o mesmo que o GitHub Actions roda no seu PR)

## Objetivo

Concluir o módulo 10: **Coroutines e Flow**, um passo do Task Manager que você constrói do zero.

## Leia antes

- Lesson06Coroutines.kt

## Conceitos

- `suspend`
- `async`/`await` e concorrência estruturada
- `coroutineScope`
- `Flow` frio
- Testar com `runTest`

## Tarefas

- [ ] `parallelMap` (Exercício 7)
- [ ] `countdown` como `Flow` (Exercício 7b)

## Arquivos

- `lessons/src/main/kotlin/lab/exercises/Exercises.kt`

## Critério de pronto

- [ ] `scripts/validate-module.sh 10` termina com "✔ Módulo 10 concluído."
- [ ] Nenhum `TODO("M10...")` restante nos arquivos do módulo
- [ ] PR aberto com `Closes #<número desta issue>` e check **Validar módulo** verde

## Desafio extra (opcional)

- Limite a concorrência do `parallelMap` a N itens por vez usando `Semaphore`.

## Nota

`parallelMap` precisa manter a ORDEM original e rodar em paralelo de verdade (o teste mede o tempo).

## Como entregar

```bash
git switch -c modulo/10-coroutines
# implemente, rode os testes do módulo até ficarem verdes
scripts/validate-module.sh 10
git push -u origin modulo/10-coroutines
# abra o PR para a main; ao fazer merge, a issue fecha sozinha
```

> Travou? Dica em `docs/FLUXO.md`. A solução completa está na branch `reference` (consulte só depois de tentar).
