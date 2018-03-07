package tomasvolker.numeriko.core.interfaces

import tomasvolker.numeriko.core.array.JvmNDArrayFactory
import tomasvolker.numeriko.core.interfaces.integer.IntNDArray
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray

val currentFactory: NDArrayFactory = JvmNDArrayFactory()

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

    fun intZeros(vararg shape: Int): IntNDArray

    fun intZeros(shape: ReadOnlyIntNDArray): IntNDArray

    fun intNDArray(vararg shape: Int, value: (index: ReadOnlyIntNDArray)->Int = { 0 }): IntNDArray

    fun intNDArray(shape: ReadOnlyIntNDArray, value: (index: ReadOnlyIntNDArray)->Int = { 0 }): IntNDArray

    fun <T> ndArray(vararg shape: Int, value: (index: ReadOnlyIntNDArray)->T): NDArray<T>

    fun <T> ndArray(shape: ReadOnlyIntNDArray, value: (index: ReadOnlyIntNDArray)->T): NDArray<T>

}
