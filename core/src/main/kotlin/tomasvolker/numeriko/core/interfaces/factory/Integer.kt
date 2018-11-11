package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D

fun IntArray.asIntArray1D(): IntArray1D = intArray1D(this)

fun intArray1DOf(vararg values: Int) = intArray1D(values)


fun intArray1D(data: IntArray): IntArray1D =
        defaultFactory.intArray1D(data)


fun copy(array: IntArray1D): IntArray1D =
        defaultFactory.copy(array)


fun intZeros(size: Int): IntArray1D =
        defaultFactory.intZeros(size)
