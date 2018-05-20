package tomasvolker.numeriko.core.interfaces.integer.array1d

class DefaultIntArray1DIterator(
        val array: IntArray1D
): IntIterator() {

    var index = 0

    override fun hasNext(): Boolean =
            index < array.size

    override fun nextInt(): Int =
            array.getInt(index).apply { index++ }

}