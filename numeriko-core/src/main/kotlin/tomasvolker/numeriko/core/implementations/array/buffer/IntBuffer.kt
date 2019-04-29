package tomasvolker.numeriko.core.implementations.array.buffer

interface IntBuffer: Buffer<Int> {

    override fun getValue(i: Int): Int = get(i)
    override fun setValue(i: Int, value: Int) {
        set(i, value)
    }

    operator fun get(i: Int): Int
    operator fun set(i: Int, value: Int)

    override fun copy(): IntBuffer
    override fun copy(start: Int, size: Int): IntBuffer

    override fun copyInto(
            destination: Buffer<Int>,
            destinationOffset: Int,
            startIndex: Int,
            endIndex: Int
    ) {
        if (destination is IntBuffer) {
            for (i in startIndex until endIndex) {
                destination[destinationOffset+i] = this[i]
            }
        } else {
            super.copyInto(destination, destinationOffset, startIndex, endIndex)
        }
    }

}

class IntArrayBuffer(
        val array: IntArray
): IntBuffer {

    override val size: Int
        get() = array.size

    override fun get(i: Int): Int = array[i]
    override fun set(i: Int, value: Int) { array[i] = value }

    override fun copy(): IntBuffer = IntArrayBuffer(array.copyOf())
    override fun copy(start: Int, size: Int): IntBuffer =
            IntArrayBuffer(array.copyOfRange(start, start+size))

    override fun copyInto(
            destination: Buffer<Int>,
            destinationOffset: Int,
            startIndex: Int,
            endIndex: Int
    ) {
        if (destination is IntArrayBuffer) {
            array.copyInto(destination.array, destinationOffset, startIndex, endIndex)
        } else {
            super.copyInto(destination, destinationOffset, startIndex, endIndex)
        }
    }

}
