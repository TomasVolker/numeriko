package tomasvolker.numeriko.core.interfaces.generic.array0d

import tomasvolker.numeriko.core.interfaces.factory.intArray1DOf
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ReadOnlyArrayND
import tomasvolker.numeriko.core.interfaces.integer.array1d.ReadOnlyIntArray1D

interface ReadOnlyArray0D<out T>: ReadOnlyArrayND<T> {

    override val shape: ReadOnlyIntArray1D get() = intArray1DOf()

    override val rank: Int get() = 0

    override val size: Int get() = 1

    override fun isEmpty(): Boolean = false

    override fun contains(element:@UnsafeVariance T) = getValue() == element

    fun getValue(): T

    fun getView(): ReadOnlyArray0D<T> = this

    override fun copy(): ReadOnlyArray0D<T>

}
