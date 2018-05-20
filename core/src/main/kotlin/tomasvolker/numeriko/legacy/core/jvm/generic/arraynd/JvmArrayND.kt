package tomasvolker.numeriko.legacy.core.jvm.generic.arraynd

import tomasvolker.numeriko.legacy.core.index.Index
import tomasvolker.numeriko.legacy.core.index.IndexProgression
import tomasvolker.numeriko.legacy.core.interfaces.factory.intArray1DOf
import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.*
import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.legacy.core.util.checkRange
import tomasvolker.numeriko.legacy.core.util.computeSizeFromShape
import tomasvolker.numeriko.legacy.core.util.dimensionWidthArray
import tomasvolker.numeriko.legacy.core.util.indexArrayToLinearIndex
/*
class JvmArrayND<T>(
        val data: Array<T>,
        internal var shapeArray: IntArray
) : ArrayND<T> {

    init {

        val requiredSize = TODO() // computeSizeFromShape(shapeArray)

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

    override fun getValue(vararg indices: Int) =
            data[indexArrayToLinearIndex(shapeArray, indices)]

    override fun getValue(indexArray: ReadOnlyIntArray1D) =
            data[indexArrayToLinearIndex(shapeArray, indexArray)]

    override fun setValue(value: T, vararg indices: Int) {
        data[indexArrayToLinearIndex(shapeArray, indices)] = value
    }

    override fun setValue(value: T, indexArray: ReadOnlyIntArray1D) {
        data[indexArrayToLinearIndex(shapeArray, indexArray)] = value
    }

    // TODO Setting on itself
    override fun setValue(value: ReadOnlyArrayND<T>, vararg indices: Any) =
            TODO()//getView(*indices).setAllInline { value.getValue(it) }

    override fun getView(vararg indices: Any): JvmArrayNDView<T> {

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

        return JvmArrayNDView(
                data = data,
                offset = offset,
                shapeArray = shapeList.toIntArray(),
                strideArray = strideList.toIntArray()
        )
    }

    override fun copy() = JvmArrayND(data.copyOf(), shapeArray.copyOf())

    fun unsafeGetDataAsArray() = data

    fun unsafeGetShapeAsArray() = shapeArray

    fun getDataAsArray() = data.copyOf()

    fun getShapeAsArray() = shapeArray.copyOf()

    override fun equals(other: Any?): Boolean {
        when(other) {
            is JvmArrayND<*> -> {
                return other.data.contentEquals(this.data) &&
                        other.shapeArray.contentEquals(other.shapeArray)
            }
            else -> return defaultEquals(other)
        }
    }

    override fun cursor() = JvmArrayNDCursor(this)

    override fun hashCode() =
           31 * shapeArray.reduce { acc, i ->  31 * acc + i.hashCode()} +
                   data.asSequence().map { it?.hashCode() ?: 0 }.reduce { acc, i -> 31 * acc + i.hashCode() }

    override fun toString() = defaultToString()

}
*/