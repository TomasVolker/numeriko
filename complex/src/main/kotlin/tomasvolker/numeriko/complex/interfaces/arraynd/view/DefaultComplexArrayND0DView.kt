package tomasvolker.numeriko.complex.interfaces.arraynd.view

import tomasvolker.numeriko.complex.Complex
import tomasvolker.numeriko.complex.interfaces.array0d.DefaultMutableComplexArray0D
import tomasvolker.numeriko.complex.interfaces.arraynd.MutableComplexArrayND

class DefaultComplexArrayND0DView(
        val array: MutableComplexArrayND
) : DefaultMutableComplexArray0D() {

    init {
        require(array.rank == 0)
    }

    override val size: Int
        get() = 1

    override fun getValue(): Complex =
            array.getValue()

    override fun setValue(value: Complex) {
        array.setValue(value)
    }

}
