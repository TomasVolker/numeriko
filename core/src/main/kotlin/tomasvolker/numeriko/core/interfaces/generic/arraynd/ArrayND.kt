package tomasvolker.numeriko.core.interfaces.generic.arraynd

import tomasvolker.numeriko.core.interfaces.int.array1d.ReadOnlyIntArray1D

interface ArrayNDViewer<T>: ReadOnlyArrayNDViewer<T> {

    override val array: ArrayND<T>

    override operator fun get(vararg indices: Any): ArrayND<T> = array.getView(*indices)

    operator fun set(vararg indices: Any, value: ReadOnlyArrayND<T>) =
            array.setValue(value, *indices)

}

class DefaultArrayNDViewer<T>(override val array: ArrayND<T>): ArrayNDViewer<T>

interface ArrayND<T>: ReadOnlyArrayND<T> {

    override val view: ArrayNDViewer<T> get() = DefaultArrayNDViewer(this)

    override fun linearCursor(): ArrayNDLinearCursor<T>

    override fun cursor(): ArrayNDCursor<T>

    override fun getView(vararg indices:Any): ArrayND<T>

    fun setValue(value: T, vararg indices: Int)

    fun setValue(value: T, indexArray: ReadOnlyIntArray1D)

    fun setValue(value: ReadOnlyArrayND<T>, vararg indices: Any)

    fun setWithIndex(setter: (indexArray: ReadOnlyIntArray1D) -> T) = setAllInline(setter)

    override fun getDataAsArray(): Array<T>

    override fun unsafeGetDataAsArray(): Array<T>

}
