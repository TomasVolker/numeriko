package tomasvolker.numeriko.core.array.generic

import tomasvolker.numeriko.core.array.jvmNDArrayFactory
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.interfaces.integer.get
import tomasvolker.numeriko.core.interfaces.integer.set

fun <T> Array<T>.wrapNDArray(vararg shape: Int = intArrayOf(this.size)) = JvmNDArray(
        data = this,
        shapeArray = shape
)

fun <T> Array<T>.toNDArray(vararg shape: Int = intArrayOf(this.size)) = JvmNDArray(
        data = this.copyOf(),
        shapeArray = shape
)

fun <T> Array<T>.wrapNDArray(shape: ReadOnlyIntNDArray) = JvmNDArray(
        data = this,
        shapeArray = shape.getDataAsIntArray()
)

fun <T> Array<T>.toNDArray(shape: ReadOnlyIntNDArray) = JvmNDArray(
        data = this.copyOf(),
        shapeArray = shape.getDataAsIntArray()
)


/*inline*/ fun <T> JvmNDArray<T>.setAllInline(setter: (indexArray: ReadOnlyIntNDArray) -> T) {

    val indexArray = jvmNDArrayFactory.intZeros(this.indexShape)

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

inline fun <T> JvmNDArray<T>.applyElementWiseInline(function: (value: T) -> T): JvmNDArray<T> {

    val result = Array<Any?>(data.size) { null }

    for (i in data.indices) {
        result[i] = function(data[i])
    }

    return JvmNDArray(result as Array<T>, getShapeAsArray())
}

/*
inline fun <reified T> JvmNDArray<T>.applyElementWiseInline(function: (value: Int) -> T): ArrayNDArray<T> {

    val result = ArrayNDArray<T?>(shape) { null }

    for (i in data.indices) {
        result[i] = function(data[i])
    }

    return result as ArrayNDArray<T>
}
*/
