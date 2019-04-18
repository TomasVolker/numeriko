package tomasvolker.numeriko.core.interfaces.arraynd.float

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayNDIterator
import tomasvolker.numeriko.core.interfaces.iteration.indexIncrement

abstract class FloatArrayNDIterator: FloatIterator(), ArrayNDIterator<Float>

class DefaultFloatArrayNDIterator(
        val array: FloatArrayND
): FloatArrayNDIterator() {

    override var previousIndexArray = IntArray(array.rank) { a -> array.shape(a)-1 }
    override var nextIndexArray = IntArray(array.rank) { 0 }
    override var shapeArray = IntArray(array.rank) { i -> array.shape(i) }

    var overflow = array.isEmpty()

    override fun hasNext(): Boolean = !overflow

    override fun nextFloat(): Float {
        nextIndexArray.copyInto(previousIndexArray)
        overflow = nextIndexArray.indexIncrement(shapeArray)
        return array.getFloat(previousIndexArray)
    }
}
