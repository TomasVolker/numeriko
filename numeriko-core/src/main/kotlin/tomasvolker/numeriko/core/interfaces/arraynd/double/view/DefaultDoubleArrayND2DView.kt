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
        get() = array.shape(1)

    override fun get(i0: Int, i1: Int): Double = array[i0, i1]

    override fun set(i0: Int, i1: Int, value: Double) {
        array[i0, i1] = value
    }

}
