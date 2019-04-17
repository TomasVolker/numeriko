package tomasvolker.numeriko.core.functions

/*
fun IntArray1D.sum(): Int = sumBy { it }

fun IntArray1D.average(): Double = sum().toDouble() / size

infix fun IntArray1D.convolve(other: IntArray1D): IntArray1D {
    requireSameSize(this, other)
    return intArray1D(this.size) { i ->
        sumInt(other.indices) { j ->
            this[(i - j) modulo size] * other[j]
        }
    }
}
*/
