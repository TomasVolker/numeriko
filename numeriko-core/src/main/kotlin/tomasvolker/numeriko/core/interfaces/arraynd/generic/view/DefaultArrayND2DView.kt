package tomasvolker.numeriko.core.interfaces.arraynd.generic.view

import tomasvolker.numeriko.core.interfaces.array2d.generic.view.DefaultMutableArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND

class DefaultArrayND2DView<T>(
        val array: MutableArrayND<T>
) : DefaultMutableArray2D<T>() {

    init {
        require(array.rank == 2)
    }

    override val shape0: Int
        get() = array.shape(0)

    override val shape1: Int
        get() = array.shape(0)

    override fun getValue(i0: Int, i1: Int): T =
            array.getValue(i0, i1)

    override fun setValue(value: T, i0: Int, i1: Int) {
        array.setValue(value, i0, i1)
    }

}