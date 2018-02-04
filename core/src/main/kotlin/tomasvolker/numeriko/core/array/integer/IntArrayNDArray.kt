package tomasvolker.numeriko.core.array.integer

import tomasvolker.numeriko.core.index.AbstractIndex
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.*
import tomasvolker.numeriko.core.interfaces.integer.IntNDArray
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.interfaces.integer.get
import tomasvolker.numeriko.core.util.computeSizeFromShape
import tomasvolker.numeriko.core.util.dimensionWidthArray
import tomasvolker.numeriko.core.util.indexArrayToLinearIndex

class IntArrayNDArray(
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
        get() = IntArrayNDArray(
                data = shapeArray,
                shapeArray = intArrayOf(rank)
        )

    override val size: Int
        get() = data.size

    override val rank: Int
        get() = shapeArray.size

    override fun getView(vararg indices: Any): IntArrayNDArrayView {

        require(indices.size <= rank) {
            "Too many indices (${indices.size}, must be less or equal than ${rank})"
        }

        val widthArray = dimensionWidthArray(shape)

        var offset = 0
        val shapeList = mutableListOf<Int>()
        val strideList = mutableListOf<Int>()

        var currentShape: Int
        var currentStride: Int

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

            currentShape = this.shapeArray[dimension]
            currentStride = widthArray[dimension]

            when (index) {
                is Int -> {

                    if (index <= -currentShape|| currentShape < index ) {
                        throw IndexOutOfBoundsException("Index ${index} in dimension ${dimension} is out of bounds")
                    }

                    offset += ((index + currentShape) % currentShape) * currentStride
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

        return IntArrayNDArrayView(
                data = data,
                offset = offset,
                shapeArray = shapeList.toIntArray(),
                strideArray = strideList.toIntArray()
        )
    }

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

    override fun setInt(value: ReadOnlyIntNDArray, vararg indices: Any) {
        this[indices].setAll { value[it] }
    }

    override fun setValue(value: ReadOnlyNDArray<Int>, vararg indices: Any) {
        this[indices].setAll { value[it] }
    }

    override fun copy() = IntArrayNDArray(data.copyOf(), shapeArray.copyOf())

    override fun dataAsArray() = data.toTypedArray()

    override fun dataAsIntArray() = data.copyOf()

    override fun shapeAsArray() = shapeArray.copyOf()

    override fun equals(other: Any?): Boolean {
        when(other) {
            is IntArrayNDArray -> {
                return other.data.contentEquals(this.data) &&
                        other.shapeArray.contentEquals(other.shapeArray)
            }
            else -> return defaultEquals(other)
        }
    }

    override fun linearCursor() = IntArrayNDArrayLinearCursor(this)

    override fun cursor() = IntArrayNDArrayCursor(this)

    override fun hashCode() =
           31 * shapeArray.reduce { acc, i ->  31 * acc + i.hashCode()} +
                   data.reduce { acc, i -> 31 * acc + i.hashCode() }

    override fun toString() = defaultToString()

}
