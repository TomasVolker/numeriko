package tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd

import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.legacy.core.jvm.int.array1d.asArray1D

interface ReadOnlyArrayNDCursor<out T>: ReadOnlyArrayNDIterator<T> {

    override val array: ReadOnlyArrayND<T>

    val currentIndices: ReadOnlyIntArray1D

    fun next(dimension: Int): T {
        val value = read()
        increment(dimension)
        return value
    }

    fun previous(dimension: Int): T {
        val value = read()
        decrement(dimension)
        return value
    }

    fun setPosition(vararg indices: Int) = setPosition(indices.asArray1D())

    fun setPosition(indexArray: ReadOnlyIntArray1D)

    fun increment(dimension: Int) = incrementBy(dimension, 1)

    fun decrement(dimension: Int) = decrementBy(dimension, 1)

    fun incrementBy(dimension: Int, amount: Int)

    fun decrementBy(dimension: Int, amount: Int)

    fun moveToFirst(dimension: Int)

    fun moveToLast(dimension: Int)

}


interface ArrayNDCursor<T>: ReadOnlyArrayNDCursor<T>, ArrayNDIterator<T> {

    override val array: ArrayND<T>

    fun setNext(value: T, dimension: Int) {
        write(value)
        increment(dimension)
    }

    fun setPrevious(value: T, dimension: Int) {
        write(value)
        decrement(dimension)
    }

}
