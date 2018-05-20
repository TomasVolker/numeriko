package tomasvolker.numeriko.legacy.core.jvm.generic.arraynd

import tomasvolker.numeriko.legacy.core.index.Index
import tomasvolker.numeriko.legacy.core.index.IndexProgression
import tomasvolker.numeriko.legacy.core.interfaces.factory.intArray1DOf
import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.*
import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.legacy.core.util.checkRange
import tomasvolker.numeriko.legacy.core.util.computeSizeFromShape
import tomasvolker.numeriko.legacy.core.util.viewIndexArrayToLinearIndex
/*
class JvmArrayNDView<T> internal constructor(
        internal val data: Array<T>,
        internal val offset: Int,
        internal val shapeArray: IntArray,
        internal val strideArray: IntArray
) : ArrayND<T> {

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

    override fun getValue(vararg indices: Int) =
            data[viewIndexArrayToLinearIndex(
                    shapeArray = shapeArray,
                    offset = offset,
                    strideArray = strideArray,
                    indexArray = indices
            )]

    override fun getValue(indexArray: ReadOnlyIntArray1D) =
            data[viewIndexArrayToLinearIndex(
                    shapeArray = shapeArray,
                    offset = offset,
                    strideArray = strideArray,
                    indexArray = indexArray
            )]

    override fun setValue(value: T, vararg indices: Int) {
        data[viewIndexArrayToLinearIndex(
                shapeArray = shapeArray,
                offset = offset,
                strideArray = strideArray,
                indexArray = indices
        )] = value
    }

    //TODO set on itself
    override fun setValue(value: ReadOnlyArrayND<T>, vararg indices: Any) =
            getView(*indices).setAllInline { value.getValue(it) }

    override fun setValue(value: T, indexArray: ReadOnlyIntArray1D) {
        data[viewIndexArrayToLinearIndex(
                shapeArray = shapeArray,
                offset = offset,
                strideArray = strideArray,
                indexArray = indexArray
        )] = value
    }

    override fun getView(vararg indices: Any): JvmArrayNDView<T> {

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

        return JvmArrayNDView(
                data = data,
                offset = offset,
                shapeArray = shapeList.toIntArray(),
                strideArray = strideList.toIntArray()
        )

    }

    override fun copy() = JvmArrayND(
            data = getDataAsArray(),
            shapeArray = shapeArray.copyOf()
    )

    override fun unsafeGetDataAsArray(): Array<T> = getDataAsArray()

    override fun getDataAsArray(): Array<T> {
        val result = Array<Any?>(size) { null }

        for ((i, x) in this.withIndex()) {
            result[i] = x
        }

        return result as Array<T>
    }

    override fun unsafeGetShapeAsArray() = shapeArray

    override fun getShapeAsArray() = shapeArray.copyOf()

    override fun iterator() = JvmArrayNDViewIterator(this)

    override fun cursor() = JvmArrayNDViewCursor(this)

    override fun toString() = defaultToString()

    override fun equals(other: Any?) = defaultEquals(other)

    override fun hashCode() = defaultHashCode()

}
*/