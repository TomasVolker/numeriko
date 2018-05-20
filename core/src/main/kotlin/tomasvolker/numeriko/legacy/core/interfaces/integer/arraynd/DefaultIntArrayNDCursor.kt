package tomasvolker.numeriko.legacy.core.interfaces.integer.arraynd

import tomasvolker.numeriko.legacy.core.interfaces.factory.intZeros
import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.ReadOnlyIntArray1D
/*
class DefaultIntArrayNDCursor(override val array: IntArrayND): IntArrayNDCursor {
    
    override fun incrementBy(amount: Int) {
        TODO("not implemented")
    }

    override fun decrementBy(amount: Int) {
        TODO("not implemented")
    }

    override fun moveToFirst() {
        TODO("not implemented")
    }

    override fun moveToLast() {
        TODO("not implemented")
    }

    override val currentIndices = intZeros(array.rank)

    override fun setPosition(indexArray: ReadOnlyIntArray1D) {
        //currentIndices.setAllInline { index ->  indexArray[index[0]] }
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
*/