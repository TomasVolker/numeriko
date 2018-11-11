package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.interfaces.array1d.generic.lastIndex
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.MutableIntArray1D
import tomasvolker.numeriko.core.interfaces.factory.intZeros

fun <T> ArrayND<T>.asMutable(): MutableArrayND<T> = this as MutableArrayND<T>

fun MutableIntArray1D.indexIncrement(shape: IntArray1D): Boolean {

    var currentDim = shape.lastIndex
    var carry = true

    while(carry) {

        if (currentDim == -1)
            return true // Overflow

        carry = false
        this[currentDim]++

        if (this[currentDim] == shape[currentDim]) {
            carry = true
            this[currentDim] = 0
        }

        currentDim--

    }

    return false
}

inline fun ArrayND<*>.forEachIndices(block: (IntArray1D)->Unit) {

    val index = intZeros(rank).asMutable()

    do {
        block(index)
    } while(!index.indexIncrement(shape))


}