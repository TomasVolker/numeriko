package tomasvolker.numeriko.core.interfaces.arraynd.integer

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.slicing.*
import tomasvolker.numeriko.core.preconditions.requireValidIndices


class DefaultSliceIntArrayND(
        val array: MutableIntArrayND,
        val slice: ArraySlice
): DefaultMutableIntArrayND() {

    override val shape: IntArray1D = slice.shape.toIntArray1D()

    override fun getInt(indices: IntArray): Int {
        requireValidIndices(indices)
        return array.getValue(slice.convert(indices))
    }

    override fun setInt(indices: IntArray, value: Int) {
        requireValidIndices(indices)
        array.setValue(slice.convert(indices), value)
    }

}


inline fun intArrayNDView(
        array: MutableIntArrayND,
        shape: IntArray1D,
        crossinline convertIndices: (source: IntArray)->IntArray
): MutableIntArrayND = object: DefaultMutableIntArrayND() {

    override val shape: IntArray1D = shape

    override fun getInt(indices: IntArray): Int {
        requireValidIndices(indices)
        return array.getValue(convertIndices(indices))
    }

    override fun setInt(indices: IntArray, value: Int) {
        requireValidIndices(indices)
        array.setValue(
                convertIndices(indices),
                value
        )
    }

}

fun defaultIntArrayNDSlice(
        array: MutableIntArrayND,
        entries: List<SliceEntry>
): IntArrayND {
    return DefaultSliceIntArrayND(
            array = array,
            slice = array.arraySlice(entries)
    )
}
