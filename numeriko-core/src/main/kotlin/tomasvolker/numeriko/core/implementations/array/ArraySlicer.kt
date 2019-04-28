package tomasvolker.numeriko.core.implementations.array

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.axes
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.interfaces.slicing.permutationSlice

object IntArrayNDSlicer: ArraySlicer<IntArrayND> {

    override fun IntArrayND.slice(slice: ArraySlice): IntArrayND = getSlice(slice)


}

interface ArraySlicer<T: ArrayND<*>> {

    fun T.slice(slice: ArraySlice): T

    fun T.transpose(
            axes: IntArray1D = this.axes.reversed().toList().toIntArray().toIntArray1D()
    ): T = slice(permutationSlice(axes))

}