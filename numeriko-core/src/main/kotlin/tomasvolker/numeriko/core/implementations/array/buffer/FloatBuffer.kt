package tomasvolker.numeriko.core.implementations.array.buffer

interface FloatBuffer: Buffer<Float> {

    override fun getValue(i: Int): Float = get(i)
    override fun setValue(i: Int, value: Float) {
        set(i, value)
    }

    operator fun get(i: Int): Float
    operator fun set(i: Int, value: Float)

    override fun copy(): FloatBuffer
    override fun copy(start: Int, size: Int): FloatBuffer

    override fun copyInto(
            destination: Buffer<Float>,
            destinationOffset: Int,
            startIndex: Int,
            endIndex: Int
    ) {
        if (destination is FloatBuffer) {
            for (i in startIndex until endIndex) {
                destination[destinationOffset+i] = this[i]
            }
        } else {
            super.copyInto(destination, destinationOffset, startIndex, endIndex)
        }
    }

}

class FloatArrayBuffer(
        val array: FloatArray
): FloatBuffer {

    override val size: Int
        get() = array.size

    override fun get(i: Int): Float = array[i]
    override fun set(i: Int, value: Float) { array[i] = value }

    override fun copy(): FloatBuffer = FloatArrayBuffer(array.copyOf())
    override fun copy(start: Int, size: Int): FloatBuffer =
            FloatArrayBuffer(array.copyOfRange(start, start+size))

    override fun copyInto(
            destination: Buffer<Float>,
            destinationOffset: Int,
            startIndex: Int,
            endIndex: Int
    ) {
        if (destination is FloatArrayBuffer) {
            array.copyInto(destination.array, destinationOffset, startIndex, endIndex)
        } else {
            super.copyInto(destination, destinationOffset, startIndex, endIndex)
        }
    }

}
