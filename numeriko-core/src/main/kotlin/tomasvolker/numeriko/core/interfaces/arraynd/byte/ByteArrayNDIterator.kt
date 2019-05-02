package tomasvolker.numeriko.core.interfaces.arraynd.byte

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayNDIterator
import tomasvolker.numeriko.core.interfaces.iteration.indexIncrement

abstract class ByteArrayNDIterator: ByteIterator(), ArrayNDIterator<Byte>

class DefaultByteArrayNDIterator(
        val array: ByteArrayND
): ByteArrayNDIterator() {

    override var nextIndexArray = IntArray(array.rank) { 0 }
    override var shapeArray = IntArray(array.rank) { i -> array.shape(i) }

    var overflow = array.isEmpty()

    override fun hasNext(): Boolean = !overflow

    override fun nextByte(): Byte =
            array.getByte(nextIndexArray).also {
                overflow = nextIndexArray.indexIncrement(shapeArray)
            }
}
