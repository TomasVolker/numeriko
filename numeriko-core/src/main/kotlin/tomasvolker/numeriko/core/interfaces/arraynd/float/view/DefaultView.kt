package tomasvolker.numeriko.core.interfaces.arraynd.float.view

import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.MutableFloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArrayND
import tomasvolker.numeriko.core.interfaces.slicing.PermutedSlice
import tomasvolker.numeriko.core.interfaces.slicing.SliceEntry
import tomasvolker.numeriko.core.interfaces.slicing.permutedSlice
import tomasvolker.numeriko.core.preconditions.requireValidIndices


class DefaultPermutedSliceFloatArrayND(
        val array: MutableFloatArrayND,
        val permutedSlice: PermutedSlice
): DefaultMutableFloatArrayND() {

    override val shape: IntArrayND = permutedSlice.shape.toIntArrayND()

    override fun getFloat(indices: IntArray): Float {
        requireValidIndices(indices)
        return array.getValue(permutedSlice.convert(indices))
    }

    override fun setFloat(indices: IntArray, value: Float) {
        requireValidIndices(indices)
        array.setValue(permutedSlice.convert(indices), value)
    }

}


inline fun floatArrayNDView(
        array: MutableFloatArrayND,
        shape: IntArrayND,
        crossinline convertIndices: (source: IntArray)->IntArray
): MutableFloatArrayND = object: DefaultMutableFloatArrayND() {

    override val shape: IntArrayND = shape

    override fun getFloat(indices: IntArray): Float {
        requireValidIndices(indices)
        return array.getValue(convertIndices(indices))
    }

    override fun setFloat(indices: IntArray, value: Float) {
        requireValidIndices(indices)
        array.setValue(
                convertIndices(indices),
                value
        )
    }

}

fun defaultFloatArrayNDSlice(
        array: MutableFloatArrayND,
        entries: List<SliceEntry>
): FloatArrayND {
    return DefaultPermutedSliceFloatArrayND(
            array = array,
            permutedSlice = permutedSlice(array, entries)
    )
}
