package numeriko.lowrank.interfaces.array1d.generic

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.core.index.Index
import tomasvolker.core.index.IndexProgression
import numeriko.lowrank.interfaces.array1d.generic.view.*
import numeriko.lowrank.interfaces.array1d.integer.IntArray1D
import tomasvolker.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.core.interfaces.factory.copy
import tomasvolker.core.interfaces.factory.intArray1DOf
import tomasvolker.core.preconditions.requireValidAxis
import tomasvolker.core.preconditions.requireValidIndices
import tomasvolker.core.view.ElementOrder

interface Array1D<out T>: ArrayND<T> {

    override val rank: Int get() = 1

    override val shape: IntArray1D
        get() = intArray1DOf(shape0)

    val shape0: Int get() = size

    override fun shape(axis: Int): Int {
        requireValidAxis(axis)
        return shape0
    }

    override val size: Int

    override fun getValue(indices: IntArray): T {
        requireValidIndices(indices)
        return getValue(indices[0])
    }

    override fun getValue(i0: Int): T
    fun getValue(i0: Index): T = getValue(i0.compute())

    fun withShape(shape: IntArray1D, order: ElementOrder = tomasvolker.numeriko.core.config.NumerikoConfig.defaultElementOrder): ArrayND<T> =
            DefaultReshapedView(
                shape = shape.copy(),
                array = this.asMutable(),
                offset = 0,
                order = order
            )

    fun getView(i0: IntProgression): Array1D<T> = defaultArray1DView(this.asMutable(), i0)
    fun getView(i0: IndexProgression): Array1D<T> = getView(i0.compute())

    fun Int.compute(): Int = this
    fun IntProgression.compute(): IntProgression = this
    fun Index.compute(): Int = this.computeValue(size)
    fun IndexProgression.compute(): IntProgression = this.computeProgression(size)

    override fun copy(): Array1D<T> = copy(this)

    fun asList(): List<T> = Default1DArrayListView(this)

    operator fun component1(): T = getValue(0)
    operator fun component2(): T = getValue(1)
    operator fun component3(): T = getValue(2)
    operator fun component4(): T = getValue(3)
    operator fun component5(): T = getValue(4)

    override fun iterator(): Iterator<T> = arrayIterator()
    override fun arrayIterator(): Array1DIterator<T> = DefaultArray1DIterator(this)

    override fun asMutable(): MutableArray1D<@UnsafeVariance T> = this as MutableArray1D<T>

    fun requireValidIndices(i0: Int) {

        if (tomasvolker.numeriko.core.config.NumerikoConfig.checkRanges) {
            // Do not use `indices` as inlining is not working
            if (i0 !in 0 until size) throw IndexOutOfBoundsException("Index $i0 is out of size $size")
        }

    }

    fun requireValidIndexRange(i0: IntProgression) {

        if (tomasvolker.numeriko.core.config.NumerikoConfig.checkRanges) {
            // Do not use `indices` as inlining is not working
            if (i0.first !in 0 until size) throw IndexOutOfBoundsException("Index $i0 is out of size $size")
            if (i0.last  !in 0 until size) throw IndexOutOfBoundsException("Index $i0 is out of size $size")
        }

    }

}

// Getter functions defined as extensions to avoid boxing when using get syntax on primitive specializations
operator fun <T> Array1D<T>.get(i0: Int): T = getValue(i0)
operator fun <T> Array1D<T>.get(i0: Index): T = getValue(i0)
operator fun <T> Array1D<T>.get(i0: IntRange): Array1D<T> = getView(i0)
operator fun <T> Array1D<T>.get(i0: IndexProgression): Array1D<T> = getView(i0)
