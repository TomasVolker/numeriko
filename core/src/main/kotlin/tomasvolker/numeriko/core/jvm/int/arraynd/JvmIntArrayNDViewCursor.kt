package tomasvolker.numeriko.core.jvm.int.arraynd

import tomasvolker.numeriko.core.jvm.factory.jvmArrayNDFactory
import tomasvolker.numeriko.core.interfaces.int.arraynd.IntArrayNDCursor
import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntArrayND
import tomasvolker.numeriko.core.interfaces.int.arraynd.get
import tomasvolker.numeriko.core.interfaces.int.arraynd.set
import tomasvolker.numeriko.core.util.dimensionWidthArray
import tomasvolker.numeriko.core.util.incrementIndexArray
import tomasvolker.numeriko.core.util.viewIndexArrayToLinearIndex

class JvmIntArrayNDViewCursor(override val array: JvmIntArrayNDView): IntArrayNDCursor {

    override val currentIndexes = jvmArrayNDFactory.intZeros(shape = array.indexShape)

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
        currentIndexes[dimension] += 1
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
        currentIndexes[dimension] -= 1
        return result
    }

    override fun setNextInt(value: Int) {
        data[linearIndex] = value
        increment()
    }

    override fun setNextInt(value: Int, dimension: Int) {
        data[linearIndex] = value
        linearIndex += widthArray[dimension]
        currentIndexes[dimension] += 1
    }

    override fun setPreviousInt(value: Int) {
        data[linearIndex] = value
        decrement()
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

    override fun setPosition(indexArray: ReadOnlyIntArrayND) {
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
        incrementIndexArray(shape, currentIndexes)
        linearIndex = viewIndexArrayToLinearIndex(
                shapeArray = shape,
                offset = array.offset,
                strideArray = array.strideArray,
                indexArray = currentIndexes,
                checkRange = false
        )
    }

    override fun decrement() {
        incrementIndexArray(shape, currentIndexes, amount = -1)
        linearIndex = viewIndexArrayToLinearIndex(
                shapeArray = shape,
                offset = array.offset,
                strideArray = array.strideArray,
                indexArray = currentIndexes,
                checkRange = false
        )
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
        incrementIndexArray(shape, currentIndexes, amount = amount)
        linearIndex = viewIndexArrayToLinearIndex(shape, array.offset, array.strideArray, currentIndexes)
    }

    override fun decrementBy(amount: Int) {
        incrementIndexArray(shape, currentIndexes, amount = amount)
        linearIndex = viewIndexArrayToLinearIndex(shape, array.offset, array.strideArray, currentIndexes)
    }

    override fun moveToLast() {
        setPosition(*IntArray(array.rank) { -1 })
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

            if (index !in 0 until shape[i])
                return false

        }

        return true
    }

}
