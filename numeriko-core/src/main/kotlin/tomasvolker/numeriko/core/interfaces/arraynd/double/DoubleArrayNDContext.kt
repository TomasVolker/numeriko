package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayNDContext
import tomasvolker.numeriko.core.interfaces.factory.doubleZeros
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice

object DoubleArrayNDContext: ArrayNDContext<DoubleArrayND> {

    override fun buildDefault(shape: IntArray1D): DoubleArrayND = doubleZeros(shape)
    override fun slice(array: DoubleArrayND, slice: ArraySlice): DoubleArrayND = array.slice(slice)
    override fun reshape(
            array: DoubleArrayND,
            shape: IntArray1D,
            copyIfNecessary: Boolean
    ): DoubleArrayND = array.reshape(shape, copyIfNecessary)
    override fun copy(array: DoubleArrayND): DoubleArrayND = array.copy()

}