package tomasvolker.numeriko.core.interfaces.array2d.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.view.DefaultMutableDoubleArray2DTransposeView
import tomasvolker.numeriko.core.interfaces.array2d.double.view.DefaultMutableDoubleArray2DView
import tomasvolker.numeriko.core.interfaces.array2d.double.view.MutableDoubleArray2DCollapseView
import tomasvolker.numeriko.core.interfaces.array2d.generic.*
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.preconditions.requireSameShape
import tomasvolker.numeriko.core.preconditions.requireValidIndices

interface MutableDoubleArray2D: DoubleArray2D, MutableArray2D<Double>, MutableDoubleArrayND {

    override fun setValue(value: Double, vararg indices: Int) {
        setDouble(value, *indices)
    }

    override fun setDouble(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        setDouble(value, indices[0], indices[1])
    }

    fun setDouble(value: Double, i0: Int, i1: Int)

    fun setDouble(value: Double, i0: Index, i1: Index) =
            setDouble(value, i0.computeValue(shape0), i1.computeValue(shape1))

    override fun setValue(value: Double, i0: Int, i1: Int) =
            setDouble(value, i0, i1)

    fun setValue(other: DoubleArray2D) {

        requireSameShape(this, other)

        forEachIndex { i0, i1 ->
            setDouble(other.getDouble(i0, i1), i0, i1)
        }

    }

    override fun setValue(value: Double) = setDouble(value)

    fun setDouble(value: Double) {

        forEachIndex { i0, i1 ->
            setDouble(value, i0, i1)
        }

    }

    override fun getView(i0: IntProgression, i1: IntProgression): MutableDoubleArray2D =
            DefaultMutableDoubleArray2DView(
                    array = this,
                    offset0 = i0.first,
                    offset1 = i1.first,
                    shape0 = i0.count(),
                    shape1 = i1.count(),
                    stride0 = i0.step,
                    stride1 = i1.step
            )

    override fun getView(i0: Int, i1: IntProgression): MutableDoubleArray1D =
            MutableDoubleArray2DCollapseView(
                    DefaultMutableDoubleArray2DView(
                            array = this,
                            offset0 = i0,
                            offset1 = i1.first,
                            shape0 = 1,
                            shape1 = i1.count(),
                            stride0 = 1,
                            stride1 = i1.step
                    )
            )

    override fun getView(i0: IntProgression, i1: Int): MutableDoubleArray1D =
            MutableDoubleArray2DCollapseView(
                    DefaultMutableDoubleArray2DView(
                            array = this,
                            offset0 = i0.first,
                            offset1 = i1,
                            shape0 = i0.count(),
                            shape1 = 1,
                            stride0 = i0.step,
                            stride1 = 1
                    )
            )

    override fun getView(i0: IndexProgression, i1: IndexProgression): MutableDoubleArray2D =
            getView(
                    i0.computeProgression(shape0),
                    i1.computeProgression(shape1)
            )

    override fun getView(i0: Int, i1: IndexProgression): MutableDoubleArray1D =
            getView(
                    i0,
                    i1.computeProgression(shape1)
            )

    override fun getView(i0: IndexProgression, i1: Int): MutableDoubleArray1D =
            getView(
                    i0.computeProgression(shape0),
                    i1
            )

    fun setView(value: DoubleArray2D, i0: IndexProgression, i1: IndexProgression) =
            setView(value,
                    i0.computeProgression(shape0),
                    i1.computeProgression(shape1)
            )

    fun setView(value: DoubleArray2D, i0: IntProgression, i1: IntProgression) =
            getView(i0, i1).setValue(value.copy())

    override fun setView(value: Double, i0: IndexProgression, i1: IndexProgression) =
            setView(value, i0.computeProgression(shape0), i1.computeProgression(shape1))

    override fun setView(value: Double, i0: IntProgression, i1: IntProgression) =
            getView(i0, i1).setDouble(value)

    override fun copy(): MutableDoubleArray2D = copy(this).asMutable()

    operator fun set(i0: Int, i1: Int, value: Double) = setDouble(value, i0, i1)
    operator fun set(i0: Index, i1: Index, value: Double) =
            setValue(value, i0.computeValue(shape0), i1.computeValue(shape1))

    /**
     * @return a transposed view of this 2DArray
     */
    override fun transpose(): MutableDoubleArray2D =
            DefaultMutableDoubleArray2DTransposeView(this)

    fun applyPlus(other: DoubleArray2D): MutableDoubleArray2D =
            applyElementWise(other) { t, o -> t + o }

    fun applyMinus(other: DoubleArray2D): MutableDoubleArray2D =
            applyElementWise(other) { t, o -> t - o }

    fun applyTimes(other: DoubleArray2D): MutableDoubleArray2D =
            applyElementWise(other) { t, o -> t * o }

    fun applyDiv(other: DoubleArray2D): MutableDoubleArray2D =
            applyElementWise(other) { t, o -> t / o }

    fun applyPlus(other: Double): MutableDoubleArray2D =
            applyElementWise { it + other }

    fun applyMinus(other: Double): MutableDoubleArray2D =
            applyElementWise { it - other }

    fun applyTimes(other: Double): MutableDoubleArray2D =
            applyElementWise { it * other }

    fun applyDiv(other: Double): MutableDoubleArray2D =
            applyElementWise { it / other }

    fun applyPlus(other: Int): MutableDoubleArray2D =
            applyPlus(other.toDouble())

    fun applyMinus(other: Int): MutableDoubleArray2D =
            applyMinus(other.toDouble())

    fun applyTimes(other: Int): MutableDoubleArray2D =
            applyTimes(other.toDouble())

    fun applyDiv(other: Int): MutableDoubleArray2D =
            applyDiv(other.toDouble())

}
