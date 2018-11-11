package tomasvolker.numeriko.core.interfaces.array1d.generic

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.generic.view.Default1DArrayListView
import tomasvolker.numeriko.core.interfaces.array1d.generic.view.DefaultArray1DView
import tomasvolker.numeriko.core.interfaces.factory.copy

interface Array1D<out T>: Iterable<T> {

    val rank: Int get() = 1

    val shape0: Int get() = size

    val size: Int

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

    override fun iterator(): Iterator<T> = DefaultArray1DIterator(this)

    fun asList(): List<T> = Default1DArrayListView(this)

}

operator fun <T> Array1D<T>.get(index: Int): T = getValue(index)
operator fun <T> Array1D<T>.get(index: Index): T = getValue(index)
operator fun <T> Array1D<T>.get(indexRange: IntRange): Array1D<T> = getView(indexRange)
operator fun <T> Array1D<T>.get(indexRange: IndexProgression): Array1D<T> = getView(indexRange)
