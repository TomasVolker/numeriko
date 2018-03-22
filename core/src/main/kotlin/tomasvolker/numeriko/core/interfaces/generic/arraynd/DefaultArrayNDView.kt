package tomasvolker.numeriko.core.interfaces.generic.arraynd

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.factory.intArray1DOf
import tomasvolker.numeriko.core.interfaces.generic.arraynd.*
import tomasvolker.numeriko.core.interfaces.integer.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.core.util.checkRange
import tomasvolker.numeriko.core.util.computeSizeFromShape
import tomasvolker.numeriko.core.util.viewIndexArrayToLinearIndex

class DefaultArrayNDView<T> internal constructor(
        val array: ArrayND<T>,
        offset: ReadOnlyIntArray1D,
        shape: ReadOnlyIntArray1D,
        stride: ReadOnlyIntArray1D
) : ArrayND<T> {

    init {

        //TODO prerequisites

    }

    private val offset: ReadOnlyIntArray1D = offset.copy()

    override val shape: ReadOnlyIntArray1D = shape.copy()

    private val stride: ReadOnlyIntArray1D = stride.copy()

    override fun getValue(indexArray: ReadOnlyIntArray1D) =
            array[indexArray.setValue {  }]

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

    override fun getView(vararg indices: Any): DefaultArrayNDView<T> {

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

        return DefaultArrayNDView(
                data = data,
                offset = offset,
                shapeArray = shapeList.toIntArray(),
                strideArray = strideList.toIntArray()
        )

    }

    override fun copy()

    override fun toString() = defaultToString()

    override fun equals(other: Any?) = defaultEquals(other)

    override fun hashCode() = defaultHashCode()

}
