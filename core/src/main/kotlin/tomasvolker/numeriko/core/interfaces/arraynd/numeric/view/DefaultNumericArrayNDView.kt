package tomasvolker.numeriko.core.interfaces.arraynd.numeric.view

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.MutableNumericArrayND
import tomasvolker.numeriko.core.interfaces.factory.intArray1D

class DefaultNumericArrayNDView<N: Number>(
        val array: MutableNumericArrayND<N>,
        val offset: IntArray1D,
        override val shape: IntArray1D,
        val stride: IntArray1D
) : DefaultMutableNumericArrayND<N>() {

    init {
        require(array.rank == offset.size &&
                array.rank == shape.size &&
                array.rank == stride.size
        )
    }

    override fun cast(value: Number): N {
        TODO("not implemented")
    }

    override fun getValue(vararg indices: Int): N {
        requireValidIndices(indices)
        return array.getValue(*convertIndices(indices))
    }

    override fun setValue(value: N, vararg indices: Int) {
        requireValidIndices(indices)
        array.setValue(value, *indices)
    }

    fun convertIndices(indices: IntArray) = IntArray(rank) { i -> offset[i] + stride[i] * indices[i] }

}

fun <N: Number> defaultNumericArrayNDView(array: MutableNumericArrayND<N>, indices: Array<out IntProgression>) =
        DefaultNumericArrayNDView(
                array = array,
                offset = intArray1D(indices.size) { i -> indices[i].first },
                shape = intArray1D(indices.size) { i -> indices[i].count() },
                stride = intArray1D(indices.size) { i -> indices[i].step }
        )
