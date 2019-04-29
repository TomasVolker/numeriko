package tomasvolker.numeriko.core.implementations.array

import tomasvolker.numeriko.core.implementations.array.buffer.FloatBuffer
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.MutableFloatArrayND
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndexed
import tomasvolker.numeriko.core.preconditions.requireRank
import tomasvolker.numeriko.core.preconditions.requireSameShape
import tomasvolker.numeriko.core.preconditions.requireValidIndices

interface LinearlyBackedFloatArrayND<D: FloatBuffer>: LinearlyBackedArrayND<Float, D>, FloatArrayND {

    override fun get(): Float {
        requireRank(0)
        return buffer[offset]
    }

    override operator fun get(i0: Int): Float {
        requireValidIndices(i0)
        return buffer[convertIndices(i0)]
    }

    override operator fun get(i0: Int, i1: Int): Float  {
        requireValidIndices(i0, i1)
        return buffer[convertIndices(i0, i1)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int): Float {
        requireValidIndices(i0, i1, i2)
        return buffer[convertIndices(i0, i1, i2)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Float {
        requireValidIndices(i0, i1, i2, i3)
        return buffer[convertIndices(i0, i1, i2, i3)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Float {
        requireValidIndices(i0, i1, i2, i3, i4)
        return buffer[convertIndices(i0, i1, i2, i3, i4)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Float {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        return buffer[convertIndices(i0, i1, i2, i3, i4, i5)]
    }

    override operator fun get(indices: IntArray): Float {
        requireValidIndices(indices)
        return buffer[convertIndices(indices)]
    }

    override fun getValue(indices: IntArray): Float = super<FloatArrayND>.getValue(indices)

    override fun getFloat(indices: IntArray): Float {
        requireValidIndices(indices)
        return buffer[convertIndices(indices)]
    }

}


interface LinearlyBackedMutableFloatArrayND<D: FloatBuffer>: LinearlyBackedFloatArrayND<D>, MutableFloatArrayND {

    override operator fun set(indices: IntArray, value: Float) {
        requireValidIndices(indices)
        buffer[convertIndices(indices)] = value
    }

    override operator fun set(i0: Int, value: Float) {
        requireValidIndices(i0)
        buffer[convertIndices(i0)] = value
    }

    override operator fun set(i0: Int, i1: Int, value: Float) {
        requireValidIndices(i0, i1)
        buffer[convertIndices(i0, i1)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, value: Float) {
        requireValidIndices(i0, i1, i2)
        buffer[convertIndices(i0, i1, i2)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Float) {
        requireValidIndices(i0, i1, i2, i3)
        buffer[convertIndices(i0, i1, i2, i3)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Float) {
        requireValidIndices(i0, i1, i2, i3, i4)
        buffer[convertIndices(i0, i1, i2, i3, i4)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Float) {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        buffer[convertIndices(i0, i1, i2, i3, i4, i5)] = value
    }

    override fun setFloat(indices: IntArray, value: Float) {
        requireValidIndices(indices)
        buffer[convertIndices(indices)] = value
    }

    override fun setValue(value: FloatArrayND) {
        requireSameShape(this, value)

        // Anti alias copy
        val source = if (value is LinearlyBackedFloatArrayND<*> && value.buffer !== this.buffer)
            value
        else
            value.copy()

        source.unsafeForEachIndexed { indices, element ->
            setFloat(indices, element)
        }

    }

}
