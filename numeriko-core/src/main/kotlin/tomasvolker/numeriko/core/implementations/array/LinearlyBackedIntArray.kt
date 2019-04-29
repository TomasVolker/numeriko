package tomasvolker.numeriko.core.implementations.array

import tomasvolker.numeriko.core.implementations.array.buffer.IntBuffer
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.MutableIntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndexed
import tomasvolker.numeriko.core.preconditions.requireRank
import tomasvolker.numeriko.core.preconditions.requireSameShape
import tomasvolker.numeriko.core.preconditions.requireValidIndices

interface LinearlyBackedIntArrayND<D: IntBuffer>: LinearlyBackedArrayND<Int, D>, IntArrayND {

    override fun get(): Int {
        requireRank(0)
        return buffer[offset]
    }

    override operator fun get(i0: Int): Int {
        requireValidIndices(i0)
        return buffer[convertIndices(i0)]
    }

    override operator fun get(i0: Int, i1: Int): Int  {
        requireValidIndices(i0, i1)
        return buffer[convertIndices(i0, i1)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int): Int {
        requireValidIndices(i0, i1, i2)
        return buffer[convertIndices(i0, i1, i2)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Int {
        requireValidIndices(i0, i1, i2, i3)
        return buffer[convertIndices(i0, i1, i2, i3)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Int {
        requireValidIndices(i0, i1, i2, i3, i4)
        return buffer[convertIndices(i0, i1, i2, i3, i4)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Int {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        return buffer[convertIndices(i0, i1, i2, i3, i4, i5)]
    }

    override operator fun get(indices: IntArray): Int {
        requireValidIndices(indices)
        return buffer[convertIndices(indices)]
    }

    override fun getValue(indices: IntArray): Int = super<IntArrayND>.getValue(indices)

    override fun getInt(indices: IntArray): Int {
        requireValidIndices(indices)
        return buffer[convertIndices(indices)]
    }

}


interface LinearlyBackedMutableIntArrayND<D: IntBuffer>: LinearlyBackedIntArrayND<D>, MutableIntArrayND {

    override operator fun set(indices: IntArray, value: Int) {
        requireValidIndices(indices)
        buffer[convertIndices(indices)] = value
    }

    override operator fun set(i0: Int, value: Int) {
        requireValidIndices(i0)
        buffer[convertIndices(i0)] = value
    }

    override operator fun set(i0: Int, i1: Int, value: Int) {
        requireValidIndices(i0, i1)
        buffer[convertIndices(i0, i1)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, value: Int) {
        requireValidIndices(i0, i1, i2)
        buffer[convertIndices(i0, i1, i2)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Int) {
        requireValidIndices(i0, i1, i2, i3)
        buffer[convertIndices(i0, i1, i2, i3)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Int) {
        requireValidIndices(i0, i1, i2, i3, i4)
        buffer[convertIndices(i0, i1, i2, i3, i4)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Int) {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        buffer[convertIndices(i0, i1, i2, i3, i4, i5)] = value
    }

    override fun setInt(indices: IntArray, value: Int) {
        requireValidIndices(indices)
        buffer[convertIndices(indices)] = value
    }

    override fun setValue(value: IntArrayND) {
        requireSameShape(this, value)

        // Anti alias copy
        val source = if (value is LinearlyBackedMutableArrayND<*, *> && value.buffer !== this.buffer)
            value
        else
            value.copy()

        if (value is LinearlyBackedIntArrayND<*>) {

            if (this.order == value.order) {
                value.buffer.copyInto(
                        destination = this.buffer,
                        destinationOffset = this.offset,
                        startIndex = value.offset,
                        endIndex = value.size
                )
                return
            }

        }

        source.unsafeForEachIndexed { indices, element ->
            setInt(indices, element)
        }

    }

}
