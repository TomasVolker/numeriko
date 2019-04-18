package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND

/**
 * Low level array permutation.
 *
 * This function returns a default view implementing an arbitrary permutation and slicing.
 *
 * @param array The backing array
 * @param permutation Array of size `shape.size` containing the axes on the backing array corresponding to the axes
 * on the view. If `permutation[a] < 0` then `shape[a] == 1`.
 * @param shape The shape of the resulting view
 * @param strides Array of size `shape.size` containing the stride corresponding to each dimension.
 * @param origin Array of size `array.rank` containing the indices on `array` corresponding to all zeros in the view
 */
data class PermutedSlice(
        val permutation: IntArray,
        val shape: IntArray,
        val strides: IntArray,
        val origin: IntArray
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PermutedSlice

        if (!permutation.contentEquals(other.permutation)) return false
        if (!shape.contentEquals(other.shape)) return false
        if (!strides.contentEquals(other.strides)) return false
        if (!origin.contentEquals(other.origin)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = permutation.contentHashCode()
        result = 31 * result + shape.contentHashCode()
        result = 31 * result + strides.contentHashCode()
        result = 31 * result + origin.contentHashCode()
        return result
    }

    fun convert(indices: IntArray): IntArray {
        val result = origin.copyOf()

        for (axis in 0 until shape.size) {
            val resultAxis = permutation[axis]
            if (0 <= resultAxis)
                result[resultAxis] += strides[axis] * indices[axis]
        }

        return result
    }

}

fun permutedSlice(
        array: ArrayND<*>,
        entries: List<SliceEntry>
): PermutedSlice {
    val origin = IntArray(array.rank)
    val entryInputAxis = IntArray(entries.size)

    var inputAxis = 0
    var outputAxis = 0
    entries.forEachIndexed { i, entry ->
        when(entry) {
            is Range -> {
                entryInputAxis[i] = inputAxis
                origin[inputAxis] = entry.start(array.shape(inputAxis))
                inputAxis++
                outputAxis++
            }
            is Shrink -> {
                origin[inputAxis] = entry.index(array.shape(inputAxis))
                inputAxis++
            }
            is NewAxis -> {
                outputAxis++
            }
        }
    }
    val resultRank = outputAxis

    val shape = IntArray(resultRank)
    val strides = IntArray(resultRank)
    val permutation = IntArray(resultRank)
    outputAxis = 0
    entries.forEachIndexed { i, entry ->
        when(entry) {
            is NewAxis -> {
                shape[outputAxis] = 1
                strides[outputAxis] = 1
                permutation[outputAxis] = -1
                outputAxis++
            }
            is Range -> {
                shape[outputAxis] = entry.length(array.shape(entryInputAxis[i]))
                strides[outputAxis] = entry.step
                permutation[outputAxis] = entryInputAxis[i]
                outputAxis++
            }
        }
    }

    return PermutedSlice(
            permutation = permutation,
            shape = shape,
            strides = strides,
            origin = origin
    )
}

fun computeAbsoluteEntries(
        array: ArrayND<*>,
        first: List<SliceEntry>,
        last: List<SliceEntry>
): List<SliceEntry> {
    val accessedAxes = first.count { it !is NewAxis } + last.count { it !is NewAxis }
    val remaining = array.rank - accessedAxes
    return first + List(remaining) { Range(All) } + last
}