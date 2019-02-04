package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.factory.asIntArray1D
import tomasvolker.numeriko.core.performance.indexIncrement

interface ArrayNDIterator<out T>: Iterator<T> {

    var currentIndexArray: IntArray
    val shapeArray: IntArray

    var currentIndex: IntArray1D
        get() = currentIndexArray.asIntArray1D()
        set(value) { currentIndexArray = value.toIntArray() }

}

class DefaultArrayNDIterator<out T>(
        val array: ArrayND<T>
): ArrayNDIterator<T> {

    override var currentIndexArray = IntArray(array.rank) { 0 }
    override var shapeArray = IntArray(array.rank) { i -> array.shape(i) }

    var overflow = array.isEmpty()

    override fun hasNext(): Boolean = !overflow

    override fun next(): T =
            array.getValue(currentIndexArray).also {
                overflow = currentIndexArray.indexIncrement(shapeArray)
            }

}

