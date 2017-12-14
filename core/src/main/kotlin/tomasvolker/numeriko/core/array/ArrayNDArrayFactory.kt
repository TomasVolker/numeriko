package tomasvolker.numeriko.core.array

import tomasvolker.numeriko.core.array.integer.IntArrayNDArray
import tomasvolker.numeriko.core.array.integer.setAllInline
import tomasvolker.numeriko.core.interfaces.NDArrayFactory
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.util.computeSizeFromShape

val arrayNDArrayFactory = ArrayNDArrayFactory()

class ArrayNDArrayFactory: NDArrayFactory {

    override fun zerosInt(vararg shape: Int) = intArray(shape = *shape)

    override fun zerosInt(shape: ReadOnlyIntNDArray) = intArray(shape = shape)

    fun constantInt(value: Int, shape: ReadOnlyIntNDArray): IntArrayNDArray {
        val shapeArray = shape.dataAsIntArray()
        val data = IntArray(computeSizeFromShape(shapeArray)) { value }
        return IntArrayNDArray(data = data, shapeArray = shapeArray)
    }

    fun intArray(data: IntArray, shapeArray: IntArray) = IntArrayNDArray(
            data = data,
            shapeArray = shapeArray.copyOf()
    )

    fun intArray(vararg shape: Int) = IntArrayNDArray(
            data = allocateIntData(shape),
            shapeArray = shape
    )

    /*inline */ fun intArray(vararg shape: Int, init: (indexArray: ReadOnlyIntNDArray) -> Int): IntArrayNDArray {
        val result = intArray(*shape)
        result.setAllInline(init)
        return result
    }

    fun intArray(shape: ReadOnlyIntNDArray): IntArrayNDArray {

        require(shape.dimension == 1) { "shape dimension must be 1" }

        val shapeArray = shape.dataAsIntArray()

        return intArray(*shapeArray)
    }

    /*inline*/ fun intArray(shape: ReadOnlyIntNDArray, init: (indexArray: ReadOnlyIntNDArray) -> Int) =
            intArray(shape).apply { setAllInline(init) }

    private fun allocateIntData(shapeArray: IntArray) = IntArray(computeSizeFromShape(shapeArray))

}

