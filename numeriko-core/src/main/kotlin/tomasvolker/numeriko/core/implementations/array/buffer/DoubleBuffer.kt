package tomasvolker.numeriko.core.implementations.array.buffer

interface DoubleBuffer: Buffer<Double> {

    override fun getValue(i: Int): Double = get(i)
    override fun setValue(i: Int, value: Double) {
        set(i, value)
    }

    operator fun get(i: Int): Double
    operator fun set(i: Int, value: Double)

    override fun copy(): DoubleBuffer
    override fun copy(start: Int, size: Int): DoubleBuffer

    override fun copyInto(
            destination: Buffer<Double>,
            destinationOffset: Int,
            startIndex: Int,
            endIndex: Int
    ) {
        if (destination is DoubleBuffer) {
            for (i in startIndex until endIndex) {
                destination[destinationOffset+i] = this[i]
            }
        } else {
            super.copyInto(destination, destinationOffset, startIndex, endIndex)
        }
    }

}

class DoubleArrayBuffer(
        val array: DoubleArray
): DoubleBuffer {

    override val size: Int
        get() = array.size

    override fun get(i: Int): Double = array[i]
    override fun set(i: Int, value: Double) { array[i] = value }

    override fun copy(): DoubleBuffer = DoubleArrayBuffer(array.copyOf())
    override fun copy(start: Int, size: Int): DoubleBuffer =
            DoubleArrayBuffer(array.copyOfRange(start, start+size))

    override fun copyInto(
            destination: Buffer<Double>,
            destinationOffset: Int,
            startIndex: Int,
            endIndex: Int
    ) {
        if (destination is DoubleArrayBuffer) {
            array.copyInto(destination.array, destinationOffset, startIndex, endIndex)
        } else {
            super.copyInto(destination, destinationOffset, startIndex, endIndex)
        }
    }

}
