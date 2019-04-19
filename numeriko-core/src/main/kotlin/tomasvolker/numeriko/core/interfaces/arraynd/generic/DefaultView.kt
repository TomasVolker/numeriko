package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArrayND
import tomasvolker.numeriko.core.interfaces.slicing.*
import tomasvolker.numeriko.core.preconditions.requireValidIndices


class DefaultSliceArrayND<T>(
        val array: MutableArrayND<T>,
        val slice: ArraySlice
): DefaultMutableArrayND<T>() {

    override val shape: IntArrayND = slice.shape.toIntArrayND()

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
        shape: IntArrayND,
        crossinline convertIndices: (source: IntArray)->IntArray
): MutableArrayND<T> = object: DefaultMutableArrayND<T>() {

    override val shape: IntArrayND = shape

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
