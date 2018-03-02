package tomasvolker.numeriko.core.interfaces

import tomasvolker.numeriko.core.array.JvmNDArrayFactory
import tomasvolker.numeriko.core.interfaces.integer.IntNDArray
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray

val currentFactory: NDArrayFactory = JvmNDArrayFactory()

fun zerosInt(vararg shape: Int) =
        currentFactory.zerosInt(*shape)

fun zerosInt(shape: ReadOnlyIntNDArray) =
        currentFactory.zerosInt(shape)

fun array(vararg shape: Int, value: (index: ReadOnlyIntNDArray)->Int) =
        currentFactory.array(*shape) { value(it) }

fun array(shape: ReadOnlyIntNDArray, value: (index: ReadOnlyIntNDArray)->Int) =
        currentFactory.array(shape, value)


interface NDArrayFactory {

    fun zerosInt(vararg shape: Int): IntNDArray

    fun zerosInt(shape: ReadOnlyIntNDArray): IntNDArray

    fun array(vararg shape: Int, value: (index: ReadOnlyIntNDArray)->Int): IntNDArray

    fun array(shape: ReadOnlyIntNDArray, value: (index: ReadOnlyIntNDArray)->Int): IntNDArray

}
