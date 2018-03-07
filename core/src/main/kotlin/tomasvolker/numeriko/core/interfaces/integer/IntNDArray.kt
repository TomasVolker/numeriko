package tomasvolker.numeriko.core.interfaces.integer

import tomasvolker.numeriko.core.interfaces.NDArray
import tomasvolker.numeriko.core.interfaces.NDArrayViewer
import tomasvolker.numeriko.core.interfaces.ReadOnlyNDArray

interface IntNDArrayViewer: ReadOnlyIntNDArrayViewer, NDArrayViewer<Int> {

    override val array: IntNDArray

    override operator fun get(vararg indices: Any): IntNDArray = array.getView(*indices)

    operator fun set(vararg indices: Any, value: ReadOnlyIntNDArray) =
            array.setValue(value, *indices)

}

class DefaultIntNDArrayViewer(override val array: IntNDArray): IntNDArrayViewer

interface IntNDArray: ReadOnlyIntNDArray, NDArray<Int> {

    override val view: IntNDArrayViewer get() = DefaultIntNDArrayViewer(this)

    override fun copy(): IntNDArray

    override fun getView(vararg indices: Any): IntNDArray

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
