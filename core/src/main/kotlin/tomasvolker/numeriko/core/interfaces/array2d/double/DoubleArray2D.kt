package tomasvolker.numeriko.core.interfaces.array2d.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.view.DefaultMutableDoubleArray2DTransposeView
import tomasvolker.numeriko.core.interfaces.array2d.double.view.DefaultMutableDoubleArray2DView
import tomasvolker.numeriko.core.interfaces.array2d.double.view.MutableDoubleArray2D1DView
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.preconditions.requireValidIndices

interface DoubleArray2D: Array2D<Double>, DoubleArrayND {

    override fun getDouble(vararg indices: Int): Double {
        requireValidIndices(indices)
        return getDouble(indices[0], indices[1])
    }

    override fun getValue(vararg indices: Int): Double =
            getValue(*indices)

    override fun getValue(i0: Int, i1: Int): Double =
            getDouble(i0, i1)

    fun getDouble(i0: Int, i1: Int): Double

    fun getDouble(i0: Index, i1: Index): Double =
            getDouble(i0.computeValue(shape0), i1.computeValue(shape1))

    operator fun get(i0: Int, i1: Int): Double = getDouble(i0, i1)

    override fun getView(i0: IntProgression, i1: IntProgression): DoubleArray2D =
            DefaultMutableDoubleArray2DView(
                    array = this.asMutable(),
                    offset0 = i0.first,
                    offset1 = i1.first,
                    shape0 = i0.count(),
                    shape1 = i1.count(),
                    stride0 = i0.step,
                    stride1 = i1.step
            )

    override fun getView(i0: Int, i1: IntProgression): DoubleArray1D =
            MutableDoubleArray2D1DView(
                    DefaultMutableDoubleArray2DView(
                            array = this.asMutable(),
                            offset0 = i0,
                            offset1 = i1.first,
                            shape0 = 1,
                            shape1 = i1.count(),
                            stride0 = 1,
                            stride1 = i1.step
                    )
            )

    override fun getView(i0: IntProgression, i1: Int): DoubleArray1D =
            MutableDoubleArray2D1DView(
                    DefaultMutableDoubleArray2DView(
                            array = this.asMutable(),
                            offset0 = i0.first,
                            offset1 = i1,
                            shape0 = i0.count(),
                            shape1 = 1,
                            stride0 = i0.step,
                            stride1 = 1
                    )
            )

    override fun getView(i0: IndexProgression, i1: IndexProgression): DoubleArray2D =
            getView(i0.computeProgression(shape0), i1.computeProgression(shape1))

    override fun getView(i0: Int, i1: IndexProgression): DoubleArray1D =
            getView(i0, i1.computeProgression(shape1))

    override fun getView(i0: IndexProgression, i1: Int): DoubleArray1D =
            getView(i0.computeProgression(shape0), i1)

    fun transpose(): DoubleArray2D =
            DefaultMutableDoubleArray2DTransposeView(this.asMutable())

    override fun copy(): DoubleArray2D = /*copy(this)*/ TODO()

    override fun iterator(): DoubleIterator =
            DefaultDoubleArray2DIterator(this)


    operator fun unaryPlus(): DoubleArray2D =
            elementWise { it }

    operator fun unaryMinus(): DoubleArray2D =
            elementWise { -it }

    operator fun plus(other: DoubleArray2D): DoubleArray2D =
            elementWise(this, other) { t, o -> t + o }

    operator fun minus(other: DoubleArray2D): DoubleArray2D =
            elementWise(this, other) { t, o -> t - o }

    operator fun times(other: DoubleArray2D): DoubleArray2D =
            elementWise(this, other) { t, o -> t * o }

    operator fun div(other: DoubleArray2D): DoubleArray2D =
            elementWise(this, other) { t, o -> t / o }

    operator fun plus(other: Double): DoubleArray2D =
            elementWise { it + other }

    operator fun minus(other: Double): DoubleArray2D =
            elementWise { it - other }

    operator fun times(other: Double): DoubleArray2D =
            elementWise { it * other }

    operator fun div(other: Double): DoubleArray2D =
            elementWise { it / other }

    operator fun plus(other: Int): DoubleArray2D =
            plus(other.toDouble())

    operator fun minus(other: Int): DoubleArray2D =
            minus(other.toDouble())

    operator fun times(other: Int): DoubleArray2D =
            times(other.toDouble())

    operator fun div(other: Int): DoubleArray2D =
            div(other.toDouble())

    fun sum(): Double = sumBy { it }

    override fun asMutable(): MutableDoubleArray2D = this as MutableDoubleArray2D

}
