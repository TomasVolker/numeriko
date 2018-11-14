package tomasvolker.numeriko.core.interfaces.array2d.generic

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.lowdim.integer.IntVector2
import tomasvolker.numeriko.core.interfaces.array1d.lowdim.integer.intVector2
import tomasvolker.numeriko.core.interfaces.array2d.generic.view.Array2DCollapseView
import tomasvolker.numeriko.core.interfaces.array2d.generic.view.DefaultArray2DView
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy

interface Array2D<out T>: ArrayND<T> {

    override val rank: Int get() = 2

    override val shape: IntVector2 get() = intVector2(shape0, shape1)

    val shape0: Int
    val shape1: Int

    override fun getShape(dimension: Int): Int =
            when(dimension) {
                0 -> shape0
                1 -> shape1
                else -> throw IndexOutOfBoundsException("$dimension")
            }

    override val size: Int
        get() = shape0 * shape1

    override fun getValue(vararg indices: Int): T {
        require(indices.size == 2)
        return getValue(indices[0], indices[1])
    }

    fun getValue(i0: Int, i1: Int): T

    fun getView(i0: IntProgression, i1: IntProgression): Array2D<T> =
            DefaultArray2DView(
                    array = this,
                    offset0 = i0.first,
                    offset1 = i1.first,
                    shape0 = i0.count(),
                    shape1 = i1.count(),
                    stride0 = i0.step,
                    stride1 = i1.step
            )

    fun getView(i0: Int, i1: IntProgression): Array1D<T> =
            Array2DCollapseView(
                    DefaultArray2DView(
                        array = this,
                        offset0 = i0,
                        offset1 = i1.first,
                        shape0 = 1,
                        shape1 = i1.count(),
                        stride0 = 1,
                        stride1 = i1.step
                )
            )

    fun getView(i0: IntProgression, i1: Int): Array1D<T> =
            Array2DCollapseView(
                    DefaultArray2DView(
                            array = this,
                            offset0 = i0.first,
                            offset1 = i1,
                            shape0 = i0.count(),
                            shape1 = i1,
                            stride0 = i0.step,
                            stride1 = 1
                    )
            )

    fun getValue(i0: Index, i1: Index): T =
            getValue(i0.computeValue(shape0), i1.computeValue(shape1))

    fun getView(i0: IndexProgression, i1: IndexProgression): Array2D<T> =
            getView(i0.computeProgression(shape0), i1.computeProgression(shape1))

    fun getView(i0: IndexProgression, i1: Int): Array1D<T> =
            getView(i0.computeProgression(shape0), i1)

    fun getView(i0: Int, i1: IndexProgression): Array1D<T> =
            getView(i0, i1.computeProgression(shape1))

    override fun copy(): Array2D<T> = copy(this)

    override fun iterator(): Iterator<T> = DefaultArray2DIterator(this)

    override fun asMutable(): MutableArray2D<@UnsafeVariance T> = this as MutableArray2D<T>

}

operator fun <T> Array2D<T>.get(i0: Int, i1: Int): T = getValue(i0, i1)
