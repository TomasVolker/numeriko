package tomasvolker.numeriko.core.interfaces.generic.arraynd

interface ReadOnlyArrayNDIterator<out T>: Iterator<T> {

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

    fun moveToFirst()

    fun moveToLast()

    fun cursorInBounds(): Boolean

}

interface ArrayNDIterator<T>: ReadOnlyArrayNDIterator<T> {

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
