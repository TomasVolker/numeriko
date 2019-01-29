package tomasvolker.numeriko.core.performance

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.forEachIndices
import tomasvolker.numeriko.core.interfaces.arraynd.generic.indices

fun IntArray.indexIncrement(shape: IntArray): Boolean {

    var currentDim = shape.size - 1
    var carry: Boolean

     do {

        if (currentDim == -1)
            return true // Overflow

        carry = false
        this[currentDim]++

        if (this[currentDim] == shape[currentDim]) {
            carry = true
            this[currentDim] = 0
        }

        currentDim--

    } while(carry)

    return false
}

/**
 * **Caution** Executes [block] with each indexArray of the ArrayND, using the same indices instance.
 *
 * If a reference to the [indices] argument is not kept, it is safe to use this function.
 * On each iteration, it provides the same [IntArray] instance, so if a reference
 * to it is kept, the array will change on later iterations.
 *
 * This function is available for fast iteration, for a safe version use [forEachIndices].
 *
 */
inline fun fastForEachIndices(shape: IntArray, block: (indices: IntArray)->Unit) {

    if (shape.isEmpty()) return

    val index = IntArray(shape.size) { 0 }

    do {
        block(index)
    } while(!index.indexIncrement(shape))

}

inline fun ArrayND<*>.fastForEachIndices(block: (indices: IntArray)->Unit) =
        fastForEachIndices(shape.toIntArray(), block)

inline fun <T> ArrayND<T>.fastForEach(block: (value: T)->Unit) =
        fastForEachIndices { indices -> block(this.getValue(indices)) }

inline fun <T> ArrayND<T>.fastForEachIndexed(block: (indices: IntArray, value: T)->Unit) =
        fastForEachIndices { indices -> block(indices, this.getValue(indices)) }

inline fun DoubleArrayND.fastForEach(block: (value: Double)->Unit) =
        fastForEachIndices { indices -> block(this.getDouble(indices)) }

inline fun DoubleArrayND.fastForEachIndexed(block: (indices: IntArray, value: Double)->Unit) =
        fastForEachIndices { indices -> block(indices, this.getDouble(indices)) }
