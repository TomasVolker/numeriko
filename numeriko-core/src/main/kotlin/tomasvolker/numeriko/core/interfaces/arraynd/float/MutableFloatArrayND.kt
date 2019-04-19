package tomasvolker.numeriko.core.interfaces.arraynd.float

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndexed
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.preconditions.requireSameShape

interface MutableFloatArrayND: FloatArrayND, MutableArrayND<Float> {

    fun set(value: Float) = setFloat(intArrayOf(), value)
    operator fun set(i0: Int, value: Float) = setFloat(intArrayOf(i0), value)
    operator fun set(i0: Int, i1: Int, value: Float) = setFloat(intArrayOf(i0, i1), value)
    operator fun set(i0: Int, i1: Int, i2: Int, value: Float) = setFloat(intArrayOf(i0, i1, i2), value)
    operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Float) = setFloat(intArrayOf(i0, i1, i2, i3), value)
    operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Float) = setFloat(intArrayOf(i0, i1, i2, i3, i4), value)
    operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Float) = setFloat(intArrayOf(i0, i1, i2, i3, i4, i5), value)

    operator fun set(vararg indices: Int, value: Float) = setFloat(indices, value)

    fun setFloat(indices: IntArray, value: Float)

    fun setFloat(indices: IntArray1D, value: Float) = setFloat(indices.toIntArray(), value)

    override fun setValue(value: ArrayND<Float>) =
            if (value is FloatArrayND)
                setValue(value)
            else
                super.setValue(value)

    fun setValue(value: FloatArrayND) {
        requireSameShape(this, value)
        // Anti alias copy
        val copy = value.copy()
        copy.unsafeForEachIndexed { indices, element ->
            setFloat(indices, element)
        }
    }

    override fun getSlice(
            slice: ArraySlice
    ): MutableFloatArrayND = DefaultSliceFloatArrayND(
            array = this,
            slice = slice
    )

    override fun setValue(indices: IntArray, value: Float) = setFloat(indices, value)
/*
    fun applyElementWise(function: FunctionDtoD): MutableFloatArrayND =
            inlinedApplyElementWise { function(it) }

    fun applyFill(function: FunctionIAtoD): MutableFloatArrayND =
            inlinedApplyFill { indices -> function(indices) }
*/
}
