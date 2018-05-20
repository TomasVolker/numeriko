package tomasvolker.numeriko.core.interfaces.generic.array1d

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.factory.mutableCopy
import tomasvolker.numeriko.core.interfaces.generic.util.defaultEquals
import tomasvolker.numeriko.core.interfaces.generic.util.defaultHashCode
import tomasvolker.numeriko.core.interfaces.generic.util.defaultToString

interface MutableArray1D<T>: Array1D<T> {

    fun setValue(value: T, index: Int)

    fun setValue(value: T, index: Index) =
            setValue(value, index.computeValue(size))

    override fun getView(indexRange: IntProgression): MutableArray1D<T> =
            DefaultMutableArray1DView(
                    array = this,
                    offset = indexRange.first,
                    size = indexRange.count(),
                    stride = indexRange.step
            )

    override fun getView(indexRange: IndexProgression): MutableArray1D<T> =
            getView(indexRange.computeProgression(size))

    override fun copy(): MutableArray1D<T> = mutableCopy(this)

}

open class DefaultMutableArray1DView<T>(
        open val array: MutableArray1D<T>,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : MutableArray1D<T> {

    override fun setValue(value: T, index: Int) {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        array.setValue(value, offset + stride * index)
    }

    override fun getValue(index: Int): T {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        return array.getValue(offset + stride * index)
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Array1D<*>) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}
