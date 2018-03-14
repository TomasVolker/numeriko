package tomasvolker.numeriko.core.interfaces.int.arraynd

import tomasvolker.numeriko.core.interfaces.numeric.arraynd.NumericNDArray
import tomasvolker.numeriko.core.interfaces.numeric.arraynd.NumericNDArrayViewer

interface IntNDArrayViewer: ReadOnlyIntNDArrayViewer, NumericNDArrayViewer<Int> {

    override val array: IntNDArray

    override operator fun get(vararg indices: Any): IntNDArray = array.getView(*indices)

    override operator fun set(vararg indices: Any, value: ReadOnlyIntNDArray) =
            array.setInt(value, *indices)

}

class DefaultIntNDArrayViewer(override val array: IntNDArray): IntNDArrayViewer

interface IntNDArray: ReadOnlyIntNDArray, NumericNDArray<Int> {

    override val view: IntNDArrayViewer get() = DefaultIntNDArrayViewer(this)

    override fun copy(): IntNDArray

    override fun getView(vararg indices: Any): IntNDArray

    override fun setValue(value: Int, indexArray: ReadOnlyIntNDArray) = setInt(value, indexArray)

    override fun setValue(value: Int, vararg indices: Int) = setInt(value, *indices)

    //TODO see if inherit this
    override fun setDouble(value: Double, indexArray: ReadOnlyIntNDArray) =
            setInt(value.toInt())

    //TODO see if inherit this
    override fun setDouble(value: Double, vararg indices: Int) =
            setInt(value.toInt(), *indices)

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

    //times jvm

    operator fun times(other: IntNDArray): IntNDArray

    operator fun times(other: DoubleNDArray): DoubleNDArray

    //times scalar

    operator fun times(other: Int): IntNDArray

    operator fun times(other: Double): DoubleNDArray

    //div jvm

    operator fun div(other: IntNDArray): IntNDArray

    operator fun div(other: DoubleNDArray): DoubleNDArray

    //div scalar

    operator fun div(other: Int): IntNDArray

    operator fun div(other: Double): DoubleNDArray
    */
}
