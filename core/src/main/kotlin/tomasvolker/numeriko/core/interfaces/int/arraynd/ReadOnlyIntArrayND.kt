package tomasvolker.numeriko.core.interfaces.int.arraynd

import tomasvolker.numeriko.core.interfaces.int.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.core.interfaces.numeric.arraynd.ReadOnlyNumericArrayND
import tomasvolker.numeriko.core.interfaces.numeric.arraynd.ReadOnlyNumericArrayNDViewer

interface ReadOnlyIntArrayNDViewer: ReadOnlyNumericArrayNDViewer<Int> {

    override val array: ReadOnlyIntArrayND

    override operator fun get(vararg indices: Any): ReadOnlyIntArrayND = array.getView(*indices)

}

class DefaultReadOnlyIntArrayNDViewer(override val array: ReadOnlyIntArrayND): ReadOnlyIntArrayNDViewer

interface ReadOnlyIntArrayND: ReadOnlyNumericArrayND<Int> {

    override val view: ReadOnlyIntArrayNDViewer get() = DefaultReadOnlyIntArrayNDViewer(this)

    override fun copy(): ReadOnlyIntArrayND

    override fun getValue(indexArray: ReadOnlyIntArray1D) = getInt(indexArray)

    override fun getView(vararg indices: Any): ReadOnlyIntArrayND

    override fun getValue(vararg indices: Int) = getInt(*indices)

    override fun getInt(vararg indices:Int): Int

    override fun getInt(indexArray: ReadOnlyIntArray1D): Int

    override fun getDouble(indexArray: ReadOnlyIntArray1D) = getInt(indexArray).toDouble()

    override fun getDouble(vararg indices: Int) = getInt(*indices).toDouble()

    fun unsafeGetDataAsIntArray(): IntArray = unsafeGetDataAsArray().toIntArray()

    fun getDataAsIntArray(): IntArray = unsafeGetDataAsIntArray().copyOf()

    infix fun equals(other: Int): Boolean {
        return rank == 0 && getInt() == other
    }

}
