package tomasvolker.numeriko.core.interfaces.arraynd.double.view

import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.preconditions.requireValidIndices

// Non parallelizable
inline fun doubleArrayNDView(
        array: MutableDoubleArrayND,
        shape: IntArrayND,
        crossinline convertIndices: (source: IntArray, target: IntArray)->Unit
): MutableDoubleArrayND = object: DefaultMutableDoubleArrayND() {

    override val shape: IntArrayND = shape

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
