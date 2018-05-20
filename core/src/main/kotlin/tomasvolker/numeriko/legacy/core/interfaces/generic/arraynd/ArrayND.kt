package tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd

import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.legacy.core.jvm.int.array1d.asArray1D

interface ArrayNDViewer<T>: ReadOnlyArrayNDViewer<T> {

    override val array: ArrayND<T>

    override operator fun get(vararg indices: Any): ArrayND<T> = array.getView(*indices)

    operator fun set(vararg indices: Any, value: ReadOnlyArrayND<T>) =
            array.setValue(value, *indices)

}

class DefaultArrayNDViewer<T>(override val array: ArrayND<T>): ArrayNDViewer<T>

interface ArrayND<T>: ReadOnlyArrayND<T> {

    override val view: ArrayNDViewer<T> get() = DefaultArrayNDViewer(this)

    override fun getView(vararg indices:Any): ArrayND<T> {
        val (offset, shape, stride) = defaultGetView(this, indices)
        return DefaultArrayNDView(
                array = this,
                offset = offset,
                shape = shape,
                stride = stride
        )
    }

    fun setValue(value: T, vararg indices: Int) =
            setValue(value, indices.asArray1D())

    fun setValue(value: T, indexArray: ReadOnlyIntArray1D)

    fun setValue(value: ReadOnlyArrayND<T>, vararg indices: Any): Unit =
            getView(*indices).setValue(value)

    fun setValue(value: ReadOnlyArrayND<T>) {

        if (shape != value.shape)
            throw IllegalArgumentException("Expected shape ${shape} got ${value.shape}")

        setValue { indices -> value.getValue(indices) }
    }

    fun setValue(setter: (indexArray: ReadOnlyIntArray1D) -> T) {

        with(cursor()) {

            while (hasNext()) {
                setNext(setter(currentIndices))
            }

        }

    }

    override fun iterator(): ArrayNDIterator<T> = cursor()

    override fun cursor(): ArrayNDCursor<T> = TODO()//DefaultArrayNDCursor(this)

}
