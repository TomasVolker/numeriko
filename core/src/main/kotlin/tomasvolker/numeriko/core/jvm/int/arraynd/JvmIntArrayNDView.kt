package tomasvolker.numeriko.core.jvm.int.arraynd

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.factory.intArray1DOf
import tomasvolker.numeriko.core.interfaces.generic.arraynd.*
import tomasvolker.numeriko.core.interfaces.integer.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.core.interfaces.integer.arraynd.IntArrayND
import tomasvolker.numeriko.core.interfaces.integer.arraynd.ReadOnlyIntArrayND
import tomasvolker.numeriko.core.util.checkRange
import tomasvolker.numeriko.core.util.computeSizeFromShape
import tomasvolker.numeriko.core.util.viewIndexArrayToLinearIndex

class JvmIntArrayNDView internal constructor(
        internal val data: IntArray,
        internal val offset: Int,
        internal val shapeArray: IntArray,
        internal val strideArray: IntArray
) : IntArrayND {

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

    override val shape: ReadOnlyIntArray1D
        get() = intArray1DOf(*shapeArray)

    override fun getInt(vararg indices: Int) =
            data[viewIndexArrayToLinearIndex(
                    shapeArray = shapeArray,
                    offset = offset,
                    strideArray = strideArray,
                    indexArray = indices
            )]

    override fun getInt(indexArray: ReadOnlyIntArray1D) =
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
    override fun setInt(value: ReadOnlyIntArrayND, vararg indices: Any) =
            getView(*indices).setAllInline { value.getInt(it) }

    //TODO set on itself
    override fun setValue(value: ReadOnlyArrayND<Int>, vararg indices: Any) =
            getView(*indices).setAllInline { value.getValue(it) }

    override fun setInt(value: Int, indexArray: ReadOnlyIntArray1D) {
        data[viewIndexArrayToLinearIndex(
                shapeArray = shapeArray,
                offset = offset,
                strideArray = strideArray,
                indexArray = indexArray
        )] = value
    }

    override fun getView(vararg indices: Any): JvmIntArrayNDView {

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

        return JvmIntArrayNDView(
                data = data,
                offset = offset,
                shapeArray = shapeList.toIntArray(),
                strideArray = strideList.toIntArray()
        )

    }

    override fun copy() = JvmIntArrayND(
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

    override fun linearCursor() = JvmIntArrayNDViewCursor(this)

    override fun cursor() = JvmIntArrayNDViewCursor(this)

    override fun toString() = defaultToString()

    override fun equals(other: Any?) = defaultEquals(other)

    override fun hashCode() = defaultHashCode()

}
