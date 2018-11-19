package tomasvolker.numeriko.core.interfaces.arraynd.numeric.view

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.MutableNumericArrayND
import tomasvolker.numeriko.core.operations.remove

class DefaultNumericArrayNDLowerRankView<N: Number>(
        val array: MutableNumericArrayND<N>,
        val axis: Int
) : DefaultMutableNumericArrayND<N>() {

    init {
        require(array.shape[axis] <= 1)
    }

    override fun cast(value: Number): N {
        TODO("not implemented")
    }

    override val shape: IntArray1D = array.shape.remove(axis)

    override fun getValue(vararg indices: Int): N {
        requireValidIndices(indices)
        return array.getValue(*convertIndices(indices))
    }

    override fun setValue(value: N, vararg indices: Int) {
        requireValidIndices(indices)
        array.setValue(value, *convertIndices(indices))
    }

    private fun convertIndices(indices: IntArray): IntArray =
            IntArray(array.rank) { i ->
                when {
                    i < axis -> indices[i]
                    i == axis -> 0
                    i > axis -> indices[i-1]
                    else -> throw IllegalStateException()
                }
            }

}