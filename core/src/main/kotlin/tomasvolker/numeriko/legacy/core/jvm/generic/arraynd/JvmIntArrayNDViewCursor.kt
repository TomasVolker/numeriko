package tomasvolker.numeriko.legacy.core.jvm.generic.arraynd

import tomasvolker.numeriko.legacy.core.interfaces.factory.intArray1D
import tomasvolker.numeriko.legacy.core.interfaces.factory.intZeros
import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.ArrayNDCursor
import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.legacy.core.util.dimensionWidthArray
import tomasvolker.numeriko.legacy.core.util.incrementIndexArray
import tomasvolker.numeriko.legacy.core.util.viewIndexArrayToLinearIndex
/*
class JvmArrayNDViewCursor<T>(override val array: JvmArrayNDView<T>): ArrayNDCursor<T> {

    val indices = intZeros(array.rank)

    override val currentIndices: ReadOnlyIntArray1D get() = indices

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

    override fun setPosition(indexArray: ReadOnlyIntArray1D) {
        linearIndex = viewIndexArrayToLinearIndex(
                shapeArray = array.shapeArray,
                offset = array.offset,
                strideArray = array.strideArray,
                indexArray = indexArray
        )
        indices.setAllInline { index ->  indexArray[index[0]] }
    }

    override fun read() = data[linearIndex]

    override fun write(value: T) {
        data[linearIndex] = value
    }

    override fun increment() {
        incrementIndexArray(shape, indices)
        linearIndex = viewIndexArrayToLinearIndex(
                shapeArray = shape,
                offset = array.offset,
                strideArray = array.strideArray,
                indexArray = currentIndices,
                checkRange = false
        )
    }

    override fun decrement() {
        incrementIndexArray(shape, indices, amount = -1)
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
        indices[dimension] += 1
    }

    override fun decrement(dimension: Int) {
        linearIndex -= widthArray[dimension]
        indices[dimension] -= 1
    }

    override fun incrementBy(amount: Int) {
        incrementIndexArray(shape, indices, amount = amount)
        linearIndex = viewIndexArrayToLinearIndex(shape, array.offset, array.strideArray, indices)
    }

    override fun decrementBy(amount: Int) {
        incrementIndexArray(shape, indices, amount = amount)
        linearIndex = viewIndexArrayToLinearIndex(shape, array.offset, array.strideArray, indices)
    }

    override fun moveToLast() {
        setPosition(intArray1D(array.rank) { -1 })
    }

    override fun incrementBy(dimension: Int, amount: Int) {
        linearIndex += amount * widthArray[dimension]
        indices[dimension] += amount
    }

    override fun decrementBy(dimension: Int, amount: Int) {
        linearIndex -= amount * widthArray[dimension]
        indices[dimension] -= amount
    }

    override fun cursorInBounds(): Boolean {

        for (i in indices.indices) {
            val index = indices[i]

            if (index !in 0 until shape[i])
                return false

        }

        return true
    }

}
*/