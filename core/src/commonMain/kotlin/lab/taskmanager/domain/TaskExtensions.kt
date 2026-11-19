package lab.taskmanager.domain

/** Mais urgente primeiro (prioridade maior); empate resolve pela ordem de criação. */
fun List<Task>.sortedByUrgency(): List<Task> =
    sortedWith(compareByDescending<Task> { it.priority.weight }.thenBy { it.createdAt })

/** Quantas tarefas existem em CADA prioridade, inclusive as que têm zero. */
fun List<Task>.countByPriority(): Map<Priority, Int> =
    Priority.entries.associateWith { priority -> count { it.priority == priority } }
