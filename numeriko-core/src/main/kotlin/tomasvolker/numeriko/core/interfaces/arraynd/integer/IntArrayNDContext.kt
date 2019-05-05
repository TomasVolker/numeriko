package tomasvolker.numeriko.core.interfaces.arraynd.integer

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayNDContext
import tomasvolker.numeriko.core.interfaces.factory.doubleZeros
import tomasvolker.numeriko.core.interfaces.factory.intZeros
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice

object IntArrayNDContext: ArrayNDContext<IntArrayND> {

    override fun buildDefault(shape: IntArray1D): IntArrayND = intZeros(shape)
    override fun slice(array: IntArrayND, slice: ArraySlice): IntArrayND = array.slice(slice)
    override fun reshape(
            array: IntArrayND,
            shape: IntArray1D,
            copyIfNecessary: Boolean
    ): IntArrayND = array.reshape(shape, copyIfNecessary)
    override fun copy(array: IntArrayND): IntArrayND = array.copy()

}