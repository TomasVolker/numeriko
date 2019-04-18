package tomasvolker.numeriko.core.interfaces.arraynd.integer.view

import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.MutableIntArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArrayND
import tomasvolker.numeriko.core.interfaces.slicing.*
import tomasvolker.numeriko.core.preconditions.requireValidIndices


class DefaultPermutedSliceIntArrayND(
        val array: MutableIntArrayND,
        val permutedSlice: PermutedSlice
): DefaultMutableIntArrayND() {

    override val shape: IntArrayND = permutedSlice.shape.toIntArrayND()

    override fun getInt(indices: IntArray): Int {
        requireValidIndices(indices)
        return array.getValue(permutedSlice.convert(indices))
    }

    override fun setInt(indices: IntArray, value: Int) {
        requireValidIndices(indices)
        array.setValue(permutedSlice.convert(indices), value)
    }

}


inline fun intArrayNDView(
        array: MutableIntArrayND,
        shape: IntArrayND,
        crossinline convertIndices: (source: IntArray)->IntArray
): MutableIntArrayND = object: DefaultMutableIntArrayND() {

    override val shape: IntArrayND = shape

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
    return DefaultPermutedSliceIntArrayND(
            array = array,
            permutedSlice = permutedSlice(array, entries)
    )
}
