package tomasvolker.numeriko.core.interfaces.arraynd.double.view

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.intArray1D

class DefaultDoubleArrayNDView(
        val array: MutableDoubleArrayND,
        val offset: IntArray1D,
        override val shape: IntArray1D,
        val stride: IntArray1D
) : DefaultMutableDoubleArrayND() {

    init {
        require(array.rank == offset.size &&
                array.rank == shape.size &&
                array.rank == stride.size
        )

    }

    override fun getDouble(indices: IntArray): Double {
        requireValidIndices(indices)

        return array.getValue(
                IntArray(rank) { i -> offset[i] + stride[i] * indices[i] }
        )
    }

    override fun setDouble(indices: IntArray, value: Double) {
        requireValidIndices(indices)

        array.setValue(
                IntArray(rank) { i -> offset[i] + stride[i] * indices[i] },
                value
        )
    }

}

fun defaultDoubleArrayNDView(array: MutableDoubleArrayND, indices: Array<out IntProgression>) =
        DefaultDoubleArrayNDView(
                array = array,
                offset = intArray1D(indices.size) { i -> indices[i].first },
                shape = intArray1D(indices.size) { i -> indices[i].count() },
                stride = intArray1D(indices.size) { i -> indices[i].step }
        )
