package tomasvolker.numeriko.core.implementations.array.buffer

interface ByteBuffer: Buffer<Byte> {

    override fun getValue(i: Int): Byte = get(i)
    override fun setValue(i: Int, value: Byte) {
        set(i, value)
    }

    operator fun get(i: Int): Byte
    operator fun set(i: Int, value: Byte)

    override fun copy(): ByteBuffer
    override fun copy(start: Int, size: Int): ByteBuffer

    override fun copyInto(
            destination: Buffer<Byte>,
            destinationOffset: Int,
            startIndex: Int,
            endIndex: Int
    ) {
        if (destination is ByteBuffer) {
            for (i in startIndex until endIndex) {
                destination[destinationOffset+i] = this[i]
            }
        } else {
            super.copyInto(destination, destinationOffset, startIndex, endIndex)
        }
    }

}

class ByteArrayBuffer(
        val array: ByteArray
): ByteBuffer {

    override val size: Int
        get() = array.size

    override fun get(i: Int): Byte = array[i]
    override fun set(i: Int, value: Byte) { array[i] = value }

    override fun copy(): ByteBuffer = ByteArrayBuffer(array.copyOf())
    override fun copy(start: Int, size: Int): ByteBuffer =
            ByteArrayBuffer(array.copyOfRange(start, start+size))

    override fun copyInto(
            destination: Buffer<Byte>,
            destinationOffset: Int,
            startIndex: Int,
            endIndex: Int
    ) {
        if (destination is ByteArrayBuffer) {
            array.copyInto(destination.array, destinationOffset, startIndex, endIndex)
        } else {
            super.copyInto(destination, destinationOffset, startIndex, endIndex)
        }
    }

}
