package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.jvm.factory.JvmArrayNDFactory
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ArrayND
import tomasvolker.numeriko.core.interfaces.int.array1d.IntArray1D
import tomasvolker.numeriko.core.interfaces.int.arraynd.IntArrayND
import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntArrayND

val currentFactory: ArrayNDFactory = JvmArrayNDFactory()

fun intZeros(s0: Int): IntArray1D =
        currentFactory.intZeros(s0)

fun intArray1D(s0: Int, value: (i0: Int) -> Int = { 0 }): IntArray1D =
        currentFactory.intArray1D(s0, value)

fun intZeros(vararg shape: Int) =
        currentFactory.intZeros(*shape)

fun intZeros(shape: ReadOnlyIntArrayND) =
        currentFactory.intZeros(shape)

fun intArrayND(vararg shape: Int, value: (index: ReadOnlyIntArrayND)->Int = { 0 }) =
        currentFactory.intArrayND(*shape) { value(it) }

fun intArrayND(shape: ReadOnlyIntArrayND, value: (index: ReadOnlyIntArrayND)->Int = { 0 }) =
        currentFactory.intArrayND(shape, value)

fun <T> arrayND(vararg shape: Int, value: (index: ReadOnlyIntArrayND)->T) =
        currentFactory.arrayND(*shape) { value(it) }

fun <T> arrayND(shape: ReadOnlyIntArrayND, value: (index: ReadOnlyIntArrayND)->T) =
        currentFactory.arrayND(shape, value)


interface ArrayNDFactory {

    fun intZeros(s0: Int): IntArray1D

    fun intZeros(vararg shape: Int): IntArrayND

    fun intZeros(shape: ReadOnlyIntArrayND): IntArrayND

    fun intArray1D(s0: Int, value: (i0: Int) -> Int = { 0 }): IntArray1D

    fun intArrayND(vararg shape: Int, value: (index: ReadOnlyIntArrayND) -> Int = { 0 }): IntArrayND

    fun intArrayND(shape: ReadOnlyIntArrayND, value: (index: ReadOnlyIntArrayND) -> Int = { 0 }): IntArrayND

    fun <T> arrayND(vararg shape: Int, value: (index: ReadOnlyIntArrayND)->T): ArrayND<T>

    fun <T> arrayND(shape: ReadOnlyIntArrayND, value: (index: ReadOnlyIntArrayND)->T): ArrayND<T>

}
