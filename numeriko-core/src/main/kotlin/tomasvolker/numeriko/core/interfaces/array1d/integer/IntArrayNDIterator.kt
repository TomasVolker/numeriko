package tomasvolker.numeriko.core.interfaces.array1d.integer

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayNDIterator
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayNDIterator
import tomasvolker.numeriko.core.interfaces.iteration.indexIncrement

abstract class IntArray1DIterator: IntIterator(), ArrayNDIterator<Int>

class DefaultIntArray1DIterator(
        val array: IntArray1D
): IntArrayNDIterator() {

    override var nextIndexArray = IntArray(1) { 0 }
    override var shapeArray = IntArray(1) { i -> array.shape(i) }

    var overflow = array.isEmpty()

    override fun hasNext(): Boolean = !overflow

    override fun nextInt(): Int =
            array[nextIndexArray[0]].also {
                overflow = nextIndexArray[0]++ == array.size
            }

}
