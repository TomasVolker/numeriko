package tomasvolker.numeriko.core.implementations.array

import tomasvolker.numeriko.core.implementations.array.buffer.Buffer
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndexed
import tomasvolker.numeriko.core.preconditions.requireRank
import tomasvolker.numeriko.core.preconditions.requireSameShape
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.view.linearIndex

interface LinearlyBackedArrayND<T, D: Buffer<T>>: ArrayND<T> {

    val buffer: D

    val offset: Int
    val strideArray: IntArray

    override fun getValue(): T {
        requireRank(0)
        return buffer.getValue(offset)
    }

    override fun getValue(i0: Int): T {
        requireValidIndices(i0)
        return buffer.getValue(convertIndices(i0))
    }

    override fun getValue(i0: Int, i1: Int): T  {
        requireValidIndices(i0, i1)
        return buffer.getValue(convertIndices(i0, i1))
    }

    override fun getValue(i0: Int, i1: Int, i2: Int): T {
        requireValidIndices(i0, i1, i2)
        return buffer.getValue(convertIndices(i0, i1, i2))
    }

    override fun getValue(i0: Int, i1: Int, i2: Int, i3: Int): T {
        requireValidIndices(i0, i1, i2, i3)
        return buffer.getValue(convertIndices(i0, i1, i2, i3))
    }

    override fun getValue(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): T {
        requireValidIndices(i0, i1, i2, i3, i4)
        return buffer.getValue(convertIndices(i0, i1, i2, i3, i4))
    }

    override fun getValue(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): T {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        return buffer.getValue(convertIndices(i0, i1, i2, i3, i4, i5))
    }

    override fun getValue(indices: IntArray): T {
        requireValidIndices(indices)
        return buffer.getValue(convertIndices(indices))
    }

    fun convertIndices(indices: IntArray): Int =
            linearIndex(offset, strideArray, indices)

    fun convertIndices(i0: Int): Int =
            linearIndex(offset, strideArray, i0)

    fun convertIndices(i0: Int, i1: Int): Int =
            linearIndex(offset, strideArray, i0, i1)

    fun convertIndices(i0: Int, i1: Int, i2: Int): Int =
            linearIndex(offset, strideArray, i0, i1, i2)

    fun convertIndices(i0: Int, i1: Int, i2: Int, i3: Int): Int =
            linearIndex(offset, strideArray, i0, i1, i2, i3)

    fun convertIndices(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Int =
            linearIndex(offset, strideArray, i0, i1, i2, i3, i4)

    fun convertIndices(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Int =
            linearIndex(offset, strideArray, i0, i1, i2, i3, i4, i5)

}


interface LinearlyBackedMutableArrayND<T, D: Buffer<T>>: LinearlyBackedArrayND<T, D>, MutableArrayND<T> {
    
    override fun setValue(indices: IntArray, value: T) {
        requireValidIndices(indices)
        buffer.setValue(convertIndices(indices), value)
    }

    override fun setValue(i0: Int, value: T) {
        requireValidIndices(i0)
        buffer.setValue(convertIndices(i0), value)
    }

    override fun setValue(i0: Int, i1: Int, value: T) {
        requireValidIndices(i0, i1)
        buffer.setValue(convertIndices(i0, i1), value)
    }

    override fun setValue(i0: Int, i1: Int, i2: Int, value: T) {
        requireValidIndices(i0, i1, i2)
        buffer.setValue(convertIndices(i0, i1, i2), value)
    }

    override fun setValue(i0: Int, i1: Int, i2: Int, i3: Int, value: T) {
        requireValidIndices(i0, i1, i2, i3)
        buffer.setValue(convertIndices(i0, i1, i2, i3), value)
    }

    override fun setValue(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: T) {
        requireValidIndices(i0, i1, i2, i3, i4)
        buffer.setValue(convertIndices(i0, i1, i2, i3, i4), value)
    }

    override fun setValue(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: T) {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        buffer.setValue(convertIndices(i0, i1, i2, i3, i4, i5), value)
    }

    override fun setValue(value: ArrayND<T>) {
        requireSameShape(this, value)

        // Anti alias copy
        val source = if (value is LinearlyBackedMutableArrayND<*, *> && value.buffer !== this.buffer)
            value
        else
            value.copy()

        source.unsafeForEachIndexed { indices, element ->
            setValue(indices, element)
        }

    }

}
