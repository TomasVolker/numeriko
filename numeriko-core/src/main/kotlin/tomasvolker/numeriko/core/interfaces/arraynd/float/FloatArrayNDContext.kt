package tomasvolker.numeriko.core.interfaces.arraynd.float

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayNDContext
import tomasvolker.numeriko.core.interfaces.factory.floatZeros
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice

object FloatArrayNDContext: ArrayNDContext<FloatArrayND> {

    override fun buildDefault(shape: IntArray1D): FloatArrayND = floatZeros(shape)
    override fun slice(array: FloatArrayND, slice: ArraySlice): FloatArrayND = array.slice(slice)
    override fun reshape(
            array: FloatArrayND,
            shape: IntArray1D,
            copyIfNecessary: Boolean
    ): FloatArrayND = array.reshape(shape, copyIfNecessary)
    override fun copy(array: FloatArrayND): FloatArrayND = array.copy()

}