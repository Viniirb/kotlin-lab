**Nível:** Intermediário  
**Validação automática:** `scripts/validate-module.sh 08` (o mesmo que o GitHub Actions roda no seu PR)

## Objetivo

Concluir o módulo 08: **Terminal e testes**, um passo do Task Manager que você constrói do zero.

## Leia antes

- docs/CLEAN_CODE.md (seção 8, testes)
- O `CliTest` é a especificação exata dos textos

## Conceitos

- Camada de apresentação
- Entrada e saída injetadas (testável sem terminal)
- Despachar comandos com `when`
- Testar sem mocks

## Tarefas

- [ ] Implementar `Cli.run` conforme o contrato do KDoc
- [ ] Rodar o app: `./gradlew :lessons:run`
- [ ] ESCREVER 3 testes SEUS em um arquivo NOVO `lessons/src/test/kotlin/lab/taskmanager/MeusTestesDoCliTest.kt` (por exemplo: lista vazia, prioridade inválida, comando em maiúsculas)

## Arquivos

- `lessons/src/main/kotlin/lab/taskmanager/presentation/Cli.kt`

## Critério de pronto

- [ ] `scripts/validate-module.sh 08` termina com "✔ Módulo 08 concluído."
- [ ] Nenhum `TODO("M08...")` restante nos arquivos do módulo
- [ ] PR aberto com `Closes #<número desta issue>` e check **Validar módulo** verde

## Desafio extra (opcional)

- Adicione o comando `count` que imprime quantas tarefas existem em cada prioridade (use `countByPriority`).

## Nota

Não altere o `CliTest` existente: o CI recusa PR que modifica testes já existentes. Testes novos em arquivos novos são bem-vindos.

## Como entregar

```bash
git switch -c modulo/08-terminal
# implemente, rode os testes do módulo até ficarem verdes
scripts/validate-module.sh 08
git push -u origin modulo/08-terminal
# abra o PR para a main; ao fazer merge, a issue fecha sozinha
```

> Travou? Dica em `docs/FLUXO.md`. A solução completa está na branch `reference` (consulte só depois de tentar).
