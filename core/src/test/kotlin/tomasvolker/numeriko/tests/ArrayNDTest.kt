package tomasvolker.numeriko.tests

import tomasvolker.numeriko.core.implementations.jvm.arraynd.JvmMutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.factory.intArray1DOf

fun main() {


    val a = JvmMutableDoubleArrayND(
            intArray1DOf(2, 3, 4),
            DoubleArray(2*3*4) { i -> i.toDouble() }
    )

    for(x in a) {
        println(x)
    }

}