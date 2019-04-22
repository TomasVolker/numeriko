package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.slicing.*
import tomasvolker.numeriko.core.preconditions.requireValidIndices


class DefaultSliceArrayND<T>(
        val array: MutableArrayND<T>,
        val slice: ArraySlice
): DefaultMutableArrayND<T>() {

    override val shape: IntArray1D = slice.shape.toIntArray1D()

    override fun getValue(indices: IntArray): T {
        requireValidIndices(indices)
        return array.getValue(slice.convert(indices))
    }

    override fun setValue(indices: IntArray, value: T) {
        requireValidIndices(indices)
        array.setValue(slice.convert(indices), value)
    }

}


inline fun <T> arrayNDView(
        array: MutableArrayND<T>,
        shape: IntArray1D,
        crossinline convertIndices: (source: IntArray)->IntArray
): MutableArrayND<T> = object: DefaultMutableArrayND<T>() {

    override val shape: IntArray1D = shape

    override fun getValue(indices: IntArray): T {
        requireValidIndices(indices)
        return array.getValue(convertIndices(indices))
    }

    override fun setValue(indices: IntArray, value: T) {
        requireValidIndices(indices)
        array.setValue(
                convertIndices(indices),
                value
        )
    }

}


fun <T> defaultArrayNDSlice(
        array: MutableArrayND<T>,
        entries: List<SliceEntry>
): ArrayND<T> {
    return DefaultSliceArrayND(
            array = array,
            slice = array.arraySlice(entries)
    )
}
