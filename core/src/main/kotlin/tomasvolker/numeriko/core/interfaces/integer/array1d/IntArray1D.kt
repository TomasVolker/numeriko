package tomasvolker.numeriko.core.interfaces.integer.array1d

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.generic.array1d.Array1D
import tomasvolker.numeriko.core.interfaces.generic.util.defaultHashCode
import tomasvolker.numeriko.core.interfaces.generic.util.defaultToString
import tomasvolker.numeriko.core.interfaces.integer.util.defaultEquals

interface IntArray1D: Array1D<Int> {

    override fun getValue(index: Int): Int =
            getInt(index)

    fun getInt(index: Int): Int

    fun getInt(index: Index): Int =
            getInt(index.computeValue(size))

    override fun getView(indexRange: IntProgression): IntArray1D =
            DefaultIntArray1DView(
                    array = this,
                    offset = indexRange.first,
                    size = indexRange.count(),
                    stride = indexRange.step
            )

    override fun getView(indexRange: IndexProgression): IntArray1D =
            getView(indexRange.computeProgression(size))

    override fun copy(): IntArray1D = copy(this)

    override fun iterator(): IntIterator =
            DefaultIntArray1DIterator(this)

    operator fun get(index: Int): Int = getInt(index)
    operator fun get(index: Index): Int = getInt(index)

    operator fun get(index: IntProgression): IntArray1D = getView(index)
    operator fun get(index: IndexProgression): IntArray1D = getView(index)

}

class DefaultIntArray1DView(
        val array: IntArray1D,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : IntArray1D {

    override fun getInt(index: Int): Int {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        return array.getInt(offset + stride * index)
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is IntArray1D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}
