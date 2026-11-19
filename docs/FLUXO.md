# Fluxo de estudo

Cada módulo é uma **issue** no GitHub. Você entrega cada um com uma **branch** e um **PR**. O GitHub Actions
roda os testes do módulo; a issue fecha sozinha quando o PR é mesclado.

## Passo a passo (por módulo)

1. Abra a issue do módulo (a issue #N é o módulo N-1: #1 = módulo 00, #2 = módulo 01, ...) e leia "Leia antes", "Conceitos" e "Tarefas".
2. Leia a lição indicada (e rode: `./gradlew :lessons:run --args="lesson N"`).
3. Crie a branch: `git switch -c modulo/NN-nome` (o nome vem na issue; o prefixo `modulo/NN-` é obrigatório).
4. Implemente no lugar dos `TODO("MNN: ...")`. Cada KDoc explica o comportamento esperado.
5. Valide localmente: `scripts/validate-module.sh NN`. Repita até aparecer `✔ Módulo NN concluído.`
6. Commit e push: `git push -u origin modulo/NN-nome`.
7. Abra o PR para `main` com `Closes #<número da issue>` (o template já pede).
8. Espere o check **Validar módulo NN** ficar verde. Faça o merge (squash). A issue fecha.
9. `git switch main && git pull` e comece o próximo.

## O que o CI confere

| Verificação | O que bloqueia |
|-------------|----------------|
| Integridade | PR que modifica ou apaga testes existentes, scripts, workflows ou arquivos de build. |
| Testes do módulo | Qualquer teste do módulo falhando. |
| TODOs | `TODO("MNN...")` ainda presente nos arquivos do módulo. |

Você PODE adicionar testes novos (arquivos novos). Incentivado.

## Ver seu progresso

- Local: `scripts/progress.sh` (roda a validação de todos os módulos; demora alguns minutos).
- GitHub: Actions > "Validar módulo" > Run workflow (boletim de todos os módulos na branch escolhida).
- Issues fechadas = módulos concluídos.

## Quando travar

1. Rode só o teste que falha e leia a mensagem inteira: `./gradlew :core:jvmTest --tests "*TitleTest*"`.
2. Releia a lição e o KDoc do arquivo.
3. Procure o erro do compilador: ele costuma dizer o que falta.
4. Só então consulte a solução na branch `reference`: `git show reference:caminho/do/arquivo.kt`. Depois feche o arquivo e refaça de memória.

## Observações

- Em repositório privado no plano gratuito do GitHub não existe proteção de branch obrigatória. O fluxo
  funciona por disciplina: só faça merge com o check verde.
- Testes de iOS e a visualização das telas (Android, iOS, desktop, web) são conferidos por você, localmente.
  O CI cobre compilação e testes em Linux.
