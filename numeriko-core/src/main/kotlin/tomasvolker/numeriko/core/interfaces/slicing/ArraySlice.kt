package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND

/**
 * Low level array slicing.
 *
 * This function returns a default view implementing an arbitrary permuted strided slicing.
 *
 * @param array The backing array
 * @param permutation Array of size `shape.size` containing the axes on the backing array corresponding to the axes
 * on the view. If `permutation[a] < 0` then `shape[a] == 1`.
 * @param shape The shape of the resulting view
 * @param strides Array of size `shape.size` containing the stride corresponding to each dimension.
 * @param origin Array of size `array.rank` containing the indices on `array` corresponding to all zeros in the view
 */
data class ArraySlice(
        val origin: IntArray,
        val shape: IntArray,
        val strides: IntArray,
        val permutation: IntArray
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ArraySlice

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

fun arraySlice(
        origin: IntArrayND,
        shape: IntArrayND,
        strides: IntArrayND,
        permutation: IntArrayND
): ArraySlice =
        ArraySlice(
                origin = origin.toIntArray(),
                shape = shape.toIntArray(),
                strides = strides.toIntArray(),
                permutation = permutation.toIntArray()
        )

/**
 * Defaults to identity slice
 */
fun ArrayND<*>.arraySlice(
        origin: IntArray = IntArray(rank) { a -> 0 },
        shape: IntArray = IntArray(rank) { a -> shape(a) },
        strides: IntArray = IntArray(rank) { a -> 1 },
        permutation: IntArray = IntArray(rank) { a -> a }
): ArraySlice =
        ArraySlice(
                origin = origin,
                shape = shape,
                strides = strides,
                permutation = permutation
        )

fun ArrayND<*>.arraySlice(
        entries: List<SliceEntry>
): ArraySlice {
    val origin = IntArray(rank)
    val entryInputAxis = IntArray(entries.size)

    var inputAxis = 0
    var outputAxis = 0
    entries.forEachIndexed { i, entry ->
        when(entry) {
            is Range -> {
                entryInputAxis[i] = inputAxis
                origin[inputAxis] = entry.start(shape(inputAxis))
                inputAxis++
                outputAxis++
            }
            is Shrink -> {
                origin[inputAxis] = entry.index(shape(inputAxis))
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
                shape[outputAxis] = entry.length(shape(entryInputAxis[i]))
                strides[outputAxis] = entry.step
                permutation[outputAxis] = entryInputAxis[i]
                outputAxis++
            }
        }
    }

    return ArraySlice(
            origin = origin,
            shape = shape,
            strides = strides,
            permutation = permutation
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