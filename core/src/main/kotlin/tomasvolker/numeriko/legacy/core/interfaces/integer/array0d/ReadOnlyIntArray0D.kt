package tomasvolker.numeriko.legacy.core.interfaces.integer.array0d

import tomasvolker.numeriko.legacy.core.interfaces.integer.arraynd.ReadOnlyIntArrayND
import tomasvolker.numeriko.legacy.core.interfaces.numeric.array0d.ReadOnlyNumericArray0D

interface ReadOnlyIntArray0D: ReadOnlyNumericArray0D<Int>, ReadOnlyIntArrayND {

    override fun getView(vararg indices: Any): ReadOnlyIntArray0D

    override fun copy(): ReadOnlyIntArray0D

    override fun getValue() = getInt()

    override fun getView(): ReadOnlyIntArray0D = this

    override fun getDouble() = getInt().toDouble()

}

