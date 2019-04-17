package tomasvolker.numeriko.core.interfaces.arraynd.integer

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayNDIterator
import tomasvolker.numeriko.core.interfaces.iteration.indexIncrement

abstract class IntArrayNDIterator: IntIterator(), ArrayNDIterator<Int>

class DefaultIntArrayNDIterator(
        val array: IntArrayND
): IntArrayNDIterator() {

    override var currentIndexArray = IntArray(array.rank) { 0 }
    override var shapeArray = IntArray(array.rank) { i -> array.shape(i) }

    var overflow = array.isEmpty()

    override fun hasNext(): Boolean = !overflow

    override fun nextInt(): Int =
            array.getInt(currentIndexArray).also {
                overflow = currentIndexArray.indexIncrement(shapeArray)
            }

}
