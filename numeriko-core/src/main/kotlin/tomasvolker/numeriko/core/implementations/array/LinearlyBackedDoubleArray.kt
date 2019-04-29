package tomasvolker.numeriko.core.implementations.array

import tomasvolker.numeriko.core.implementations.array.buffer.DoubleBuffer
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndexed
import tomasvolker.numeriko.core.preconditions.requireRank
import tomasvolker.numeriko.core.preconditions.requireSameShape
import tomasvolker.numeriko.core.preconditions.requireValidIndices

interface LinearlyBackedDoubleArrayND<D: DoubleBuffer>: LinearlyBackedArrayND<Double, D>, DoubleArrayND {

    override fun get(): Double {
        requireRank(0)
        return buffer[offset]
    }

    override operator fun get(i0: Int): Double {
        requireValidIndices(i0)
        return buffer[convertIndices(i0)]
    }

    override operator fun get(i0: Int, i1: Int): Double  {
        requireValidIndices(i0, i1)
        return buffer[convertIndices(i0, i1)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int): Double {
        requireValidIndices(i0, i1, i2)
        return buffer[convertIndices(i0, i1, i2)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Double {
        requireValidIndices(i0, i1, i2, i3)
        return buffer[convertIndices(i0, i1, i2, i3)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Double {
        requireValidIndices(i0, i1, i2, i3, i4)
        return buffer[convertIndices(i0, i1, i2, i3, i4)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Double {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        return buffer[convertIndices(i0, i1, i2, i3, i4, i5)]
    }

    override operator fun get(indices: IntArray): Double {
        requireValidIndices(indices)
        return buffer[convertIndices(indices)]
    }

    override fun getValue(indices: IntArray): Double = super<DoubleArrayND>.getValue(indices)

    override fun getDouble(indices: IntArray): Double {
        requireValidIndices(indices)
        return buffer[convertIndices(indices)]
    }

}


interface LinearlyBackedMutableDoubleArrayND<D: DoubleBuffer>: LinearlyBackedDoubleArrayND<D>, MutableDoubleArrayND {

    override operator fun set(indices: IntArray, value: Double) {
        requireValidIndices(indices)
        buffer[convertIndices(indices)] = value
    }

    override operator fun set(i0: Int, value: Double) {
        requireValidIndices(i0)
        buffer[convertIndices(i0)] = value
    }

    override operator fun set(i0: Int, i1: Int, value: Double) {
        requireValidIndices(i0, i1)
        buffer[convertIndices(i0, i1)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, value: Double) {
        requireValidIndices(i0, i1, i2)
        buffer[convertIndices(i0, i1, i2)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Double) {
        requireValidIndices(i0, i1, i2, i3)
        buffer[convertIndices(i0, i1, i2, i3)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Double) {
        requireValidIndices(i0, i1, i2, i3, i4)
        buffer[convertIndices(i0, i1, i2, i3, i4)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Double) {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        buffer[convertIndices(i0, i1, i2, i3, i4, i5)] = value
    }

    override fun setDouble(indices: IntArray, value: Double) {
        requireValidIndices(indices)
        buffer[convertIndices(indices)] = value
    }

    override fun setValue(value: DoubleArrayND) {
        requireSameShape(this, value)

        // Anti alias copy
        val source = if (value is LinearlyBackedMutableArrayND<*, *> && value.buffer !== this.buffer)
            value
        else
            value.copy()

        if (value is LinearlyBackedDoubleArrayND<*>) {

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
            setDouble(indices, element)
        }

    }

}
