package tomasvolker.numeriko.core.jvm.int.arraynd

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ReadOnlyNDArray
import tomasvolker.numeriko.core.interfaces.generic.arraynd.defaultEquals
import tomasvolker.numeriko.core.interfaces.generic.arraynd.defaultHashCode
import tomasvolker.numeriko.core.interfaces.generic.arraynd.defaultToString
import tomasvolker.numeriko.core.interfaces.int.arraynd.IntNDArray
import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.util.checkRange
import tomasvolker.numeriko.core.util.computeSizeFromShape
import tomasvolker.numeriko.core.util.viewIndexArrayToLinearIndex

class JvmIntNDArrayView internal constructor(
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

            require(0 <= shapeArray[i]) {
                "shape in rank $i must be non negative (${shapeArray[i]})"
            }

            require(0 < strideArray[i]) {
                "stride in rank $i must be positive (${strideArray[i]})"
            }

        }

    }

    override val rank: Int = shapeArray.size

    override val size: Int = computeSizeFromShape(shapeArray)

    override val shape: ReadOnlyIntNDArray
        get() = JvmIntNDArray(
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

    override fun setInt(value: Int, vararg indices: Int) {
        data[viewIndexArrayToLinearIndex(
                shapeArray = shapeArray,
                offset = offset,
                strideArray = strideArray,
                indexArray = indices
        )] = value
    }

    //TODO set on itself
    override fun setInt(value: ReadOnlyIntNDArray, vararg indices: Any) =
            getView(*indices).setAll { value.getInt(it) }

    //TODO set on itself
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

    override fun getView(vararg indices: Any): JvmIntNDArrayView {

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
                is Index -> {
                    index = index.computeValue(shape.getInt(dimension))
                }
                is IndexProgression -> {
                    index = index.computeProgression(shape.getInt(dimension))
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
                else -> throw IllegalArgumentException("LiteralIndex $index is not an Int, IntRange, Index or IndexProgression")
            }

        }

        for (dimension in indices.size until rank) {

            shapeList.add(this.shapeArray[dimension])
            strideList.add(this.strideArray[dimension])

        }

        return JvmIntNDArrayView(
                data = data,
                offset = offset,
                shapeArray = shapeList.toIntArray(),
                strideArray = strideList.toIntArray()
        )

    }

    override fun copy() = JvmIntNDArray(
            data = getDataAsIntArray(),
            shapeArray = shapeArray.copyOf()
    )

    override fun unsafeGetDataAsArray(): Array<Int> = getDataAsArray()

    override fun getDataAsArray(): Array<Int> = getDataAsIntArray().toTypedArray()

    override fun getDataAsIntArray(): IntArray {
        val result = IntArray(size)

        for ((i, x) in this.withIndex()) {
            result[i] = x
        }

        return result
    }

    override fun unsafeGetShapeAsArray() = shapeArray

    override fun getShapeAsArray() = shapeArray.copyOf()

    override fun linearCursor() = JvmIntNDArrayViewCursor(this)

    override fun cursor() = JvmIntNDArrayViewCursor(this)

    override fun toString() = defaultToString()

    override fun equals(other: Any?) = defaultEquals(other)

    override fun hashCode() = defaultHashCode()

}
