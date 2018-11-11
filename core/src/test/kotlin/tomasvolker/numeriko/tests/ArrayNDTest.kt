package tomasvolker.numeriko.tests

import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D


fun main() {
/*
    val a = JvmMutableDoubleArrayND(
            shape = I[2, 4, 3, 2],
            data = DoubleArray(2*4*3*2) { i -> i.toDouble() }
    )

    println(a)
    */
    val b = doubleArray1D(100) { i -> i.toDouble() }.asMutable()

    println(b)

    val view = b[4..12 step 2]

    println(view[1..3])

    view.setDouble(-5.0)

    println(b)

}