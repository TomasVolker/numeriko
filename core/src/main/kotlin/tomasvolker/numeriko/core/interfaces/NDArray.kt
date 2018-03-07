package tomasvolker.numeriko.core.interfaces

import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray

interface NDArrayViewer<T>: ReadOnlyNDArrayViewer<T> {

    override val array: NDArray<T>

    override operator fun get(vararg indices: Any): NDArray<T> = array.getView(*indices)

    operator fun set(vararg indices: Any, value: ReadOnlyNDArray<T>) =
            array.setValue(value, *indices)

}

class DefaultNDArrayViewer<T>(override val array: NDArray<T>): NDArrayViewer<T>

interface NDArray<T>: ReadOnlyNDArray<T> {

    override val view: NDArrayViewer<T> get() = DefaultNDArrayViewer(this)

    override fun linearCursor(): NDArrayLinearCursor<T>

    override fun cursor(): NDArrayCursor<T>

    override fun getView(vararg indices:Any): NDArray<T>

    fun setValue(value: T, vararg indices: Int)

    fun setValue(value: T, indexArray: ReadOnlyIntNDArray)

    fun setValue(value: ReadOnlyNDArray<T>, vararg indices: Any)

    fun setAll(setter: (indexArray: ReadOnlyIntNDArray) -> T) = setAllInline(setter)

    override fun getDataAsArray(): Array<T>

    override fun unsafeGetDataAsArray(): Array<T>

}
