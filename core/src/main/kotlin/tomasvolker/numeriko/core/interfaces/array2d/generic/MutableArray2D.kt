package tomasvolker.numeriko.core.interfaces.array2d.generic

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.factory.mutableCopy
import tomasvolker.numeriko.core.preconditions.requireSameShape

interface MutableArray2D<T>: Array2D<T> {

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

    override fun getView(i0: IntProgression, i1: IntProgression): MutableArray2D<T> =
            DefaultMutableArray2DView(
                    array = this,
                    offset0 = i0.first,
                    offset1 = i1.first,
                    shape0 = i0.count(),
                    shape1 = i1.count(),
                    stride0 = i0.step,
                    stride1 = i1.step
            )

    override fun getView(i0: IndexProgression, i1: IndexProgression): MutableArray2D<T> =
            getView(
                    i0.computeProgression(shape0),
                    i1.computeProgression(shape1)
            )

    fun setView(value: Array2D<T>, i0: IndexProgression, i1: IndexProgression) =
            setView(value,
                    i0.computeProgression(shape0),
                    i1.computeProgression(shape1)
            )

    fun setView(value: Array2D<T>, i0: IntProgression, i1: IntProgression) =
            getView(i0, i1).setValue(value.copy())

    fun setView(value: T, i0: IndexProgression, i1: IndexProgression) =
            setView(value, i0.computeProgression(shape0), i1.computeProgression(shape1))

    fun setView(value: T, i0: IntProgression, i1: IntProgression) =
            getView(i0, i1).setValue(value)

    override fun copy(): MutableArray2D<T> = mutableCopy(this)

}
