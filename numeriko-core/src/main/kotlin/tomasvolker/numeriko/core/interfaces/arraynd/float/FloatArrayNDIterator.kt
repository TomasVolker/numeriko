package tomasvolker.numeriko.core.interfaces.arraynd.float

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayNDIterator
import tomasvolker.numeriko.core.interfaces.iteration.indexIncrement

abstract class FloatArrayNDIterator: FloatIterator(), ArrayNDIterator<Float>

class DefaultFloatArrayNDIterator(
        val array: FloatArrayND
): FloatArrayNDIterator() {

    override var nextIndexArray = IntArray(array.rank) { 0 }
    override var shapeArray = IntArray(array.rank) { i -> array.shape(i) }

    var overflow = array.isEmpty()

    override fun hasNext(): Boolean = !overflow

    override fun nextFloat(): Float =
            array.getFloat(nextIndexArray).also {
                overflow = nextIndexArray.indexIncrement(shapeArray)
            }
}
