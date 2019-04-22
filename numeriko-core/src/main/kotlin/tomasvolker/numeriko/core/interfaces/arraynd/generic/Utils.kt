package tomasvolker.numeriko.core.interfaces.arraynd.generic

val ArrayND<*>.axes get(): IntRange = 0 until rank
val ArrayND<*>.lastAxis get(): Int = rank - 1

fun ArrayND<*>.indices(axis: Int): IntRange = 0 until shape(axis)


