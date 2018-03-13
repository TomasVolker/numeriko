package tomasvolker.numeriko.core.interfaces.generic.arraynd

import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntNDArray

interface ReadOnlyNDArrayLinearCursor<out T>: Iterator<T> {

    val array: ReadOnlyNDArray<T>

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

interface NDArrayLinearCursor<T>: ReadOnlyNDArrayLinearCursor<T> {

    override val array: NDArray<T>

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

interface ReadOnlyNDArrayCursor<out T>: ReadOnlyNDArrayLinearCursor<T> {

    val currentIndexes: ReadOnlyIntNDArray

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

    fun setPosition(indexArray: ReadOnlyIntNDArray)

    fun increment(dimension: Int)

    fun decrement(dimension: Int)

    fun incrementBy(dimension: Int, amount: Int)

    fun decrementBy(dimension: Int, amount: Int)

}


interface NDArrayCursor<T>: NDArrayLinearCursor<T>, ReadOnlyNDArrayCursor<T> {

    override val array: NDArray<T>

    fun setNext(value: T, dimension: Int) {
        write(value)
        increment(dimension)
    }

    fun setPrevious(value: T, dimension: Int) {
        write(value)
        decrement(dimension)
    }

}
