package tomasvolker.numeriko.legacy.core.interfaces.integer.array0d

import tomasvolker.numeriko.legacy.core.interfaces.integer.arraynd.IntArrayND
import tomasvolker.numeriko.legacy.core.interfaces.numeric.array0d.NumericArray0D

interface IntArray0D: ReadOnlyIntArray0D, NumericArray0D<Int>, IntArrayND {

    override fun getView(vararg indices: Any): IntArray0D {
        require(indices.isEmpty()) {
            "${indices.size} indices provided, expected 1 or less"
        }
        return getView()
    }

    override fun copy(): IntArray0D

    override fun getView(): IntArray0D =
            this

    override fun setValue(value: Int) =
            setInt(value)

    //TODO see if inherit this
    override fun setDouble(value: Double) =
            setInt(value.toInt())

}