package tomasvolker.numeriko.core.interfaces.arraynd.generic.view

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.interfaces.factory.intArray1D

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

    override fun getValue(indices: IntArray): T {
        requireValidIndices(indices)
        return array.getValue(convertIndices(indices))
    }

    override fun setValue(indices: IntArray, value: T) {
        requireValidIndices(indices)
        array.setValue(
                convertIndices(indices),
                value
        )
    }

    fun convertIndices(indices: IntArray): IntArray =
            IntArray(rank) { i -> offset[i] + stride[i] * indices[i] }

}

fun <T> defaultArrayNDView(array: MutableArrayND<T>, indices: Array<out IntProgression>) =
        DefaultArrayNDView(
                array = array,
                offset = intArray1D(indices.size) { i -> indices[i].first },
                shape = intArray1D(indices.size) { i -> indices[i].count() },
                stride = intArray1D(indices.size) { i -> indices[i].step }
        )