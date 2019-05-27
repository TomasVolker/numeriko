package tomasvolker.numeriko.core.interfaces.arraynd.integer

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndex
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.preconditions.requireSameShape

interface MutableIntArrayND: IntArrayND, MutableArrayND<Int> {

    fun set(value: Int) = setInt(intArrayOf(), value)
    operator fun set(i0: Int, value: Int) = setInt(intArrayOf(i0), value)
    operator fun set(i0: Int, i1: Int, value: Int) = setInt(intArrayOf(i0, i1), value)
    operator fun set(i0: Int, i1: Int, i2: Int, value: Int) = setInt(intArrayOf(i0, i1, i2), value)
    operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Int) = setInt(intArrayOf(i0, i1, i2, i3), value)
    operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Int) = setInt(intArrayOf(i0, i1, i2, i3, i4), value)
    operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Int) = setInt(intArrayOf(i0, i1, i2, i3, i4, i5), value)

    operator fun set(vararg indices: Int, value: Int) = setInt(indices, value)

    fun setInt(indices: IntArray, value: Int)

    fun setInt(indices: IntArray1D, value: Int) = setInt(indices.toIntArray(), value)

    override fun setValue(value: ArrayND<Int>) =
            if (value is IntArrayND)
                setValue(value)
            else
                super.setValue(value)

    fun setValue(value: IntArrayND) {
        requireSameShape(this, value)
        // Anti alias copy
        val copy = value.copy()
        copy.unsafeForEachIndex { indices ->
            setValue(indices, copy.getValue(indices))
        }
    }

    override fun slice(
            slice: ArraySlice
    ): MutableIntArrayND = DefaultSliceIntArrayND(
            array = this,
            slice = slice
    )

    override fun setValue(indices: IntArray, value: Int) = setInt(indices, value)
/*
    fun applyElementWise(function: FunctionDtoD): MutableIntArrayND =
            inlinedApplyElementWise { function(it) }

    fun applyFill(function: FunctionIAtoD): MutableIntArrayND =
            inlinedApplyFill { indices -> function(indices) }
*/
}