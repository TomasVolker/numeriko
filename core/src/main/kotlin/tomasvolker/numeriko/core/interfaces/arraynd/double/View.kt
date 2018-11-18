package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultMutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.view.DefaultMutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.intArray1D

abstract class DefaultDoubleArrayND: DoubleArrayND {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleArrayND) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

abstract class DefaultMutableDoubleArrayND: DefaultDoubleArrayND(), MutableDoubleArrayND


class DefaultDoubleArrayNDCollapseView(
        val array: MutableDoubleArrayND,
        val axis: Int
) : DefaultMutableDoubleArrayND() {

    init {
        require(array.shape[axis] == 1)
    }

    override val shape: IntArray1D = intArray1D(array.shape.filterIndexed { i, _ -> i != axis }.toIntArray())

    private fun convertIndices(indices: IntArray): IntArray =
            IntArray(array.rank) { i ->
                when {
                    i < axis -> indices[i]
                    i == axis -> 0
                    i > axis -> indices[i-1]
                    else -> throw IllegalStateException()
                }
            }

    override fun getDouble(indices: IntArray): Double {
        requireValidIndices(indices)
        return array.getDouble(*convertIndices(indices))
    }

    override fun setDouble(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        array.setDouble(value, *convertIndices(indices))
    }

}

fun DoubleArrayND.collapseView(axis: Int): DoubleArrayND =
        DefaultDoubleArrayNDCollapseView(this.asMutable(), axis)

class DefaultMutableDoubleArrayNDView(
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

    override fun getDouble(vararg indices: Int): Double {
        requireValidIndices(indices)

        return array.getValue(
                *IntArray(rank) { i -> offset[i] + stride[i] * indices[i] }
        )
    }

    override fun setDouble(value: Double, vararg indices: Int) {
        requireValidIndices(indices)

        array.setValue(
                value,
                *IntArray(rank) { i -> offset[i] + stride[i] * indices[i] }
        )
    }

}


class DefaultDoubleArrayND1DView(
        val array: MutableDoubleArrayND
) : DefaultMutableDoubleArray1D() {

    init {
        require(array.rank == 1)
    }

    override val size: Int
        get() = array.shape(0)

    override fun getDouble(i0: Int): Double =
            array.getDouble(i0)

    override fun setDouble(value: Double, i0: Int) {
        array.setDouble(value, i0)
    }

}


class DefaultDoubleArrayND2DView(
        val array: MutableDoubleArrayND
) : DefaultMutableDoubleArray2D() {

    init {
        require(array.rank == 2)
    }

    override val shape0: Int
        get() = array.shape(0)

    override val shape1: Int
        get() = array.shape(0)

    override fun getDouble(i0: Int, i1: Int): Double =
            array.getDouble(i0, i1)

    override fun setDouble(value: Double, i0: Int, i1: Int) {
        array.setDouble(value, i0, i1)
    }

}