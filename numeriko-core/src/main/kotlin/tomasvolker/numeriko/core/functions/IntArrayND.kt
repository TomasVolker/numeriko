package tomasvolker.numeriko.core.functions

import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.primitives.productInt
import tomasvolker.numeriko.core.primitives.sumInt

fun IntArrayND.sum(): Int = when(rank) {
    1 -> sumInt(0 until rank) { i -> this[i] }
    else -> fold(0) { acc, next -> acc + next }
}

fun IntArrayND.product(): Int = when(rank) {
    1 -> productInt(0 until size) { i -> this[i] }
    else -> fold(1) { acc, next -> acc * next }
}

fun IntArrayND.average(): Double = sum().toDouble() / size
/*
infix fun IntArrayND.convolve(other: IntArrayND): IntArrayND {
    requireSameSize(this, other)
    return intArrayND(this.size) { i ->
        sumInt(other.indices) { j ->
            this[(i - j) modulo size] * other[j]
        }
    }
}
*/
