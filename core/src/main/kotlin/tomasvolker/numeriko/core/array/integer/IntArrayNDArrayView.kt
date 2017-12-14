package tomasvolker.numeriko.core.array.integer

import tomasvolker.numeriko.core.array.index.AbstractIndex
import tomasvolker.numeriko.core.array.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.ReadOnlyNDArray
import tomasvolker.numeriko.core.interfaces.integer.IntNDArray
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.util.computeSizeFromShape
import tomasvolker.numeriko.core.util.viewIndexArrayToLinearIndex

class IntArrayNDArrayView internal constructor(
        internal val data: IntArray,
        internal val shapeArray: IntArray,
        internal val offsetArray: IntArray,
        internal val strideArray: IntArray,
        internal val viewShapeArray: IntArray,
        internal val collapseArray: BooleanArray
) : IntNDArray {

    init {

        require(data.size == computeSizeFromShape(shapeArray)) {
            "data size (${data.size}) does not match shape computed size (${computeSizeFromShape(shapeArray)})"
        }

        require(offsetArray.size == shapeArray.size) {
            "offsetArray size (${offsetArray.size}) does not match shape size (${shapeArray.size})"
        }

        require(strideArray.size == shapeArray.size) {
            "strideArray size (${strideArray.size}) does not match shape size (${shapeArray.size})"
        }

        require(viewShapeArray.size == shapeArray.size) {
            "viewShapeArray size (${viewShapeArray.size}) does not match shape size (${shapeArray.size})"
        }

        require(collapseArray.size == shapeArray.size) {
            "collapseArray size (${collapseArray.size}) does not match shape size (${shapeArray.size})"
        }

        for (i in shapeArray.indices) {

            require(0 < shapeArray[i]) {
                "shape in dimension $i cannot be negative (${shapeArray[i]})"
            }

            require(0 < strideArray[i]) {
                "stride in dimension $i cannot be negative (${strideArray[i]})"
            }

            require(0 < viewShapeArray[i]) {
                "view shape in dimension $i cannot be negative (${viewShapeArray[i]})"
            }

            require(0 <= offsetArray[i] &&
                    offsetArray[i] + strideArray[i] * viewShapeArray[i] <= shapeArray[i]) {
                "view exceeds array's size in dimension $i"
            }

            if (collapseArray[i]) {
                require(viewShapeArray[i] == 1) {
                    "collapsed dimension $i does not have size 1"
                }
            }

        }

    }

    override val shape: ReadOnlyIntNDArray
        get() = IntArrayNDArray(
                data = viewShapeArray.filterIndexed { index, i ->
                    println(1)
                    val a = !collapseArray[i]
                    println(2)
                    a
                }.toIntArray(),
                shapeArray = intArrayOf(dimension)
        )

    override val size: Int
        get() = computeSizeFromShape(viewShapeArray)

    override val dimension: Int
        get() = collapseArray.count { !it }

    override fun getInt(vararg indices: Int) =
            data[viewIndexArrayToLinearIndex(
                    shapeArray = shapeArray,
                    viewShapeArray = viewShapeArray,
                    offsetArray = offsetArray,
                    strideArray = strideArray,
                    collapsedArray = collapseArray,
                    indexArray = indices
            )]

    override fun getInt(indexArray: ReadOnlyIntNDArray) =
            data[viewIndexArrayToLinearIndex(
                    shapeArray = shapeArray,
                    viewShapeArray = viewShapeArray,
                    offsetArray = offsetArray,
                    strideArray = strideArray,
                    collapsedArray = collapseArray,
                    indexArray = indexArray
            )]

    override fun getView(vararg indices: Any): IntArrayNDArrayView {

        //TODO view indices

        require(indices.size == dimension) {
            "Wrong amount of indices (${indices.size} expected ${dimension})"
        }

        val offsetArray = IntArray(indices.size)
        val viewShapeArray = IntArray(indices.size)
        val strideArray = IntArray(indices.size)

        for (i in indices.indices) {

            var index: Any = indices[i]

            when (index) {
                is AbstractIndex -> {
                    index = index.computeValue(shape, i)
                }
                is IndexProgression -> {
                    index = index.computeProgression(shape, i)
                }
            }

            when (index) {
                is Int -> {
                    offsetArray[i] = this.offsetArray[i] + index * this.strideArray[i]
                    viewShapeArray[i] = 1
                    strideArray[i] = 1
                    collapseArray[i] = true
                }
                is IntProgression -> {
                    offsetArray[i] = this.offsetArray[i] + index.first * this.strideArray[i]
                    viewShapeArray[i] = index.count()
                    strideArray[i] = index.step * this.strideArray[i]
                }
                else -> throw IllegalArgumentException("Index $i is not an Int, IntRange, AbstractIndex or IndexProgression")
            }

        }

        return IntArrayNDArrayView(
                data = data,
                shapeArray = shapeArray.copyOf(),
                offsetArray = offsetArray,
                strideArray = strideArray,
                viewShapeArray = viewShapeArray,
                collapseArray = collapseArray.copyOf()
        )

    }

    override fun setInt(value: Int, vararg indices: Int) {
        data[viewIndexArrayToLinearIndex(
                shapeArray = shapeArray,
                viewShapeArray = viewShapeArray,
                offsetArray = offsetArray,
                strideArray = strideArray,
                collapsedArray = collapseArray,
                indexArray = indices
        )] = value
    }

    override fun setInt(value: ReadOnlyIntNDArray, vararg indices: Any) {
        TODO()
    }

    override fun setValue(value: ReadOnlyNDArray<Int>, vararg indices: Any) {
        TODO("not implemented")
    }

    override fun setInt(value: Int, indexArray: ReadOnlyIntNDArray) {
        data[viewIndexArrayToLinearIndex(
                shapeArray = shapeArray,
                viewShapeArray = viewShapeArray,
                offsetArray = offsetArray,
                strideArray = strideArray,
                collapsedArray = collapseArray,
                indexArray = indexArray
        )] = value
    }

    override fun collapse(dimension: Int) {

        val i = uncollapsedIndex(dimension)

        require(viewShapeArray[i] == 1) {
            "dimension to collapse $dimension does not have size 1"
        }

        collapseArray[i] = true

    }

    override fun collapseAll() {

        for (i in shapeArray.indices) {

            if (shapeArray[i] == 1) {
                collapseArray[i] = true
            }

        }

    }

    override fun copy() = IntArrayNDArrayView(
            data = data,
            shapeArray = shapeArray.copyOf(),
            offsetArray = offsetArray.copyOf(),
            strideArray = strideArray.copyOf(),
            viewShapeArray = viewShapeArray.copyOf(),
            collapseArray = collapseArray.copyOf()
    )

    override fun dataAsArray() = TODO("")

    override fun dataAsIntArray() = TODO("")

    override fun shapeAsArray() = viewShapeArray.copyOf()

    override fun linearCursor() = IntArrayNDArrayViewCursor(this)

    override fun cursor() = IntArrayNDArrayViewCursor(this)

    override fun toString() = defaultToString()

    override fun equals(other: Any?) = defaultEquals(other)

    override fun hashCode() = defaultHashCode()

    private fun uncollapsedIndex(collapsedIndex: Int): Int {

        var remaining = collapsedIndex

        for (i in collapseArray.indices) {

            if (!collapseArray[i]) {

                if(remaining == 0)
                    return i

                remaining--

            }

        }

        throw IndexOutOfBoundsException("index $collapsedIndex is out of bounds")
    }

}
