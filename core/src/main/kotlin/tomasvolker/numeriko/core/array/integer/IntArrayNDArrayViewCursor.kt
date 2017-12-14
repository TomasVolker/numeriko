package tomasvolker.numeriko.core.array.integer

import tomasvolker.numeriko.core.array.arrayNDArrayFactory
import tomasvolker.numeriko.core.interfaces.integer.*
import tomasvolker.numeriko.core.util.dimensionWidthArray
import tomasvolker.numeriko.core.util.incrementIndexArray
import tomasvolker.numeriko.core.util.indexArrayToLinearIndex
import tomasvolker.numeriko.core.util.viewIndexArrayToLinearIndex

//TODO Collapsed dimensions

class IntArrayNDArrayViewCursor(override val array: IntArrayNDArrayView): IntNDArrayCursor {

    override val currentIndexes = arrayNDArrayFactory.intArray(shape = array.indexShape)

    private val data = array.data

    private val shape: IntNDArray = array.viewShapeArray.toNDArray()

    private val widthArray: IntArray = dimensionWidthArray(array.shape, array.strideArray)

    private var linearIndex: Int = indexArrayToLinearIndex(
            shapeArray = array.shapeArray,
            indexArray = array.offsetArray
    )

    private val lastLinearIndex: Int = indexArrayToLinearIndex(
            shapeArray = array.shapeArray,
            indexArray = array.offsetArray.mapIndexed { i, offset -> offset + shape[i] * array.strideArray[i] - 1 }.toIntArray()
    )

    init {
        println("lastLinearIndex: $lastLinearIndex")
    }

    override fun hasNext() = linearIndex in 0..lastLinearIndex

    override fun nextInt(): Int {
        val result = data[linearIndex]
        linearIndex += widthArray.last()
        incrementIndexArray(shape, currentIndexes)
        return result
    }

    override fun nextInt(dimension: Int): Int {
        val result = data[linearIndex]
        linearIndex += widthArray[dimension]
        currentIndexes[dimension] += 1
        return result
    }

    override fun previousInt(): Int {
        val result = data[linearIndex]
        currentIndexes[-1] -= widthArray.last()
        incrementIndexArray(shape, currentIndexes, amount = -1)
        return result
    }

    override fun previousInt(dimension: Int): Int {
        val result = data[linearIndex]
        linearIndex -= widthArray[dimension]
        currentIndexes[dimension] -= 1
        return result
    }

    override fun setNextInt(value: Int) {
        data[linearIndex] = value
        linearIndex += widthArray.last()
        incrementIndexArray(shape, currentIndexes)
    }

    override fun setNextInt(value: Int, dimension: Int) {
        data[linearIndex] = value
        linearIndex += widthArray[dimension]
        currentIndexes[dimension] += 1
    }

    override fun setPreviousInt(value: Int) {
        data[linearIndex] = value
        linearIndex -= widthArray.last()
        incrementIndexArray(shape, currentIndexes, amount = -1)
    }

    override fun setPreviousInt(value: Int, dimension: Int) {
        data[linearIndex] = value
        linearIndex -= widthArray[dimension]
        currentIndexes[dimension] -= 1
    }

    override fun setPosition(vararg indexArray: Int) {
        linearIndex = viewIndexArrayToLinearIndex(
                array.shapeArray,
                array.viewShapeArray,
                array.offsetArray,
                array.strideArray,
                array.collapseArray,
                indexArray
        )
        currentIndexes.setAllInline { index ->  indexArray[index[0]] }
    }

    override fun setPosition(indexArray: ReadOnlyIntNDArray) {
        linearIndex = viewIndexArrayToLinearIndex(
                array.shapeArray,
                array.viewShapeArray,
                array.offsetArray,
                array.strideArray,
                array.collapseArray,
                indexArray.dataAsIntArray()
        )
        currentIndexes.setAllInline { index ->  indexArray[index[0]] }
    }

    override fun readInt() = data[linearIndex]

    override fun writeInt(value: Int) {
        data[linearIndex] = value
    }

    override fun increment() {
        linearIndex += widthArray.last()
        incrementIndexArray(shape, currentIndexes)
    }

    override fun decrement() {
        linearIndex -= widthArray.last()
        incrementIndexArray(shape, currentIndexes, amount = -1)
    }

    override fun increment(dimension: Int) {
        linearIndex += widthArray[dimension]
        currentIndexes[dimension] += 1
    }

    override fun decrement(dimension: Int) {
        linearIndex -= widthArray[dimension]
        currentIndexes[dimension] -= 1
    }

    override fun incrementBy(amount: Int) {
        linearIndex += amount * widthArray.last()
        incrementIndexArray(shape, currentIndexes, amount = amount)
    }

    override fun decrementBy(amount: Int) {
        linearIndex -= amount * widthArray.last()
        incrementIndexArray(shape, currentIndexes, amount = amount)
    }

    override fun moveToLast() {
        setPosition(*array.viewShapeArray.map { it - 1 }.toIntArray())
    }

    override fun incrementBy(dimension: Int, amount: Int) {
        linearIndex += amount * widthArray[dimension]
        currentIndexes[dimension] += amount
    }

    override fun decrementBy(dimension: Int, amount: Int) {
        linearIndex -= amount * widthArray[dimension]
        currentIndexes[dimension] -= amount
    }

    override fun cursorInBounds(): Boolean {

        for (i in 0 until currentIndexes.size) {
            val index = currentIndexes[i]

            if (index < 0 || shape[i] <= index)
                return false

        }

        return true
    }

}
