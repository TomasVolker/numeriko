package tomasvolker.numeriko.core.interfaces.generic.array1d

class DefaultArray1DIterator<T>(
        val array: Array1D<T>
): Iterator<T> {

    var index = 0

    override fun hasNext(): Boolean =
            index < array.size

    override fun next(): T =
            array.getValue(index).apply { index++ }

}