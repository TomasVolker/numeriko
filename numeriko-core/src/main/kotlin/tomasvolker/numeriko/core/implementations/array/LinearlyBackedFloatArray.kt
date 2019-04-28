package tomasvolker.numeriko.core.implementations.array

import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.MutableFloatArrayND
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndexed
import tomasvolker.numeriko.core.preconditions.requireRank
import tomasvolker.numeriko.core.preconditions.requireSameShape
import tomasvolker.numeriko.core.preconditions.requireValidIndices

interface LinearlyBackedFloatArrayND<D>: LinearlyBackedArrayND<Float, D>, FloatArrayND {
    
    fun linearGet(i: Int): Float

    override fun linearGetValue(i: Int): Float = linearGet(i)

    override fun get(): Float {
        requireRank(0)
        return linearGet(offset)
    }

    override operator fun get(i0: Int): Float {
        requireValidIndices(i0)
        return linearGet(convertIndices(i0))
    }

    override operator fun get(i0: Int, i1: Int): Float  {
        requireValidIndices(i0, i1)
        return linearGet(convertIndices(i0, i1))
    }

    override operator fun get(i0: Int, i1: Int, i2: Int): Float {
        requireValidIndices(i0, i1, i2)
        return linearGet(convertIndices(i0, i1, i2))
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Float {
        requireValidIndices(i0, i1, i2, i3)
        return linearGet(convertIndices(i0, i1, i2, i3))
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Float {
        requireValidIndices(i0, i1, i2, i3, i4)
        return linearGet(convertIndices(i0, i1, i2, i3, i4))
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Float {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        return linearGet(convertIndices(i0, i1, i2, i3, i4, i5))
    }

    override operator fun get(indices: IntArray): Float {
        requireValidIndices(indices)
        return linearGet(convertIndices(indices))
    }

    override fun getValue(indices: IntArray): Float = super<FloatArrayND>.getValue(indices)

    override fun getFloat(indices: IntArray): Float {
        requireValidIndices(indices)
        return linearGet(convertIndices(indices))
    }

}


interface LinearlyBackedMutableFloatArrayND<D>: LinearlyBackedFloatArrayND<D>, MutableFloatArrayND {

    fun linearSet(i: Int, value: Float)
    
    fun linearSetValue(i: Int, value: Float) { linearSet(i, value) }

    override operator fun set(indices: IntArray, value: Float) {
        requireValidIndices(indices)
        linearSet(convertIndices(indices), value)
    }

    override operator fun set(i0: Int, value: Float) {
        requireValidIndices(i0)
        linearSet(convertIndices(i0), value)
    }

    override operator fun set(i0: Int, i1: Int, value: Float) {
        requireValidIndices(i0, i1)
        linearSet(convertIndices(i0, i1), value)
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, value: Float) {
        requireValidIndices(i0, i1, i2)
        linearSet(convertIndices(i0, i1, i2), value)
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Float) {
        requireValidIndices(i0, i1, i2, i3)
        linearSet(convertIndices(i0, i1, i2, i3), value)
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Float) {
        requireValidIndices(i0, i1, i2, i3, i4)
        linearSet(convertIndices(i0, i1, i2, i3, i4), value)
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Float) {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        linearSet(convertIndices(i0, i1, i2, i3, i4, i5), value)
    }

    override fun setFloat(indices: IntArray, value: Float) {
        requireValidIndices(indices)
        linearSet(convertIndices(indices), value)
    }

    override fun setValue(value: FloatArrayND) {
        requireSameShape(this, value)

        // Anti alias copy
        val source = if (value is LinearlyBackedFloatArrayND<*> && value.data !== this.data)
            value
        else
            value.copy()

        source.unsafeForEachIndexed { indices, element ->
            setFloat(indices, element)
        }

    }

}
