package tomasvolker.numeriko.core.interfaces.arraynd.generic.view

import tomasvolker.numeriko.core.interfaces.array1d.generic.view.DefaultMutableArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.generic.view.DefaultMutableArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.*
import tomasvolker.numeriko.core.interfaces.factory.intArray1D

class DefaultArrayNDCollapseView<T>(
        val array: ArrayND<T>,
        val dimension: Int
) : DefaultArrayND<T>() {

    init {
        require(array.shape[dimension] == 1)
    }

    override val shape: IntArray1D = intArray1D(array.shape.filterIndexed { i, _ -> i != dimension }.toIntArray())

    override fun getValue(indices: IntArray): T {
        requireValidIndices(indices)
        return array.getValue(
                *IntArray(array.rank) { i ->
                    when {
                        i < dimension -> indices[i]
                        i == dimension -> 0
                        i > dimension -> indices[i-1]
                        else -> throw IllegalStateException()
                    }
                }
        )
    }

}

fun <T> ArrayND<T>.collapseView(dimension: Int): ArrayND<T> =
        DefaultArrayNDCollapseView(this, dimension)

open class DefaultArrayNDView<T>(
        array: ArrayND<T>,
        val offset: IntArray1D,
        final override val shape: IntArray1D,
        val stride: IntArray1D
) : DefaultArrayND<T>() {

    init {
        require(array.rank == offset.size &&
                array.rank == shape.size &&
                array.rank == stride.size
        )

    }

    open val array: ArrayND<T> = array

    override fun getValue(vararg indices: Int): T {
        requireValidIndices(indices)
        return array.getValue(
                *IntArray(rank) { i -> offset[i] + stride[i] * indices[i] }
        )
    }

}

class DefaultMutableArrayNDView<T>(
        override val array: MutableArrayND<T>,
        offset: IntArray1D,
        shape: IntArray1D,
        stride: IntArray1D
) : DefaultArrayNDView<T>(
        array,
        offset,
        shape,
        stride
), MutableArrayND<T> {

    override fun setValue(value: T, vararg indices: Int) {
        requireValidIndices(indices)
        array.setValue(
                value,
                *IntArray(rank) { i -> offset[i] + stride[i] * indices[i] }
        )
    }

}


class DefaultArrayND1DView<T>(
        val array: MutableArrayND<T>
) : DefaultMutableArray1D<T>() {

    init {
        require(array.rank == 1)
    }

    override val size: Int
        get() = array.getShape(0)


    override fun getValue(i0: Int): T =
            array.getValue(i0)

    override fun setValue(value: T, index: Int) {
        array.setValue(value, index)
    }

}


class DefaultArrayND2DView<T>(
        val array: MutableArrayND<T>
) : DefaultMutableArray2D<T>() {

    init {
        require(array.rank == 2)
    }

    override val shape0: Int
        get() = array.getShape(0)

    override val shape1: Int
        get() = array.getShape(0)

    override fun getValue(i0: Int, i1: Int): T =
            array.getValue(i0, i1)

    override fun setValue(value: T, i0: Int, i1: Int) {
        array.setValue(value, i0, i1)
    }

}
