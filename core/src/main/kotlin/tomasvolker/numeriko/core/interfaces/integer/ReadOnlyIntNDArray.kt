package tomasvolker.numeriko.core.interfaces.integer

import tomasvolker.numeriko.core.interfaces.ReadOnlyNDArray

interface ReadOnlyIntNDArray: ReadOnlyNDArray<Int> {

    override fun copy(): ReadOnlyIntNDArray

    override fun getValue(indexArray: ReadOnlyIntNDArray) = getInt(indexArray)

    override fun getView(vararg indices: Any): ReadOnlyIntNDArray

    override fun getValue(vararg indices: Int) = getInt(*indices)

    fun getInt(vararg indices:Int): Int

    fun getInt(indexArray: ReadOnlyIntNDArray): Int

    fun unsafeDataAsIntArray(): IntArray = unsafeGetDataAsArray().toIntArray()

    fun dataAsIntArray(): IntArray = unsafeDataAsIntArray().copyOf()

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