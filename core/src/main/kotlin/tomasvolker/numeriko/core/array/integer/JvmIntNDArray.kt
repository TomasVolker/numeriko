package tomasvolker.numeriko.core.array.integer

import tomasvolker.numeriko.core.index.AbstractIndex
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.*
import tomasvolker.numeriko.core.interfaces.integer.IntNDArray
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.util.checkRange
import tomasvolker.numeriko.core.util.computeSizeFromShape
import tomasvolker.numeriko.core.util.dimensionWidthArray
import tomasvolker.numeriko.core.util.indexArrayToLinearIndex

class JvmIntNDArray(
        val data: IntArray,
        internal var shapeArray: IntArray
) : IntNDArray {

    init {

        val requiredSize = computeSizeFromShape(shapeArray)

        require(data.size == requiredSize) {
            "data size (${data.size}) does not match shape size ($requiredSize)"
        }

    }

    override val shape: ReadOnlyIntNDArray
        get() = JvmIntNDArray(
                data = shapeArray,
                shapeArray = intArrayOf(rank)
        )

    override val size: Int
        get() = data.size

    override val rank: Int
        get() = shapeArray.size

    override fun getInt(vararg indices: Int) =
            data[indexArrayToLinearIndex(shapeArray, indices)]

    override fun getInt(indexArray: ReadOnlyIntNDArray) =
            data[indexArrayToLinearIndex(shapeArray, indexArray)]

    override fun setInt(value: Int, vararg indices: Int) {
        data[indexArrayToLinearIndex(shapeArray, indices)] = value
    }

    override fun setInt(value: Int, indexArray: ReadOnlyIntNDArray) {
        data[indexArrayToLinearIndex(shapeArray, indexArray)] = value
    }

    // TODO Setting on itself
    override fun setInt(value: ReadOnlyIntNDArray, vararg indices: Any) =
            getView(*indices).setAll { value.getInt(it) }

    // TODO Setting on itself
    override fun setValue(value: ReadOnlyNDArray<Int>, vararg indices: Any) =
            getView(*indices).setAll { value.getValue(it) }

    override fun getView(vararg indices: Any): JvmIntNDArrayView {

        require(indices.size <= rank) {
            "Too many indices (${indices.size}, must be less or equal than ${rank})"
        }

        val widthArray = dimensionWidthArray(shape)

        var offset = 0
        val shapeList = mutableListOf<Int>()
        val strideList = mutableListOf<Int>()

        for (dimension in indices.indices) {

            var index: Any = indices[dimension]

            when (index) {
                is AbstractIndex -> {
                    index = index.computeValue(shape, dimension)
                }
                is IndexProgression -> {
                    index = index.computeProgression(shape, dimension)
                }
            }

            val currentSize = this.shapeArray[dimension]
            val currentStride = widthArray[dimension]

            when (index) {
                is Int -> {

                    checkRange(dimension, currentSize, index)

                    offset += ((index + currentSize) % currentSize) * currentStride
                }
                is IntProgression -> {

                    //TODO check out of range

                    offset += index.first * currentStride
                    shapeList.add(index.count())
                    strideList.add(index.step * currentStride)
                }
                else -> throw IllegalArgumentException("Index $index is not an Int, IntRange, AbstractIndex or IndexProgression")
            }

        }

        for (dimension in indices.size until rank) {

            shapeList.add(this.shapeArray[dimension])
            strideList.add(widthArray[dimension])

        }

        return JvmIntNDArrayView(
                data = data,
                offset = offset,
                shapeArray = shapeList.toIntArray(),
                strideArray = strideList.toIntArray()
        )
    }

    override fun copy() = JvmIntNDArray(data.copyOf(), shapeArray.copyOf())

    override fun unsafeGetDataAsArray() = getDataAsArray()

    override fun unsafeGetDataAsIntArray() = data

    override fun unsafeGetShapeAsArray() = shapeArray

    override fun getDataAsArray() = data.toTypedArray()

    override fun getShapeAsArray() = shapeArray.copyOf()

    override fun equals(other: Any?): Boolean {
        when(other) {
            is JvmIntNDArray -> {
                return other.data.contentEquals(this.data) &&
                        other.shapeArray.contentEquals(other.shapeArray)
            }
            else -> return defaultEquals(other)
        }
    }

    override fun linearCursor() = JvmIntNDArrayLinearCursor(this)

    override fun cursor() = JvmIntNDArrayCursor(this)

    override fun hashCode() =
           31 * shapeArray.reduce { acc, i ->  31 * acc + i.hashCode()} +
                   data.reduce { acc, i -> 31 * acc + i.hashCode() }

    override fun toString() = defaultToString()

}
