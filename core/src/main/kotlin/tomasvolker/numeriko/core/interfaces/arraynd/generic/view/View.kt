package tomasvolker.numeriko.core.interfaces.arraynd.generic.view

import tomasvolker.numeriko.core.interfaces.array1d.generic.view.DefaultMutableArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.generic.view.DefaultMutableArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.*
import tomasvolker.numeriko.core.interfaces.factory.intArray1D

class DefaultArrayNDCollapseView<T>(
        val array: MutableArrayND<T>,
        val axis: Int
) : DefaultMutableArrayND<T>() {

    init {
        require(array.shape[axis] == 1)
    }

    override val shape: IntArray1D = intArray1D(array.shape.filterIndexed { i, _ -> i != axis }.toIntArray())

    override fun getValue(indices: IntArray): T {
        requireValidIndices(indices)
        return array.getValue(
                *convertIndices(indices)
        )
    }

    override fun setValue(value: T, vararg indices: Int) {
        requireValidIndices(indices)
        array.setValue(value, *convertIndices(indices))
    }

    fun convertIndices(indices: IntArray): IntArray =
            IntArray(array.rank) { i ->
                when {
                    i < axis -> indices[i]
                    i == axis -> 0
                    i > axis -> indices[i-1]
                    else -> throw IllegalStateException()
                }
            }

}

fun <T> ArrayND<T>.collapseView(axis: Int): ArrayND<T> =
        DefaultArrayNDCollapseView(this.asMutable(), axis)

fun <T> MutableArrayND<T>.collapseView(axis: Int): MutableArrayND<T> =
        DefaultArrayNDCollapseView(this.asMutable(), axis)

class DefaultArrayNDView<T>(
        val array: MutableArrayND<T>,
        val offset: IntArray1D,
        override val shape: IntArray1D,
        val stride: IntArray1D
) : DefaultMutableArrayND<T>() {

    init {
        require(array.rank == offset.size &&
                array.rank == shape.size  &&
                array.rank == stride.size
        )

        array.requireValidIndices(convertIndices(IntArray(rank) { 0 }))
        array.requireValidIndices(convertIndices(IntArray(rank) { i -> shape[i]-1 }))
    }

    override fun getValue(vararg indices: Int): T {
        requireValidIndices(indices)
        return array.getValue(
                *convertIndices(indices)
        )
    }

    override fun setValue(value: T, vararg indices: Int) {
        requireValidIndices(indices)
        array.setValue(
                value,
                *convertIndices(indices)
        )
    }

    fun convertIndices(indices: IntArray): IntArray =
            IntArray(rank) { i -> offset[i] + stride[i] * indices[i] }

}

class DefaultArrayND1DView<T>(
        val array: MutableArrayND<T>
) : DefaultMutableArray1D<T>() {

    init {
        require(array.rank == 1)
    }

    override val size: Int
        get() = array.shape(0)


    override fun getValue(i0: Int): T =
            array.getValue(i0)

    override fun setValue(value: T, i0: Int) {
        array.setValue(value, i0)
    }

}


class DefaultArrayND2DView<T>(
        val array: MutableArrayND<T>
) : DefaultMutableArray2D<T>() {

    init {
        require(array.rank == 2)
    }

    override val shape0: Int
        get() = array.shape(0)

    override val shape1: Int
        get() = array.shape(0)

    override fun getValue(i0: Int, i1: Int): T =
            array.getValue(i0, i1)

    override fun setValue(value: T, i0: Int, i1: Int) {
        array.setValue(value, i0, i1)
    }

}
