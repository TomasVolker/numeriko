package tomasvolker.numeriko.core.interfaces

import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray

interface NDArray<T>: ReadOnlyNDArray<T> {

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
