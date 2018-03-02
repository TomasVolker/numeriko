package tomasvolker.numeriko.core.array.integer

import tomasvolker.numeriko.core.index.AbstractIndex
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.ReadOnlyNDArray
import tomasvolker.numeriko.core.interfaces.defaultEquals
import tomasvolker.numeriko.core.interfaces.defaultHashCode
import tomasvolker.numeriko.core.interfaces.defaultToString
import tomasvolker.numeriko.core.interfaces.integer.IntNDArray
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.util.checkRange
import tomasvolker.numeriko.core.util.computeSizeFromShape
import tomasvolker.numeriko.core.util.viewIndexArrayToLinearIndex

class IntArrayNDArrayView internal constructor(
        internal val data: IntArray,
        internal val offset: Int,
        internal val shapeArray: IntArray,
        internal val strideArray: IntArray
) : IntNDArray {

    init {

        require(data.size >= computeSizeFromShape(shapeArray)) {
            "data size (${data.size}) is not greater or equal than shape computed size (${computeSizeFromShape(shapeArray)})"
        }

        require(offset <= data.size) {
            "offset (${offset}) is not lower or equal than match shape size (${shapeArray.size})"
        }

        require(strideArray.size == shapeArray.size) {
            "strideArray size (${strideArray.size}) does not match shape size (${shapeArray.size})"
        }

        for (i in shapeArray.indices) {

            require(0 < shapeArray[i]) {
                "shape in rank $i cannot be negative (${shapeArray[i]})"
            }

            require(0 < strideArray[i]) {
                "stride in rank $i cannot be negative (${strideArray[i]})"
            }

        }

    }

    override val rank: Int = shapeArray.size

    override val size: Int = computeSizeFromShape(shapeArray)

    override val shape: ReadOnlyIntNDArray
        get() = IntArrayNDArray(
                data = shapeArray,
                shapeArray = intArrayOf(rank)
        )

    override fun getInt(vararg indices: Int) =
            data[viewIndexArrayToLinearIndex(
                    shapeArray = shapeArray,
                    offset = offset,
                    strideArray = strideArray,
                    indexArray = indices
            )]

    override fun getInt(indexArray: ReadOnlyIntNDArray) =
            data[viewIndexArrayToLinearIndex(
                    shapeArray = shapeArray,
                    offset = offset,
                    strideArray = strideArray,
                    indexArray = indexArray
            )]

    override fun getView(vararg indices: Any): IntArrayNDArrayView {

        require(indices.size <= rank) {
            "Wrong amount of indices (${indices.size} expected ${rank})"
        }

        var offset = this.offset
        val shapeList = mutableListOf<Int>()
        val strideList = mutableListOf<Int>()

        var currentSize: Int
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

            currentSize = this.shapeArray[dimension]
            currentStride = this.strideArray[dimension]

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
            strideList.add(this.strideArray[dimension])

        }

        return IntArrayNDArrayView(
                data = data,
                offset = offset,
                shapeArray = shapeList.toIntArray(),
                strideArray = strideList.toIntArray()
        )

    }

    override fun setInt(value: Int, vararg indices: Int) {
        data[viewIndexArrayToLinearIndex(
                shapeArray = shapeArray,
                offset = offset,
                strideArray = strideArray,
                indexArray = indices
        )] = value
    }

    override fun setInt(value: ReadOnlyIntNDArray, vararg indices: Any) =
            getView(*indices).setAll { value.getInt(it) }

    override fun setValue(value: ReadOnlyNDArray<Int>, vararg indices: Any) =
            getView(*indices).setAll { value.getValue(it) }

    override fun setInt(value: Int, indexArray: ReadOnlyIntNDArray) {
        data[viewIndexArrayToLinearIndex(
                shapeArray = shapeArray,
                offset = offset,
                strideArray = strideArray,
                indexArray = indexArray
        )] = value
    }

    override fun copy() = IntArrayNDArray(
            data = dataAsIntArray(),
            shapeArray = shapeArray.copyOf()
    )

    override fun dataAsArray(): Array<Int> = dataAsIntArray().toTypedArray()

    override fun dataAsIntArray(): IntArray {
        val result = IntArray(size)

        for ((i, x) in this.withIndex()) {
            result[i] = x
        }

        return result
    }

    override fun shapeAsArray() = shapeArray.copyOf()

    override fun linearCursor() = IntArrayNDArrayViewCursor(this)

    override fun cursor() = IntArrayNDArrayViewCursor(this)

    override fun toString() = defaultToString()

    override fun equals(other: Any?) = defaultEquals(other)

    override fun hashCode() = defaultHashCode()

}
