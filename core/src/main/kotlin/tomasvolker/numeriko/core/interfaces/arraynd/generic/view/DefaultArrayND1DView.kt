package tomasvolker.numeriko.core.interfaces.arraynd.generic.view

import tomasvolker.numeriko.core.interfaces.array1d.generic.view.DefaultMutableArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND

class DefaultArrayND1DView<T>(
        val array: MutableArrayND<T>
) : DefaultMutableArray1D<T>() {

    init {
        require(array.rank == 1)
    }

    override val size: Int
        get() = array.shape(0)


    override fun getValue(i0: Int): T =
            array.getValue(i0)

    override fun setValue(value: T, i0: Int) {
        array.setValue(value, i0)
    }

}