package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.preconditions.requireValidAxis

fun ArrayND<*>.permutationSlice(permutation: IntArray1D): ArraySlice {
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


fun <T> ArrayND<T>.permutation(permutation: IntArray1D): ArrayND<T>    = getSlice(permutationSlice(permutation))
fun IntArrayND    .permutation(permutation: IntArray1D): IntArrayND    = getSlice(permutationSlice(permutation))
fun DoubleArrayND .permutation(permutation: IntArray1D): DoubleArrayND = getSlice(permutationSlice(permutation))
fun FloatArrayND  .permutation(permutation: IntArray1D): FloatArrayND  = getSlice(permutationSlice(permutation))
fun ByteArrayND   .permutation(permutation: IntArray1D): ByteArrayND   = getSlice(permutationSlice(permutation))
