package tomasvolker.numeriko.core.interfaces.arraynd.double.view

import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArrayND
import tomasvolker.numeriko.core.interfaces.slicing.*
import tomasvolker.numeriko.core.preconditions.requireValidIndices


class DefaultPermutedSliceDoubleArrayND(
        val array: MutableDoubleArrayND,
        val permutedSlice: PermutedSlice
): DefaultMutableDoubleArrayND() {

    override val shape: IntArrayND = permutedSlice.shape.toIntArrayND()

    override fun getDouble(indices: IntArray): Double {
        requireValidIndices(indices)
        return array.getValue(permutedSlice.convert(indices))
    }

    override fun setDouble(indices: IntArray, value: Double) {
        requireValidIndices(indices)
        array.setValue(permutedSlice.convert(indices), value)
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
    return DefaultPermutedSliceDoubleArrayND(
            array = array,
            permutedSlice = permutedSlice(array, entries)
    )
}
