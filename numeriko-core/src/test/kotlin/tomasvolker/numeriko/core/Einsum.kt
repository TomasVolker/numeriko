package tomasvolker.numeriko.core

import tomasvolker.numeriko.core.dsl.D
import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.doubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.doubleZeros
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.iteration.indexIncrement
import tomasvolker.numeriko.core.operations.stack
import tomasvolker.numeriko.core.operations.unstack
import tomasvolker.numeriko.core.preconditions.illegalArgument
import tomasvolker.numeriko.core.primitives.productDouble

object Einsum {

    val h = "h"
    val i = "i"
    val j = "j"
    val s = "s"
    val t = "t"
    val u = "u"
    val v = "v"
    val k = "k"
    val l = "l"
    val m = "m"
    val n = "n"
    val p = "p"
    val q = "q"
    val r = "r"
    val x = "x"
    val y = "y"
    val z = "z"

    operator fun DoubleArrayND.get(vararg indices: String) = Einsum.Expression.Tensor(this, indices.toList())

    sealed class Expression {

        data class Tensor(
                val array: DoubleArrayND,
                val indices: List<String>
        ): Expression() {

            operator fun times(other: Expression) = when(other) {
                is Tensor -> Product(listOf(this, other))
                is Product -> Product(listOf(this) + other.arrayList)
            }

        }

        class Product(
                val arrayList: List<Tensor>
        ): Expression() {

            operator fun times(other: Expression) = when(other) {
                is Tensor -> Product(this.arrayList + other)
                is Product -> Product(this.arrayList + other.arrayList)
            }

        }

        fun freeIndices(): List<String> = when(this) {
            is Tensor -> indices.distinct()
            is Product -> arrayList.flatMap { it.indices }.distinct()
        }

        fun toList(): List<Tensor> = when(this) {
            is Tensor -> listOf(this)
            is Product -> arrayList
        }

    }

}

data class TensorAxis(
        val tensor: Einsum.Expression.Tensor,
        val tensorIndex: Int,
        val axis: Int
) {

    val range get() = tensor.array.shape(axis)

}

fun <K,V> Iterable<Pair<K, V>>.toMultiMap(): Map<K, List<V>> {
    val result = mutableMapOf<K, MutableList<V>>()

    forEach { (key, value) ->
        result.getOrPut(key) { mutableListOf() }.add(value)
    }

    return result
}

fun einsum(outRank: Int, block: Einsum.(List<String>) -> Einsum.Expression): DoubleArrayND {

    val outputIndices = List(outRank) { i -> "i$i" }
    val expression = Einsum.block(outputIndices)
    val freeIndices = expression.freeIndices() - outputIndices

    val tensorList = expression.toList()

    val outputIndexMap = tensorList
            .withIndex()
            .flatMap { (tensorIndex, tensor) ->
                tensor.indices.mapIndexed { axis, index ->
                    index to TensorAxis(tensor, tensorIndex, axis)
                }
            }.toMultiMap()

    val ranges = outputIndexMap.mapValues {
        val firstRange = it.value.first().range
        if (it.value.any { it.range != firstRange }) illegalArgument("Ranges dont match")
        firstRange
    }

    val freeIndexTurn = IntArray(freeIndices.size) { 0 }
    val freeIndexShape = freeIndices.map { ranges[it] ?: error("cannot infer range of $it") }.toIntArray()

    val outputIndex = IntArray(outRank) { 0 }
    val outputShape = outputIndices.map { ranges[it] ?: error("cannot infer range of $it") }.toIntArray()

    val result = doubleZeros(outputShape.toIntArray1D()).asMutable()

    val inputIndices = tensorList.map { IntArray(it.array.rank) { 0 } }.toTypedArray()

    do {

        var value = 0.0
        freeIndexTurn.fill(0)

        outputIndices.forEachIndexed { i, index ->
            outputIndexMap[index]?.forEach {
                inputIndices[it.tensorIndex][it.axis] = outputIndex[i]
            }
        }

        do {

            freeIndices.forEachIndexed { i, index ->
                outputIndexMap[index]?.forEach {
                    inputIndices[it.tensorIndex][it.axis] = freeIndexTurn[i]
                }
            }

            value += productDouble(0 until tensorList.size) { t ->
                tensorList[t].array.getDouble(inputIndices[t])
            }

        } while(!freeIndexTurn.indexIncrement(freeIndexShape))

        result.setDouble(outputIndex, value)

    } while (!outputIndex.indexIncrement(outputShape))

    return result
}

@JvmName("einsum0")
fun einsum(block: Einsum.()-> Einsum.Expression): Double =
        einsum(0) { block() }.get()

@JvmName("einsum1")
fun einsum(block: Einsum.(String)-> Einsum.Expression): DoubleArrayND =
        einsum(1) { (i0) -> block(i0) }

@JvmName("einsum2")
fun einsum(block: Einsum.(String, String)-> Einsum.Expression): DoubleArrayND =
        einsum(2) { (i0, i1) -> block(i0, i1) }

@JvmName("einsum3")
fun einsum(block: Einsum.(String, String, String)-> Einsum.Expression): DoubleArrayND =
        einsum(3) { (i0, i1, i2) -> block(i0, i1, i2) }

@JvmName("einsum4")
fun einsum(block: Einsum.(String, String, String, String)-> Einsum.Expression): DoubleArrayND =
        einsum(4) { (i0, i1, i2, i3) -> block(i0, i1, i2, i3) }

fun main() {

    val a = D[1,2]
    val b = D[2,3]
    val m = D[D[1, 2],
              D[3, 4]]

    val n = D[D[1, 2],
              D[3, 4]]

    val diagonal = einsum { i -> m[i, i] }
    println(diagonal)

    val trace = einsum { -> m[i, i] }
    println(trace)

    val vecmul = einsum { i -> m[i, j] * a[j] }
    println(vecmul)

    val matmul = einsum { i, j -> m[i, k] * n[k, j] }
    println(matmul)

    val inner = einsum { -> a[i] * b[i] }
    println(inner)

    val norm2 = einsum { -> a[i] * a[i] }
    println(norm2)

    val outer = einsum { i, j -> a[i] * b[j] }
    println(outer)

    val transpose = einsum { i, j -> m[j, i] }
    println(transpose)

    val cuadratic = einsum { -> m[i, j] * a[i] * b[j] }
    println(cuadratic)

    val matmul3 = einsum { i, j -> m[i, k] * n[k, l] * m[l, j] }
    println(matmul3)

}