package tomasvolker.numeriko.core.interfaces.arraynd.generic.view

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.*
import tomasvolker.numeriko.core.operations.remove

class DefaultArrayNDLowerRankView<T>(
        val array: MutableArrayND<T>,
        val axis: Int
) : DefaultMutableArrayND<T>() {

    init {
        require(array.shape(axis) <= 1)
    }

    override val shape: IntArray1D = array.shape.remove(axis)

    override fun getValue(indices: IntArray): T {
        requireValidIndices(indices)
        return array.getValue(
                *convertIndices(indices)
        )
    }

    override fun setValue(value: T, vararg indices: Int) {
        requireValidIndices(indices)
        array.setValue(value, *convertIndices(indices))
    }

    fun convertIndices(indices: IntArray): IntArray =
            IntArray(array.rank) { i ->
                when {
                    i < axis -> indices[i]
                    i == axis -> 0
                    i > axis -> indices[i-1]
                    else -> throw IllegalStateException()
                }
            }

}

