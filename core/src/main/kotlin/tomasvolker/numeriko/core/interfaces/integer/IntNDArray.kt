package tomasvolker.numeriko.core.interfaces.integer

import tomasvolker.numeriko.core.interfaces.NDArray
import tomasvolker.numeriko.core.interfaces.ReadOnlyNDArray

interface ReadOnlyIntNDArray: ReadOnlyNDArray<Int> {

    override fun copy(): ReadOnlyIntNDArray

    override fun getValue(indexArray: ReadOnlyIntNDArray) = getInt(indexArray)

    override fun getReadOnlyView(vararg indices: Any): ReadOnlyIntNDArray

    override fun getValue(vararg indices: Int) = getInt(*indices)

    fun getInt(vararg indices:Int): Int

    fun getInt(indexArray: ReadOnlyIntNDArray): Int

    fun dataAsIntArray(): IntArray = dataAsArray().toIntArray()

}

interface IntNDArray: ReadOnlyIntNDArray, NDArray<Int> {

    override fun copy(): IntNDArray

    override fun getView(vararg indices: Any): IntNDArray

    override fun getReadOnlyView(vararg indices: Any): ReadOnlyIntNDArray =
            getView(*indices)

    override fun setValue(value: Int, vararg indices: Int) =
            setInt(value, *indices)

    override fun setValue(value: Int, indexArray: ReadOnlyIntNDArray) =
            setInt(value, indexArray)

    override fun setValue(value: ReadOnlyNDArray<Int>, vararg indices: Any)

    fun setInt(value: Int, indexArray: ReadOnlyIntNDArray)

    fun setInt(value: Int, vararg indices: Int)

    fun setInt(value: ReadOnlyIntNDArray, vararg indices: Any) =
            setValue(value as ReadOnlyNDArray<Int>, *indices)

    /*
    //sign

    operator fun unaryPlus(): IntNDArray = this

    operator fun unaryMinus(): IntNDArray

    //plus

    operator fun plus(other: IntNDArray): IntNDArray

    operator fun plus(other: DoubleNDArray): DoubleNDArray

    //minus

    operator fun minus(other: IntNDArray): IntNDArray

    operator fun minus(other: DoubleNDArray): DoubleNDArray

    //times array

    operator fun times(other: IntNDArray): IntNDArray

    operator fun times(other: DoubleNDArray): DoubleNDArray

    //times scalar

    operator fun times(other: Int): IntNDArray

    operator fun times(other: Double): DoubleNDArray

    //div array

    operator fun div(other: IntNDArray): IntNDArray

    operator fun div(other: DoubleNDArray): DoubleNDArray

    //div scalar

    operator fun div(other: Int): IntNDArray

    operator fun div(other: Double): DoubleNDArray
    */
}

/*inline*/ operator fun ReadOnlyIntNDArray.get(vararg indeces: Int) =
        getInt(*indeces)

/*inline*/ operator fun ReadOnlyIntNDArray.get(vararg indexArray: Any): ReadOnlyIntNDArray =
        getReadOnlyView(*indexArray)

/*inline*/ operator fun ReadOnlyIntNDArray.get(indexArray: ReadOnlyIntNDArray) =
        getInt(indexArray)

/*inline*/ operator fun IntNDArray.get(vararg indeces: Int) =
        getInt(*indeces)

/*inline*/ operator fun IntNDArray.get(indexArray: ReadOnlyIntNDArray) =
        getInt(indexArray)

/*inline*/ operator fun IntNDArray.get(vararg indexArray: Any): IntNDArray =
        getView(indexArray)

/*inline*/ operator fun IntNDArray.set(vararg indeces: Int, value: Int) =
        setInt(value, *indeces)

/*inline*/ operator fun IntNDArray.set(vararg indeces: Any, valueArray: ReadOnlyIntNDArray) =
        setInt(valueArray, *indeces)

/*inline*/ operator fun IntNDArray.set(indexArray: ReadOnlyIntNDArray, value: Int) =
        setInt(value, indexArray)
