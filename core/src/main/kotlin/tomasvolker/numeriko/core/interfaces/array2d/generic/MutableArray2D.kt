package tomasvolker.numeriko.core.interfaces.array2d.generic

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.MutableArray1D
import tomasvolker.numeriko.core.interfaces.array2d.generic.view.Array2DCollapseView
import tomasvolker.numeriko.core.interfaces.array2d.generic.view.DefaultArray2DView
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.preconditions.requireSameShape

interface MutableArray2D<T>: Array2D<T>, MutableArrayND<T> {

    override fun setValue(value: T, vararg indices: Int) {
        require(indices.size == 2)
        setValue(value, indices[0], indices[1])
    }

    fun setValue(value: T, i0: Int, i1: Int)

    fun setValue(value: T, i0: Index, i1: Index) =
            setValue(value, i0.computeValue(shape0), i1.computeValue(shape1))

    fun setValue(other: Array2D<T>) {

        requireSameShape(this, other)

        applyElementWise(other) { _, o -> o }

    }

    fun setValue(value: T) {
        applyMap { value }
    }

    override fun getView(i0: Int, i1: IntProgression): MutableArray1D<T> =
            Array2DCollapseView(
                    DefaultArray2DView(
                            array = this,
                            offset0 = i0,
                            offset1 = i1.first,
                            shape0 = i0,
                            shape1 = i1.count(),
                            stride0 = i0,
                            stride1 = i1.step
                    )
            )

    override fun getView(i0: Int  , i1: IndexProgression): MutableArray1D<T> = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: IntProgression  ): MutableArray1D<T> = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: IndexProgression): MutableArray1D<T> = getView(i0.compute(0), i1.compute(1))

    override fun getView(i0: IntProgression, i1: Int): MutableArray1D<T> =
            Array2DCollapseView(
                    DefaultArray2DView(
                            array = this,
                            offset0 = i0.first,
                            offset1 = i1,
                            shape0 = i0.count(),
                            shape1 = i1,
                            stride0 = i0.step,
                            stride1 = i1
                    )
            )

    override fun getView(i0: IndexProgression, i1: Int  ): MutableArray1D<T> = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IntProgression  , i1: Index): MutableArray1D<T> = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: Index): MutableArray1D<T> = getView(i0.compute(0), i1.compute(1))

    override fun getView(i0: IntProgression, i1: IntProgression): MutableArray2D<T> =
            DefaultArray2DView(
                    array = this,
                    offset0 = i0.first,
                    offset1 = i1.first,
                    shape0 = i0.count(),
                    shape1 = i1.count(),
                    stride0 = i0.step,
                    stride1 = i1.step
            )
    override fun getView(i0: IntProgression  , i1: IndexProgression): MutableArray2D<T> = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: IntProgression  ): MutableArray2D<T> = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: IndexProgression): MutableArray2D<T> = getView(i0.compute(0), i1.compute(1))

    fun setView(value: Array1D<T>, i0: Int  , i1: IntProgression  ) = getView(i0, i1).setValue(value.copy())
    fun setView(value: Array1D<T>, i0: Int  , i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: Array1D<T>, i0: Index, i1: IntProgression  ) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: Array1D<T>, i0: Index, i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))

    fun setView(value: Array2D<T>, i0: IntProgression  , i1: IntProgression  ) = getView(i0, i1).setValue(value.copy())
    fun setView(value: Array2D<T>, i0: IntProgression  , i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: Array2D<T>, i0: IndexProgression, i1: IntProgression  ) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: Array2D<T>, i0: IndexProgression, i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))

    fun setView(value: T, i0: IntProgression  , i1: IntProgression  ) = getView(i0, i1).setValue(value)
    fun setView(value: T, i0: IntProgression  , i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: T, i0: IndexProgression, i1: IntProgression  ) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: T, i0: IndexProgression, i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))

    override fun copy(): MutableArray2D<T> = copy(this).asMutable()

}

operator fun <T> MutableArray2D<T>.set(i0: Int, i1: Int, value: T): Unit = setValue(value, i0, i1)
