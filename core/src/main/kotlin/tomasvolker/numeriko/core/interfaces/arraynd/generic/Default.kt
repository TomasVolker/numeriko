package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.mutableIntZeros


class DefaultArrayNDIterator<T>(
        val array: ArrayND<T>
): Iterator<T> {

    var currentIndex = mutableIntZeros(array.rank)

    var overflow = false

    override fun hasNext(): Boolean = !overflow

    override fun next(): T =
            array.getValue(currentIndex).also {
                overflow = currentIndex.indexIncrement(array.shape)
            }

}


class DefaultDoubleArrayNDIterator(
        val array: DoubleArrayND
): DoubleIterator() {

    var currentIndex = mutableIntZeros(array.rank)

    var overflow = false

    override fun hasNext(): Boolean = !overflow

    override fun nextDouble(): Double =
            array.getDouble(currentIndex).also {
                overflow = currentIndex.indexIncrement(array.shape)
            }

}
