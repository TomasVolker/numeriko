package tomasvolker.numeriko.core.interfaces

import tomasvolker.numeriko.core.array.ArrayNDArrayFactory
import tomasvolker.numeriko.core.interfaces.integer.IntNDArray
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray

val currentFactory: NDArrayFactory = ArrayNDArrayFactory()

interface NDArrayFactory {

    fun zerosInt(vararg shape: Int): IntNDArray

    fun zerosInt(shape: ReadOnlyIntNDArray): IntNDArray

}
