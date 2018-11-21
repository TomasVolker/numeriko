package tomasvolker.numeriko.core.interfaces.arraynd.double.view

import tomasvolker.numeriko.core.interfaces.array0d.double.DefaultMutableDoubleArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultMutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND

class DefaultDoubleArrayND0DView(
        val array: MutableDoubleArrayND
) : DefaultMutableDoubleArray0D() {

    init {
        require(array.rank == 0)
    }

    override val size: Int
        get() = 1

    override fun getDouble(): Double =
            array.getDouble()

    override fun setDouble(value: Double) {
        array.setDouble(value)
    }

}
