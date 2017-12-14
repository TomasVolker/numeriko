package tomasvolker.numeriko.core.interfaces.integer

import tomasvolker.numeriko.core.interfaces.NDArrayCursor
import tomasvolker.numeriko.core.interfaces.NDArrayLinearCursor
import tomasvolker.numeriko.core.interfaces.ReadOnlyNDArrayCursor
import tomasvolker.numeriko.core.interfaces.ReadOnlyNDArrayLinearCursor

interface ReadOnlyIntNDArrayLinearCursor: ReadOnlyNDArrayLinearCursor<Int> {

    override val array: ReadOnlyIntNDArray

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

interface IntNDArrayLinearCursor: ReadOnlyIntNDArrayLinearCursor, NDArrayLinearCursor<Int> {

    override val array: IntNDArray

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

interface ReadOnlyIntNDArrayCursor: ReadOnlyIntNDArrayLinearCursor, ReadOnlyNDArrayCursor<Int> {

    override val currentIndexes: ReadOnlyIntNDArray

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


interface IntNDArrayCursor: IntNDArrayLinearCursor, ReadOnlyIntNDArrayCursor, NDArrayCursor<Int> {

    override val array: IntNDArray

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
