package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.asIntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.indexIncrement

interface ArrayNDIterator<out T>: Iterator<T> {

    var previousIndexArray: IntArray
    var nextIndexArray: IntArray
    val shapeArray: IntArray

    var nextIndex: IntArrayND
        get() = nextIndexArray.asIntArrayND()
        set(value) { nextIndexArray = value.toIntArray() }

}

class DefaultArrayNDIterator<out T>(
        val array: ArrayND<T>
): ArrayNDIterator<T> {

    override var previousIndexArray = IntArray(array.rank) { a -> array.shape(a)-1 }
    override var nextIndexArray = IntArray(array.rank) { 0 }
    override var shapeArray = IntArray(array.rank) { i -> array.shape(i) }

    var overflow = array.isEmpty()

    override fun hasNext(): Boolean = !overflow

    override fun next(): T {
        nextIndexArray.copyInto(previousIndexArray)
        overflow = nextIndexArray.indexIncrement(shapeArray)
        return array.getValue(previousIndexArray)
    }

}

