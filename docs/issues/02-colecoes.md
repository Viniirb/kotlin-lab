**Nível:** Básico  
**Validação automática:** `scripts/validate-module.sh 02` (o mesmo que o GitHub Actions roda no seu PR)

## Objetivo

Concluir o módulo 02: **Coleções e lambdas**, um passo do Task Manager que você constrói do zero.

## Leia antes

- Lesson03Functional.kt: seções Coleções e Sequence

## Conceitos

- `filter`, `map`, `groupBy`, `groupingBy.eachCount`
- `sortedWith` + `compareBy`
- `runningFold`
- List vs Sequence

## Tarefas

- [ ] Implementar `wordFrequency` (Exercício 3)
- [ ] Implementar `groupAnagrams` (Exercício 3b)
- [ ] Implementar `runningTotal` (Exercício 3c)

## Arquivos

- `lessons/src/main/kotlin/lab/exercises/Exercises.kt`

## Critério de pronto

- [ ] `scripts/validate-module.sh 02` termina com "✔ Módulo 02 concluído."
- [ ] Nenhum `TODO("M02...")` restante nos arquivos do módulo
- [ ] PR aberto com `Closes #<número desta issue>` e check **Validar módulo** verde

## Desafio extra (opcional)

- Implemente `wordFrequency` com `Sequence` e explique quando isso compensa.

## Nota

Nada de `for` com lista mutável auxiliar: descreva o resultado com operadores de coleção.

## Como entregar

```bash
git switch -c modulo/02-colecoes
# implemente, rode os testes do módulo até ficarem verdes
scripts/validate-module.sh 02
git push -u origin modulo/02-colecoes
# abra o PR para a main; ao fazer merge, a issue fecha sozinha
```

> Travou? Dica em `docs/FLUXO.md`. A solução completa está na branch `reference` (consulte só depois de tentar).
