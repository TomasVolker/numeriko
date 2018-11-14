package tomasvolker.numeriko.core.interfaces.array2d.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.view.DefaultMutableDoubleArray2DTransposeView
import tomasvolker.numeriko.core.interfaces.array2d.double.view.DefaultMutableDoubleArray2DView
import tomasvolker.numeriko.core.interfaces.array2d.double.view.MutableDoubleArray2DCollapseView
import tomasvolker.numeriko.core.interfaces.array2d.generic.*
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.linearalgebra.DefaultLinearAlgebra
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.primitives.sumDouble

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
            MutableDoubleArray2DCollapseView(
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
            MutableDoubleArray2DCollapseView(
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

    fun determinant(): Double = DefaultLinearAlgebra.determinant(this)

    fun solve(image: DoubleArray1D): DoubleArray1D = DefaultLinearAlgebra.solve(this, image)

    fun inverse(): DoubleArray2D = DefaultLinearAlgebra.inverse(this)

    infix fun matMul(other: DoubleArray1D): DoubleArray1D {
        require(this.shape1 == other.shape0) {
            "sizes dont match"
        }
        return doubleArray1D(this.shape0) { i0 ->
            sumDouble(this.indices1) { k -> this[i0, k] * other[k] }
        }
    }

    fun quadraticForm(other: DoubleArray1D): Double {
        require(this.isSquare()) {
            "Array is not square ${this.shape}"
        }
        require(this.shape0 == other.size) {
            "Sizes dont match"
        }

        var result = 0.0
        forEachIndex { i0, i1 ->
            result += this[i0, i1] * other[i0] * other[i1]
        }
        return result
    }

    infix fun matMul(other: DoubleArray2D): DoubleArray2D {
        require(this.shape1 == other.shape0) {
            "sizes dont match"
        }
        return doubleArray2D(this.shape0, other.shape1) { i0, i1 ->
            sumDouble(this.indices0) { k -> this[i0, k] * other[k, i1] }
        }
    }

    fun filter2D(filter: DoubleArray2D, padding: Double = 0.0): DoubleArray2D {

        val filterCenter0 = filter.shape0 / 2
        val filterCenter1 = filter.shape1 / 2

        return doubleArray2D(this.shape0, this.shape1) { i0, i1 ->
            sumDouble(filter.indices(0), filter.indices(1)) { j0, j1 ->
                val k0 = i0 - j0 - filterCenter0
                val k1 = i1 - j1 - filterCenter1
                if (k0 in 0 until this.shape0 && k1 in 0 until this.shape1) {
                    this[k0, k1] * filter[j0, j1]
                } else {
                    padding
                }
            }
        }
    }


    override fun asMutable(): MutableDoubleArray2D = this as MutableDoubleArray2D

}
