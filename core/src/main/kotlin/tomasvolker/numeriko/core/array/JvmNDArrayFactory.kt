package tomasvolker.numeriko.core.array

import tomasvolker.numeriko.core.array.generic.JvmNDArray
import tomasvolker.numeriko.core.array.generic.setAllInline
import tomasvolker.numeriko.core.array.integer.JvmIntNDArray
import tomasvolker.numeriko.core.array.integer.setAllInline
import tomasvolker.numeriko.core.interfaces.NDArray
import tomasvolker.numeriko.core.interfaces.NDArrayFactory
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.util.computeSizeFromShape

val jvmNDArrayFactory = JvmNDArrayFactory()

class JvmNDArrayFactory: NDArrayFactory {

    override fun <T> ndArray(vararg shape: Int, value: (index: ReadOnlyIntNDArray) -> T): JvmNDArray<T> {
        val result = JvmNDArray(
                data = allocateData(shape) as Array<T?>,
                shapeArray = shape
        )
        result.setAllInline(value)
        return result as JvmNDArray<T>
    }

    override fun <T> ndArray(shape: ReadOnlyIntNDArray, value: (index: ReadOnlyIntNDArray) -> T): JvmNDArray<T> {
        val shapeArray = shape.getDataAsIntArray()
        val result = JvmNDArray(
                data = allocateData(shapeArray) as Array<T?>,
                shapeArray = shapeArray
        )
        result.setAllInline(value)
        return result as JvmNDArray<T>
    }

    override fun intNDArray(vararg shape: Int, value: (index: ReadOnlyIntNDArray) -> Int): JvmIntNDArray {
        val result = JvmIntNDArray(
                data = allocateIntData(shape),
                shapeArray = shape
        )
        result.setAllInline(value)
        return result
    }

    override fun intNDArray(shape: ReadOnlyIntNDArray, value: (index: ReadOnlyIntNDArray) -> Int): JvmIntNDArray {
        val shapeArray = shape.getDataAsIntArray()
        val result = JvmIntNDArray(
                data = allocateIntData(shapeArray),
                shapeArray = shapeArray
        )
        result.setAllInline(value)
        return result
    }

    override fun intZeros(vararg shape: Int): JvmIntNDArray =
            JvmIntNDArray(
                    data = allocateIntData(shape),
                    shapeArray = shape
            )

    override fun intZeros(shape: ReadOnlyIntNDArray): JvmIntNDArray =
            intZeros(*shape.getDataAsIntArray())

    private fun allocateIntData(shapeArray: IntArray) = IntArray(computeSizeFromShape(shapeArray))

    private fun allocateData(shapeArray: IntArray) = Array<Any?>(computeSizeFromShape(shapeArray)) { null }

}

