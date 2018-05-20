package tomasvolker.numeriko.legacy.core.interfaces.integer.arraynd

import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.ArrayNDIterator
import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.ReadOnlyArrayNDIterator

interface ReadOnlyIntArrayNDIterator: ReadOnlyArrayNDIterator<Int> {

    override val array: ReadOnlyIntArrayND

    fun readInt(): Int

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

    override fun read(): Int = readInt()

    override fun next(): Int = nextInt()

    override fun previous(): Int = previousInt()

}

interface IntArrayNDIterator: ReadOnlyIntArrayNDIterator, ArrayNDIterator<Int> {

    override val array: IntArrayND

    fun writeInt(value: Int)

    fun setNextInt(value :Int) {
        writeInt(value)
        increment()
    }

    fun setPreviousInt(value: Int) {
        writeInt(value)
        decrement()
    }

    override fun setNext(value: Int) = setNextInt(value)

    override fun setPrevious(value: Int) = setPreviousInt(value)

    override fun write(value: Int) = writeInt(value)

}
