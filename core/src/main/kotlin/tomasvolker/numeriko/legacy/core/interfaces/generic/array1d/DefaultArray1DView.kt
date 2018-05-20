package tomasvolker.numeriko.legacy.core.interfaces.generic.array1d

import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.ReadOnlyIntArray1D

open class DefaultReadOnlyArray1DView<out T>(
        open val array: ReadOnlyArray1D<T>,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : ReadOnlyArray1D<T> {

    override fun getValue(i0: Int): T =
            array.getValue(offset + stride * i0)

}

open class DefaultArray1DView<T>(
        open val array: Array1D<T>,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : Array1D<T> {

    override fun setValue(value: ReadOnlyArray1D<T>, indices: IntProgression) {
        TODO("not implemented")
    }

    override fun setValue(value: T, indexArray: ReadOnlyIntArray1D) {
        TODO("not implemented")
    }

    override fun setAll(setter: (i0: Int) -> T) {
        TODO("not implemented")
    }

    override fun setValue(value: T, i0: Int) =
        array.setValue(value, offset + stride * i0)


    override fun getValue(i0: Int): T =
            array.getValue(offset + stride * i0)

}