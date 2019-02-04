package tomasvolker.numeriko.complex.interfaces.arraynd.view

import tomasvolker.numeriko.complex.interfaces.arraynd.MutableComplexArrayND
import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.MutableNumericArrayND
import tomasvolker.numeriko.core.operations.reduction.remove

class DefaultComplexArrayNDLowerRankView(
        val array: MutableComplexArrayND,
        val axis: Int
) : DefaultMutableComplexArrayND() {

    init {
        require(array.shape[axis] <= 1)
    }

    override val shape: IntArray1D = array.shape.remove(axis)

    private fun convertIndices(indices: IntArray): IntArray =
            IntArray(array.rank) { i ->
                when {
                    i < axis -> indices[i]
                    i == axis -> 0
                    i > axis -> indices[i-1]
                    else -> throw IllegalStateException()
                }
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

    override fun higherRank(axis: Int): MutableNumericArrayND<Complex> = TODO("not implemented")

}