package tomasvolker.numeriko.legacy.core.interfaces.numeric.array0d

import tomasvolker.numeriko.legacy.core.interfaces.generic.array0d.ReadOnlyArray0D
import tomasvolker.numeriko.legacy.core.interfaces.numeric.arraynd.ReadOnlyNumericArrayND

interface ReadOnlyNumericArray0D<T: Number>: ReadOnlyArray0D<T>, ReadOnlyNumericArrayND<T> {

    override fun copy(): ReadOnlyNumericArray0D<T>

    override fun getView(): ReadOnlyNumericArray0D<T>

    fun getInt(): Int

    fun getDouble(): Double

    override fun getView(vararg indices: Any): ReadOnlyNumericArray0D<T>

}

