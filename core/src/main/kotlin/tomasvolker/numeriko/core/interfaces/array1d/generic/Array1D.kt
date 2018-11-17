package tomasvolker.numeriko.core.interfaces.array1d.generic

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.generic.view.Default1DArrayListView
import tomasvolker.numeriko.core.interfaces.array1d.generic.view.defaultArray1DView
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.factory.intArray1DOf

interface Array1D<out T>: ArrayND<T> {

    override val rank: Int get() = 1

    override val shape: IntArray1D
        get() = intArray1DOf(shape0)

    val shape0: Int get() = size

    override fun getShape(axis: Int): Int {
        requireValidAxis(axis)
        return shape0
    }

    override val size: Int

    override fun getValue(vararg indices: Int): T {
        requireValidIndices(indices)
        return getValue(indices[0])
    }

    fun getValue(i0: Int): T
    fun getValue(i0: Index): T = getValue(i0.compute())

    fun getView(i0: IntProgression): Array1D<T> = defaultArray1DView(this.asMutable(), i0)
    fun getView(i0: IndexProgression): Array1D<T> = getView(i0.compute())

    fun Int.compute(): Int = this
    fun IntProgression.compute(): IntProgression = this
    fun Index.compute(): Int = this.computeValue(size)
    fun IndexProgression.compute(): IntProgression = this.computeProgression(size)

    override fun copy(): Array1D<T> = copy(this)

    override fun iterator(): Iterator<T> = DefaultArray1DIterator(this)

    fun asList(): List<T> = Default1DArrayListView(this)

    operator fun component1(): T = getValue(0)
    operator fun component2(): T = getValue(1)
    operator fun component3(): T = getValue(2)
    operator fun component4(): T = getValue(3)
    operator fun component5(): T = getValue(4)

    override fun asMutable(): MutableArray1D<@UnsafeVariance T> = this as MutableArray1D<T>

    fun requireValidIndices(i0: Int) {

        if (NumerikoConfig.checkRanges) {
            if (i0 !in indices) throw IndexOutOfBoundsException("Index $i0 is out of size $size")
        }

    }

}

// Getter functions defined as extensions to avoid boxing when using get syntax on primitive specializations
operator fun <T> Array1D<T>.get(i0: Int): T = getValue(i0)
operator fun <T> Array1D<T>.get(i0: Index): T = getValue(i0)
operator fun <T> Array1D<T>.get(i0: IntRange): Array1D<T> = getView(i0)
operator fun <T> Array1D<T>.get(i0: IndexProgression): Array1D<T> = getView(i0)
