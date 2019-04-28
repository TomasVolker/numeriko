package tomasvolker.numeriko.core.implementations.array

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndexed
import tomasvolker.numeriko.core.preconditions.requireRank
import tomasvolker.numeriko.core.preconditions.requireSameShape
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.view.linearIndex

interface LinearlyBackedDoubleArrayND<D>: LinearlyBackedArrayND<Double, D>, DoubleArrayND {
    
    fun linearGet(i: Int): Double

    override fun linearGetValue(i: Int): Double = linearGet(i)

    override fun get(): Double {
        requireRank(0)
        return linearGet(offset)
    }

    override operator fun get(i0: Int): Double {
        requireValidIndices(i0)
        return linearGet(convertIndices(i0))
    }

    override operator fun get(i0: Int, i1: Int): Double  {
        requireValidIndices(i0, i1)
        return linearGet(convertIndices(i0, i1))
    }

    override operator fun get(i0: Int, i1: Int, i2: Int): Double {
        requireValidIndices(i0, i1, i2)
        return linearGet(convertIndices(i0, i1, i2))
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Double {
        requireValidIndices(i0, i1, i2, i3)
        return linearGet(convertIndices(i0, i1, i2, i3))
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Double {
        requireValidIndices(i0, i1, i2, i3, i4)
        return linearGet(convertIndices(i0, i1, i2, i3, i4))
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Double {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        return linearGet(convertIndices(i0, i1, i2, i3, i4, i5))
    }

    override operator fun get(indices: IntArray): Double {
        requireValidIndices(indices)
        return linearGet(convertIndices(indices))
    }

    override fun getValue(indices: IntArray): Double = super<DoubleArrayND>.getValue(indices)

    override fun getDouble(indices: IntArray): Double {
        requireValidIndices(indices)
        return linearGet(convertIndices(indices))
    }

}


interface LinearlyBackedMutableDoubleArrayND<D>: LinearlyBackedDoubleArrayND<D>, MutableDoubleArrayND {

    fun linearSet(i: Int, value: Double)
    
    fun linearSetValue(i: Int, value: Double) { linearSet(i, value) }

    override operator fun set(indices: IntArray, value: Double) {
        requireValidIndices(indices)
        linearSet(convertIndices(indices), value)
    }

    override operator fun set(i0: Int, value: Double) {
        requireValidIndices(i0)
        linearSet(convertIndices(i0), value)
    }

    override operator fun set(i0: Int, i1: Int, value: Double) {
        requireValidIndices(i0, i1)
        linearSet(convertIndices(i0, i1), value)
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, value: Double) {
        requireValidIndices(i0, i1, i2)
        linearSet(convertIndices(i0, i1, i2), value)
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Double) {
        requireValidIndices(i0, i1, i2, i3)
        linearSet(convertIndices(i0, i1, i2, i3), value)
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Double) {
        requireValidIndices(i0, i1, i2, i3, i4)
        linearSet(convertIndices(i0, i1, i2, i3, i4), value)
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Double) {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        linearSet(convertIndices(i0, i1, i2, i3, i4, i5), value)
    }

    override fun setDouble(indices: IntArray, value: Double) {
        requireValidIndices(indices)
        linearSet(convertIndices(indices), value)
    }

    override fun setValue(value: DoubleArrayND) {
        requireSameShape(this, value)

        // Anti alias copy
        val source = if (value is LinearlyBackedDoubleArrayND<*> && value.data !== this.data)
            value
        else
            value.copy()

        source.unsafeForEachIndexed { indices, element ->
            setDouble(indices, element)
        }

    }

}
