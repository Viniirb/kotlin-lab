# Arquitetura do Task Manager

O domínio, os casos de uso e os adaptadores moram em `core/src/commonMain/kotlin/lab/taskmanager` e são
compartilhados por terminal, backend, Android, iOS, desktop e web. A camada de apresentação muda por plataforma:
`Cli` (`:lessons`), rotas Ktor (`:backend`), telas Compose (`:composeApp`). O app é pequeno de propósito:
o objetivo é enxergar as ideias de arquitetura limpa sem a distração de um framework.

## Regra de dependência

```
presentation ──► application ──► domain ◄── infrastructure
   (Cli)          (casos de uso)   (regras)     (adaptadores)
```

- `domain` não importa nada dos outros pacotes. Contém entidades (`Task`), value objects (`Title`, `TaskId`),
  erros (`DomainError`) e a PORTA `TaskRepository`.
- `application` conhece só o `domain`. Cada caso de uso coordena entidades e portas.
- `infrastructure` IMPLEMENTA as portas do domínio. A seta aponta para dentro: é a inversão de dependência.
- `presentation` traduz entrada/saída em chamadas de caso de uso. Existe uma por plataforma.
- `App.kt` (terminal) e `TaskModule` (demais módulos) são composition roots: escolhem as implementações.

Como verificar: abra `domain/Task.kt` e confira os imports. Se aparecer `lab.taskmanager.infrastructure`
ou `java.io`, a regra foi quebrada. Em código comum a regra é imposta pelo compilador: `java.*` nem compila.

## Onde cada ideia aparece no código

| Ideia | Onde ver |
|-------|----------|
| Estados impossíveis não representáveis | `TaskStatus` (`Done` carrega `completedAt`; `Open` não tem) |
| Validação na construção (parse, don't validate) | `Title.of(...)`: se existe um `Title`, já é válido |
| Imutabilidade | `Task.complete` devolve uma nova `Task` com `copy` |
| Regra de negócio dentro da entidade | `Task.complete` rejeita concluir duas vezes |
| Erro esperado como valor | `Outcome<Task, DomainError>` nas assinaturas |
| `when` exaustivo | `Cli.message(error)`: erro novo no domínio quebra a compilação até ser tratado |
| Inversão de dependência | `TaskRepository` no domain, `InMemoryTaskRepository` na infrastructure |
| Tempo e id como portas | `kotlinx.datetime.Clock` e `IdGenerator` injetados: testes são determinísticos |
| Entrada/saída injetadas | `Cli` recebe `BufferedReader` e `PrintStream`: testável sem terminal |
| Composition root | `App.kt`, `TaskModule` |
| Contrato único cliente/servidor | `TaskContract.kt` (`@Serializable`) usado por API e telas |
| Decorator para concorrência | `LockedTaskRepository` (`:backend`) embrulha o repositório |
| State holder separado da UI | `TaskStore` (`:composeApp`) |

## Decisões e trade-offs

- **Injeção manual, sem framework.** Para este tamanho, construtores bastam e deixam o fluxo explícito.
  Quando o grafo de objetos crescer, considere Koin ou Dagger/Hilt.
- **`Outcome` em vez de exceções para regras de negócio.** O custo é mais cerimônia nas assinaturas.
  O ganho: o compilador mostra quem trata cada erro. Exceções ficam para falhas inesperadas (I/O, bug).
- **Repositório síncrono.** Simples de ler. Com banco real você tornaria as funções `suspend` (desafio 4 do README).
- **Um arquivo agrupa vários tipos pequenos** (`UseCases.kt`, `Adapters.kt`). Kotlin permite e a convenção
  oficial aceita quando os tipos são coesos. Separe quando um deles crescer.
- **Os nomes "hexagonal", "clean" e "onion" descrevem a mesma ideia central**: o domínio no centro, detalhes
  (banco, UI) nas bordas, dependências apontando para dentro.

## Testando por camada

| Camada | Estratégia | Arquivo |
|--------|-----------|---------|
| domain | Teste unitário puro, sem mocks | `TitleTest`, `TaskTest` |
| application | Casos de uso com repositório em memória e `Clock` fixo | `UseCaseTest` |
| presentation | Sessão inteira com strings de entrada e saída | `CliTest` |

Repare: nenhum teste usa biblioteca de mock. Boas fronteiras (portas) tornam fakes simples suficientes.
