package tomasvolker.numeriko.core.interfaces.arraynd.double.view

import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultMutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND

class DefaultDoubleArrayND1DView(
        val array: MutableDoubleArrayND
) : DefaultMutableDoubleArray1D() {

    init {
        require(array.rank == 1)
    }

    override val size: Int
        get() = array.shape(0)

    override fun get(i0: Int): Double = array[i0]

    override fun set(i0: Int, value: Double) {
        array[i0] = value
    }

}
