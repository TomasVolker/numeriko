package tomasvolker.numeriko.legacy.core.jvm.generic.arraynd

import tomasvolker.numeriko.legacy.core.interfaces.factory.intZeros
import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.ArrayNDCursor
import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.ArrayNDIterator
import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.ReadOnlyIntArray1D

import tomasvolker.numeriko.legacy.core.util.dimensionWidthArray
import tomasvolker.numeriko.legacy.core.util.incrementIndexArray
import tomasvolker.numeriko.legacy.core.util.indexArrayToLinearIndex
/*
class JvmArrayNDIterator<T>(override val array: JvmArrayND<T>): ArrayNDIterator<T> {

    private val data = array.data

    private var linearIndex: Int = 0

    override fun next() = data[linearIndex++]

    override fun previous() = data[linearIndex--]

    override fun setNext(value: T) {
        data[linearIndex] = value
        linearIndex++
    }

    override fun setPrevious(value: T) {
        data[linearIndex] = value
        linearIndex--
    }

    override fun read() = data[linearIndex]

    override fun write(value: T) {
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

class JvmArrayNDCursor<T>(override val array: JvmArrayND<T>): ArrayNDCursor<T> {

    override val currentIndices = intZeros(array.rank)

    private val data = array.data

    private val shape = array.shapeArray

    private val widthArray: IntArray = dimensionWidthArray(array.shape)

    private var linearIndex: Int = 0

    override fun hasNext() = 0 <= linearIndex && linearIndex < data.size

    override fun next(): T {
        val result = data[linearIndex++]
        incrementIndexArray(shape, currentIndices)
        return result
    }

    override fun next(dimension: Int): T {
        val result = data[linearIndex]
        linearIndex += widthArray[dimension]
        currentIndices[dimension] += 1
        return result
    }

    override fun previous(): T {
        val result = data[linearIndex--]
        incrementIndexArray(shape, currentIndices, amount = -1)
        return result
    }

    override fun previous(dimension: Int): T {
        val result = data[linearIndex]
        linearIndex -= widthArray[dimension]
        currentIndices[dimension] -= 1
        return result
    }

    override fun setNext(value: T) {
        data[linearIndex++] = value
        incrementIndexArray(shape, currentIndices)
    }

    override fun setNext(value: T, dimension: Int) {
        data[linearIndex] = value
        linearIndex += widthArray[dimension]
        currentIndices[dimension] += 1
    }

    override fun setPrevious(value: T) {
        data[linearIndex--] = value
        incrementIndexArray(shape, currentIndices, amount = -1)
    }

    override fun setPrevious(value: T, dimension: Int) {
        data[linearIndex] = value
        linearIndex -= widthArray[dimension]
        currentIndices[dimension] -= 1
    }

    override fun setPosition(vararg indices: Int) {
        linearIndex = indexArrayToLinearIndex(array.shapeArray, indices)
        currentIndices.setAllInline { index ->  indices[index[0]] }
    }

    override fun setPosition(indexArray: ReadOnlyIntArray1D) {
        linearIndex = indexArrayToLinearIndex(array.shapeArray, indexArray)
        currentIndices.setAllInline { index ->  indexArray[index[0]] }
    }

    override fun read() = data[linearIndex]

    override fun write(value: T) {
        data[linearIndex] = value
    }

    override fun increment() {
        linearIndex++
        incrementIndexArray(shape, currentIndices)
    }

    override fun decrement() {
        linearIndex--
        incrementIndexArray(shape, currentIndices, amount = -1)
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
        linearIndex += amount
        incrementIndexArray(shape, currentIndices, amount = amount)
    }

    override fun decrementBy(amount: Int) {
        linearIndex -= amount
        incrementIndexArray(shape, currentIndices, amount = -amount)
    }

    override fun moveToLast() {
        linearIndex = data.lastIndex
        currentIndices.setAllInline { shape[it[0]] - 1 }
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

        if (0 < linearIndex || data.size <= linearIndex)
            return false

        for (i in 0 until currentIndices.size) {
            val index = currentIndices[i]

            if (index < 0 || shape[i] <= index) return false
        }

        return true
    }

}
*/