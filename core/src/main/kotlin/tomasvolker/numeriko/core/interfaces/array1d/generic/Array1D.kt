package tomasvolker.numeriko.core.interfaces.array1d.generic

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.generic.view.Default1DArrayListView
import tomasvolker.numeriko.core.interfaces.array1d.generic.view.DefaultArray1DView
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.factory.intArray1DOf
import tomasvolker.numeriko.core.preconditions.requireValidIndices

interface Array1D<out T>: ArrayND<T> {

    override val rank: Int get() = 1

    override val shape: IntArray1D
        get() = intArray1DOf(shape0)

    val shape0: Int get() = size

    override fun getShape(dimension: Int): Int =
            when(dimension) {
                0 -> shape0
                else -> throw IndexOutOfBoundsException("$dimension")
            }

    override val size: Int

    override fun getValue(vararg indices: Int): T {
        requireValidIndices(indices)
        return getValue(indices[0])
    }

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

    override fun copy(): Array1D<T> = copy(this)

    override fun iterator(): Iterator<T> = DefaultArray1DIterator(this)

    fun asList(): List<T> = Default1DArrayListView(this)

    operator fun component1(): T = getValue(0)
    operator fun component2(): T = getValue(1)
    operator fun component3(): T = getValue(2)
    operator fun component4(): T = getValue(3)
    operator fun component5(): T = getValue(4)

    override fun asMutable(): MutableArray1D<@UnsafeVariance T> = this as MutableArray1D<T>

}

operator fun <T> Array1D<T>.get(index: Int): T = getValue(index)
operator fun <T> Array1D<T>.get(index: Index): T = getValue(index)
operator fun <T> Array1D<T>.get(indexRange: IntRange): Array1D<T> = getView(indexRange)
operator fun <T> Array1D<T>.get(indexRange: IndexProgression): Array1D<T> = getView(indexRange)
