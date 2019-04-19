package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.preconditions.requireValidAxis

fun ArrayND<*>.permutationSlice(permutation: IntArrayND): ArraySlice {
    require(permutation.size == rank) {
        "permutation array must be the same size (${permutation.size}) as array rank ($rank)"
    }
    val visited = BooleanArray(rank) { false }
    permutation.forEach { axis ->
        requireValidAxis(axis)
        require(!visited[axis]) { "axis $axis inserted twice in the permutation" }
        visited[axis] = true
    }
    require(visited.all { it }) { "" }
    return arraySlice(
            shape = IntArray(rank) { a -> shape(permutation[a]) },
            permutation = IntArray(rank) { a -> permutation[a] }
    )
}


fun <T> ArrayND<T>.permutation(permutation: IntArrayND): ArrayND<T>    = getSlice(permutationSlice(permutation))
fun IntArrayND    .permutation(permutation: IntArrayND): IntArrayND    = getSlice(permutationSlice(permutation))
fun DoubleArrayND .permutation(permutation: IntArrayND): DoubleArrayND = getSlice(permutationSlice(permutation))
fun FloatArrayND  .permutation(permutation: IntArrayND): FloatArrayND  = getSlice(permutationSlice(permutation))
