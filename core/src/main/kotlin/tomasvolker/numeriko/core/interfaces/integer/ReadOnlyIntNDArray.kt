package tomasvolker.numeriko.core.interfaces.integer

import tomasvolker.numeriko.core.interfaces.ReadOnlyNDArray
import tomasvolker.numeriko.core.interfaces.ReadOnlyNDArrayViewer

interface ReadOnlyIntNDArrayViewer: ReadOnlyNDArrayViewer<Int> {

    override val array: ReadOnlyIntNDArray

    override operator fun get(vararg indices: Any): ReadOnlyIntNDArray = array.getView(*indices)

}

class DefaultReadOnlyIntNDArrayViewer(override val array: ReadOnlyIntNDArray): ReadOnlyIntNDArrayViewer

interface ReadOnlyIntNDArray: ReadOnlyNDArray<Int> {

    override val view: ReadOnlyIntNDArrayViewer get() = DefaultReadOnlyIntNDArrayViewer(this)

    override fun copy(): ReadOnlyIntNDArray

    override fun getValue(indexArray: ReadOnlyIntNDArray) = getInt(indexArray)

    override fun getView(vararg indices: Any): ReadOnlyIntNDArray

    override fun getValue(vararg indices: Int) = getInt(*indices)

    fun getInt(vararg indices:Int): Int

    fun getInt(indexArray: ReadOnlyIntNDArray): Int

    fun unsafeGetDataAsIntArray(): IntArray = unsafeGetDataAsArray().toIntArray()

    fun dataAsIntArray(): IntArray = unsafeGetDataAsIntArray().copyOf()

    infix fun equals(other: Int): Boolean {
        return rank == 0 && getInt() == other
    }
    /*
    operator fun component1(): Int = getInt(0)

    operator fun component2(): Int = getInt(1)

    operator fun component3(): Int = getInt(2)

    operator fun component4(): Int = getInt(3)

    operator fun component5(): Int = getInt(4)
    */
}