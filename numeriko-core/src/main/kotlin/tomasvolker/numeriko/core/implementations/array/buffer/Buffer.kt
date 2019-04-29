package tomasvolker.numeriko.core.implementations.array.buffer

interface Buffer<T> {

    val size: Int
    fun getValue(i: Int): T
    fun setValue(i: Int, value: T)

    fun copy(): Buffer<T>

}

class ArrayBuffer<T>(
        val array: Array<T>
): Buffer<T> {

    override val size: Int get() = array.size

    override fun getValue(i: Int): T = array[i]
    override fun setValue(i: Int, value: T) { array[i] = value }

    override fun copy(): Buffer<T> = ArrayBuffer(array.copyOf())

}

