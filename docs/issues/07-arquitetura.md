**Nível:** Intermediário  
**Validação automática:** `scripts/validate-module.sh 07` (o mesmo que o GitHub Actions roda no seu PR)

## Objetivo

Concluir o módulo 07: **Casos de uso e arquitetura limpa**, um passo do Task Manager que você constrói do zero.

## Leia antes

- docs/ARQUITETURA.md (inteiro)
- docs/CLEAN_CODE.md (seção 5, SOLID)

## Conceitos

- Regra de dependência e inversão (ports/adapters)
- Um caso de uso por classe
- Injeção de dependência por construtor
- `operator fun invoke`
- `Clock` e `IdGenerator` como portas

## Tarefas

- [ ] `InMemoryTaskRepository` e `SequentialIdGenerator`
- [ ] `CreateTask`, `CompleteTask` e `ListTasks`

## Arquivos

- `core/src/commonMain/kotlin/lab/taskmanager/application/UseCases.kt`
- `core/src/commonMain/kotlin/lab/taskmanager/infrastructure/Adapters.kt`

## Critério de pronto

- [ ] `scripts/validate-module.sh 07` termina com "✔ Módulo 07 concluído."
- [ ] Nenhum `TODO("M07...")` restante nos arquivos do módulo
- [ ] PR aberto com `Closes #<número desta issue>` e check **Validar módulo** verde

## Desafio extra (opcional)

- Prove a regra de dependência: `grep -rn "infrastructure" core/src/commonMain/kotlin/lab/taskmanager/domain` deve ficar vazio.

## Nota

`TaskModule` já vem pronto: leia para entender a composição. Não use `Clock.System` nem gere ids dentro dos casos de uso.

## Como entregar

```bash
git switch -c modulo/07-arquitetura
# implemente, rode os testes do módulo até ficarem verdes
scripts/validate-module.sh 07
git push -u origin modulo/07-arquitetura
# abra o PR para a main; ao fazer merge, a issue fecha sozinha
```

> Travou? Dica em `docs/FLUXO.md`. A solução completa está na branch `reference` (consulte só depois de tentar).
