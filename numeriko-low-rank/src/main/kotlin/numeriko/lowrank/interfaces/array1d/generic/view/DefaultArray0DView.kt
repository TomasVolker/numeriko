package numeriko.lowrank.interfaces.array1d.generic.view

import numeriko.lowrank.interfaces.array0d.generic.DefaultMutableArray0D
import numeriko.lowrank.interfaces.array1d.generic.MutableArray1D

class DefaultArray0DView<T> (
        val array: MutableArray1D<T>,
        val offset: Int
) : DefaultMutableArray0D<T>() {

    init {
        array.requireValidIndices(offset)
    }

    override fun getValue(): T {
        return array.getValue(offset)
    }

    override fun setValue(value: T) {
        array.setValue(offset, value)
    }

}

fun <T> defaultArray0DView(array: MutableArray1D<T>, i0: Int) =
        DefaultArray0DView(
                array = array,
                offset = i0
        )
