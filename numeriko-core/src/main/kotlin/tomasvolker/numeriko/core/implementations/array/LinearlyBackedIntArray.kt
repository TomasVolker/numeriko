package tomasvolker.numeriko.core.implementations.array

import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.MutableIntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndexed
import tomasvolker.numeriko.core.preconditions.requireRank
import tomasvolker.numeriko.core.preconditions.requireSameShape
import tomasvolker.numeriko.core.preconditions.requireValidIndices

interface LinearlyBackedIntArrayND<D>: LinearlyBackedArrayND<Int, D>, IntArrayND {
    
    fun linearGet(i: Int): Int

    override fun linearGetValue(i: Int): Int = linearGet(i)

    override fun get(): Int {
        requireRank(0)
        return linearGet(offset)
    }

    override operator fun get(i0: Int): Int {
        requireValidIndices(i0)
        return linearGet(convertIndices(i0))
    }

    override operator fun get(i0: Int, i1: Int): Int  {
        requireValidIndices(i0, i1)
        return linearGet(convertIndices(i0, i1))
    }

    override operator fun get(i0: Int, i1: Int, i2: Int): Int {
        requireValidIndices(i0, i1, i2)
        return linearGet(convertIndices(i0, i1, i2))
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Int {
        requireValidIndices(i0, i1, i2, i3)
        return linearGet(convertIndices(i0, i1, i2, i3))
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Int {
        requireValidIndices(i0, i1, i2, i3, i4)
        return linearGet(convertIndices(i0, i1, i2, i3, i4))
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Int {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        return linearGet(convertIndices(i0, i1, i2, i3, i4, i5))
    }

    override operator fun get(indices: IntArray): Int {
        requireValidIndices(indices)
        return linearGet(convertIndices(indices))
    }

    override fun getValue(indices: IntArray): Int = super<IntArrayND>.getValue(indices)

    override fun getInt(indices: IntArray): Int {
        requireValidIndices(indices)
        return linearGet(convertIndices(indices))
    }

}


interface LinearlyBackedMutableIntArrayND<D>: LinearlyBackedIntArrayND<D>, MutableIntArrayND {

    fun linearSet(i: Int, value: Int)
    
    fun linearSetValue(i: Int, value: Int) { linearSet(i, value) }

    override operator fun set(indices: IntArray, value: Int) {
        requireValidIndices(indices)
        linearSet(convertIndices(indices), value)
    }

    override operator fun set(i0: Int, value: Int) {
        requireValidIndices(i0)
        linearSet(convertIndices(i0), value)
    }

    override operator fun set(i0: Int, i1: Int, value: Int) {
        requireValidIndices(i0, i1)
        linearSet(convertIndices(i0, i1), value)
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, value: Int) {
        requireValidIndices(i0, i1, i2)
        linearSet(convertIndices(i0, i1, i2), value)
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Int) {
        requireValidIndices(i0, i1, i2, i3)
        linearSet(convertIndices(i0, i1, i2, i3), value)
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Int) {
        requireValidIndices(i0, i1, i2, i3, i4)
        linearSet(convertIndices(i0, i1, i2, i3, i4), value)
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Int) {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        linearSet(convertIndices(i0, i1, i2, i3, i4, i5), value)
    }

    override fun setInt(indices: IntArray, value: Int) {
        requireValidIndices(indices)
        linearSet(convertIndices(indices), value)
    }

    override fun setValue(value: IntArrayND) {
        requireSameShape(this, value)

        // Anti alias copy
        val source = if (value is LinearlyBackedIntArrayND<*> && value.data !== this.data)
            value
        else
            value.copy()

        source.unsafeForEachIndexed { indices, element ->
            setInt(indices, element)
        }

    }

}
