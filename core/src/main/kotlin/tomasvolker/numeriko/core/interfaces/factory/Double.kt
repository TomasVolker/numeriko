package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.lowdim.integer.IntVector2
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D

fun DoubleArray.asDoubleArray1D(): DoubleArray1D = doubleArray1D(this)

fun DoubleArray.asMutableDoubleArray1D(): MutableDoubleArray1D = mutableDoubleArray1D(this)

fun doubleArray1D(data: DoubleArray): DoubleArray1D = mutableDoubleArray1D(data)

inline fun doubleArray1D(size: Int, init: (index: Int)->Double): DoubleArray1D =
        mutableDoubleArray1D(size, init)

fun mutableDoubleArray1D(data: DoubleArray): MutableDoubleArray1D =
        defaultFactory.mutableDoubleArray1D(data)

inline fun mutableDoubleArray1D(size: Int, init: (index: Int)->Double): MutableDoubleArray1D =
        mutableDoubleArray1D(DoubleArray(size) {i -> init(i) })

fun doubleArray1DOf(vararg values: Double) = doubleArray1D(values)

fun mutableDoubleArray1DOf(vararg values: Double) =
        mutableDoubleArray1D(values)

fun copy(array: DoubleArray1D): DoubleArray1D =
        defaultFactory.copy(array)

fun mutableCopy(array: DoubleArray1D): MutableDoubleArray1D =
        defaultFactory.mutableCopy(array)

fun doubleZeros(size: Int): DoubleArray1D =
        defaultFactory.doubleZeros(size)

fun mutableDoubleZeros(size: Int): MutableDoubleArray1D =
        defaultFactory.mutableDoubleZeros(size)



fun doubleArray2D(shape: IntVector2, data: DoubleArray): DoubleArray2D =
        mutableDoubleArray2D(shape.value0, shape.value1, data)

fun doubleArray2D(shape0: Int, shape1: Int, data: DoubleArray): DoubleArray2D = mutableDoubleArray2D(shape0, shape1, data)

inline fun doubleArray2D(shape0: Int, shape1: Int, init: (i0: Int, i1: Int)->Double): DoubleArray2D =
        mutableDoubleArray2D(shape0, shape1, init)

fun mutableDoubleArray2D(shape0: Int, shape1: Int, data: DoubleArray): MutableDoubleArray2D =
        defaultFactory.mutableDoubleArray2D(shape0, shape1, data)

inline fun mutableDoubleArray2D(shape0: Int, shape1: Int, init: (i0: Int, i1: Int)->Double): MutableDoubleArray2D =
        mutableDoubleArray2D(shape0, shape1, DoubleArray(shape0 * shape1) { i -> init(i / shape1, i % shape1) })

fun copy(array: DoubleArray2D): DoubleArray2D =
        defaultFactory.copy(array)

fun mutableCopy(array: DoubleArray2D): MutableDoubleArray2D =
        defaultFactory.mutableCopy(array)

fun doubleZeros(shape0: Int, shape1: Int): DoubleArray2D =
        defaultFactory.doubleZeros(shape0, shape1)

fun mutableDoubleZeros(shape0: Int, shape1: Int): MutableDoubleArray2D =
        defaultFactory.mutableDoubleZeros(shape0, shape1)