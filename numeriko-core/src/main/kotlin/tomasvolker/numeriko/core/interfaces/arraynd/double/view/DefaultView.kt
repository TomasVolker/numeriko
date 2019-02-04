package tomasvolker.numeriko.core.interfaces.arraynd.double.view

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND

// Non parallelizable
inline fun doubleArrayNDView(
        array: MutableDoubleArrayND,
        shape: IntArray1D,
        crossinline convertIndices: (source: IntArray, target: IntArray)->Unit
): MutableDoubleArrayND = object: DefaultMutableDoubleArrayND() {

    override val shape: IntArray1D = shape

    private val indicesBuffer = IntArray(array.rank)

    override fun getDouble(indices: IntArray): Double {
        requireValidIndices(indices)
        convertIndices(indices, indicesBuffer)
        return array.getValue(indicesBuffer)
    }

    override fun setDouble(indices: IntArray, value: Double) {
        requireValidIndices(indices)
        convertIndices(indices, indicesBuffer)
        array.setValue(
                indicesBuffer,
                value
        )
    }

}
