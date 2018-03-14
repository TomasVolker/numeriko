package tomasvolker.numeriko.core.interfaces.numeric.array1d

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.generic.array1d.Array1D
import tomasvolker.numeriko.core.interfaces.int.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntArrayND
import tomasvolker.numeriko.core.interfaces.numeric.arraynd.NumericArrayND
import tomasvolker.numeriko.core.interfaces.numeric.arraynd.ReadOnlyNumericArrayND

interface NumericArray1D<T: Number>: ReadOnlyNumericArray1D<T>, Array1D<T>, NumericArrayND<T> {

    override fun copy(): NumericArray1D<T>

    override fun getView(i0: IntProgression): NumericArray1D<T>

    override fun getView(i0: IndexProgression): NumericArray1D<T>

    fun setInt(value: Int, i0: Int)

    fun setInt(value: Int, i0: Index)

    fun setDouble(value: Double, i0: Int)

    fun setDouble(value: Double, i0: Index)

    override fun setInt(value: Int, indexArray: ReadOnlyIntArray1D) {
        require(indexArray.size == 1) { "${indexArray.size} indices provided, expected 1" }
        setInt(value, indexArray.getInt(0))
    }

    override fun setInt(value: Int, vararg indices: Int) {
        require(indices.size == 1) { "${indices.size} indices provided, expected 1" }
        return setInt(value, indices[0])
    }

    override fun setInt(value: ReadOnlyIntArrayND, vararg indices: Any) {
        TODO("not implemented")
    }

    override fun getView(vararg indices: Any): NumericArray1D<T> =
            TODO()

}