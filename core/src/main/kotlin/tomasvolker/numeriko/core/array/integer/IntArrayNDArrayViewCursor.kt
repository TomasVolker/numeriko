package tomasvolker.numeriko.core.array.integer

import tomasvolker.numeriko.core.array.arrayNDArrayFactory
import tomasvolker.numeriko.core.interfaces.integer.*
import tomasvolker.numeriko.core.util.dimensionWidthArray
import tomasvolker.numeriko.core.util.incrementIndexArray
import tomasvolker.numeriko.core.util.indexArrayToLinearIndex
import tomasvolker.numeriko.core.util.viewIndexArrayToLinearIndex

class IntArrayNDArrayViewCursor(override val array: IntArrayNDArrayView): IntNDArrayCursor {

    override val currentIndexes = arrayNDArrayFactory.intArray(shape = array.indexShape)

    private val data = array.data

    private val shape = array.shape

    private val widthArray: IntArray = dimensionWidthArray(array.shape, array.strideArray)

    private var linearIndex: Int = array.offset

    private val lastLinearIndex: Int = viewIndexArrayToLinearIndex(
            shapeArray = array.shapeArray,
            indexArray = IntArray(array.rank) { -1 },
            offset = array.offset,
            strideArray = array.strideArray
    )

    override fun hasNext() = linearIndex in 0..lastLinearIndex

    override fun nextInt(): Int {
        TODO()
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
                shapeArray = array.shapeArray,
                offset = array.offset,
                strideArray = array.strideArray,
                indexArray = indexArray
        )
        currentIndexes.setAllInline { index ->  indexArray[index[0]] }
    }

    override fun setPosition(indexArray: ReadOnlyIntNDArray) {
        linearIndex = viewIndexArrayToLinearIndex(
                shapeArray = array.shapeArray,
                offset = array.offset,
                strideArray = array.strideArray,
                indexArray = indexArray
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
        setPosition(*array.shapeArray.map { it - 1 }.toIntArray())
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

        for (i in currentIndexes.indices) {
            val index = currentIndexes[i]

            if (index !in 0 until index)
                return false

        }

        return true
    }

}
