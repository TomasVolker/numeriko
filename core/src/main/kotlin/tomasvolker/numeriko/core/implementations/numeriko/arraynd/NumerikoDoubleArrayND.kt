package tomasvolker.numeriko.core.implementations.numeriko.arraynd

import tomasvolker.numeriko.core.interfaces.array1d.generic.lastIndex
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.view.DefaultMutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.intArray1D
import tomasvolker.numeriko.core.operations.remove

class NumerikoDoubleArrayND(
        override val shape: IntArray1D,
        val data: DoubleArray,
        val offset: Int = 0,
        val strideArray: IntArray = strideArray(shape)
): DefaultMutableDoubleArrayND() {

    override val rank: Int
        get() = shape.size

    override val size: Int get() = data.size

    override fun getDouble(vararg indices: Int): Double {
        requireValidIndices(indices)
        return data[linearIndex(indices)]
    }

    override fun setDouble(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        data[linearIndex(indices)] = value
    }

    override fun getView(vararg indices: IntProgression): MutableDoubleArrayND {
        require(indices.size == rank) { "${indices.size} indices supplied when $rank required" }
        for (axis in 0 until rank) {
            requireValidIndexRange(indices[axis], axis = axis)
        }
        return NumerikoDoubleArrayND(
                shape = intArray1D(rank) { axis -> indices[axis].count() },
                data = data,
                offset = linearIndex(IntArray(rank) { axis -> indices[axis].first }),
                strideArray = IntArray(rank) { axis -> indices[axis].step * strideArray[axis] }
        )
    }

    override fun lowerRank(axis: Int): MutableDoubleArrayND {
        require(shape(axis) <= 1)
        return NumerikoDoubleArrayND(
                shape = shape.remove(axis),
                data = data,
                offset = linearIndex(IntArray(rank) { 0 }),
                strideArray = IntArray(rank-1) { i -> if (i < axis) strideArray[i] else strideArray[i+1] }
        )
    }

    private fun linearIndex(indices: IntArray): Int {
        var result = offset
        for (axis in 0 until rank) {
            result += strideArray[axis] * indices[axis]
        }
        return result
    }

}

private fun strideArray(shapeArray: IntArray1D): IntArray =
        IntArray(shapeArray.size).apply {
            val lastAxis = shapeArray.lastIndex
            this[lastAxis] = 1
            for (axis in lastAxis-1 downTo 0) {
                this[axis] = this[axis+1] * shapeArray[axis+1]
            }
        }
