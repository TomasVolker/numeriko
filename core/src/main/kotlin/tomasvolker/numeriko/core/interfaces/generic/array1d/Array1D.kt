package tomasvolker.numeriko.core.interfaces.generic.array1d

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.generic.util.defaultEquals
import tomasvolker.numeriko.core.interfaces.generic.util.defaultHashCode
import tomasvolker.numeriko.core.interfaces.generic.util.defaultToString

interface Array1D<out T>: Collection<T> {

    val rank: Int get() = 1

    fun getValue(index: Int): T

    fun getValue(index: Index): T =
            getValue(index.computeValue(size))

    fun getView(indexRange: IntProgression): Array1D<T> =
            DefaultArray1DView(
                    array = this,
                    offset = indexRange.first,
                    size = indexRange.count(),
                    stride = indexRange.step
            )

    fun getView(indexRange: IndexProgression): Array1D<T> =
            getView(indexRange.computeProgression(size))

    fun copy(): Array1D<T> = copy(this)

    override fun contains(element:@UnsafeVariance T): Boolean =
            any { it == element }

    override fun containsAll(elements: Collection<@UnsafeVariance T>): Boolean =
            elements.all { this.contains(it) }

    override fun isEmpty(): Boolean = size == 0

    override fun iterator(): Iterator<T> = DefaultArray1DIterator(this)

}

open class DefaultArray1DView<out T>(
        open val array: Array1D<T>,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : Array1D<T> {

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
