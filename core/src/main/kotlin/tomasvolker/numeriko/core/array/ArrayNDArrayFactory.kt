package tomasvolker.numeriko.core.array

import tomasvolker.numeriko.core.array.integer.IntArrayNDArray
import tomasvolker.numeriko.core.array.integer.setAllInline
import tomasvolker.numeriko.core.interfaces.NDArrayFactory
import tomasvolker.numeriko.core.interfaces.integer.IntNDArray
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.util.computeSizeFromShape

val arrayNDArrayFactory = ArrayNDArrayFactory()

class ArrayNDArrayFactory: NDArrayFactory {

    override fun array(vararg shape: Int, value: (index: ReadOnlyIntNDArray) -> Int): IntArrayNDArray {
        val result = IntArrayNDArray(
                data = allocateIntData(shape),
                shapeArray = shape
        )
        result.setAllInline(value)
        return result
    }

    override fun array(shape: ReadOnlyIntNDArray, value: (index: ReadOnlyIntNDArray) -> Int): IntArrayNDArray {
        val shapeArray = shape.dataAsIntArray()
        val result = IntArrayNDArray(
                data = allocateIntData(shapeArray),
                shapeArray = shapeArray
        )
        result.setAllInline(value)
        return result
    }

    override fun zerosInt(vararg shape: Int): IntArrayNDArray =
            IntArrayNDArray(
                    data = allocateIntData(shape),
                    shapeArray = shape
            )

    override fun zerosInt(shape: ReadOnlyIntNDArray): IntArrayNDArray =
            zerosInt(*shape.dataAsIntArray())


    private fun allocateIntData(shapeArray: IntArray) = IntArray(computeSizeFromShape(shapeArray))

}

