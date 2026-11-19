# Clean code e princípios em Kotlin

Cada item traz o princípio, o jeito idiomático em Kotlin e onde ver no projeto.

## 1. Prefira imutabilidade

- `val` por padrão, `var` só com motivo. `data class` com `copy` em vez de setters.
- Exponha `List`, `Map` (somente leitura); mude `MutableList` só por dentro.
- Ver: `Task` e `Task.complete`.

```kotlin
// Evite
class Cart { var items = mutableListOf<Item>() }
// Prefira
data class Cart(val items: List<Item> = emptyList()) {
    fun add(item: Item) = copy(items = items + item)
}
```

## 2. Torne estados inválidos impossíveis

- `sealed interface` para variantes fechadas; `when` sem `else`.
- Value classes contra "primitive obsession": `TaskId` não se confunde com outra `String`.
- Valide na construção: `Title.of` devolve `Outcome`; quem tem um `Title` não revalida.

```kotlin
// Evite: dois booleanos geram 4 combinações, só 3 fazem sentido
data class Request(val loading: Boolean, val error: String?)
// Prefira
sealed interface State { data object Loading : State; data class Failed(val msg: String) : State; data class Ready(val data: Data) : State }
```

## 3. Null safety sem `!!`

- Se um valor pode faltar, o tipo diz `T?`. Trate com `?.`, `?:`, `let`, ou `return` antecipado.
- `!!` quase sempre esconde uma decisão não tomada. Em revisão de código, questione cada um.
- Ver: `Lesson01Basics.nullSafety`.

## 4. Erros: esperado vs inesperado

- Esperado (entrada inválida, regra de negócio): valor no retorno (`Outcome`, `Result`, nullable).
- Inesperado (bug, I/O quebrado): exceção.
- `require` valida argumentos, `check` valida estado, ambos com mensagem útil.
- Nunca engula `CancellationException` em coroutines.

## 5. SOLID aplicado

| Princípio | Em Kotlin | No projeto |
|-----------|-----------|------------|
| **S**ingle Responsibility | Uma razão para mudar por classe | Um caso de uso por classe (`CreateTask`, `CompleteTask`) |
| **O**pen/Closed | Estenda por nova implementação, não por edição | Novo `FileTaskRepository` sem tocar no domínio |
| **L**iskov | Subtipos honram o contrato do supertipo | Qualquer `TaskRepository` serve onde o repositório é esperado |
| **I**nterface Segregation | Interfaces pequenas, `fun interface` | `IdGenerator` tem um único método |
| **D**ependency Inversion | Dependa de abstrações, injete no construtor | Casos de uso recebem `TaskRepository`, `Clock`, `IdGenerator` |

Também valem: **composição sobre herança** (`by` delegation, ver `LessonLoggingList`), **DRY** com bom senso
(duplicação acidental é melhor que abstração errada), **KISS** e **YAGNI** (não crie a interface "para o futuro").

## 6. Funções pequenas, nomes que contam a intenção

- Uma função, um nível de abstração. Se precisa de comentário para dizer "o que", extraia e nomeie.
- Nome revela intenção: `findOverdueTasks`, não `process2`. Booleanos soam como pergunta: `isDone`, `hasDueDate`.
- Argumentos nomeados em chamadas com vários parâmetros do mesmo tipo.
- Comentário explica o PORQUÊ, nunca repete o código.

## 7. Idiomas Kotlin que reduzem ruído

| Em vez de | Use |
|-----------|-----|
| loop com índice e lista temporária | `filter`/`map`/`groupBy` |
| `if (x != null) x.foo()` | `x?.foo()` |
| `if/else` que atribui | `val a = if (...) ... else ...` ou `when` |
| classe só com dados | `data class` |
| `Util.doSomething(str)` | extensão `str.doSomething()` |
| construtores com muitos parâmetros | argumentos nomeados + valores padrão |
| estado inicializado em vários passos | `apply { }` ou `buildString { }` |

Cuidado com excesso: três scope functions aninhadas (`let { run { also { } } }`) são menos legíveis que um loop simples.

## 8. Testes como parte do design

- Teste comportamento, não implementação. Nomes em linguagem natural entre crases.
- Dependências externas (tempo, id, I/O) entram por interface: o teste troca por um fake.
- Se algo é difícil de testar, geralmente há acoplamento demais. O teste revela o problema de design.
- Ver: `TaskManagerTest.kt` (sem mocks, `Clock.fixed`, repositório em memória).

## 9. Concorrência com disciplina

- Estrutura: toda coroutine pertence a um escopo. Evite `GlobalScope`.
- `runBlocking` só em `main` e testes.
- Dados compartilhados mutáveis são o inimigo: prefira imutáveis, confinamento a uma coroutine, ou `Mutex`/`Atomic*`.

## Checklist de revisão (cole no PR)

- [ ] Algum `!!` ou `var` evitável?
- [ ] Algum `when` com `else` escondendo variantes de `sealed`?
- [ ] Erro esperado virou exceção?
- [ ] A função faz mais de uma coisa ou mistura níveis de abstração?
- [ ] Dependência concreta (relógio, banco, `System.out`) criada dentro da regra de negócio?
- [ ] O domínio importou algo de infraestrutura?
- [ ] Existe teste para o comportamento novo e para o caso de erro?
