package tomasvolker.numeriko.core.interfaces.int.array1d

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntArrayND
import tomasvolker.numeriko.core.interfaces.numeric.array1d.ReadOnlyNumericArray1D

interface ReadOnlyIntArray1D: ReadOnlyNumericArray1D<Int>, ReadOnlyIntArrayND {

    override fun getView(vararg indices: Any): ReadOnlyIntArray1D = TODO()

    override fun copy(): ReadOnlyIntArray1D

    override fun getValue(i0: Int) = getInt(i0)

    override fun getValue(i0: Index) = getInt(i0)

    override fun getView(i0: IntProgression): ReadOnlyIntArray1D

    override fun getView(i0: IndexProgression): ReadOnlyIntArray1D

    override fun getInt(i0: Int): Int

    override fun getInt(i0: Index): Int

    override fun getDouble(i0: Int) = getInt(i0).toDouble()

    override fun getDouble(i0: Index) = getInt(i0).toDouble()

    operator fun component1(): Int = getInt(0)

    operator fun component2(): Int = getInt(1)

    operator fun component3(): Int = getInt(2)

    operator fun component4(): Int = getInt(3)

    operator fun component5(): Int = getInt(4)

    override fun getInt(indexArray: ReadOnlyIntArray1D): Int {
        require(indexArray.size == 1) { "${indexArray.size} indices provided, expected 1" }
        return getInt(indexArray.getInt(0))
    }

    override fun getInt(vararg indices: Int): Int {
        require(indices.size == 1) { "${indices.size} indices provided, expected 1" }
        return getInt(indices[0])
    }

}

