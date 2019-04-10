package tomasvolker.numeriko.lowrank.interfaces.array2d.double

import tomasvolker.numeriko.lowrank.interfaces.array2d.generic.Array2DIterator
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayNDIterator

abstract class DoubleArray2DIterator: DoubleArrayNDIterator(), Array2DIterator<Double> {

    abstract override var i0: Int
    abstract override var i1: Int

}

class DefaultDoubleArray2DIterator(
        val array: DoubleArray2D
): DoubleArray2DIterator() {

    override var i0
        get() = currentIndexArray[0]
        set(value) { currentIndexArray[0] = value }

    override var i1
        get() = currentIndexArray[1]
        set(value) { currentIndexArray[1] = value }

    override var currentIndexArray = intArrayOf(0, 0)

    override var shapeArray: IntArray = intArrayOf(array.shape0, array.shape1)

    override fun hasNext(): Boolean =
            i0 < array.shape0

    override fun nextDouble(): Double {
        val result = array.getDouble(currentIndexArray)
        currentIndexArray[1]++
        if (i1 == array.shape1) {
            currentIndexArray[1] = 0
            currentIndexArray[0]++
        }
        return result
    }

}
