package tomasvolker.numeriko.core.interfaces.array2d.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.elementWise
import tomasvolker.numeriko.core.interfaces.factory.copy

interface DoubleArray2D: Array2D<Double> {

    override fun getValue(i0: Int, i1: Int): Double =
            getDouble(i0, i1)

    fun getDouble(i0: Int, i1: Int): Double

    fun getDouble(i0: Index, i1: Index): Double =
            getDouble(i0.computeValue(shape0), i1.computeValue(shape1))

    operator fun get(i0: Int, i1: Int): Double = getDouble(i0, i1)

/*
    override fun getView(indexRange: IntProgression): DoubleArray2D =
            DefaultDoubleArray2DView(
                    array = this,
                    offset = indexRange.first,
                    size = indexRange.count(),
                    stride = indexRange.step
            )

    override fun getView(indexRange: IndexProgression): DoubleArray1D =
            getView(indexRange.computeProgression(size))
*/
    override fun copy(): DoubleArray2D = /*copy(this)*/ TODO()
/*
    override fun iterator(): DoubleIterator =
            DefaultDoubleArray1DIterator(this)
*/

    operator fun unaryPlus(): MutableDoubleArray2D =
            elementWise { it }

    operator fun unaryMinus(): MutableDoubleArray2D =
            elementWise { -it }

    operator fun plus(other: DoubleArray2D): MutableDoubleArray2D =
            elementWise(this, other) { t, o -> t + o }

    operator fun minus(other: DoubleArray2D): MutableDoubleArray2D =
            elementWise(this, other) { t, o -> t - o }

    operator fun times(other: DoubleArray2D): MutableDoubleArray2D =
            elementWise(this, other) { t, o -> t * o }

    operator fun div(other: DoubleArray2D): MutableDoubleArray2D =
            elementWise(this, other) { t, o -> t / o }

    operator fun plus(other: Double): MutableDoubleArray2D =
            elementWise { it + other }

    operator fun minus(other: Double): MutableDoubleArray2D =
            elementWise { it - other }

    operator fun times(other: Double): MutableDoubleArray2D =
            elementWise { it * other }

    operator fun div(other: Double): MutableDoubleArray2D =
            elementWise { it / other }

    operator fun plus(other: Int): MutableDoubleArray2D =
            plus(other.toDouble())

    operator fun minus(other: Int): MutableDoubleArray2D =
            minus(other.toDouble())

    operator fun times(other: Int): MutableDoubleArray2D =
            times(other.toDouble())

    operator fun div(other: Int): MutableDoubleArray2D =
            div(other.toDouble())

    fun sum(): Double = sumBy { it }

}
