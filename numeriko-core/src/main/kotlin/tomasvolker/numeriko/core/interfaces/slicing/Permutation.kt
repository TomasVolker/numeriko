package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayNDContext
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayNDContext
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayNDContext
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayNDContext
import tomasvolker.numeriko.core.interfaces.arraynd.generic.GenericArrayNDContext
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayNDContext
import tomasvolker.numeriko.core.preconditions.requireValidAxis

fun <A: ArrayND<*>> ArrayNDContext<A>.transpose(array: A, permutation: IntArray1D): A {
    val rank = array.rank
    require(permutation.size == rank) {
        "transpose array must be the same size (${permutation.size}) as array rank ($rank)"
    }
    val visited = BooleanArray(rank) { false }
    permutation.forEach { axis ->
        array.requireValidAxis(axis)
        require(!visited[axis]) { "axis $axis inserted twice in the transpose" }
        visited[axis] = true
    }
    require(visited.all { it }) { "" }
    return slice(
            array = array,
            slice = array.arraySlice(
                shape = IntArray(rank) { a -> array.shape(permutation[a]) },
                permutation = IntArray(rank) { a -> permutation[a] }
            )
    )
}


fun <T> ArrayND<T>.transpose(permutation: IntArray1D): ArrayND<T>    = GenericArrayNDContext<T>().transpose(this, permutation)
fun IntArrayND    .transpose(permutation: IntArray1D): IntArrayND    = IntArrayNDContext.transpose(this, permutation)
fun DoubleArrayND .transpose(permutation: IntArray1D): DoubleArrayND = DoubleArrayNDContext.transpose(this, permutation)
fun FloatArrayND  .transpose(permutation: IntArray1D): FloatArrayND  = FloatArrayNDContext.transpose(this, permutation)
fun ByteArrayND   .transpose(permutation: IntArray1D): ByteArrayND   = ByteArrayNDContext.transpose(this, permutation)
