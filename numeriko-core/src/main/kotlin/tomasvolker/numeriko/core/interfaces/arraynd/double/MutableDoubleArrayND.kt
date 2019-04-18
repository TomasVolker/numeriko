package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndexed
import tomasvolker.numeriko.core.interfaces.slicing.PermutedSlice
import tomasvolker.numeriko.core.preconditions.requireSameShape

interface MutableDoubleArrayND: DoubleArrayND, MutableArrayND<Double> {

    fun set(value: Double) = setDouble(intArrayOf(), value)
    operator fun set(i0: Int, value: Double) = setDouble(intArrayOf(i0), value)
    operator fun set(i0: Int, i1: Int, value: Double) = setDouble(intArrayOf(i0, i1), value)
    operator fun set(i0: Int, i1: Int, i2: Int, value: Double) = setDouble(intArrayOf(i0, i1, i2), value)
    operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Double) = setDouble(intArrayOf(i0, i1, i2, i3), value)
    operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Double) = setDouble(intArrayOf(i0, i1, i2, i3, i4), value)
    operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Double) = setDouble(intArrayOf(i0, i1, i2, i3, i4, i5), value)

    operator fun set(vararg indices: Int, value: Double) = setDouble(indices, value)

    fun setDouble(indices: IntArray, value: Double)

    fun setDouble(indices: IntArrayND, value: Double) = setDouble(indices.toIntArray(), value)

    override fun setValue(value: ArrayND<Double>) =
            if (value is DoubleArrayND)
                setValue(value)
            else
                super.setValue(value)

    fun setValue(value: DoubleArrayND) {
        requireSameShape(this, value)
        // Anti alias copy
        val copy = value.copy()
        copy.unsafeForEachIndexed { indices, element ->
            setDouble(indices, element)
        }
    }

    override fun getPermutedSlice(
            slice: PermutedSlice
    ): MutableDoubleArrayND = DefaultPermutedSliceDoubleArrayND(
            array = this,
            permutedSlice = slice
    )

    override fun setValue(indices: IntArray, value: Double) = setDouble(indices, value)
/*
    fun applyElementWise(function: FunctionDtoD): MutableDoubleArrayND =
            inlinedApplyElementWise { function(it) }

    fun applyFill(function: FunctionIAtoD): MutableDoubleArrayND =
            inlinedApplyFill { indices -> function(indices) }
*/
}
