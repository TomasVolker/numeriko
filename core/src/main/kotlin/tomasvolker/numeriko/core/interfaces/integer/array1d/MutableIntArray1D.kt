package tomasvolker.numeriko.core.interfaces.integer.array1d

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.factory.mutableCopy
import tomasvolker.numeriko.core.interfaces.generic.array1d.MutableArray1D
import tomasvolker.numeriko.core.interfaces.generic.util.defaultToString
import tomasvolker.numeriko.core.interfaces.integer.util.defaultEquals
import tomasvolker.numeriko.core.interfaces.integer.util.defaultHashCode

interface MutableIntArray1D: IntArray1D, MutableArray1D<Int> {

    fun setInt(value: Int, index: Int)

    fun setInt(value: Int, index: Index) =
            setInt(value, index.computeValue(size))

    override fun setValue(value: Int, index: Int) =
            setInt(value, index)

    fun setValue(other: IntArray1D) {

        require(other.size == this.size) {
            "Sizes must much"
        }

        for (i in indices) {
            setInt(other.getInt(i), i)
        }

    }

    override fun setValue(value: Int) = setInt(value)

    fun setInt(value: Int) {

        for (i in indices) {
            setInt(value, i)
        }

    }

    override fun getView(indexRange: IntProgression): MutableIntArray1D =
            DefaultMutableIntArray1DView(
                    array = this,
                    offset = indexRange.first,
                    size = indexRange.count(),
                    stride = indexRange.step
            )

    override fun getView(indexRange: IndexProgression): MutableIntArray1D =
            getView(indexRange.computeProgression(size))

    fun setView(value: IntArray1D, indexRange: IndexProgression) =
            setView(value, indexRange.computeProgression(size))

    //TODO avoid copy when possible
    fun setView(value: IntArray1D, indexRange: IntProgression) =
            getView(indexRange).setValue(value.copy())

    override fun setView(value: Int, indexRange: IndexProgression) =
            setView(value, indexRange.computeProgression(size))

    override fun setView(value: Int, indexRange: IntProgression) =
            getView(indexRange).setInt(value)

    override fun copy(): MutableIntArray1D = mutableCopy(this)

    override operator fun get(index: IntProgression): MutableIntArray1D = getView(index)
    override operator fun get(index: IndexProgression): MutableIntArray1D = getView(index)

    operator fun set(index: Int, value: Int) = setValue(value, index)
    operator fun set(index: Index, value: Int) = setValue(value, index)

    operator fun set(index: IntProgression, value: Int) = setView(value, index)
    operator fun set(index: IndexProgression, value: Int) = setView(value, index)

    operator fun set(index: IntProgression, value: IntArray1D) = setView(value, index)
    operator fun set(index: IndexProgression, value: IntArray1D) = setView(value, index)

}

open class DefaultMutableIntArray1DView (
        open val array: MutableIntArray1D,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : MutableIntArray1D {

    override fun setInt(value: Int, index: Int) {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        array.setInt(value, offset + stride * index)
    }

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
