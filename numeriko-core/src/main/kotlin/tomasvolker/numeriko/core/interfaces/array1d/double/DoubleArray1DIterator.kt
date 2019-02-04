package tomasvolker.numeriko.core.interfaces.array1d.double

import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1DIterator
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayNDIterator

abstract class DoubleArray1DIterator: DoubleArrayNDIterator(), Array1DIterator<Double>

class DefaultDoubleArray1DIterator(
        val array: DoubleArray1D
): DoubleArray1DIterator() {

    override var i0
        get() = currentIndexArray[0]
        set(value) { currentIndexArray[0] = value }

    override var currentIndexArray = intArrayOf(0, 0)

    override var shapeArray: IntArray = intArrayOf(array.shape0)

    override fun hasNext(): Boolean = i0 < array.shape0

    override fun nextDouble(): Double {
        val result = array[i0]
        currentIndexArray[0]++
        return result
    }

    override fun hasPrevious(): Boolean =
            0 < i0

    override fun nextIndex(): Int = i0

    override fun previous(): Double {
        currentIndexArray[0]--
        return array.getValue(i0)
    }

    override fun previousIndex(): Int = i0 - 1

}