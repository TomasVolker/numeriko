package tomasvolker.numeriko.sandbox.einsum

import tomasvolker.numeriko.core.annotations.CompileTimeError
import tomasvolker.numeriko.core.dsl.D
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.functions.transpose
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND

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

    operator fun DoubleArray1D.get(i0: String) = Expression.ArrayND(this, listOf(i0))
    @CompileTimeError(
            message = "Rank doesn't match",
            level = DeprecationLevel.ERROR
    )
    operator fun DoubleArray1D.get(vararg indices: String): Nothing = TODO()

    operator fun DoubleArrayND.get(vararg indices: String) = Expression.ArrayND(this, indices.toList())

    sealed class Expression {

        class ArrayND(
                val array: DoubleArrayND,
                val indices: List<String>
        ): Expression() {

            operator fun times(other: Expression) = when(other) {
                is ArrayND -> Product(listOf(this, other))
                is Product -> Product(listOf(this) + other.arrayList)
            }

        }

        class Product(
                val arrayList: List<ArrayND>
        ): Expression() {

            operator fun times(other: Expression) = when(other) {
                is ArrayND -> Product(this.arrayList + other)
                is Product -> Product(this.arrayList + other.arrayList)
            }

        }

        fun freeIndices(): List<String> = when(this) {
            is ArrayND -> indices.distinct()
            is Product -> arrayList.flatMap { it.indices }.distinct()
        }

    }

}

@JvmName("einsum0")
fun einsum(block: Einsum.()-> Einsum.Expression): Double = TODO()

@JvmName("einsum1")
fun einsum(block: Einsum.(String)-> Einsum.Expression): DoubleArray1D {

    val i0 = "i0"
    val expression = Einsum.block(i0)

    val freeIndices = expression.freeIndices()

    val arrayOfIndices = when(expression) {
        is Einsum.Expression.ArrayND -> arrayOf(IntArray(expression.array.rank) { 0 })
        is Einsum.Expression.Product -> expression.arrayList.map { IntArray(it.array.rank) { 0 } }.toTypedArray()
    }

    TODO()

}

@JvmName("einsum2")
fun einsum(block: Einsum.(String, String)-> Einsum.Expression): DoubleArray2D = TODO()

@JvmName("einsum3")
fun einsum(block: Einsum.(String, String, String)-> Einsum.Expression): DoubleArrayND = TODO()

@JvmName("einsum4")
fun einsum(block: Einsum.(String, String, String, String)-> Einsum.Expression): DoubleArrayND = TODO()

fun main() {

    val a = D[1,2,3]
    val b = D[1,2,3]
    val m = D[D[1, 2],
            D[3, 4]]
    val n = m.transpose()

    val diagonal = einsum { i -> m[i, i] }
    val trace = einsum { -> m[i, i] }
    val broadcast = einsum { i, j -> a[i] }
    val vecmul = einsum { i -> m[i, j] * n[j] }
    val matmul = einsum { i, j -> m[i, k] * n[k, j] }
    val inner = einsum { -> a[i] * b[i] }
    val norm2 = einsum { -> a[i] * a[i] }
    val outer = einsum { i, j -> a[i] * b[j] }
    val transpose = einsum { i, j -> m[j, i] }
    val cuadratic = einsum { -> m[i, j] * a[i] * b[j] }
    val matmul3 = einsum { i, j -> m[i, k] * n[k, l] * m[l, j] }

}