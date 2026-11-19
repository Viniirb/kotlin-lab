package lab.taskmanager

import lab.shared.Outcome
import lab.taskmanager.domain.DomainError
import lab.taskmanager.domain.Priority
import lab.taskmanager.domain.Task

/**
 * M09. DSL com lambda com receptor. Uso esperado:
 *
 *     module.seed {
 *         high("pagar conta")
 *         low("regar plantas")
 *     }
 *
 * `low`, `medium` e `high` apenas ANOTAM o pedido; quem cria é `seed`, depois que o bloco termina.
 * (Leia antes: Lesson05Generics.kt, seção "DSL".)
 */
class TaskSeed {
    // Guarde aqui os pedidos (título + prioridade), na ordem em que o bloco os declara.

    fun low(title: String): Unit = TODO("M09: anote o pedido LOW")

    fun medium(title: String): Unit = TODO("M09: anote o pedido MEDIUM")

    fun high(title: String): Unit = TODO("M09: anote o pedido HIGH")
}

/** M09. Executa o bloco e cria cada tarefa pedida, na ordem, devolvendo o resultado de cada uma. */
fun TaskModule.seed(build: TaskSeed.() -> Unit): List<Outcome<Task, DomainError>> = TODO("M09: execute o bloco")
