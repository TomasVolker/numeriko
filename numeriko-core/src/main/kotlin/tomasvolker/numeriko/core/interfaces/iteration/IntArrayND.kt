package tomasvolker.numeriko.core.interfaces.iteration

import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.MutableIntArrayND
import tomasvolker.numeriko.core.interfaces.factory.intZeros
import tomasvolker.numeriko.core.preconditions.requireSameShape

inline fun elementWise(
        source: IntArrayND,
        destination: MutableIntArrayND,
        operation: (Int)->Int
) {
    requireSameShape(source, destination)
    source.unsafeForEachIndexed { index, value ->
        destination.setInt(index, operation(value))
    }
}

inline fun IntArrayND.elementWise(operation: (Int)->Int): IntArrayND {
    val result = intZeros(shape).asMutable()
    elementWise(
            source = this,
            destination = result,
            operation = operation
    )
    return result
}

inline fun MutableIntArrayND.applyElementWise(
        operation: (Int)->Int
): MutableIntArrayND {
    elementWise(
            source = this,
            destination = this,
            operation = operation
    )
    return this
}

inline fun MutableIntArrayND.applyElementWise(
        other: IntArrayND,
        operation: (Int, Int)->Int
): MutableIntArrayND {
    requireSameShape(this, other)
    elementWise(
            source1 = this,
            source2 = other,
            destination = this,
            operation = operation
    )
    return this
}

inline fun elementWise(
        source1: IntArrayND,
        source2: IntArrayND,
        destination: MutableIntArrayND,
        operation: (Int, Int)->Int
) {
    requireSameShape(source1, source2)
    requireSameShape(source1, destination)
    source1.unsafeForEachIndex { index ->
        destination.setInt(index, operation(source1.getInt(index), source2.getInt(index)))
    }
}

inline fun elementWise(
        array1: IntArrayND,
        array2: IntArrayND,
        operation: (Int, Int)->Int
): IntArrayND {
    requireSameShape(array1, array2)
    val result = intZeros(array1.shape).asMutable()
    elementWise(
            source1 = array1,
            source2 = array2,
            destination = result,
            operation = operation
    )
    return result

}


inline fun IntArrayND.inlinedForEach(function: (Int)->Unit) {
    unsafeForEachIndex { index ->
        function(getInt(index))
    }
}

inline fun IntArrayND.unsafeForEachIndexed(function: (index: IntArray, value: Int)->Unit) {
    unsafeForEachIndex { index ->
        function(index, getInt(index))
    }
}
