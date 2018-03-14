package tomasvolker.numeriko.core.jvm.int.array1d

import tomasvolker.numeriko.core.interfaces.factory.intZeros
import tomasvolker.numeriko.core.interfaces.generic.arraynd.setAllInline
import tomasvolker.numeriko.core.interfaces.int.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.core.jvm.factory.jvmArrayNDFactory
import tomasvolker.numeriko.core.interfaces.int.arraynd.*

import tomasvolker.numeriko.core.util.dimensionWidthArray
import tomasvolker.numeriko.core.util.incrementIndexArray
import tomasvolker.numeriko.core.util.indexArrayToLinearIndex

class JvmIntArrayNDLinearCursor(override val array: JvmIntArray1D): IntArrayNDLinearCursor {

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

    override val currentIndexes = intZeros(1)

    private val data = array.data

    private var linearIndex: Int = 0

    override fun hasNext() = linearIndex in data.indices

    override fun nextInt(): Int {
        val result = data[linearIndex++]
        currentIndexes[0] += 1
        return result
    }

    override fun nextInt(dimension: Int): Int {

        if (dimension != 0)
            throw IndexOutOfBoundsException("requested dimension $dimension out of rank 1")

        return nextInt()
    }

    override fun previousInt(): Int {
        val result = data[linearIndex--]
        currentIndexes[0] -= 1
        return result
    }

    override fun previousInt(dimension: Int): Int {

        if (dimension != 0)
            throw IndexOutOfBoundsException("requested dimension $dimension out of rank 1")

        return previousInt()
    }

    override fun setPosition(vararg indexArray: Int) {
        linearIndex = indexArrayToLinearIndex(array.getShapeAsArray(), indexArray)
        currentIndexes.setAllInline { index ->  indexArray[index[0]] }
    }

    override fun setPosition(indexArray: ReadOnlyIntArray1D) {
        linearIndex = indexArrayToLinearIndex(array.getShapeAsArray(), indexArray)
        currentIndexes.setAllInline { index ->  indexArray[index[0]] }
    }

    override fun readInt() = data[linearIndex]

    override fun writeInt(value: Int) {
        data[linearIndex] = value
    }

    override fun increment() {
        linearIndex++
        currentIndexes[0] += 1
    }

    override fun decrement() {
        linearIndex--
        currentIndexes[0] -= 1
    }

    override fun increment(dimension: Int) {
        linearIndex += 1
        currentIndexes[dimension] += 1
    }

    override fun decrement(dimension: Int) {
        linearIndex -= 1
        currentIndexes[dimension] -= 1
    }

    override fun incrementBy(amount: Int) {
        linearIndex += amount
        currentIndexes[0] += amount
    }

    override fun decrementBy(amount: Int) {
        linearIndex -= amount
        currentIndexes[0] += amount
    }

    override fun moveToLast() {
        linearIndex = data.lastIndex
        currentIndexes[0] = data.lastIndex
    }

    override fun incrementBy(dimension: Int, amount: Int) {
        linearIndex += amount
        currentIndexes[dimension] += amount
    }

    override fun decrementBy(dimension: Int, amount: Int) {
        linearIndex -= amount
        currentIndexes[dimension] -= amount
    }

    override fun cursorInBounds() = linearIndex in data.indices

}
