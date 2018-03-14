package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.jvm.factory.JvmNDArrayFactory
import tomasvolker.numeriko.core.interfaces.generic.arraynd.NDArray
import tomasvolker.numeriko.core.interfaces.int.array1d.IntArray1D
import tomasvolker.numeriko.core.interfaces.int.arraynd.IntNDArray
import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntNDArray

val currentFactory: NDArrayFactory = JvmNDArrayFactory()

fun intZeros(s0: Int): IntArray1D =
        currentFactory.intZeros(s0)

fun intArray1D(s0: Int, value: (i0: Int) -> Int = { 0 }): IntArray1D =
        currentFactory.intArray1D(s0, value)

fun intZeros(vararg shape: Int) =
        currentFactory.intZeros(*shape)

fun intZeros(shape: ReadOnlyIntNDArray) =
        currentFactory.intZeros(shape)

fun intNDArray(vararg shape: Int, value: (index: ReadOnlyIntNDArray)->Int = { 0 }) =
        currentFactory.intNDArray(*shape) { value(it) }

fun intNDArray(shape: ReadOnlyIntNDArray, value: (index: ReadOnlyIntNDArray)->Int = { 0 }) =
        currentFactory.intNDArray(shape, value)

fun <T> ndArray(vararg shape: Int, value: (index: ReadOnlyIntNDArray)->T) =
        currentFactory.ndArray(*shape) { value(it) }

fun <T> ndArray(shape: ReadOnlyIntNDArray, value: (index: ReadOnlyIntNDArray)->T) =
        currentFactory.ndArray(shape, value)


interface NDArrayFactory {

    fun intZeros(s0: Int): IntArray1D

    fun intZeros(vararg shape: Int): IntNDArray

    fun intZeros(shape: ReadOnlyIntNDArray): IntNDArray

    fun intArray1D(s0: Int, value: (i0: Int) -> Int = { 0 }): IntArray1D

    fun intNDArray(vararg shape: Int, value: (index: ReadOnlyIntNDArray) -> Int = { 0 }): IntNDArray

    fun intNDArray(shape: ReadOnlyIntNDArray, value: (index: ReadOnlyIntNDArray) -> Int = { 0 }): IntNDArray

    fun <T> ndArray(vararg shape: Int, value: (index: ReadOnlyIntNDArray)->T): NDArray<T>

    fun <T> ndArray(shape: ReadOnlyIntNDArray, value: (index: ReadOnlyIntNDArray)->T): NDArray<T>

}
