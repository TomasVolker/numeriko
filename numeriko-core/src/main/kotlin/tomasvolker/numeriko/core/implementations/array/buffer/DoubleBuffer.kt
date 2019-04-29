package tomasvolker.numeriko.core.implementations.array.buffer

interface DoubleBuffer: Buffer<Double> {

    override fun getValue(i: Int): Double = get(i)
    override fun setValue(i: Int, value: Double) {
        set(i, value)
    }

    operator fun get(i: Int): Double
    operator fun set(i: Int, value: Double)

    override fun copy(): DoubleBuffer

}

class DoubleArrayBuffer(
        val array: DoubleArray
): DoubleBuffer {

    override val size: Int
        get() = array.size

    override fun get(i: Int): Double = array[i]
    override fun set(i: Int, value: Double) { array[i] = value }

    override fun copy(): DoubleBuffer = DoubleArrayBuffer(array.copyOf())

}
