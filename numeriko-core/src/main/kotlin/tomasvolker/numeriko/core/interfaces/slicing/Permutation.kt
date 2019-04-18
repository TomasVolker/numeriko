package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND

fun permutationPermutedSlice(array: ArrayND<*>, permutation: IntArrayND): PermutedSlice {
    require(permutation.size == array.rank) {
        "permutation array must be the same size (${permutation.size}) as array rank (${array.rank})"
    }
    val visited = BooleanArray(array.rank) { false }
    permutation.forEach { axis ->
        require(axis in 0 until array.rank) { "axis $axis out of bounds of rank ${array.rank}" }
        require(!visited[axis]) { "axis $axis inserted twice in the permutation" }
        visited[axis] = true
    }
    require(visited.all { it }) { "" }
    return PermutedSlice(
            permutation = IntArray(array.rank) { a -> permutation[a] },
            shape = IntArray(array.rank) { a -> array.shape(permutation[a]) },
            strides = IntArray(array.rank) { 1 },
            origin = IntArray(array.rank) { 0 }
    )
}


fun <T> ArrayND<T>.permutation(permutation: IntArrayND): ArrayND<T> =
        getPermutedSlice(
                permutationPermutedSlice(this, permutation)
        )


fun DoubleArrayND.permutation(permutation: IntArrayND): DoubleArrayND =
        getPermutedSlice(
                permutationPermutedSlice(this, permutation)
        )

fun IntArrayND.permutation(permutation: IntArrayND): IntArrayND =
        getPermutedSlice(
                permutationPermutedSlice(this, permutation)
        )

fun FloatArrayND.permutation(permutation: IntArrayND): FloatArrayND =
        getPermutedSlice(
                permutationPermutedSlice(this, permutation)
        )
