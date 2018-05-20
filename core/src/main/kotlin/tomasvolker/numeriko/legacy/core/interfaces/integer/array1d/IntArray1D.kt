package tomasvolker.numeriko.legacy.core.interfaces.integer.array1d

import tomasvolker.numeriko.legacy.core.index.Index
import tomasvolker.numeriko.legacy.core.index.IndexProgression
import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.ReadOnlyArrayND
import tomasvolker.numeriko.legacy.core.interfaces.integer.arraynd.IntArrayND
import tomasvolker.numeriko.legacy.core.interfaces.numeric.array1d.NumericArray1D

interface IntArray1D: ReadOnlyIntArray1D, NumericArray1D<Int>, IntArrayND {

    override fun getView(vararg indices: Any): IntArray1D {
        require(indices.size <= 1) { "${indices.size} indices provided, expected 1 or less" }
        TODO("implement")
    }

    override fun setValue(value: ReadOnlyArrayND<Int>, vararg indices: Any) {
        getView(*indices).setAll { value.getValue(it) }
    }

    override fun copy(): IntArray1D

    override fun getView(i0: IntProgression): IntArray1D

    override fun getView(i0: IndexProgression): IntArray1D

    override fun setValue(value: Int, i0: Int) = setInt(value, i0)

    override fun setValue(value: Int, i0: Index) = setInt(value, i0)

    //TODO see if inherit this
    override fun setDouble(value: Double, i0: Int) =
            setInt(value.toInt(), i0)

    //TODO see if inherit this
    override fun setDouble(value: Double, i0: Index) =
            setInt(value.toInt(), i0)

    override operator fun get(i0: IntProgression): IntArray1D = getView(i0)

    override operator fun get(i0: IndexProgression): IntArray1D = getView(i0)

    operator fun set(value: Int, i0: Int) = setValue(value, i0)

    operator fun set(value: Int, i0: Index) = setValue(value, i0)

    operator fun set(value: ReadOnlyIntArray1D, i0: IntProgression) = setValue(value, i0)

    operator fun set(value: ReadOnlyIntArray1D, i0: IndexProgression) = setValue(value, i0)

}