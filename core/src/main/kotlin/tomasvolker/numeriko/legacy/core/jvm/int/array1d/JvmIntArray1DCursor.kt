package tomasvolker.numeriko.legacy.core.jvm.int.array1d

import tomasvolker.numeriko.legacy.core.interfaces.factory.intZeros
import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.legacy.core.interfaces.integer.arraynd.*

import tomasvolker.numeriko.legacy.core.util.indexArrayToLinearIndex
/*
class JvmIntArrayNDIterator(override val array: JvmIntArray1D): IntArrayNDIterator {

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

    override fun cursorInBounds() = linearIndex in data.indices

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

class JvmIntArray1DCursor(override val array: JvmIntArray1D): IntArrayNDCursor {

    override val currentIndices = intZeros(1)

    private val data = array.data

    private var linearIndex: Int = 0

    override fun hasNext() = linearIndex in data.indices

    override fun nextInt(): Int {
        val result = data[linearIndex++]
        currentIndices[0] += 1
        return result
    }

    override fun nextInt(dimension: Int): Int {

        if (dimension != 0)
            throw IndexOutOfBoundsException("requested dimension $dimension out of rank 1")

        return nextInt()
    }

    override fun previousInt(): Int {
        val result = data[linearIndex--]
        currentIndices[0] -= 1
        return result
    }

    override fun previousInt(dimension: Int): Int {

        if (dimension != 0)
            throw IndexOutOfBoundsException("requested dimension $dimension out of rank 1")

        return previousInt()
    }

    override fun setPosition(vararg indices: Int) {
        linearIndex = indexArrayToLinearIndex(array.getShapeAsArray(), indices)
        currentIndices.setAllInline { index ->  indices[index[0]] }
    }

    override fun setPosition(indexArray: ReadOnlyIntArray1D) {
        linearIndex = indexArrayToLinearIndex(array.getShapeAsArray(), indexArray)
        currentIndices.setAllInline { index ->  indexArray[index[0]] }
    }

    override fun readInt() = data[linearIndex]

    override fun writeInt(value: Int) {
        data[linearIndex] = value
    }

    override fun increment() {
        linearIndex++
        currentIndices[0] += 1
    }

    override fun decrement() {
        linearIndex--
        currentIndices[0] -= 1
    }

    override fun increment(dimension: Int) {
        linearIndex += 1
        currentIndices[dimension] += 1
    }

    override fun decrement(dimension: Int) {
        linearIndex -= 1
        currentIndices[dimension] -= 1
    }

    override fun incrementBy(amount: Int) {
        linearIndex += amount
        currentIndices[0] += amount
    }

    override fun decrementBy(amount: Int) {
        linearIndex -= amount
        currentIndices[0] += amount
    }

    override fun moveToLast() {
        linearIndex = data.lastIndex
        currentIndices[0] = data.lastIndex
    }

    override fun incrementBy(dimension: Int, amount: Int) {
        linearIndex += amount
        currentIndices[dimension] += amount
    }

    override fun decrementBy(dimension: Int, amount: Int) {
        linearIndex -= amount
        currentIndices[dimension] -= amount
    }

    override fun cursorInBounds() = linearIndex in data.indices

}
*/