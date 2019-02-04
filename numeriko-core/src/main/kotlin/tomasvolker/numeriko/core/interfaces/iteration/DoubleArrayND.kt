package tomasvolker.numeriko.core.interfaces.iteration

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.doubleZeros
import tomasvolker.numeriko.core.preconditions.requireSameShape

inline fun DoubleArrayND.inlinedForEach(function: (Double)->Double) {
    val iterator = arrayIterator()
    while(iterator.hasNext()) {
        function(iterator.nextDouble())
    }
}

inline fun DoubleArrayND.inlinedForEachIndexed(function: (indices: IntArray, value: Double)->Unit) {
    val iterator = arrayIterator()
    while (iterator.hasNext()) {
        val value = iterator.nextDouble()
        function(iterator.currentIndexArray, value)
    }
}

inline fun DoubleArrayND.inlinedElementWise(function: (Double)->Double): DoubleArrayND {
    val result = doubleZeros(shape).asMutable()
    this.inlinedForEachIndexed { indices, value ->
        result.setDouble(indices, function(value))
    }
    return result
}

inline fun MutableDoubleArrayND.inlinedApplyElementWise(function: (Double)->Double): MutableDoubleArrayND {
    inlinedForEachIndexed { indices, value ->
        setDouble(indices, function(value))
    }
    return this
}

inline fun MutableDoubleArrayND.inlinedApplyFill(function: (indices: IntArray)->Double): MutableDoubleArrayND {
    fastForEachIndices { indices ->
        setDouble(indices, function(indices))
    }
    return this
}

inline fun DoubleArrayND.inlinedZipIndexed(
        other: DoubleArrayND,
        function: (indices: IntArray, thisValue: Double, otherValue: Double)->Unit
) {
    requireSameShape(this, other)
    val thisIterator = this.arrayIterator()
    val otherIterator = other.arrayIterator()
    while (thisIterator.hasNext()) {
        val thisValue = thisIterator.nextDouble()
        val otherValue = otherIterator.nextDouble()
        function(thisIterator.currentIndexArray, thisValue, otherValue)
    }
}

inline fun MutableDoubleArrayND.inlinedApplyElementWise(
        other: DoubleArrayND,
        function: (Double, Double)->Double
): MutableDoubleArrayND {
    inlinedZipIndexed(other) { indices, thisValue, otherValue ->
        setDouble(indices, function(thisValue, otherValue))
    }
    return this
}
