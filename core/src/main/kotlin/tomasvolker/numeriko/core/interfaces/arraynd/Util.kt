package tomasvolker.numeriko.core.interfaces.arraynd

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D

fun Array<out Index>.computeIndices(shape: IntArray1D): IntArray =
        IntArray(size) { i -> this[i].computeValue(shape[i]) }