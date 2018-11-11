package tomasvolker.numeriko.core.implementations.jvm.arraynd

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND

class JvmMutableDoubleArrayND(
        override val shape: IntArray1D,
        val data: DoubleArray
): MutableDoubleArrayND {

    init {
        require(data.size == shape.fold(1) { acc, v -> acc * v }) {
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

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is ArrayND<*>) return false
        TODO()
    }

    override fun hashCode(): Int = TODO()

    override fun toString(): String = TODO()

}