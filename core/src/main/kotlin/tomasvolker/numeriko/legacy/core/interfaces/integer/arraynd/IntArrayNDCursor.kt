package tomasvolker.numeriko.legacy.core.interfaces.integer.arraynd

import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.ArrayNDCursor
import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.ReadOnlyArrayNDCursor
import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.ReadOnlyIntArray1D

interface ReadOnlyIntArrayNDCursor: ReadOnlyArrayNDCursor<Int> {

    override val currentIndices: ReadOnlyIntArray1D

    fun readInt(): Int

    fun nextInt(dimension: Int): Int {
        val value = readInt()
        increment(dimension)
        return value
    }

    fun previousInt(dimension: Int): Int {
        val value = readInt()
        decrement(dimension)
        return value
    }


    override fun read() = readInt()

    override fun next(dimension: Int) = nextInt(dimension)

    override fun previous(dimension: Int) = previousInt(dimension)

}


interface IntArrayNDCursor: ReadOnlyIntArrayNDCursor, ArrayNDCursor<Int> {

    override val array: IntArrayND

    fun writeInt(value: Int)

    fun setNextInt(value: Int, dimension: Int) {
        writeInt(value)
        increment(dimension)
    }

    fun setPreviousInt(value: Int, dimension: Int) {
        writeInt(value)
        decrement(dimension)
    }

    override fun write(value: Int) = writeInt(value)

    override fun setNext(value: Int, dimension: Int) = setNextInt(value, dimension)

    override fun setPrevious(value: Int, dimension: Int) = setPreviousInt(value, dimension)

}
