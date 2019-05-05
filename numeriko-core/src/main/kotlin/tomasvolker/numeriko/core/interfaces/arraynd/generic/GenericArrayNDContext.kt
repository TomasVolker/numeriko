package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.factory.arrayNDOfNulls
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice

class GenericArrayNDContext<T>: ArrayNDContext<ArrayND<T>> {

    override fun buildDefault(shape: IntArray1D): ArrayND<T> = arrayNDOfNulls<T>(shape) as ArrayND<T>
    override fun slice(array: ArrayND<T>, slice: ArraySlice): ArrayND<T> = array.slice(slice)
    override fun reshape(
            array: ArrayND<T>,
            shape: IntArray1D,
            copyIfNecessary: Boolean
    ): ArrayND<T> = array.reshape(shape, copyIfNecessary)
    override fun copy(array: ArrayND<T>): ArrayND<T> = array.copy()

}