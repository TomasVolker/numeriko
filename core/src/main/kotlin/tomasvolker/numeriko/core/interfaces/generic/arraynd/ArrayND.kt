package tomasvolker.numeriko.core.interfaces.generic.arraynd

import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntArrayND

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

    fun setValue(value: T, indexArray: ReadOnlyIntArrayND)

    fun setValue(value: ReadOnlyArrayND<T>, vararg indices: Any)

    fun setAll(setter: (indexArray: ReadOnlyIntArrayND) -> T) = setAllInline(setter)

    override fun getDataAsArray(): Array<T>

    override fun unsafeGetDataAsArray(): Array<T>

}
