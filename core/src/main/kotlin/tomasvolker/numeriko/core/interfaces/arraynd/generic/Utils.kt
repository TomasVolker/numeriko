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

val ArrayND<*>.volume get(): Int = size
inline val ArrayND<*>.axes get(): IntRange = 0 until rank
val ArrayND<*>.lastAxis get(): Int = rank - 1

fun ArrayND<*>.indices(axis: Int): IntRange = 0 until shape(axis)

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

/**
 * **Caution** Executes [block] with each indexArray of the ArrayND, using the same indices instance.
 *
 * If a reference to the [indices] argument is not kept, it is safe to use this function.
 * On each iteration, it provides the same [IntArray1D] instance with a read only interface, so if a reference
 * to it is kept, the array will change on later iterations.
 *
 * This function is available for fast iteration, for a safe version use [forEachIndexed].
 *
 */
inline fun ArrayND<*>.unsafeForEachIndices(block: (indices: IntArray1D)->Unit) {

    val index = intZeros(rank).asMutable()

    do {
        block(index)
    } while(!index.indexIncrement(shape))

}

/**
 * Executes [block] with each indexArray of the ArrayND.
 *
 * This function provides a copy of each index on each iteration.
 * For faster iteration use [unsafeForEachIndices].
 */
inline fun ArrayND<*>.forEachIndices(block: (indices: IntArray1D)->Unit) {

    val index = intZeros(rank).asMutable()

    do {
        block(index.copy())
    } while(!index.indexIncrement(shape))

}
