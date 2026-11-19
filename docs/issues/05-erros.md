**Nível:** Intermediário  
**Validação automática:** `scripts/validate-module.sh 05` (o mesmo que o GitHub Actions roda no seu PR)

## Objetivo

Concluir o módulo 05: **Erros como valor**, um passo do Task Manager que você constrói do zero.

## Leia antes

- Lesson04Errors.kt
- docs/CLEAN_CODE.md (seção 4)

## Conceitos

- `Outcome<out V, out E>` e variância
- Funções `inline` com lambdas
- `map` vs `flatMap` vs `fold`
- Exceção vs erro esperado

## Tarefas

- [ ] Implementar `map`, `flatMap` e `fold` em `Outcome.kt`
- [ ] Implementar `parsePositive` (Exercício 5)

## Arquivos

- `core/src/commonMain/kotlin/lab/shared/Outcome.kt`
- `lessons/src/main/kotlin/lab/exercises/Exercises.kt`

## Critério de pronto

- [ ] `scripts/validate-module.sh 05` termina com "✔ Módulo 05 concluído."
- [ ] Nenhum `TODO("M05...")` restante nos arquivos do módulo
- [ ] PR aberto com `Closes #<número desta issue>` e check **Validar módulo** verde

## Desafio extra (opcional)

- Escreva `mapError` (transforma só o erro) com testes seus.

## Nota

`flatMap` NÃO deve chamar a lambda depois de uma falha: o teste confere isso.

## Como entregar

```bash
git switch -c modulo/05-erros
# implemente, rode os testes do módulo até ficarem verdes
scripts/validate-module.sh 05
git push -u origin modulo/05-erros
# abra o PR para a main; ao fazer merge, a issue fecha sozinha
```

> Travou? Dica em `docs/FLUXO.md`. A solução completa está na branch `reference` (consulte só depois de tentar).
