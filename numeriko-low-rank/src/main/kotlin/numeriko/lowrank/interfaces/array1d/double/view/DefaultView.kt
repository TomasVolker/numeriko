package numeriko.lowrank.interfaces.array1d.double.view

import numeriko.lowrank.interfaces.array1d.double.MutableDoubleArray1D

inline fun doubleArray1DView(
        array: MutableDoubleArray1D,
        size: Int,
        crossinline convertIndex: (i0: Int)->Int
): MutableDoubleArray1D = object: DefaultMutableDoubleArray1D() {

    override val size: Int = size

    override fun get(i0: Int): Double {
        requireValidIndices(i0)
        return array.getDouble(convertIndex(i0))
    }

    override fun set(i0: Int, value: Double) {
        requireValidIndices(i0)
        array.setDouble(convertIndex(i0), value)
    }

}

