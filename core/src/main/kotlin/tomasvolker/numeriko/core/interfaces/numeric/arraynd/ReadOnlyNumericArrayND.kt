package tomasvolker.numeriko.core.interfaces.numeric.arraynd

import tomasvolker.numeriko.core.interfaces.generic.arraynd.ReadOnlyArrayND
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ReadOnlyArrayNDViewer
import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntArrayND

interface ReadOnlyNumericArrayNDViewer<out T: Number>: ReadOnlyArrayNDViewer<T> {

    override val array: ReadOnlyNumericArrayND<T>

    override operator fun get(vararg indices: Any): ReadOnlyNumericArrayND<T> = array.getView(*indices)

}

class DefaultReadOnlyNumericArrayNDViewer<out T: Number>(
        override val array: ReadOnlyNumericArrayND<T>
        ): ReadOnlyNumericArrayNDViewer<T>

interface ReadOnlyNumericArrayND<out T: Number>: ReadOnlyArrayND<T> {

    override val view: ReadOnlyNumericArrayNDViewer<T> get() = DefaultReadOnlyNumericArrayNDViewer(this)

    override fun copy(): ReadOnlyNumericArrayND<T>

    override fun getValue(indexArray: ReadOnlyIntArrayND): T

    override fun getView(vararg indices: Any): ReadOnlyNumericArrayND<T>

    override fun getValue(vararg indices: Int): T

    fun getInt(vararg indices:Int): Int

    fun getInt(indexArray: ReadOnlyIntArrayND): Int

    fun getDouble(vararg indices:Int): Double

    fun getDouble(indexArray: ReadOnlyIntArrayND): Double

}


