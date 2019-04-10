package tomasvolker.numeriko.lowrank.interfaces.array2d.generic

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayNDIterator

interface Array2DIterator<out T>: ArrayNDIterator<T> {

    var i0: Int
    var i1: Int

}

class DefaultArray2DIterator<out T>(
        val array: Array2D<T>
): Array2DIterator<T> {

    override var i0
        get() = currentIndexArray[0]
        set(value) { currentIndexArray[0] = value }

    override var i1
        get() = currentIndexArray[1]
        set(value) { currentIndexArray[1] = value }

    override var currentIndexArray = intArrayOf(0, 0)

    override var shapeArray: IntArray = intArrayOf(array.shape0, array.shape1)

    override fun hasNext(): Boolean =
            currentIndexArray[0] < array.shape0

    override fun next(): T {
        val result = array.getValue(i0, i1)
        currentIndexArray[1]++
        if (currentIndexArray[1] == array.shape1) {
            currentIndexArray[1] = 0
            currentIndexArray[0]++
        }
        return result
    }

}
