package tomasvolker.numeriko.legacy.core.jvm.int.arraynd

import tomasvolker.numeriko.legacy.core.interfaces.factory.intZeros
import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.legacy.core.interfaces.integer.arraynd.IntArrayNDCursor
import tomasvolker.numeriko.legacy.core.util.dimensionWidthArray
import tomasvolker.numeriko.legacy.core.util.incrementIndexArray
import tomasvolker.numeriko.legacy.core.util.viewIndexArrayToLinearIndex
/*
class JvmIntArrayNDViewCursor(override val array: JvmIntArrayNDView): IntArrayNDCursor {

    override val currentIndices = intZeros(array.rank)

    private val data = array.data

    private val shape = array.shapeArray

    private val widthArray: IntArray = dimensionWidthArray(array.shape, array.strideArray)

    private var linearIndex: Int = array.offset

    private val lastLinearIndex: Int = viewIndexArrayToLinearIndex(
            shapeArray = array.shapeArray,
            indexArray = IntArray(array.rank) { -1 },
            offset = array.offset,
            strideArray = array.strideArray
    )

    override fun hasNext() = cursorInBounds()

    override fun nextInt(): Int {
        val result = data[linearIndex]
        increment()
        return result
    }

    override fun nextInt(dimension: Int): Int {
        val result = data[linearIndex]
        linearIndex += widthArray[dimension]
        currentIndices[dimension] += 1
        return result
    }

    override fun previousInt(): Int {
        val result = data[linearIndex]
        decrement()
        return result
    }

    override fun previousInt(dimension: Int): Int {
        val result = data[linearIndex]
        linearIndex -= widthArray[dimension]
        currentIndices[dimension] -= 1
        return result
    }

    override fun setNextInt(value: Int) {
        data[linearIndex] = value
        increment()
    }

    override fun setNextInt(value: Int, dimension: Int) {
        data[linearIndex] = value
        linearIndex += widthArray[dimension]
        currentIndices[dimension] += 1
    }

    override fun setPreviousInt(value: Int) {
        data[linearIndex] = value
        decrement()
    }

    override fun setPreviousInt(value: Int, dimension: Int) {
        data[linearIndex] = value
        linearIndex -= widthArray[dimension]
        currentIndices[dimension] -= 1
    }

    override fun setPosition(vararg indices: Int) {
        linearIndex = viewIndexArrayToLinearIndex(
                shapeArray = array.shapeArray,
                offset = array.offset,
                strideArray = array.strideArray,
                indexArray = indices
        )
        TODO()
        //currentIndices.setAllInline { index ->  indices[index[0]] }
    }

    override fun setPosition(indexArray: ReadOnlyIntArray1D) {
        linearIndex = viewIndexArrayToLinearIndex(
                shapeArray = array.shapeArray,
                offset = array.offset,
                strideArray = array.strideArray,
                indexArray = indexArray
        )
        TODO()
        //currentIndices.setAllInline { index ->  indexArray[index[0]] }
    }

    override fun readInt() = data[linearIndex]

    override fun writeInt(value: Int) {
        data[linearIndex] = value
    }

    override fun increment() {
        incrementIndexArray(shape, currentIndices)
        linearIndex = viewIndexArrayToLinearIndex(
                shapeArray = shape,
                offset = array.offset,
                strideArray = array.strideArray,
                indexArray = currentIndices,
                checkRange = false
        )
    }

    override fun decrement() {
        incrementIndexArray(shape, currentIndices, amount = -1)
        linearIndex = viewIndexArrayToLinearIndex(
                shapeArray = shape,
                offset = array.offset,
                strideArray = array.strideArray,
                indexArray = currentIndices,
                checkRange = false
        )
    }

    override fun increment(dimension: Int) {
        linearIndex += widthArray[dimension]
        currentIndices[dimension] += 1
    }

    override fun decrement(dimension: Int) {
        linearIndex -= widthArray[dimension]
        currentIndices[dimension] -= 1
    }

    override fun incrementBy(amount: Int) {
        incrementIndexArray(shape, currentIndices, amount = amount)
        linearIndex = viewIndexArrayToLinearIndex(shape, array.offset, array.strideArray, currentIndices)
    }

    override fun decrementBy(amount: Int) {
        incrementIndexArray(shape, currentIndices, amount = amount)
        linearIndex = viewIndexArrayToLinearIndex(shape, array.offset, array.strideArray, currentIndices)
    }

    override fun moveToLast() {
        setPosition(*IntArray(array.rank) { -1 })
    }

    override fun incrementBy(dimension: Int, amount: Int) {
        linearIndex += amount * widthArray[dimension]
        currentIndices[dimension] += amount
    }

    override fun decrementBy(dimension: Int, amount: Int) {
        linearIndex -= amount * widthArray[dimension]
        currentIndices[dimension] -= amount
    }

    override fun cursorInBounds(): Boolean {

        for (i in currentIndices.indices) {
            val index = currentIndices[i]

            if (index !in 0 until shape[i])
                return false

        }

        return true
    }

}
*/