package numeriko.lowrank.interfaces.array1d.generic

val Array1D<*>.lastIndex: Int get() = size -1

val Array1D<*>.indices: IntRange get() = 0 until size

fun Array1D<*>.isNotEmpty(): Boolean = size != 0
