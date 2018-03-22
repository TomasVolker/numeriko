package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.jvm.factory.JvmArrayNDFactory
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ArrayND
import tomasvolker.numeriko.core.interfaces.integer.array0d.IntArray0D
import tomasvolker.numeriko.core.interfaces.integer.array1d.IntArray1D
import tomasvolker.numeriko.core.interfaces.integer.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.core.interfaces.integer.arraynd.IntArrayND

val currentFactory: ArrayNDFactory = JvmArrayNDFactory()

fun intZeros(s0: Int): IntArray1D =
        currentFactory.intZeros(s0)

fun intArray1D(s0: Int, value: (i0: Int) -> Int = { 0 }): IntArray1D =
        currentFactory.intArray1D(s0, value)

fun intArray1DOf(vararg values: Int): IntArray1D =
        currentFactory.intArray1DOf(*values)

fun intZeros(vararg shape: Int) =
        currentFactory.intZeros(*shape)

fun intZeros(shape: ReadOnlyIntArray1D) =
        currentFactory.intZeros(shape)

fun intArrayND(vararg shape: Int, value: (index: ReadOnlyIntArray1D)->Int = { 0 }) =
        currentFactory.intArrayND(*shape) { value(it) }

fun intArrayND(shape: ReadOnlyIntArray1D, value: (index: ReadOnlyIntArray1D)->Int = { 0 }) =
        currentFactory.intArrayND(shape, value)

fun <T> arrayND(vararg shape: Int, value: (index: ReadOnlyIntArray1D)->T) =
        currentFactory.arrayND(*shape) { value(it) }

fun <T> arrayND(shape: ReadOnlyIntArray1D, value: (index: ReadOnlyIntArray1D)->T) =
        currentFactory.arrayND(shape, value)


interface ArrayNDFactory {

    fun intZeros(): IntArray0D = intArray0DOf(0)

    fun intZeros(s0: Int): IntArray1D = intArray1DOf(0)

    fun intZeros(vararg shape: Int): IntArrayND =
            when(shape.size) {
                0 -> intZeros()
                1 -> intZeros(shape[0])
                else -> intArrayND(*shape) { 0 }
            }

    fun intZeros(shape: ReadOnlyIntArray1D): IntArrayND

    fun intArray0DOf(value: Int): IntArray0D

    fun intArray1D(s0: Int, value: (i0: Int) -> Int = { 0 }): IntArray1D

    fun intArray1DOf(vararg values: Int): IntArray1D

    fun intArrayND(vararg shape: Int, value: (index: ReadOnlyIntArray1D) -> Int = { 0 }): IntArrayND

    fun intArrayND(shape: ReadOnlyIntArray1D, value: (index: ReadOnlyIntArray1D) -> Int = { 0 }): IntArrayND

    fun <T> arrayND(vararg shape: Int, value: (index: ReadOnlyIntArray1D)->T): ArrayND<T>

    fun <T> arrayND(shape: ReadOnlyIntArray1D, value: (index: ReadOnlyIntArray1D)->T): ArrayND<T>

}
