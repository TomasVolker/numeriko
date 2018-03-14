package tomasvolker.numeriko.core.jvm.generic.arraynd

import tomasvolker.numeriko.core.jvm.factory.jvmArrayNDFactory
import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntArrayND
import tomasvolker.numeriko.core.interfaces.int.arraynd.get
import tomasvolker.numeriko.core.interfaces.int.arraynd.set

fun <T> Array<T>.wrapArrayND(vararg shape: Int = intArrayOf(this.size)) = JvmArrayND(
        data = this,
        shapeArray = shape
)

fun <T> Array<T>.toArrayND(vararg shape: Int = intArrayOf(this.size)) = JvmArrayND(
        data = this.copyOf(),
        shapeArray = shape
)

fun <T> Array<T>.wrapArrayND(shape: ReadOnlyIntArrayND) = JvmArrayND(
        data = this,
        shapeArray = shape.getDataAsIntArray()
)

fun <T> Array<T>.toArrayND(shape: ReadOnlyIntArrayND) = JvmArrayND(
        data = this.copyOf(),
        shapeArray = shape.getDataAsIntArray()
)


/*inline*/ fun <T> JvmArrayND<T>.setAllInline(setter: (indexArray: ReadOnlyIntArrayND) -> T) {

    val indexArray = jvmArrayNDFactory.intZeros(this.indexShape)

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

inline fun <T> JvmArrayND<T>.applyElementWiseInline(function: (value: T) -> T): JvmArrayND<T> {

    val result = Array<Any?>(data.size) { null }

    for (i in data.indices) {
        result[i] = function(data[i])
    }

    return JvmArrayND(result as Array<T>, getShapeAsArray())
}

/*
inline fun <reified T> JvmArrayND<T>.applyElementWiseInline(function: (value: Int) -> T): ArrayNDArray<T> {

    val result = ArrayNDArray<T?>(shape) { null }

    for (i in data.indices) {
        result[i] = function(data[i])
    }

    return result as ArrayNDArray<T>
}
*/
