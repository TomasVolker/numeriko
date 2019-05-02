package tomasvolker.numeriko.core

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.dsl.F
import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.factory.toByteArrayND
import tomasvolker.numeriko.core.interfaces.factory.unsafeDoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.unsafeFloatArrayND
import tomasvolker.numeriko.core.interfaces.iteration.forEachIndex2
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndex
import tomasvolker.numeriko.core.interfaces.slicing.expandRank
import tomasvolker.numeriko.core.primitives.d
import tomasvolker.numeriko.core.primitives.sumFloat
import kotlin.system.measureTimeMillis

infix fun FloatArrayND.matMul(other: FloatArrayND): FloatArrayND {
    require(this.rank == 2)
    require(other.rank in 1..2)
    require(this.shape(1) == other.shape(0))

    return unsafeFloatArrayND(I[this.shape(0), other.shape(1)]) { (i0, i1) ->
        sumFloat(0 until this.shape(1)) { j ->
            this[i0, j] * other[j, i1]
        }
    }
}

fun main() {

    val matrix = F[
        F[0, 1, 0],
        F[1, 1, 1],
        F[0, 0, 1]
    ]

    repeat(10) {

        val n = 10000

        var a = 0.0

        val array = unsafeDoubleArrayND(I[n, n])  { (i0, i1) -> i0.d * i1.d }

        measureTimeMillis {
            a = 0.0
            array.unsafeForEachIndex { index ->
                a += array.getDouble(index)
            }
        }.also { println("unsafe: $it") }

        measureTimeMillis {
            a = 0.0
            array.forEachIndex2 { i0, i1 ->
                a += array[i0, i1]
            }
        }.also { println("doubleFor: $it") }

        val raw = DoubleArray(n * n) { i -> (i / n).d * (i % n).d }

        measureTimeMillis {
            a = 0.0
            val index = IntArray(1) { 0 }
            for (i in 0 until raw.size) {
                a += raw[i]
            }
        }.also { println("raw: $it") }

        /*
        measureTimeMillis {
            a = 0.0
            array.forEachIndex { index ->
                a += array.getDouble(index)
            }
        }.also { println("safe: $it") }
        */

    }


}
