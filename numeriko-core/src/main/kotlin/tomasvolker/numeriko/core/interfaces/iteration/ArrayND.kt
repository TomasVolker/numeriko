package tomasvolker.numeriko.core.interfaces.iteration

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.factory.arrayNDOfNulls

inline fun <T> ArrayND<T>.inlinedForEach(function: (T)->T) {
    val iterator = arrayIterator()
    while(iterator.hasNext()) {
        function(iterator.next())
    }
}

inline fun <T> ArrayND<T>.inlinedForEachIndexed(function: (indices: IntArray, value: T)->Unit) {
    val iterator = arrayIterator()
    while (iterator.hasNext()) {
        val value = iterator.next()
        function(iterator.previousIndexArray, value)
    }
}

inline fun <T> ArrayND<T>.inlinedElementWise(function: (T)->T): ArrayND<T> {
    val result = arrayNDOfNulls<T>(shape).asMutable()
    this.inlinedForEachIndexed { indices, value ->
        result.setValue(indices, function(value))
    }
    return result as ArrayND<T>
}

inline fun <T> MutableArrayND<T>.inlinedApplyElementWise(function: (T)->T): ArrayND<T> {
    inlinedForEachIndexed { indices, value ->
        setValue(indices, function(value))
    }
    return this
}
