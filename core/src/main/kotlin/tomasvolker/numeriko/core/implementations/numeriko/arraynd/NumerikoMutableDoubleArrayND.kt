package tomasvolker.numeriko.core.implementations.numeriko.arraynd

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.defaultEquals
import tomasvolker.numeriko.core.interfaces.arraynd.double.*
import tomasvolker.numeriko.core.reductions.product

class NumerikoMutableDoubleArrayND(
        override val shape: IntArray1D,
        val data: DoubleArray
): DefaultMutableDoubleArrayND() {

    init {
        require(data.size == shape.product()) {
            "Data size (${data.size}) does not match shape size ${shape}"
        }
    }

    override val rank: Int
        get() = shape.size

    override val size: Int get() = data.size

    override fun setDouble(value: Double, vararg indices: Int) {
        checkIndices(indices)
        data[convertIndices(indices)] = value
    }

    override fun getDouble(vararg indices: Int): Double {
        checkIndices(indices)
        return data[convertIndices(indices)]
    }

    private fun checkIndices(indices: IntArray) {
        require(indices.size == rank)
        for (d in indices.indices) {
            require(indices[d] in 0 until shape[d])
        }
    }

    private fun convertIndices(indices: IntArray): Int {
        var result = 0
        var stride = 1
        for (d in rank-1 downTo 0) {
            result += indices[d] * stride
            stride *= shape[d]
        }
        return result
    }

}