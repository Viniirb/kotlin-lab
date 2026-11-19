**Nível:** Básico  
**Validação automática:** `scripts/validate-module.sh 00` (o mesmo que o GitHub Actions roda no seu PR)

## Objetivo

Concluir o módulo 00: **Setup e primeira execução**, um passo do Task Manager que você constrói do zero.

## Leia antes

- README.md
- docs/PLATAFORMAS.md (seção Setup)

## Conceitos

- JDK 17
- Gradle wrapper (`./gradlew`)
- Estrutura de módulos do projeto
- Fluxo de estudo por branch e PR

## Tarefas

- [ ] Instalar o JDK 17 e conferir `java -version` (17.x)
- [ ] Rodar `./gradlew :lessons:run --args="lesson 1"` e ver a saída da lição 1
- [ ] Rodar `scripts/progress.sh 00` e ver o módulo 00 concluído
- [ ] Ler `docs/FLUXO.md` (como entregar cada módulo)
- [ ] Abrir um PR deste módulo seguindo o fluxo (mesmo sem mudar código, adicione a você mesmo em `docs/ESTUDANTE.md` com uma linha sobre seu objetivo)

## Critério de pronto

- [ ] `scripts/validate-module.sh 00` termina com "✔ Módulo 00 concluído."
- [ ] Nenhum `TODO("M00...")` restante nos arquivos do módulo
- [ ] PR aberto com `Closes #<número desta issue>` e check **Validar módulo** verde

## Desafio extra (opcional)

- Rode `./gradlew projects` e explique, com suas palavras, o papel de cada módulo.

## Nota

Este módulo existe para você treinar o FLUXO (branch, PR, validação, issue fechada) antes do código de verdade.

## Como entregar

```bash
git switch -c modulo/00-setup
# implemente, rode os testes do módulo até ficarem verdes
scripts/validate-module.sh 00
git push -u origin modulo/00-setup
# abra o PR para a main; ao fazer merge, a issue fecha sozinha
```

> Travou? Dica em `docs/FLUXO.md`. A solução completa está na branch `reference` (consulte só depois de tentar).
