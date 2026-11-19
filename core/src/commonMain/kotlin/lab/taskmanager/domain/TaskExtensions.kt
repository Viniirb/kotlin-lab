package lab.taskmanager.domain

/**
 * M06. Mais urgente primeiro (prioridade maior); empate resolve pela ordem de criação (`createdAt` menor primeiro).
 * Dica: `sortedWith(compareByDescending<Task> { ... }.thenBy { ... })`.
 */
fun List<Task>.sortedByUrgency(): List<Task> = TODO("M06: ordene por urgência")

/**
 * M06. Quantas tarefas existem em CADA prioridade, inclusive as que têm zero.
 * Dica: `Priority.entries.associateWith { ... }`.
 */
fun List<Task>.countByPriority(): Map<Priority, Int> = TODO("M06: conte por prioridade")
