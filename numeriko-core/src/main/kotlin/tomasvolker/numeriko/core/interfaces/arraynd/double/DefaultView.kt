package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArrayND
import tomasvolker.numeriko.core.interfaces.slicing.*
import tomasvolker.numeriko.core.preconditions.requireValidIndices


class DefaultSliceDoubleArrayND(
        val array: MutableDoubleArrayND,
        val slice: ArraySlice
): DefaultMutableDoubleArrayND() {

    override val shape: IntArrayND = slice.shape.toIntArrayND()

    override fun getDouble(indices: IntArray): Double {
        requireValidIndices(indices)
        return array.getValue(slice.convert(indices))
    }

    override fun setDouble(indices: IntArray, value: Double) {
        requireValidIndices(indices)
        array.setValue(slice.convert(indices), value)
    }

}


inline fun doubleArrayNDView(
        array: MutableDoubleArrayND,
        shape: IntArrayND,
        crossinline convertIndices: (source: IntArray)->IntArray
): MutableDoubleArrayND = object: DefaultMutableDoubleArrayND() {

    override val shape: IntArrayND = shape

    override fun getDouble(indices: IntArray): Double {
        requireValidIndices(indices)
        return array.getValue(convertIndices(indices))
    }

    override fun setDouble(indices: IntArray, value: Double) {
        requireValidIndices(indices)
        array.setValue(
                convertIndices(indices),
                value
        )
    }

}

fun defaultDoubleArrayNDSlice(
        array: MutableDoubleArrayND,
        entries: List<SliceEntry>
): DoubleArrayND {
    return DefaultSliceDoubleArrayND(
            array = array,
            slice = array.arraySlice(entries)
    )
}
