package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.interfaces.arraynd.computeIndices

interface MutableArrayND<T>: ArrayND<T> {

    fun setValue(value: T, vararg indices: Int)

    fun setValue(value: T, vararg indices: Index) =
            setValue(value, *indices.computeIndices(shape))

    override fun copy(): MutableArrayND<T> = TODO()

}