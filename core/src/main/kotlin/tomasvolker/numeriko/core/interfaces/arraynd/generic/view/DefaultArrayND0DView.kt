package tomasvolker.numeriko.core.interfaces.arraynd.generic.view

import tomasvolker.numeriko.core.interfaces.array0d.generic.DefaultMutableArray0D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND

class DefaultArrayND0DView<T>(
        val array: MutableArrayND<T>
) : DefaultMutableArray0D<T>() {

    init {
        require(array.rank == 0)
    }

    override val size: Int
        get() = 1

    override fun getValue(): T =
            array.getValue()

    override fun setValue(value: T) {
        array.setValue(value)
    }

}
