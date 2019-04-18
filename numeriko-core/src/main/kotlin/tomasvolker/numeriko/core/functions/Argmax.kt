package tomasvolker.numeriko.core.functions

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.asIntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.forEachIndex1
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndex


fun DoubleArrayND.argMax(): Int {
    var resultIndex = 0
    var resultValue = Double.NEGATIVE_INFINITY

    forEachIndex1 { i ->
        val value = this[i]
        if (value > resultValue) {
            resultIndex = i
            resultValue = value
        }
    }
    return resultIndex
}

fun DoubleArrayND.argMaxND(): IntArrayND {
    var resultIndex = IntArray(rank) { 0 }
    var resultValue = Double.NEGATIVE_INFINITY

    unsafeForEachIndex { index ->
        val value = this.getValue(index)
        if (value > resultValue) {
            resultIndex = index.copyInto(resultIndex)
            resultValue = value
        }
    }
    return resultIndex.asIntArrayND()
}
