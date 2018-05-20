package tomasvolker.numeriko.core.interfaces.double.array1d

class DefaultDoubleArray1DIterator(
        val array: DoubleArray1D
): DoubleIterator() {

    var index = 0

    override fun hasNext(): Boolean =
            index < array.size

    override fun nextDouble(): Double =
            array.getDouble(index).apply { index++ }

}