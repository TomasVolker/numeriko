package tomasvolker.numeriko.core.interfaces.arraynd.generic.view

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.preconditions.requireValidIndices

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

sealed class SliceEntry

object NewAxis: SliceEntry()

class Shrink(
        val axis: Int,
        val index: Int
): SliceEntry()

class Range(
        val axis: Int,
        val start: Int,
        val end: Int,
        val stride: Int = 1
): SliceEntry() {

    val count get() = (start - end) / stride

}

fun <T> defaultArrayNDSlice(
        array: MutableArrayND<T>,
        first: List<SliceEntry>,
        last: List<SliceEntry>
): ArrayND<T> {

    val resultShape =  mutableListOf<Int>()

    val axisList = MutableList(array.rank) { i -> i }

    (first + last).forEach { entry ->
        when(entry) {
            is Shrink -> axisList.remove(entry.axis)
            is Range -> axisList.remove(entry.axis)
        }
    }

    first.forEach { entry ->
        when(entry) {
            is NewAxis -> 1
            is Shrink -> null
            is Range -> entry.count
        }?.let { resultShape.add(it) }
    }

    axisList.forEach {
        resultShape.add(array.shape(it))
    }

    last.forEach { entry ->
        when(entry) {
            is NewAxis -> 1
            is Shrink -> null
            is Range -> entry.count
        }?.let { resultShape.add(it) }
    }

    return arrayNDView(array, resultShape.toIntArray()) { indices ->

    }
}