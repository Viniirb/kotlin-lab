**Nível:** Básico  
**Validação automática:** `scripts/validate-module.sh 03` (o mesmo que o GitHub Actions roda no seu PR)

## Objetivo

Concluir o módulo 03: **Classes, data, enum e value class**, um passo do Task Manager que você constrói do zero.

## Leia antes

- Lesson02Types.kt: seções data, enum/object/companion e value class
- docs/CLEAN_CODE.md (seções 1 e 2)

## Conceitos

- `value class` com construtor privado
- `companion object` como fábrica
- `enum` com propriedade
- Propriedade calculada (getter)
- Validar na construção

## Tarefas

- [ ] `Title.of`: aparar, validar vazio e tamanho máximo, devolver `Outcome`
- [ ] `Priority.weight`: LOW=1, MEDIUM=2, HIGH=3
- [ ] `Priority.parse`: ignora caixa e espaços; desconhecido vira `null`
- [ ] `Task.isDone`

## Arquivos

- `core/src/commonMain/kotlin/lab/taskmanager/domain/Task.kt`

## Critério de pronto

- [ ] `scripts/validate-module.sh 03` termina com "✔ Módulo 03 concluído."
- [ ] Nenhum `TODO("M03...")` restante nos arquivos do módulo
- [ ] PR aberto com `Closes #<número desta issue>` e check **Validar módulo** verde

## Desafio extra (opcional)

- Troque o enum por `enum class Priority(val weight: Int)` (construtor com peso) em vez de getter calculado.

## Nota

Os tipos `TaskId`, `TaskStatus`, `Task` e `DomainError` já estão declarados. Você implementa os comportamentos marcados `TODO("M03...")`.

## Como entregar

```bash
git switch -c modulo/03-classes
# implemente, rode os testes do módulo até ficarem verdes
scripts/validate-module.sh 03
git push -u origin modulo/03-classes
# abra o PR para a main; ao fazer merge, a issue fecha sozinha
```

> Travou? Dica em `docs/FLUXO.md`. A solução completa está na branch `reference` (consulte só depois de tentar).
