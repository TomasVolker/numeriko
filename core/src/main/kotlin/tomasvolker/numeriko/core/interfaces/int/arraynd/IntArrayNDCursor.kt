package tomasvolker.numeriko.core.interfaces.int.arraynd

import tomasvolker.numeriko.core.interfaces.generic.arraynd.ArrayNDCursor
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ArrayNDLinearCursor
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ReadOnlyArrayNDCursor
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ReadOnlyArrayNDLinearCursor
import tomasvolker.numeriko.core.interfaces.int.array1d.ReadOnlyIntArray1D

interface ReadOnlyIntArrayNDLinearCursor: ReadOnlyArrayNDLinearCursor<Int> {

    override val array: ReadOnlyIntArrayND

    override fun hasNext() = cursorInBounds()

    fun nextInt(): Int {
        val value = readInt()
        increment()
        return value
    }

    fun previousInt(): Int {
        val value = readInt()
        decrement()
        return value
    }

    fun readInt(): Int

    override fun next(): Int = nextInt()

    override fun previous(): Int = previousInt()

    override fun read(): Int = readInt()

}

interface IntArrayNDLinearCursor: ReadOnlyIntArrayNDLinearCursor, ArrayNDLinearCursor<Int> {

    override val array: IntArrayND

    fun setNextInt(value :Int) {
        writeInt(value)
        increment()
    }

    fun setPreviousInt(value: Int) {
        writeInt(value)
        decrement()
    }

    fun writeInt(value: Int)

    override fun setNext(value: Int) = setNextInt(value)

    override fun setPrevious(value: Int) = setPreviousInt(value)

    override fun write(value: Int) = writeInt(value)

}

interface ReadOnlyIntArrayNDCursor: ReadOnlyIntArrayNDLinearCursor, ReadOnlyArrayNDCursor<Int> {

    override val currentIndexes: ReadOnlyIntArray1D

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

    override fun next(dimension: Int): Int = nextInt(dimension)

    override fun previous(dimension: Int): Int = previousInt(dimension)

}


interface IntArrayNDCursor: IntArrayNDLinearCursor, ReadOnlyIntArrayNDCursor, ArrayNDCursor<Int> {

    override val array: IntArrayND

    fun setNextInt(value: Int, dimension: Int) {
        writeInt(value)
        increment(dimension)
    }

    fun setPreviousInt(value: Int, dimension: Int) {
        writeInt(value)
        decrement(dimension)
    }

    override fun setNext(value: Int, dimension: Int) = setNextInt(value, dimension)

    override fun setPrevious(value: Int, dimension: Int) = setPreviousInt(value, dimension)

}
