package tomasvolker.numeriko.core.interfaces.arraynd.double.view

import tomasvolker.numeriko.core.interfaces.array2d.double.view.DefaultMutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND

class DefaultDoubleArrayND2DView(
        val array: MutableDoubleArrayND
) : DefaultMutableDoubleArray2D() {

    init {
        require(array.rank == 2)
    }

    override val shape0: Int
        get() = array.shape(0)

    override val shape1: Int
        get() = array.shape(0)

    override fun getDouble(i0: Int, i1: Int): Double =
            array.getDouble(i0, i1)

    override fun setDouble(value: Double, i0: Int, i1: Int) {
        array.setDouble(value, i0, i1)
    }

}
