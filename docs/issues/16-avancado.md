**Nível:** Avançado  
**Validação automática:** `scripts/validate-module.sh 16` (o mesmo que o GitHub Actions roda no seu PR)

## Objetivo

Concluir o módulo 16: **Avançado e projeto livre**, um passo do Task Manager que você constrói do zero.

## Leia antes

- Todas as docs
- https://kotlinlang.org/docs/home.html

## Conceitos

- Interoperabilidade (Java, Swift)
- Ktor Client e consumo da API
- Persistência multiplataforma
- Gradle customizado
- Desempenho e benchmarks

## Tarefas

- [ ] Tudo verde: `scripts/validate-module.sh 16` (todos os testes e ZERO `TODO("M…")` no projeto)
- [ ] Escolher e concluir PELO MENOS 2 itens do menu abaixo, cada um com testes seus

## Critério de pronto

- [ ] `scripts/validate-module.sh 16` termina com "✔ Módulo 16 concluído."
- [ ] Nenhum `TODO("M16...")` restante nos arquivos do módulo
- [ ] PR aberto com `Closes #<número desta issue>` e check **Validar módulo** verde

## Desafio extra (opcional)

- Menu: (a) Ktor Client no `composeApp` falando com o `:backend`; (b) SQLDelight ou Room KMP; (c) autenticação JWT no backend + tela de login; (d) tela SwiftUI nativa chamando o `:core` (interop sem Compose); (e) task Gradle própria que gera um relatório de cobertura por módulo; (f) benchmark de `wordFrequency` com `Sequence` vs `List`; (g) publicar o `:core` como biblioteca no `mavenLocal`.

## Nota

Aqui não há gabarito. Abra novas issues para cada item escolhido.

## Como entregar

```bash
git switch -c modulo/16-avancado
# implemente, rode os testes do módulo até ficarem verdes
scripts/validate-module.sh 16
git push -u origin modulo/16-avancado
# abra o PR para a main; ao fazer merge, a issue fecha sozinha
```

> Travou? Dica em `docs/FLUXO.md`. A solução completa está na branch `reference` (consulte só depois de tentar).
