package tomasvolker.numeriko.core.interfaces.arraynd.generic.view

import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArrayND
import tomasvolker.numeriko.core.interfaces.slicing.*
import tomasvolker.numeriko.core.preconditions.requireValidIndices


class DefaultPermutedSliceArrayND<T>(
        val array: MutableArrayND<T>,
        val permutedSlice: PermutedSlice
): DefaultMutableArrayND<T>() {


    override val shape: IntArrayND = permutedSlice.shape.toIntArrayND()

    override fun getValue(indices: IntArray): T {
        requireValidIndices(indices)
        return array.getValue(permutedSlice.convert(indices))
    }

    override fun setValue(indices: IntArray, value: T) {
        requireValidIndices(indices)
        array.setValue(permutedSlice.convert(indices), value)
    }

}


// Non parallelizable
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
        first: List<SliceEntry>,
        last: List<SliceEntry>
): ArrayND<T> {
    val accessedAxes = first.count { it !is NewAxis } + last.count { it !is NewAxis }
    val remaining = accessedAxes - array.rank
    return defaultArrayNDSlice(
            array = array,
            entries = first + List(remaining) { Range(All) } + last
    )
}

fun <T> defaultArrayNDSlice(
        array: MutableArrayND<T>,
        entries: List<SliceEntry>
): ArrayND<T> {
    return DefaultPermutedSliceArrayND(
            array = array,
            permutedSlice = permutedSlice(array, entries)
    )
}
