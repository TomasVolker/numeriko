package tomasvolker.numeriko.core.implementations.array.buffer

interface Buffer<T> {

    val size: Int
    fun getValue(i: Int): T
    fun setValue(i: Int, value: T)

    fun copy(): Buffer<T>
    fun copy(start: Int, size: Int): Buffer<T>

    fun copyInto(
            destination: Buffer<T>,
            destinationOffset: Int = 0,
            startIndex: Int = 0,
            endIndex: Int = size
    ) {
        for (i in startIndex until endIndex) {
            destination.setValue(destinationOffset+i, getValue(i))
        }
    }

}

class ArrayBuffer<T>(
        val array: Array<T>
): Buffer<T> {

    override val size: Int get() = array.size

    override fun getValue(i: Int): T = array[i]
    override fun setValue(i: Int, value: T) { array[i] = value }

    override fun copy(): Buffer<T> = ArrayBuffer(array.copyOf())
    override fun copy(start: Int, size: Int): Buffer<T> =
            ArrayBuffer(array.copyOfRange(start, start+size))

    override fun copyInto(
            destination: Buffer<T>,
            destinationOffset: Int,
            startIndex: Int,
            endIndex: Int
    ) {
        if (destination is ArrayBuffer<T>) {
            array.copyInto(destination.array, destinationOffset, startIndex, endIndex)
        } else {
            super.copyInto(destination, destinationOffset, startIndex, endIndex)
        }
    }

}

