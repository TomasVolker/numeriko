package tomasvolker.numeriko.core.implementations.array.buffer

interface IntBuffer: Buffer<Int> {

    override fun getValue(i: Int): Int = get(i)
    override fun setValue(i: Int, value: Int) {
        set(i, value)
    }

    operator fun get(i: Int): Int
    operator fun set(i: Int, value: Int)

    override fun copy(): IntBuffer

}

class IntArrayBuffer(
        val array: IntArray
): IntBuffer {

    override val size: Int
        get() = array.size

    override fun get(i: Int): Int = array[i]
    override fun set(i: Int, value: Int) { array[i] = value }

    override fun copy(): IntBuffer = IntArrayBuffer(array.copyOf())

}
