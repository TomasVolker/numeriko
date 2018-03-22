package tomasvolker.numeriko.core.interfaces.integer.arraynd

import tomasvolker.numeriko.core.interfaces.factory.intZeros
import tomasvolker.numeriko.core.interfaces.generic.arraynd.setAllInline
import tomasvolker.numeriko.core.interfaces.integer.array1d.ReadOnlyIntArray1D

class DefaultIntArrayNDCursor(override val array: IntArrayND): IntArrayNDCursor {

    override val currentIndices = intZeros(array.rank)

    override fun setPosition(indexArray: ReadOnlyIntArray1D) {
        currentIndices.setAllInline { index ->  indexArray[index[0]] }
    }

    override fun readInt() = array[currentIndices]

    override fun writeInt(value: Int) {
        array[currentIndices] = value
    }

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

    override fun cursorInBounds(): Boolean {

        for (i in currentIndices.indices) {
            val index = currentIndices[i]

            if (index !in 0 until array.shape[i])
                return false
        }

        return true
    }

}
