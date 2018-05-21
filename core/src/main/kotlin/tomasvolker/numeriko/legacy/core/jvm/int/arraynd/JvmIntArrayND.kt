package tomasvolker.numeriko.legacy.core.jvm.int.arraynd

import tomasvolker.numeriko.legacy.core.index.Index
import tomasvolker.numeriko.legacy.core.index.IndexProgression
import tomasvolker.numeriko.legacy.core.interfaces.factory.intArray1DOf
import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.ReadOnlyArrayND
import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.defaultEquals
import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.defaultToString
import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.legacy.core.interfaces.integer.arraynd.IntArrayND
import tomasvolker.numeriko.legacy.core.interfaces.integer.arraynd.ReadOnlyIntArrayND
import tomasvolker.numeriko.legacy.core.util.checkRange
import tomasvolker.numeriko.legacy.core.util.computeSizeFromShape
import tomasvolker.numeriko.legacy.core.util.dimensionWidthArray
import tomasvolker.numeriko.legacy.core.util.indexArrayToLinearIndex
/*
class JvmIntArrayND(
        val data: IntArray,
        internal var shapeArray: IntArray
) : IntArrayND {

    init {

        val requiredSize = computeSizeFromShape(shapeArray)

        require(data.size == requiredSize) {
            "data size (${data.size}) does not match shape size ($requiredSize)"
        }

    }

    override val shape: ReadOnlyIntArray1D
        get() = intArray1DOf(*shapeArray)

    override val size: Int
        get() = data.size

    override val rank: Int
        get() = shapeArray.size

    override fun getInt(vararg indices: Int) =
            data[indexArrayToLinearIndex(shapeArray, indices)]

    override fun getInt(indexArray: ReadOnlyIntArray1D) =
            data[indexArrayToLinearIndex(shapeArray, indexArray)]

    override fun setInt(value: Int, vararg indices: Int) {
        data[indexArrayToLinearIndex(shapeArray, indices)] = value
    }

    override fun setInt(value: Int, indexArray: ReadOnlyIntArray1D) {
        data[indexArrayToLinearIndex(shapeArray, indexArray)] = value
    }

    // TODO Setting on itself
    override fun setInt(value: ReadOnlyIntArrayND, vararg indices: Any) =
            getView(*indices).setAllInline { value.getInt(it) }

    // TODO Setting on itself
    override fun setValue(value: ReadOnlyArrayND<Int>, vararg indices: Any) =
            getView(*indices).setAllInline { value.getValue(it) }

    override fun getView(vararg indices: Any): JvmIntArrayNDView {

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
                    index = index.computeValue(shape.getInt(dimension))
                }
                is IndexProgression -> {
                    index = index.computeProgression(shape.getInt(dimension))
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

        return JvmIntArrayNDView(
                data = data,
                offset = offset,
                shapeArray = shapeList.toIntArray(),
                strideArray = strideList.toIntArray()
        )
    }

    override fun copy() = JvmIntArrayND(data.copyOf(), shapeArray.copyOf())

    override fun unsafeGetDataAsArray() = getDataAsArray()

    override fun unsafeGetDataAsIntArray() = data

    override fun unsafeGetShapeAsArray() = shapeArray

    override fun getDataAsArray() = data.toTypedArray()

    override fun getShapeAsArray() = shapeArray.copyOf()

    override fun equals(other: Any?): Boolean {
        when(other) {
            is JvmIntArrayND -> {
                return other.data.contentEquals(this.data) &&
                        other.shapeArray.contentEquals(other.shapeArray)
            }
            else -> return defaultEquals(other)
        }
    }

    override fun linearCursor() = JvmIntArrayNDIterator(this)

    override fun cursor() = DefaultIntArrayNDCursor(this)

    override fun hashCode() =
           31 * shapeArray.sumBy { acc, i ->  31 * acc + i.hashCode()} +
                   data.sumBy { acc, i -> 31 * acc + i.hashCode() }

    override fun toString() = defaultToString()

}
*/