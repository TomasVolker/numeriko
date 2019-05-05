package tomasvolker.numeriko.core.interfaces.arraynd.byte

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayNDContext
import tomasvolker.numeriko.core.interfaces.factory.byteZeros
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice

object ByteArrayNDContext: ArrayNDContext<ByteArrayND> {

    override fun buildDefault(shape: IntArray1D): ByteArrayND = byteZeros(shape)
    override fun slice(array: ByteArrayND, slice: ArraySlice): ByteArrayND = array.slice(slice)
    override fun reshape(
            array: ByteArrayND,
            shape: IntArray1D,
            copyIfNecessary: Boolean
    ): ByteArrayND = array.reshape(shape, copyIfNecessary)
    override fun copy(array: ByteArrayND): ByteArrayND = array.copy()

}