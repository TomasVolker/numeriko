package tomasvolker.numeriko.core.implementations.array.buffer

interface FloatBuffer: Buffer<Float> {

    override fun getValue(i: Int): Float = get(i)
    override fun setValue(i: Int, value: Float) {
        set(i, value)
    }

    operator fun get(i: Int): Float
    operator fun set(i: Int, value: Float)

    override fun copy(): FloatBuffer

}

class FloatArrayBuffer(
        val array: FloatArray
): FloatBuffer {

    override val size: Int
        get() = array.size

    override fun get(i: Int): Float = array[i]
    override fun set(i: Int, value: Float) { array[i] = value }

    override fun copy(): FloatBuffer = FloatArrayBuffer(array.copyOf())

}
