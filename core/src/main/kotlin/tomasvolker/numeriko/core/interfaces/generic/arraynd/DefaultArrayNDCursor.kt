package tomasvolker.numeriko.core.interfaces.generic.arraynd

import tomasvolker.numeriko.core.interfaces.factory.intZeros
import tomasvolker.numeriko.core.interfaces.integer.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.core.util.incrementIndexArray

open class DefaultReadOnlyArrayNDCursor<T>(array: ReadOnlyArrayND<T>): ReadOnlyArrayNDCursor<T> {

    override val array: ReadOnlyArrayND<T> = array

    override val currentIndices = intZeros(array.rank)

    override fun setPosition(indexArray: ReadOnlyIntArray1D) {
        currentIndices.setValue { (i0) ->  indexArray[i0] }
    }

    override fun read() = array[currentIndices]

    override fun moveToFirst(dimension: Int) {
        currentIndices[dimension] = 0
    }

    override fun moveToLast(dimension: Int) {
        currentIndices[dimension] = array.lastIndex(dimension)
    }

    override fun incrementBy(dimension: Int, amount: Int) {
        currentIndices[dimension] += amount
    }

    override fun decrementBy(dimension: Int, amount: Int) {
        currentIndices[dimension] -= amount
    }

    override fun incrementBy(amount: Int) {
        incrementIndexArray(
                shape = array.shape,
                indexArray = currentIndices,
                amount = amount
        )
    }

    override fun decrementBy(amount: Int) {
        incrementIndexArray(
                shape = array.shape,
                indexArray = currentIndices,
                amount = -amount
        )
    }

    override fun moveToFirst() {
        currentIndices.setValue { 0 }
    }

    override fun moveToLast() {
        currentIndices.setValue { -1 }
    }

    override fun cursorInBounds(): Boolean {

        for (i in currentIndices.indices) {
            val index = currentIndices[i]

            if (index !in 0 until array.shape[i])
                return false
        }

        return true
    }

}

class DefaultArrayNDCursor<T>(override val array: ArrayND<T>): DefaultReadOnlyArrayNDCursor<T>(array), ArrayNDCursor<T> {

    override fun write(value: T) {
        array[currentIndices] = value
    }

}
