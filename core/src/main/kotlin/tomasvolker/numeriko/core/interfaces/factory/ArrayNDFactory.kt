@file:Suppress("UNCHECKED_CAST")

package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.implementations.numeriko.factory.NumerikoArrayNDFactory
import tomasvolker.numeriko.core.interfaces.array1d.generic.*
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.forEachIndices
import tomasvolker.numeriko.core.primitives.indicative
import tomasvolker.numeriko.core.reductions.product
import kotlin.random.Random

interface ArrayNDFactory {

    fun <T> array1D(data: Array<T>): Array1D<T>

    fun <T> array2D(shape0: Int, shape1: Int, data: Array<T>): Array2D<T>

    fun <T> arrayND(shape: IntArray1D, data: Array<T>): ArrayND<T>

    // Copy

    fun <T> copy(array: Array1D<T>): Array1D<T> =
            array1D(array.size) { i -> array.getValue(i) }

    fun <T> copy(array: Array2D<T>): Array2D<T> =
            array2D(array.shape0, array.shape1) { i0, i1 -> array.getValue(i0, i1) }

    fun <T> copy(array: ArrayND<T>): ArrayND<T> =
            arrayND(array.shape) { indices -> array.getValue(indices) }

    // Array Of Nulls

    fun <T> array1DOfNulls(size: Int): Array1D<T?> =
            array1D(arrayOfNulls<Any?>(size) as Array<T?>)

    fun <T> array2DOfNulls(shape0: Int, shape1: Int): Array2D<T?> =
            array2D(shape0, shape1, arrayOfNulls<Any?>(shape0*shape1) as Array<T?>)

    fun <T> arrayNDOfNulls(shape: IntArray1D): ArrayND<T?> =
            arrayND(shape, arrayOfNulls<Any?>(shape.product()) as Array<T?>)

    // Int

    fun intArray1D(data: IntArray): IntArray1D

    fun copy(array: IntArray1D): IntArray1D =
            intArray1D(array.size) { i -> array[i] }

    fun intZeros(size: Int): IntArray1D =
            intArray1D(IntArray(size) { 0 })

    // Double

    fun doubleArray1D(data: DoubleArray): DoubleArray1D

    fun doubleArray2D(shape0: Int, shape1: Int, data: DoubleArray): DoubleArray2D

    fun doubleArrayND(shape: IntArray1D, data: DoubleArray): DoubleArrayND

    // Copy

    fun copy(array: DoubleArray1D): DoubleArray1D =
            doubleArray1D(array.size) { i -> array[i] }

    fun copy(array: DoubleArray2D): DoubleArray2D =
            doubleArray2D(array.shape0, array.shape1) { i0, i1 -> array[i0, i1] }

    fun copy(array: DoubleArrayND): DoubleArrayND =
            doubleArrayND(array.shape) { indices -> array[indices] }

    // Zeros

    fun doubleZeros(size: Int): DoubleArray1D =
            doubleArray1D(DoubleArray(size) { 0.0 })

    fun doubleZeros(shape0: Int, shape1: Int): DoubleArray2D =
            doubleArray2D(shape0, shape1, DoubleArray(shape0*shape1) { 0.0 })

    fun doubleZeros(shape: IntArray1D): DoubleArrayND =
            doubleArrayND(shape, DoubleArray(shape.product()) { 0.0 })

    // Math

    fun doubleIdentity(size: Int): DoubleArray2D = doubleArray2D(size, size) { i0, i1 ->
        (i0 == i1).indicative()
    }

    fun doubleRandom(size: Int): DoubleArray1D =
            doubleArray1D(size) { Random.nextDouble() }

    fun doubleRandom(shape0: Int, shape1: Int): DoubleArray2D =
            doubleArray2D(shape0, shape1) { _, _ -> Random.nextDouble() }

    fun doubleRandom(shape: IntArray1D): DoubleArrayND =
            doubleArrayND(shape) { Random.nextDouble() }

    fun doubleDiagonal(diagonal: DoubleArray1D): DoubleArray2D =
            doubleDiagonal(diagonal.size) { i -> diagonal[i] }

}

inline fun <T> array1D(size: Int, init: (i: Int)->T): Array1D<T> =
        NumerikoConfig.defaultFactory.array1DOfNulls<T>(size).asMutable().apply {
            forEachIndex { i0 ->
                setValue(init(i0), i0)
            }
        } as Array1D<T>

inline fun <T> array2D(shape0: Int, shape1: Int, init: (i0: Int, i1: Int)->T): Array2D<T> =
        NumerikoConfig.defaultFactory.array2DOfNulls<T>(shape0, shape1).asMutable().apply {
            forEachIndex { i0, i1 ->
                setValue(init(i0, i1), i0, i1)
            }
        } as Array2D<T>

inline fun <T> arrayND(shape: IntArray1D, init: (indices: IntArray1D)->T): ArrayND<T> =
        NumerikoConfig.defaultFactory.arrayNDOfNulls<T>(shape).asMutable().apply {
            forEachIndices { indices ->
                setValue(init(indices), indices)
            }
        } as ArrayND<T>



inline fun intArray1D(size: Int, init: (i: Int)->Int): IntArray1D =
        NumerikoConfig.defaultFactory.intZeros(size).asMutable().apply {
            forEachIndex { i ->
                this[i] = init(i)
            }
        }


inline fun doubleArray1D(size: Int, init: (i: Int)->Double): DoubleArray1D =
        NumerikoConfig.defaultFactory.doubleZeros(size).asMutable().apply {
            forEachIndex { i ->
                this[i] = init(i)
            }
        }


inline fun doubleArray2D(shape0: Int, shape1: Int, init: (i0: Int, i1: Int)->Double): DoubleArray2D =
        NumerikoConfig.defaultFactory.doubleZeros(shape0, shape1).asMutable().apply {
            forEachIndex { i0, i1 ->
                this[i0, i1] = init(i0, i1)
            }
        }

inline fun doubleArrayND(shape: IntArray1D, init: (indices: IntArray1D)->Double): DoubleArrayND =
        NumerikoConfig.defaultFactory.doubleZeros(shape).asMutable().apply {
            forEachIndices { indices ->
                this[indices] = init(indices)
            }
        }
