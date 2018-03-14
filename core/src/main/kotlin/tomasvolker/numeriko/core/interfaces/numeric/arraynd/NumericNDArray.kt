package tomasvolker.numeriko.core.interfaces.numeric.arraynd

import tomasvolker.numeriko.core.interfaces.generic.arraynd.NDArray
import tomasvolker.numeriko.core.interfaces.generic.arraynd.NDArrayViewer
import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntNDArray


interface NumericNDArrayViewer<T: Number>: ReadOnlyNumericNDArrayViewer<T>, NDArrayViewer<T> {

    override val array: NumericNDArray<T>

    override operator fun get(vararg indices: Any): NumericNDArray<T> = array.getView(*indices)

    operator fun set(vararg indices: Any, value: ReadOnlyIntNDArray) =
            array.setInt(value, *indices)

}

class DefaultNumericNDArrayViewer<T: Number>(override val array: NumericNDArray<T>): NumericNDArrayViewer<T>

interface NumericNDArray<T: Number>: ReadOnlyNumericNDArray<T>, NDArray<T> {

    override val view: NumericNDArrayViewer<T> get() = DefaultNumericNDArrayViewer(this)

    override fun copy(): NumericNDArray<T>

    override fun getView(vararg indices: Any): NumericNDArray<T>

    fun setInt(value: Int, indexArray: ReadOnlyIntNDArray)

    fun setInt(value: Int, vararg indices: Int)

    fun setInt(value: ReadOnlyIntNDArray, vararg indices: Any)

    fun setDouble(value: Double, indexArray: ReadOnlyIntNDArray)

    fun setDouble(value: Double, vararg indices: Int)
/*
    fun setDouble(value: ReadOnlyDoubleNDArray, vararg indices: Any) =
            setValue(value as ReadOnlyNDArray<Number>, *indices)
*/
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

