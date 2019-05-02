package tomasvolker.numeriko.core.implementations.array

import tomasvolker.numeriko.core.implementations.array.buffer.ByteBuffer
import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.byte.MutableByteArrayND
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndexed
import tomasvolker.numeriko.core.preconditions.requireRank
import tomasvolker.numeriko.core.preconditions.requireSameShape
import tomasvolker.numeriko.core.preconditions.requireValidIndices

interface LinearlyBackedByteArrayND<D: ByteBuffer>: LinearlyBackedArrayND<Byte, D>, ByteArrayND {

    override fun get(): Byte {
        requireRank(0)
        return buffer[offset]
    }

    override operator fun get(i0: Int): Byte {
        requireValidIndices(i0)
        return buffer[convertIndices(i0)]
    }

    override operator fun get(i0: Int, i1: Int): Byte  {
        requireValidIndices(i0, i1)
        return buffer[convertIndices(i0, i1)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int): Byte {
        requireValidIndices(i0, i1, i2)
        return buffer[convertIndices(i0, i1, i2)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Byte {
        requireValidIndices(i0, i1, i2, i3)
        return buffer[convertIndices(i0, i1, i2, i3)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Byte {
        requireValidIndices(i0, i1, i2, i3, i4)
        return buffer[convertIndices(i0, i1, i2, i3, i4)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Byte {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        return buffer[convertIndices(i0, i1, i2, i3, i4, i5)]
    }

    override operator fun get(indices: IntArray): Byte {
        requireValidIndices(indices)
        return buffer[convertIndices(indices)]
    }

    override fun getValue(indices: IntArray): Byte = super<ByteArrayND>.getValue(indices)

    override fun getByte(indices: IntArray): Byte {
        requireValidIndices(indices)
        return buffer[convertIndices(indices)]
    }

}


interface LinearlyBackedMutableByteArrayND<D: ByteBuffer>: LinearlyBackedByteArrayND<D>, MutableByteArrayND {

    override operator fun set(indices: IntArray, value: Byte) {
        requireValidIndices(indices)
        buffer[convertIndices(indices)] = value
    }

    override operator fun set(i0: Int, value: Byte) {
        requireValidIndices(i0)
        buffer[convertIndices(i0)] = value
    }

    override operator fun set(i0: Int, i1: Int, value: Byte) {
        requireValidIndices(i0, i1)
        buffer[convertIndices(i0, i1)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, value: Byte) {
        requireValidIndices(i0, i1, i2)
        buffer[convertIndices(i0, i1, i2)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Byte) {
        requireValidIndices(i0, i1, i2, i3)
        buffer[convertIndices(i0, i1, i2, i3)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Byte) {
        requireValidIndices(i0, i1, i2, i3, i4)
        buffer[convertIndices(i0, i1, i2, i3, i4)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Byte) {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        buffer[convertIndices(i0, i1, i2, i3, i4, i5)] = value
    }

    override fun setByte(indices: IntArray, value: Byte) {
        requireValidIndices(indices)
        buffer[convertIndices(indices)] = value
    }

    override fun setValue(value: ByteArrayND) {
        requireSameShape(this, value)

        // Anti alias copy
        val source = if (value is LinearlyBackedMutableArrayND<*, *> && value.buffer !== this.buffer)
            value
        else
            value.copy()

        if (value is LinearlyBackedByteArrayND<*>) {

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
            setByte(indices, element)
        }

    }

}
