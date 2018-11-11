package tomasvolker.numeriko.core.implementations.jvm.arraynd

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.*
import tomasvolker.numeriko.core.reductions.product

class JvmMutableArrayND<T>(
        override val shape: IntArray1D,
        val data: Array<T>
): MutableArrayND<T> {

    init {
        require(data.size == shape.product()) {
            "Data size (${data.size}) does not match shape size ${shape}"
        }
    }

    override val rank: Int
        get() = shape.size

    override val size: Int get() = data.size

    override fun setValue(value: T, vararg indices: Int) {
        checkIndices(indices)
        data[convertIndices(indices)] = value
    }

    override fun getValue(vararg indices: Int): T {
        checkIndices(indices)
        return data[convertIndices(indices)]
    }

    private fun checkIndices(indices: IntArray) {
        require(indices.size == rank) {
            "Indices size (${indices.size}) doesnt match rank ($rank) "
        }
        for (d in indices.indices) {
            if(indices[d] !in 0 until shape[d])
                throw IndexOutOfBoundsException(
                        "Index (${indices[d]}) is out of bounds for size ${shape[d]}"
                )
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
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}