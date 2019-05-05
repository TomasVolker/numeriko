package tomasvolker.numeriko.core.interfaces.arraynd.byte

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndexed
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.preconditions.requireSameShape

interface MutableByteArrayND: ByteArrayND, MutableArrayND<Byte> {

    fun set(value: Byte) = setByte(intArrayOf(), value)
    operator fun set(i0: Int, value: Byte) = setByte(intArrayOf(i0), value)
    operator fun set(i0: Int, i1: Int, value: Byte) = setByte(intArrayOf(i0, i1), value)
    operator fun set(i0: Int, i1: Int, i2: Int, value: Byte) = setByte(intArrayOf(i0, i1, i2), value)
    operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Byte) = setByte(intArrayOf(i0, i1, i2, i3), value)
    operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Byte) = setByte(intArrayOf(i0, i1, i2, i3, i4), value)
    operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Byte) = setByte(intArrayOf(i0, i1, i2, i3, i4, i5), value)

    operator fun set(vararg indices: Int, value: Byte) = setByte(indices, value)

    fun setByte(indices: IntArray, value: Byte)

    fun setByte(indices: IntArray1D, value: Byte) = setByte(indices.toIntArray(), value)

    override fun setValue(value: ArrayND<Byte>) =
            if (value is ByteArrayND)
                setValue(value)
            else
                super.setValue(value)

    fun setValue(value: ByteArrayND) {
        requireSameShape(this, value)
        // Anti alias copy
        val copy = value.copy()
        copy.unsafeForEachIndexed { indices, element ->
            setByte(indices, element)
        }
    }

    override fun slice(
            slice: ArraySlice
    ): MutableByteArrayND = DefaultSliceByteArrayND(
            array = this,
            slice = slice
    )

    override fun setValue(indices: IntArray, value: Byte) = setByte(indices, value)
/*
    fun applyElementWise(function: FunctionDtoD): MutableByteArrayND =
            inlinedApplyElementWise { function(it) }

    fun applyFill(function: FunctionIAtoD): MutableByteArrayND =
            inlinedApplyFill { indices -> function(indices) }
*/
}
