package tomasvolker.numeriko.core.interfaces.numeric.arraynd

import tomasvolker.numeriko.core.interfaces.generic.arraynd.ArrayND
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ArrayNDViewer
import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntArrayND


interface NumericArrayNDViewer<T: Number>: ReadOnlyNumericArrayNDViewer<T>, ArrayNDViewer<T> {

    override val array: NumericArrayND<T>

    override operator fun get(vararg indices: Any): NumericArrayND<T> = array.getView(*indices)

    operator fun set(vararg indices: Any, value: ReadOnlyIntArrayND) =
            array.setInt(value, *indices)

}

class DefaultNumericArrayNDViewer<T: Number>(override val array: NumericArrayND<T>): NumericArrayNDViewer<T>

interface NumericArrayND<T: Number>: ReadOnlyNumericArrayND<T>, ArrayND<T> {

    override val view: NumericArrayNDViewer<T> get() = DefaultNumericArrayNDViewer(this)

    override fun copy(): NumericArrayND<T>

    override fun getView(vararg indices: Any): NumericArrayND<T>

    fun setInt(value: Int, indexArray: ReadOnlyIntArrayND)

    fun setInt(value: Int, vararg indices: Int)

    fun setInt(value: ReadOnlyIntArrayND, vararg indices: Any)

    fun setDouble(value: Double, indexArray: ReadOnlyIntArrayND)

    fun setDouble(value: Double, vararg indices: Int)
/*
    fun setDouble(value: ReadOnlyDoubleNDArray, vararg indices: Any) =
            setValue(value as ReadOnlyArrayND<Number>, *indices)
*/
    /*
    //sign

    operator fun unaryPlus(): IntArrayND = this

    operator fun unaryMinus(): IntArrayND

    //plus

    operator fun plus(other: IntArrayND): IntArrayND

    operator fun plus(other: DoubleNDArray): DoubleNDArray

    //minus

    operator fun minus(other: IntArrayND): IntArrayND

    operator fun minus(other: DoubleNDArray): DoubleNDArray

    //times jvm

    operator fun times(other: IntArrayND): IntArrayND

    operator fun times(other: DoubleNDArray): DoubleNDArray

    //times scalar

    operator fun times(other: Int): IntArrayND

    operator fun times(other: Double): DoubleNDArray

    //div jvm

    operator fun div(other: IntArrayND): IntArrayND

    operator fun div(other: DoubleNDArray): DoubleNDArray

    //div scalar

    operator fun div(other: Int): IntArrayND

    operator fun div(other: Double): DoubleNDArray
    */
}

