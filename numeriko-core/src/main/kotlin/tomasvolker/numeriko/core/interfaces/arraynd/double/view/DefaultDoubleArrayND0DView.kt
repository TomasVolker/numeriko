package tomasvolker.numeriko.core.interfaces.arraynd.double.view

import tomasvolker.numeriko.lowrank.interfaces.array0d.double.DefaultMutableDoubleArray0D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND

class DefaultDoubleArrayND0DView(
        val array: MutableDoubleArrayND
) : DefaultMutableDoubleArray0D() {

    init {
        require(array.rank == 0)
    }

    override val size: Int
        get() = 1

    override fun get(): Double = array.get()

    override fun set(value: Double) {
        array.set(value)
    }

}
