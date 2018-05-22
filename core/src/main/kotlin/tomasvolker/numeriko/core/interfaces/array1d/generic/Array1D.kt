package tomasvolker.numeriko.core.interfaces.array1d.generic

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.factory.copy

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
