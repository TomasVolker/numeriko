package tomasvolker.numeriko.core.array.generic

import tomasvolker.numeriko.core.array.integer.JvmIntNDArray
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.generic.arraynd.NDArray
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ReadOnlyNDArray
import tomasvolker.numeriko.core.interfaces.generic.arraynd.defaultEquals
import tomasvolker.numeriko.core.interfaces.generic.arraynd.defaultToString
import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.util.checkRange
import tomasvolker.numeriko.core.util.computeSizeFromShape
import tomasvolker.numeriko.core.util.dimensionWidthArray
import tomasvolker.numeriko.core.util.indexArrayToLinearIndex

class JvmNDArray<T>(
        val data: Array<T>,
        internal var shapeArray: IntArray
) : NDArray<T> {

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

    override fun getValue(vararg indices: Int) =
            data[indexArrayToLinearIndex(shapeArray, indices)]

    override fun getValue(indexArray: ReadOnlyIntNDArray) =
            data[indexArrayToLinearIndex(shapeArray, indexArray)]

    override fun setValue(value: T, vararg indices: Int) {
        data[indexArrayToLinearIndex(shapeArray, indices)] = value
    }

    override fun setValue(value: T, indexArray: ReadOnlyIntNDArray) {
        data[indexArrayToLinearIndex(shapeArray, indexArray)] = value
    }

    // TODO Setting on itself
    override fun setValue(value: ReadOnlyNDArray<T>, vararg indices: Any) =
            getView(*indices).setAll { value.getValue(it) }

    override fun getView(vararg indices: Any): JvmNDArrayView<T> {

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
                is Index -> {
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
                else -> throw IllegalArgumentException("LiteralIndex $index is not an Int, IntRange, Index or IndexProgression")
            }

        }

        for (dimension in indices.size until rank) {

            shapeList.add(this.shapeArray[dimension])
            strideList.add(widthArray[dimension])

        }

        return JvmNDArrayView(
                data = data,
                offset = offset,
                shapeArray = shapeList.toIntArray(),
                strideArray = strideList.toIntArray()
        )
    }

    override fun copy() = JvmNDArray(data.copyOf(), shapeArray.copyOf())

    override fun unsafeGetDataAsArray() = data

    override fun unsafeGetShapeAsArray() = shapeArray

    override fun getDataAsArray() = data.copyOf()

    override fun getShapeAsArray() = shapeArray.copyOf()

    override fun equals(other: Any?): Boolean {
        when(other) {
            is JvmNDArray<*> -> {
                return other.data.contentEquals(this.data) &&
                        other.shapeArray.contentEquals(other.shapeArray)
            }
            else -> return defaultEquals(other)
        }
    }

    override fun linearCursor() = JvmNDArrayLinearCursor(this)

    override fun cursor() = JvmNDArrayCursor(this)

    override fun hashCode() =
           31 * shapeArray.reduce { acc, i ->  31 * acc + i.hashCode()} +
                   data.asSequence().map { it?.hashCode() ?: 0 }.reduce { acc, i -> 31 * acc + i.hashCode() }

    override fun toString() = defaultToString()

}
