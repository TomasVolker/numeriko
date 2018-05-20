package tomasvolker.numeriko.legacy.core.interfaces.generic.array0d

import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.ArrayND

interface Array0D<T>: ReadOnlyArray0D<T>, ArrayND<T> {

    fun setValue(value: T)

    fun setValue(value: ReadOnlyArray0D<T>) =
            setValue(value.getValue())

    override fun getView(): Array0D<T> =
            this

    override fun copy(): Array0D<T>

}