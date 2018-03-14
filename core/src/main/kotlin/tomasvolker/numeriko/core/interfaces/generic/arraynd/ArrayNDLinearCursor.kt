package tomasvolker.numeriko.core.interfaces.generic.arraynd

import tomasvolker.numeriko.core.interfaces.int.array1d.ReadOnlyIntArray1D

interface ReadOnlyArrayNDLinearCursor<out T>: Iterator<T> {

    val array: ReadOnlyArrayND<T>

    override fun hasNext() = cursorInBounds()

    override fun next(): T {
        val value = read()
        increment()
        return value
    }

    fun previous(): T {
        val value = read()
        decrement()
        return value
    }

    fun read(): T

    fun increment() = incrementBy(1)

    fun incrementBy(amount: Int)

    fun decrement() = decrementBy(1)

    fun decrementBy(amount: Int)

    fun moveToLast()

    fun cursorInBounds(): Boolean

}

interface ArrayNDLinearCursor<T>: ReadOnlyArrayNDLinearCursor<T> {

    override val array: ArrayND<T>

    fun setNext(value: T) {
        write(value)
        increment()
    }

    fun setPrevious(value: T) {
        write(value)
        decrement()
    }

    fun write(value: T)

}

interface ReadOnlyArrayNDCursor<out T>: ReadOnlyArrayNDLinearCursor<T> {

    val currentIndexes: ReadOnlyIntArray1D

    fun next(dimension: Int): T {
        val value = read()
        increment(dimension)
        return value
    }

    fun previous(dimension: Int): T {
        val value = read()
        decrement()
        return value
    }

    fun setPosition(vararg indexArray: Int)

    fun setPosition(indexArray: ReadOnlyIntArray1D)

    fun increment(dimension: Int)

    fun decrement(dimension: Int)

    fun incrementBy(dimension: Int, amount: Int)

    fun decrementBy(dimension: Int, amount: Int)

}


interface ArrayNDCursor<T>: ArrayNDLinearCursor<T>, ReadOnlyArrayNDCursor<T> {

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
