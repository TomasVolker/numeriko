package tomasvolker.numeriko.core.array.integer

import tomasvolker.numeriko.core.array.index.AbstractIndex
import tomasvolker.numeriko.core.array.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.NDArray
import tomasvolker.numeriko.core.interfaces.ReadOnlyNDArray
import tomasvolker.numeriko.core.interfaces.get
import tomasvolker.numeriko.core.interfaces.integer.IntNDArray
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.interfaces.integer.get
import tomasvolker.numeriko.core.util.computeSizeFromShape
import tomasvolker.numeriko.core.util.indexArrayToLinearIndex

class IntArrayNDArray(
        val data: IntArray,
        internal var shapeArray: IntArray
) : IntNDArray {

    init {

        require(data.size == computeSizeFromShape(shapeArray)) {
            "data size (${data.size}) does not match shape size (${computeSizeFromShape(shapeArray)})"
        }

    }

    override val shape: ReadOnlyIntNDArray
        get() = IntArrayNDArray(
                data = shapeArray,
                shapeArray = intArrayOf(dimension)
        )

    override val size: Int
        get() = data.size

    override val dimension: Int
        get() = shapeArray.size

    override fun getView(vararg indices: Any): IntArrayNDArrayView {

        //TODO view indices

        require(indices.size == dimension) {
            "Wrong amount of indices (${indices.size} expected $dimension)"
        }

        val offsetArray = IntArray(indices.size)
        val viewShapeArray = IntArray(indices.size)
        val strideArray = IntArray(indices.size)
        val collapseArray = BooleanArray(indices.size)

        for(i in indices.indices) {

            var index = indices[i]

            when(index) {
                is AbstractIndex -> {
                    index = index.computeValue(shape, i)
                }
                is IndexProgression -> {
                    index = index.computeProgression(shape, i)
                }
            }

            when(index) {
                is Int -> {
                    offsetArray[i] = index
                    viewShapeArray[i] = 1
                    strideArray[i] = 1
                    collapseArray[i] = true
                }
                is IntProgression -> {
                    offsetArray[i] = index.first
                    viewShapeArray[i] = index.count()
                    strideArray[i] = index.step
                }
                else -> throw IllegalArgumentException("Index $i is not an Int, IntProgression, AbstractIndex or IndexProgression")
            }

        }

        return IntArrayNDArrayView(
                data = data,
                shapeArray = shapeArray.copyOf(),
                offsetArray = offsetArray,
                strideArray = strideArray,
                collapseArray = collapseArray,
                viewShapeArray = viewShapeArray
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

    override fun collapse(dimension: Int) {

        if (shapeArray[dimension] == 1) {
            //TODO Optimize
            val indexList = shapeArray.indices.toMutableList()
            indexList.removeAt(dimension)
            shapeArray = shapeArray.sliceArray(indexList)
        }

    }

    override fun collapseAll() {
        shapeArray = shapeArray.filter { it == 1 }.toIntArray()
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
            is NDArray<*> -> return defaultEquals(other)
            else -> return false
        }
    }

    override fun linearCursor() = IntArrayNDArrayLinearCursor(this)

    override fun cursor() = IntArrayNDArrayCursor(this)

    override fun hashCode() =
           31 * shapeArray.reduce { acc, i ->  31 * acc + i.hashCode()} +
                   data.reduce { acc, i -> 31 * acc + i.hashCode() }

    override fun toString() = defaultToString()

}
