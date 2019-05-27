package tomasvolker.core.implementations.numeriko.array1d.integer

import numeriko.lowrank.interfaces.array1d.generic.lastIndex
import numeriko.lowrank.interfaces.array1d.integer.MutableIntArray1D
import numeriko.lowrank.interfaces.array1d.integer.view.DefaultMutableIntArray1D

class NumerikoIntArray1D(
        val data: IntArray
): DefaultMutableIntArray1D() {

    override val size: Int get() = data.size

    override fun getInt(i0: Int): Int = data[i0]

    override fun setInt(i0: Int, value: Int) {
        data[i0] = value
    }

    override fun getView(i0: IntProgression): MutableIntArray1D {
        requireValidIndexRange(i0)
        return NumerikoMutableIntArray1DView(
                data = data,
                offset = i0.first,
                size = i0.count(),
                stride = i0.step
        )
    }

}

class NumerikoMutableIntArray1DView(
        val data: IntArray,
        val offset: Int,
        override val size: Int,
        val stride: Int
): DefaultMutableIntArray1D() {

    init {
        require(offset in 0 until data.size)
        require(convertIndex(lastIndex) in 0 until data.size)
    }

    override fun getInt(i0: Int): Int {
        requireValidIndices(i0)
        return data[convertIndex(i0)]
    }

    override fun setInt(i0: Int, value: Int) {
        requireValidIndices(i0)
        data[convertIndex(i0)] = value
    }

    fun convertIndex(i0: Int): Int = offset + stride * i0

    override fun getView(i0: IntProgression): MutableIntArray1D {
        requireValidIndexRange(i0)
        return NumerikoMutableIntArray1DView(
                data = data,
                offset = convertIndex(i0.first),
                size = i0.count(),
                stride = stride * i0.step
        )
    }

}