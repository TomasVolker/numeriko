package tomasvolker.numeriko.core.array.integer

import tomasvolker.numeriko.core.array.jvmNDArrayFactory
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.interfaces.integer.get
import tomasvolker.numeriko.core.interfaces.integer.set

fun IntArray.wrapNDArray(vararg shape: Int = intArrayOf(this.size)) = JvmIntNDArray(
        data = this,
        shapeArray = shape
)

fun IntArray.toNDArray(vararg shape: Int = intArrayOf(this.size)) = JvmIntNDArray(
        data = this.copyOf(),
        shapeArray = shape
)

fun IntArray.wrapNDArray(shape: ReadOnlyIntNDArray) = JvmIntNDArray(
        data = this,
        shapeArray = shape.dataAsIntArray()
)

fun IntArray.toNDArray(shape: ReadOnlyIntNDArray) = JvmIntNDArray(
        data = this.copyOf(),
        shapeArray = shape.dataAsIntArray()
)


/*inline*/ fun JvmIntNDArray.setAllInline(setter: (indexArray: ReadOnlyIntNDArray) -> Int) {

    val indexArray = jvmNDArrayFactory.zerosInt(this.indexShape)

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

inline fun JvmIntNDArray.applyElementWiseInline(function: (value: Int) -> Int): JvmIntNDArray {

    val result = IntArray(data.size)

    for (i in data.indices) {
        result[i] = function(data[i])
    }

    return JvmIntNDArray(shapeAsArray(), result)
}

/*
inline fun <reified T> JvmIntNDArray.applyElementWiseInline(function: (value: Int) -> T): ArrayNDArray<T> {

    val result = ArrayNDArray<T?>(shape) { null }

    for (i in data.indices) {
        result[i] = function(data[i])
    }

    return result as ArrayNDArray<T>
}
*/

inline fun JvmIntNDArray.binaryElementWiseInline(other: JvmIntNDArray, function: (lhs: Int, rhs: Int) -> Int): JvmIntNDArray {

    val result = jvmNDArrayFactory.zerosInt(shape)

    for (i in data.indices) {
        result.data[i] = function(data[i], other.data[i])
    }

    return result
}
