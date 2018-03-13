package tomasvolker.numeriko.core.interfaces.numeric.arraynd

import tomasvolker.numeriko.core.interfaces.generic.arraynd.ReadOnlyNDArray
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ReadOnlyNDArrayViewer
import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntNDArray

interface ReadOnlyNumericNDArrayViewer<out T: Number>: ReadOnlyNDArrayViewer<T> {

    override val array: ReadOnlyNumericNDArray<T>

    override operator fun get(vararg indices: Any): ReadOnlyNumericNDArray<T> = array.getView(*indices)

}

class DefaultReadOnlyNumericNDArrayViewer<out T: Number>(
        override val array: ReadOnlyNumericNDArray<T>
        ): ReadOnlyNumericNDArrayViewer<T>

interface ReadOnlyNumericNDArray<out T: Number>: ReadOnlyNDArray<T> {

    override val view: ReadOnlyNumericNDArrayViewer<T> get() = DefaultReadOnlyNumericNDArrayViewer(this)

    override fun copy(): ReadOnlyNumericNDArray<T>

    override fun getValue(indexArray: ReadOnlyIntNDArray): T

    override fun getView(vararg indices: Any): ReadOnlyNumericNDArray<T>

    override fun getValue(vararg indices: Int): T

    fun getInt(vararg indices:Int): Int

    fun getInt(indexArray: ReadOnlyIntNDArray): Int

    fun getDouble(vararg indices:Int): Double

    fun getDouble(indexArray: ReadOnlyIntNDArray): Double

}


