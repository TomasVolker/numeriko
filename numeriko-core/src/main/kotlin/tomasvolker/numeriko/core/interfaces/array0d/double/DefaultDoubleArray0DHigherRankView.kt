package tomasvolker.numeriko.core.interfaces.array0d.double

import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultMutableDoubleArray1D

class DefaultDoubleArray0DHigherRankView (
        val array: MutableDoubleArray0D
) : DefaultMutableDoubleArray1D() {
    
    override val size: Int get() = 1

    override fun getDouble(i0: Int): Double {
        requireValidIndices(i0)
        return array.getDouble()
    }

    override fun setDouble(value: Double, i0: Int) {
        requireValidIndices(i0)
        setDouble(value)
    }

}