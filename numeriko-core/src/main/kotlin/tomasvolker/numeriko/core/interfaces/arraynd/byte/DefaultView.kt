package tomasvolker.numeriko.core.interfaces.arraynd.byte

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.interfaces.slicing.SliceEntry
import tomasvolker.numeriko.core.interfaces.slicing.arraySlice
import tomasvolker.numeriko.core.preconditions.requireValidIndices


class DefaultSliceByteArrayND(
        val array: MutableByteArrayND,
        val slice: ArraySlice
): DefaultMutableByteArrayND() {

    override val shape: IntArray1D = slice.shape.toIntArray1D()

    override fun getByte(indices: IntArray): Byte {
        requireValidIndices(indices)
        return array.getValue(slice.convert(indices))
    }

    override fun setByte(indices: IntArray, value: Byte) {
        requireValidIndices(indices)
        array.setValue(slice.convert(indices), value)
    }

}


inline fun byteArrayNDView(
        array: MutableByteArrayND,
        shape: IntArray1D,
        crossinline convertIndices: (source: IntArray)->IntArray
): MutableByteArrayND = object: DefaultMutableByteArrayND() {

    override val shape: IntArray1D = shape

    override fun getByte(indices: IntArray): Byte {
        requireValidIndices(indices)
        return array.getValue(convertIndices(indices))
    }

    override fun setByte(indices: IntArray, value: Byte) {
        requireValidIndices(indices)
        array.setValue(
                convertIndices(indices),
                value
        )
    }

}

fun defaultByteArrayNDSlice(
        array: MutableByteArrayND,
        entries: List<SliceEntry>
): ByteArrayND {
    return DefaultSliceByteArrayND(
            array = array,
            slice = array.arraySlice(entries)
    )
}
