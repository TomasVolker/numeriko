package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.lastIndex
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.MutableIntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.view.DefaultArrayND1DView
import tomasvolker.numeriko.core.interfaces.arraynd.generic.view.DefaultArrayND2DView
import tomasvolker.numeriko.core.interfaces.factory.intZeros

fun <T> ArrayND<T>.as1D(): Array1D<T> = DefaultArrayND1DView(this.asMutable())
fun <T> ArrayND<T>.as2D(): Array2D<T> = DefaultArrayND2DView(this.asMutable())

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