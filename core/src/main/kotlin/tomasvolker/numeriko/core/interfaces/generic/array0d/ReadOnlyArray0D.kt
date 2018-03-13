package tomasvolker.numeriko.core.interfaces.generic.array0d

import tomasvolker.numeriko.core.interfaces.int.array1d.ReadOnlyIntArray1D

interface ReadOnlyArray0D<out T>: Collection<T> {

    val shape: ReadOnlyIntArray1D

    val indexShape: ReadOnlyIntArray1D get() = shape.shape

    val rank: Int get() = 0

    override val size: Int get() = 1

    override fun isEmpty(): Boolean = false

    override fun contains(element:@UnsafeVariance T): Boolean {

        for (item in this) {

            if (item == element)
                return true

        }
        return false
    }

    override fun containsAll(elements: Collection<@UnsafeVariance T>): Boolean {

        for (element in elements) {

            if(!contains(element))
                return false

        }

        return true
    }

    fun getValue(): T

    fun getView(): ReadOnlyArray0D<T> = this

    fun copy(): ReadOnlyArray0D<T>

    fun lastIndex() = 0

}

interface Array0D<T>: ReadOnlyArray0D<T> {

    fun setValue(value: T)

    fun setValue(value: ReadOnlyArray0D<T>) = setValue(value.getValue())

    override fun getView(): Array0D<T> = this

}