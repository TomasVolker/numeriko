package tomasvolker.numeriko.core.interfaces.array2d.generic

val Array2D<*>.rows: Int get() = shape0
val Array2D<*>.cols: Int get() = shape1

val Array2D<*>.indices0: IntRange get() = 0 until shape0
val Array2D<*>.indices1: IntRange get() = 0 until shape0

fun Array2D<*>.shape(dim: Int): Int = when(dim) {
    0 -> shape0
    1 -> shape1
    else -> throw IndexOutOfBoundsException(dim)
}


fun Array2D<*>.indices(dim: Int): IntRange = 0 until shape(dim)