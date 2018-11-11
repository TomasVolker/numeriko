package tomasvolker.numeriko.tests

import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.implementations.jvm.arraynd.JvmMutableDoubleArrayND

fun main() {

    val a = JvmMutableDoubleArrayND(
            shape = I[2, 4, 3, 2],
            data = DoubleArray(2*4*3*2) { i -> i.toDouble() }
    )

    println(a)

}