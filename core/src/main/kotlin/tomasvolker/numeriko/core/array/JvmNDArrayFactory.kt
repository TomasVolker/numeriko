package tomasvolker.numeriko.core.array

import tomasvolker.numeriko.core.array.integer.JvmIntNDArray
import tomasvolker.numeriko.core.array.integer.setAllInline
import tomasvolker.numeriko.core.interfaces.NDArrayFactory
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.util.computeSizeFromShape

val jvmNDArrayFactory = JvmNDArrayFactory()

class JvmNDArrayFactory: NDArrayFactory {

    override fun array(vararg shape: Int, value: (index: ReadOnlyIntNDArray) -> Int): JvmIntNDArray {
        val result = JvmIntNDArray(
                data = allocateIntData(shape),
                shapeArray = shape
        )
        result.setAllInline(value)
        return result
    }

    override fun array(shape: ReadOnlyIntNDArray, value: (index: ReadOnlyIntNDArray) -> Int): JvmIntNDArray {
        val shapeArray = shape.dataAsIntArray()
        val result = JvmIntNDArray(
                data = allocateIntData(shapeArray),
                shapeArray = shapeArray
        )
        result.setAllInline(value)
        return result
    }

    override fun zerosInt(vararg shape: Int): JvmIntNDArray =
            JvmIntNDArray(
                    data = allocateIntData(shape),
                    shapeArray = shape
            )

    override fun zerosInt(shape: ReadOnlyIntNDArray): JvmIntNDArray =
            zerosInt(*shape.dataAsIntArray())


    private fun allocateIntData(shapeArray: IntArray) = IntArray(computeSizeFromShape(shapeArray))

}

