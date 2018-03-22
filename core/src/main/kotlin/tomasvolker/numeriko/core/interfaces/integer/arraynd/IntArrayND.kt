package tomasvolker.numeriko.core.interfaces.integer.arraynd

import tomasvolker.numeriko.core.interfaces.integer.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.core.interfaces.numeric.arraynd.NumericArrayND
import tomasvolker.numeriko.core.interfaces.numeric.arraynd.NumericArrayNDViewer

interface IntArrayNDViewer: ReadOnlyIntArrayNDViewer, NumericArrayNDViewer<Int> {

    override val array: IntArrayND

    override operator fun get(vararg indices: Any): IntArrayND = array.getView(*indices)

    override operator fun set(vararg indices: Any, value: ReadOnlyIntArrayND) =
            array.setInt(value, *indices)

}

class DefaultIntArrayNDViewer(override val array: IntArrayND): IntArrayNDViewer

interface IntArrayND: ReadOnlyIntArrayND, NumericArrayND<Int> {

    override val view: IntArrayNDViewer get() = DefaultIntArrayNDViewer(this)

    override fun copy(): IntArrayND

    override fun getView(vararg indices: Any): IntArrayND

    override fun setValue(value: Int, indexArray: ReadOnlyIntArray1D) = setInt(value, indexArray)

    override fun setValue(value: Int, vararg indices: Int) = setInt(value, *indices)

    override fun linearCursor(): IntArrayNDIterator

    override fun cursor(): IntArrayNDCursor

    //TODO see if inherit this
    override fun setDouble(value: Double, indexArray: ReadOnlyIntArray1D) =
            setInt(value.toInt())

    //TODO see if inherit this
    override fun setDouble(value: Double, vararg indices: Int) =
            setInt(value.toInt(), *indices)

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
