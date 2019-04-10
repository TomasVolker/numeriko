package tomasvolker.numeriko.lowrank.interfaces.array0d.double

import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultMutableDoubleArray1D

class DefaultDoubleArray0DHigherRankView (
        val array: MutableDoubleArray0D
) : DefaultMutableDoubleArray1D() {
    
    override val size: Int get() = 1

    override fun get(i0: Int): Double {
        requireValidIndices(i0)
        return array.getDouble()
    }

    override fun set(i0: Int, value: Double) {
        requireValidIndices(i0)
        set(i0, value)
    }

}