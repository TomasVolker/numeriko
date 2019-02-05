package tomasvolker.numeriko.core.interfaces.array1d.generic

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayNDIterator

interface Array1DIterator<out T>: ArrayNDIterator<T>, ListIterator<T> {

    var i0: Int

}

class DefaultArray1DIterator<out T>(
        val array: Array1D<T>,
        initialIndex: Int = 0
): Array1DIterator<T> {

    override var currentIndexArray: IntArray
        get() = intArrayOf(i0)
        set(value) { i0 = value[0] }

    override val shapeArray: IntArray = array.shape.toIntArray()

    override var i0 = initialIndex

    override fun hasNext(): Boolean =
            i0 < array.size

    override fun next(): T =
            array.getValue(i0).apply { i0++ }

    override fun hasPrevious(): Boolean =
            0 < i0

    override fun nextIndex(): Int = i0

    override fun previous(): T {
        i0--
        return array.getValue(i0)
    }

    override fun previousIndex(): Int = i0 - 1

}