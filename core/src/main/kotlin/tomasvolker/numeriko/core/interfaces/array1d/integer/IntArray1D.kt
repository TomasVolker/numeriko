package tomasvolker.numeriko.core.interfaces.array1d.integer

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.view.DefaultMutableIntArray1DView

interface IntArray1D: Array1D<Int> {

    override fun getValue(index: Int): Int =
            getInt(index)

    fun getInt(index: Int): Int

    fun getInt(index: Index): Int =
            getInt(index.computeValue(size))

    override fun getView(indexRange: IntProgression): IntArray1D =
            DefaultMutableIntArray1DView(
                    array = this.asMutable(),
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

    // Return copy?
    operator fun unaryPlus(): IntArray1D = this

    operator fun unaryMinus(): IntArray1D =
            elementWise { -it }

    operator fun plus(other: IntArray1D): IntArray1D =
            elementWise(this, other) { t, o -> t + o }

    operator fun minus(other: IntArray1D): IntArray1D =
            elementWise(this, other) { t, o -> t - o }

    operator fun times(other: IntArray1D): IntArray1D =
            elementWise(this, other) { t, o -> t * o }

    operator fun div(other: IntArray1D): IntArray1D =
            elementWise(this, other) { t, o -> t / o }

    operator fun plus(other: Int): IntArray1D =
            elementWise { it + other }

    operator fun minus(other: Int): IntArray1D =
            elementWise { it - other }

    operator fun times(other: Int): IntArray1D =
            elementWise { it * other }

    operator fun div(other: Int): IntArray1D =
            elementWise { it / other }

    fun sum(): Int = sumBy { it }

    fun asMutable(): MutableIntArray1D = this as MutableIntArray1D

}
