package tomasvolker.numeriko.core.interfaces.array1d.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultMutableDoubleArray1DView
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.preconditions.requireValidIndices

interface DoubleArray1D: Array1D<Double>, DoubleArrayND {

    override fun getValue(vararg indices: Int): Double =
            getDouble(*indices)

    override fun getDouble(vararg indices: Int): Double {
        requireValidIndices(indices)
        return getDouble(indices[0])
    }

    override fun getValue(index: Int): Double =
            getDouble(index)

    fun getDouble(index: Int): Double

    fun getDouble(index: Index): Double =
            getDouble(index.computeValue(size))

    override fun getView(indexRange: IntProgression): DoubleArray1D =
            DefaultMutableDoubleArray1DView(
                    array = this.asMutable(),
                    offset = indexRange.first,
                    size = indexRange.count(),
                    stride = indexRange.step
            )

    override fun getView(indexRange: IndexProgression): DoubleArray1D =
            getView(indexRange.computeProgression(size))

    override fun copy(): DoubleArray1D = copy(this)

    override fun iterator(): DoubleIterator =
            DefaultDoubleArray1DIterator(this)

    operator fun get(index: Int): Double = getDouble(index)
    operator fun get(index: Index): Double = getDouble(index)

    operator fun get(index: IntProgression): DoubleArray1D = getView(index)
    operator fun get(index: IndexProgression): DoubleArray1D = getView(index)

    operator fun unaryPlus(): DoubleArray1D =
            elementWise { it }

    operator fun unaryMinus(): DoubleArray1D =
            elementWise { -it }

    operator fun plus(other: DoubleArray1D): DoubleArray1D =
            elementWise(this, other) { t, o -> t + o }

    operator fun minus(other: DoubleArray1D): DoubleArray1D =
            elementWise(this, other) { t, o -> t - o }

    operator fun times(other: DoubleArray1D): DoubleArray1D =
            elementWise(this, other) { t, o -> t * o }

    operator fun div(other: DoubleArray1D): DoubleArray1D =
            elementWise(this, other) { t, o -> t / o }

    operator fun plus(other: Double): DoubleArray1D =
            elementWise { it + other }

    operator fun minus(other: Double): DoubleArray1D =
            elementWise { it - other }

    operator fun times(other: Double): DoubleArray1D =
            elementWise { it * other }

    operator fun div(other: Double): DoubleArray1D =
            elementWise { it / other }

    operator fun plus(other: Int): DoubleArray1D =
            plus(other.toDouble())

    operator fun minus(other: Int): DoubleArray1D =
            minus(other.toDouble())

    operator fun times(other: Int): DoubleArray1D =
            times(other.toDouble())

    operator fun div(other: Int): DoubleArray1D =
            div(other.toDouble())

    fun sum(): Double = sumBy { it }

    override fun asMutable(): MutableDoubleArray1D = this as MutableDoubleArray1D

}
