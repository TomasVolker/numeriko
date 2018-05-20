package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.interfaces.generic.array1d.Array1D
import tomasvolker.numeriko.core.interfaces.generic.array1d.MutableArray1D
import tomasvolker.numeriko.core.interfaces.integer.array1d.IntArray1D
import tomasvolker.numeriko.core.interfaces.integer.array1d.MutableIntArray1D

fun IntArray.asIntArray1D(): IntArray1D = intArray1D(this)

fun IntArray.asMutableArray1D(): IntArray1D = mutableIntArray1D(this)

fun intArray1D(data: IntArray): IntArray1D = mutableIntArray1D(data)

inline fun intArray1D(size: Int, init: (index: Int)->Int): IntArray1D =
        mutableIntArray1D(size, init)

fun mutableIntArray1D(data: IntArray): MutableIntArray1D =
        defaultFactory.mutableIntArray1D(data)

inline fun mutableIntArray1D(size: Int, init: (index: Int)->Int): MutableIntArray1D =
        mutableIntArray1D(IntArray(size) {i -> init(i) })

fun intArray1DOf(vararg values: Int) = intArray1D(values)

fun mutableIntArray1DOf(vararg values: Int) = mutableIntArray1D(values)

fun copy(array: IntArray1D): IntArray1D =
        defaultFactory.copy(array)

fun mutableCopy(array: IntArray1D): MutableIntArray1D =
        defaultFactory.mutableCopy(array)