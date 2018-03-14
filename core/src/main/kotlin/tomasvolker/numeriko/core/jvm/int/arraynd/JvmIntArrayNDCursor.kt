package tomasvolker.numeriko.core.jvm.int.arraynd

import tomasvolker.numeriko.core.interfaces.factory.intZeros
import tomasvolker.numeriko.core.interfaces.generic.arraynd.setAllInline
import tomasvolker.numeriko.core.interfaces.int.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.core.jvm.factory.jvmArrayNDFactory
import tomasvolker.numeriko.core.interfaces.int.arraynd.*

import tomasvolker.numeriko.core.util.dimensionWidthArray
import tomasvolker.numeriko.core.util.incrementIndexArray
import tomasvolker.numeriko.core.util.indexArrayToLinearIndex

class JvmIntArrayNDLinearCursor(override val array: JvmIntArrayND): IntArrayNDLinearCursor {
    private val data = array.data

    private var linearIndex: Int = 0

    override fun nextInt() = data[linearIndex++]

    override fun previousInt() = data[linearIndex--]

    override fun setNextInt(value: Int) {
        data[linearIndex] = value
        linearIndex++
    }

    override fun setPreviousInt(value: Int) {
        data[linearIndex] = value
        linearIndex--
    }

    override fun readInt() = data[linearIndex]

    override fun writeInt(value: Int) {
        data[linearIndex] = value
    }

    override fun increment() {
        linearIndex++
    }

    override fun decrement() {
        linearIndex--
    }

    override fun cursorInBounds() = 0 <= linearIndex && linearIndex < data.size

    override fun incrementBy(amount: Int) {
        linearIndex += amount
    }

    override fun decrementBy(amount: Int) {
        linearIndex -= amount
    }

    override fun moveToLast() {
        linearIndex = data.lastIndex
    }

}

class JvmIntArrayNDCursor(override val array: JvmIntArrayND): IntArrayNDCursor {

    override val currentIndexes = intZeros(array.rank)

    private val data = array.data

    private val shape = array.shapeArray

    private val widthArray: IntArray = dimensionWidthArray(array.shape)

    private var linearIndex: Int = 0

    override fun hasNext() = 0 <= linearIndex && linearIndex < data.size

    override fun nextInt(): Int {
        val result = data[linearIndex++]
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
        val result = data[linearIndex--]
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
        data[linearIndex++] = value
        incrementIndexArray(shape, currentIndexes)
    }

    override fun setNextInt(value: Int, dimension: Int) {
        data[linearIndex] = value
        linearIndex += widthArray[dimension]
        currentIndexes[dimension] += 1
    }

    override fun setPreviousInt(value: Int) {
        data[linearIndex--] = value
        incrementIndexArray(shape, currentIndexes, amount = -1)
    }

    override fun setPreviousInt(value: Int, dimension: Int) {
        data[linearIndex] = value
        linearIndex -= widthArray[dimension]
        currentIndexes[dimension] -= 1
    }

    override fun setPosition(vararg indexArray: Int) {
        linearIndex = indexArrayToLinearIndex(array.shapeArray, indexArray)
        currentIndexes.setAllInline { index ->  indexArray[index[0]] }
    }

    override fun setPosition(indexArray: ReadOnlyIntArray1D) {
        linearIndex = indexArrayToLinearIndex(array.shapeArray, indexArray)
        currentIndexes.setAllInline { index ->  indexArray[index[0]] }
    }

    override fun readInt() = data[linearIndex]

    override fun writeInt(value: Int) {
        data[linearIndex] = value
    }

    override fun increment() {
        linearIndex++
        incrementIndexArray(shape, currentIndexes)
    }

    override fun decrement() {
        linearIndex--
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
        linearIndex += amount
        incrementIndexArray(shape, currentIndexes, amount = amount)
    }

    override fun decrementBy(amount: Int) {
        linearIndex -= amount
        incrementIndexArray(shape, currentIndexes, amount = -amount)
    }

    override fun moveToLast() {
        linearIndex = data.lastIndex
        currentIndexes.setAllInline { shape[it[0]] - 1 }
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

        if (0 < linearIndex || data.size <= linearIndex)
            return false

        for (i in 0 until currentIndexes.size) {
            val index = currentIndexes[i]

            if (index < 0 || shape[i] <= index) return false
        }

        return true
    }

}
