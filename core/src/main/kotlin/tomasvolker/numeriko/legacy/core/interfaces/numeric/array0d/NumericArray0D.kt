package tomasvolker.numeriko.legacy.core.interfaces.numeric.array0d

import tomasvolker.numeriko.legacy.core.interfaces.generic.array0d.Array0D
import tomasvolker.numeriko.legacy.core.interfaces.numeric.arraynd.NumericArrayND

interface NumericArray0D<T: Number>: ReadOnlyNumericArray0D<T>, Array0D<T>, NumericArrayND<T> {

    override fun copy(): NumericArray0D<T>

    override fun getView(): NumericArray0D<T> = this

    fun setInt(value: Int)

    fun setDouble(value: Double)

    override fun getView(vararg indices: Any): NumericArray0D<T>

}