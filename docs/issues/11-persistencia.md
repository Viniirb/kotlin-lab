**Nível:** Intermediário  
**Validação automática:** `scripts/validate-module.sh 11` (o mesmo que o GitHub Actions roda no seu PR)

## Objetivo

Concluir o módulo 11: **Serialização e persistência**, um passo do Task Manager que você constrói do zero.

## Leia antes

- Lesson07Multiplatform.kt (seção serialização)
- docs/ARQUITETURA.md

## Conceitos

- `kotlinx.serialization`
- DTO como contrato
- Dados externos não são confiáveis (`toTask` devolve `null`)
- Outra implementação da MESMA porta
- `jvmMain` vs `commonMain`

## Tarefas

- [ ] `Task.toDto()` e `TaskDto.toTask()` em `TaskContract.kt`
- [ ] `FileTaskRepository` (JSON em arquivo)

## Arquivos

- `core/src/commonMain/kotlin/lab/taskmanager/TaskContract.kt`
- `core/src/jvmMain/kotlin/lab/taskmanager/infrastructure/FileTaskRepository.kt`

## Critério de pronto

- [ ] `scripts/validate-module.sh 11` termina com "✔ Módulo 11 concluído."
- [ ] Nenhum `TODO("M11...")` restante nos arquivos do módulo
- [ ] PR aberto com `Closes #<número desta issue>` e check **Validar módulo** verde

## Desafio extra (opcional)

- Faça o terminal (`App.kt`) usar `FileTaskRepository` em `~/.kotlin-lab/tasks.json` para as tarefas sobreviverem a reinícios.

## Nota

Só `FileTaskRepository` usa `java.io`; o resto continua em código comum.

## Como entregar

```bash
git switch -c modulo/11-persistencia
# implemente, rode os testes do módulo até ficarem verdes
scripts/validate-module.sh 11
git push -u origin modulo/11-persistencia
# abra o PR para a main; ao fazer merge, a issue fecha sozinha
```

> Travou? Dica em `docs/FLUXO.md`. A solução completa está na branch `reference` (consulte só depois de tentar).
