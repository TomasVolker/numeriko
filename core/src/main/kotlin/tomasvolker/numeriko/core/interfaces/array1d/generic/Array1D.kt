package tomasvolker.numeriko.core.interfaces.array1d.generic

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.factory.copy

interface Array1D<out T>: List<T> {

    val rank: Int get() = 1

    val shape0: Int get() = size

    fun getValue(index: Int): T

    override fun get(index: Int): T = getValue(index)

    override fun indexOf(element: @UnsafeVariance T): Int {
        for (i in indices) {
            if (element == getValue(i)) {
                return i
            }
        }
        return -1
    }

    override fun lastIndexOf(element: @UnsafeVariance T): Int {
        for (i in indices.reversed()) {
            if (element == getValue(i)) {
                return i
            }
        }
        return -1
    }

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

    override fun iterator(): Iterator<T> = listIterator()

    override fun listIterator(): ListIterator<T> = DefaultArray1DIterator(this)

    override fun listIterator(index: Int): ListIterator<T> = DefaultArray1DIterator(this, index)

    override fun subList(fromIndex: Int, toIndex: Int): List<T> =
            getView(fromIndex until toIndex)

}
