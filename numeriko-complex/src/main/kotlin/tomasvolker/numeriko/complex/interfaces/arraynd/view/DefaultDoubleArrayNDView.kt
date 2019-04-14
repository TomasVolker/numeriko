package tomasvolker.numeriko.complex.interfaces.arraynd.view

import tomasvolker.numeriko.complex.interfaces.arraynd.MutableComplexArrayND
import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.lowrank.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.MutableNumericArrayND
import tomasvolker.numeriko.core.interfaces.factory.intArray1D

class DefaultComplexArrayNDView(
        val array: MutableComplexArrayND,
        val offset: IntArray1D,
        override val shape: IntArray1D,
        val stride: IntArray1D
) : DefaultMutableComplexArrayND() {

    init {
        require(array.rank == offset.size &&
                array.rank == shape.size &&
                array.rank == stride.size
        )

    }

    override fun real(vararg indices: Int): Double {
        requireValidIndices(indices)
        return array.real(*convertIndices(indices))
    }

    override fun imag(vararg indices: Int): Double {
        requireValidIndices(indices)
        return array.imag(*convertIndices(indices))
    }

    override fun setReal(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        array.setReal(value, *convertIndices(indices))
    }

    override fun setImag(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        array.setImag(value, *convertIndices(indices))
    }

    private fun convertIndices(indices: IntArray): IntArray =
            IntArray(rank) { i -> offset[i] + stride[i] * indices[i] }


    override fun higherRank(axis: Int): MutableNumericArrayND<Complex> = TODO("not implemented")

}

fun defaultComplexArrayNDView(array: MutableComplexArrayND, indices: Array<out IntProgression>) =
        DefaultComplexArrayNDView(
                array = array,
                offset = intArray1D(indices.size) { i -> indices[i].first },
                shape = intArray1D(indices.size) { i -> indices[i].count() },
                stride = intArray1D(indices.size) { i -> indices[i].step }
        )
