package tomasvolker.numeriko.core.interfaces.array2d.generic

val Array2D<*>.rows: Int get() = shape0
val Array2D<*>.cols: Int get() = shape1

val Array2D<*>.indices0: IntRange get() = 0 until shape0
val Array2D<*>.indices1: IntRange get() = 0 until shape1

val Array2D<*>.lastIndex0: Int get() = shape0 - 1
val Array2D<*>.lastIndex1: Int get() = shape1 - 1

fun Array2D<*>.shape(dimension: Int): Int = when(dimension) {
    0 -> shape0
    1 -> shape1
    else -> throw IndexOutOfBoundsException("$dimension")
}

fun Array2D<*>.lastIndex(dimension: Int): Int = shape(dimension) - 1

fun Array2D<*>.indices(dimension: Int): IntRange = 0 until shape(dimension)