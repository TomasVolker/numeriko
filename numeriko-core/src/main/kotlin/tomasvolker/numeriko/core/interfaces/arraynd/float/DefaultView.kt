package tomasvolker.numeriko.core.interfaces.arraynd.float

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.interfaces.slicing.SliceEntry
import tomasvolker.numeriko.core.interfaces.slicing.arraySlice
import tomasvolker.numeriko.core.preconditions.requireValidIndices


class DefaultSliceFloatArrayND(
        val array: MutableFloatArrayND,
        val slice: ArraySlice
): DefaultMutableFloatArrayND() {

    override val shape: IntArray1D = slice.shape.toIntArray1D()

    override fun getFloat(indices: IntArray): Float {
        requireValidIndices(indices)
        return array.getValue(slice.convert(indices))
    }

    override fun setFloat(indices: IntArray, value: Float) {
        requireValidIndices(indices)
        array.setValue(slice.convert(indices), value)
    }

}


inline fun floatArrayNDView(
        array: MutableFloatArrayND,
        shape: IntArray1D,
        crossinline convertIndices: (source: IntArray)->IntArray
): MutableFloatArrayND = object: DefaultMutableFloatArrayND() {

    override val shape: IntArray1D = shape

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
    return DefaultSliceFloatArrayND(
            array = array,
            slice = array.arraySlice(entries)
    )
}
