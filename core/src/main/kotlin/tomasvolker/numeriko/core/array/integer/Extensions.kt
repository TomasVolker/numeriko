package tomasvolker.numeriko.core.array.integer

import tomasvolker.numeriko.core.array.arrayNDArrayFactory
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.interfaces.integer.get
import tomasvolker.numeriko.core.interfaces.integer.set

fun IntArray.wrapNDArray(vararg shape: Int = intArrayOf(this.size)) = IntArrayNDArray(
        data = this,
        shapeArray = shape
)

fun IntArray.toNDArray(vararg shape: Int = intArrayOf(this.size)) = IntArrayNDArray(
        data = this.copyOf(),
        shapeArray = shape
)

fun IntArray.wrapNDArray(shape: ReadOnlyIntNDArray) = IntArrayNDArray(
        data = this,
        shapeArray = shape.dataAsIntArray()
)

fun IntArray.toNDArray(shape: ReadOnlyIntNDArray) = IntArrayNDArray(
        data = this.copyOf(),
        shapeArray = shape.dataAsIntArray()
)


/*inline*/ fun IntArrayNDArray.setAllInline(setter: (indexArray: ReadOnlyIntNDArray) -> Int) {

    val indexArray = arrayNDArrayFactory.zerosInt(this.indexShape)

    var dimensionIndex: Int

    for (i in data.indices) {

        data[i] = setter(indexArray)

        dimensionIndex = indexArray.lastIndex(0)

        while (dimensionIndex >= 0) {
            indexArray[dimensionIndex] += 1
            if (indexArray[dimensionIndex] < shape[dimensionIndex]) {
                break
            } else {
                indexArray[dimensionIndex] = 0
                dimensionIndex--
            }
        }
    }

}

inline fun IntArrayNDArray.applyElementWiseInline(function: (value: Int) -> Int): IntArrayNDArray {

    val result = IntArray(data.size)

    for (i in data.indices) {
        result[i] = function(data[i])
    }

    return IntArrayNDArray(shapeAsArray(), result)
}

/*
inline fun <reified T> IntArrayNDArray.applyElementWiseInline(function: (value: Int) -> T): ArrayNDArray<T> {

    val result = ArrayNDArray<T?>(shape) { null }

    for (i in data.indices) {
        result[i] = function(data[i])
    }

    return result as ArrayNDArray<T>
}
*/

inline fun IntArrayNDArray.binaryElementWiseInline(other: IntArrayNDArray, function: (lhs: Int, rhs: Int) -> Int): IntArrayNDArray {

    val result = arrayNDArrayFactory.zerosInt(shape)

    for (i in data.indices) {
        result.data[i] = function(data[i], other.data[i])
    }

    return result
}
