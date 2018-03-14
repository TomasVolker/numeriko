package tomasvolker.numeriko.core.jvm.int.arraynd

import tomasvolker.numeriko.core.jvm.factory.jvmArrayNDFactory
import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntArrayND
import tomasvolker.numeriko.core.interfaces.int.arraynd.get
import tomasvolker.numeriko.core.interfaces.int.arraynd.set

fun IntArray.wrapArrayND(vararg shape: Int = intArrayOf(this.size)) = JvmIntArrayND(
        data = this,
        shapeArray = shape
)

fun IntArray.toArrayND(vararg shape: Int = intArrayOf(this.size)) = JvmIntArrayND(
        data = this.copyOf(),
        shapeArray = shape
)

fun IntArray.wrapArrayND(shape: ReadOnlyIntArrayND) = JvmIntArrayND(
        data = this,
        shapeArray = shape.getDataAsIntArray()
)

fun IntArray.toArrayND(shape: ReadOnlyIntArrayND) = JvmIntArrayND(
        data = this.copyOf(),
        shapeArray = shape.getDataAsIntArray()
)


/*inline*/ fun JvmIntArrayND.setAllInline(setter: (indexArray: ReadOnlyIntArrayND) -> Int) {

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

inline fun JvmIntArrayND.applyElementWiseInline(function: (value: Int) -> Int): JvmIntArrayND {

    val result = IntArray(data.size)

    for (i in data.indices) {
        result[i] = function(data[i])
    }

    return JvmIntArrayND(result, getShapeAsArray())
}

/*
inline fun <reified T> JvmIntArrayND.applyElementWiseInline(function: (value: Int) -> T): ArrayNDArray<T> {

    val result = ArrayNDArray<T?>(shape) { null }

    for (i in data.indices) {
        result[i] = function(data[i])
    }

    return result as ArrayNDArray<T>
}
*/

inline fun JvmIntArrayND.binaryElementWiseInline(other: JvmIntArrayND, function: (lhs: Int, rhs: Int) -> Int): JvmIntArrayND {

    val result = jvmArrayNDFactory.intZeros(shape)

    for (i in data.indices) {
        result.data[i] = function(data[i], other.data[i])
    }

    return result
}
