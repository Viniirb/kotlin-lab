**Nível:** Básico  
**Validação automática:** `scripts/validate-module.sh 01` (o mesmo que o GitHub Actions roda no seu PR)

## Objetivo

Concluir o módulo 01: **Fundamentos**, um passo do Task Manager que você constrói do zero.

## Leia antes

- lessons/src/main/kotlin/lab/lessons/Lesson01Basics.kt (rode: `./gradlew :lessons:run --args="lesson 1"`)

## Conceitos

- `val` vs `var`
- Null safety (`?`, `?.`, `?:`)
- `when` e `if` como expressão
- Ranges e loops
- Funções com padrão, nomeadas e `vararg`

## Tarefas

- [ ] Implementar `fizzBuzz(n)` (Exercício 1)
- [ ] Implementar `firstNonBlank(vararg)` (Exercício 2)

## Arquivos

- `lessons/src/main/kotlin/lab/exercises/Exercises.kt`

## Critério de pronto

- [ ] `scripts/validate-module.sh 01` termina com "✔ Módulo 01 concluído."
- [ ] Nenhum `TODO("M01...")` restante nos arquivos do módulo
- [ ] PR aberto com `Closes #<número desta issue>` e check **Validar módulo** verde

## Desafio extra (opcional)

- Reescreva `fizzBuzz` sem nenhum `if` (só `when`). Depois, sem `when` (só `mapOf` e `getOrElse`).

## Nota

Sem `!!` em nenhum lugar. Se precisar dele, repense o tipo.

## Como entregar

```bash
git switch -c modulo/01-fundamentos
# implemente, rode os testes do módulo até ficarem verdes
scripts/validate-module.sh 01
git push -u origin modulo/01-fundamentos
# abra o PR para a main; ao fazer merge, a issue fecha sozinha
```

> Travou? Dica em `docs/FLUXO.md`. A solução completa está na branch `reference` (consulte só depois de tentar).
