**Nível:** Avançado  
**Validação automática:** `scripts/validate-module.sh 12` (o mesmo que o GitHub Actions roda no seu PR)

## Objetivo

Concluir o módulo 12: **Backend com Ktor**, um passo do Task Manager que você constrói do zero.

## Leia antes

- docs/PLATAFORMAS.md (seção Backend)
- O `ServerTest` é a especificação

## Conceitos

- Plugins do Ktor (`ContentNegotiation`, `StatusPages`)
- Rotas
- Mapear erro de domínio para status HTTP
- `testApplication`
- Thread-safety com decorator

## Tarefas

- [ ] `Application.module`
- [ ] `taskRoutes`
- [ ] `LockedTaskRepository`
- [ ] Subir (`./gradlew :backend:run`) e testar com `curl`

## Arquivos

- `backend/src/main/kotlin/lab/backend/Server.kt`
- `backend/src/main/kotlin/lab/backend/Routes.kt`
- `backend/src/main/kotlin/lab/backend/LockedTaskRepository.kt`

## Critério de pronto

- [ ] `scripts/validate-module.sh 12` termina com "✔ Módulo 12 concluído."
- [ ] Nenhum `TODO("M12...")` restante nos arquivos do módulo
- [ ] PR aberto com `Closes #<número desta issue>` e check **Validar módulo** verde

## Desafio extra (opcional)

- Adicione `GET /tasks/{id}` com seus próprios testes (arquivo novo), devolvendo 404 quando não existir.

## Nota

Nunca devolva stack trace ao cliente. O `when` sobre `DomainError` precisa ser exaustivo.

## Como entregar

```bash
git switch -c modulo/12-backend
# implemente, rode os testes do módulo até ficarem verdes
scripts/validate-module.sh 12
git push -u origin modulo/12-backend
# abra o PR para a main; ao fazer merge, a issue fecha sozinha
```

> Travou? Dica em `docs/FLUXO.md`. A solução completa está na branch `reference` (consulte só depois de tentar).
