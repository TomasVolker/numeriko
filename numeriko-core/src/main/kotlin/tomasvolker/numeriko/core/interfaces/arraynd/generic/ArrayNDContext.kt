package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice

interface ArrayNDContext<A: ArrayND<*>> {

    fun buildDefault(shape: IntArray1D): A

    fun slice(array: A, slice: ArraySlice): A

    fun reshape(array: A, shape: IntArray1D, copyIfNecessary: Boolean = true): A

    fun copy(array: A): A

}
