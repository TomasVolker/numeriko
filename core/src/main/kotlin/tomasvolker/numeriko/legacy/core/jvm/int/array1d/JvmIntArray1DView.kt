package tomasvolker.numeriko.legacy.core.jvm.int.array1d

import tomasvolker.numeriko.legacy.core.index.Index
import tomasvolker.numeriko.legacy.core.index.IndexProgression
import tomasvolker.numeriko.legacy.core.interfaces.generic.array1d.ReadOnlyArray1D
import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.IntArray1D
import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.legacy.core.interfaces.integer.arraynd.IntArrayNDCursor
import tomasvolker.numeriko.legacy.core.interfaces.integer.arraynd.IntArrayNDIterator
/*
class JvmIntArray1DView internal constructor(
        val data: IntArray,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : IntArray1D {

    override val shape: ReadOnlyIntArray1D
        get() = JvmIntArray1D(intArrayOf(size))

    override fun getView(i0: IntProgression): IntArray1D =
            JvmIntArray1DView(
                data = data,
                offset = offset + i0.first * stride,
                size = i0.count(),
                stride = i0.step * stride
            )

    override fun getView(i0: IndexProgression): IntArray1D =
            getView(i0.computeProgression(size))

    override fun setValue(value: ReadOnlyArray1D<Int>, indices: IntProgression) {
        TODO("not implemented")
    }

    override fun setValue(value: ReadOnlyArray1D<Int>, indices: IndexProgression) {
        TODO("not implemented")
    }

    //TODO check bounds
    override fun getInt(i0: Int) = data[offset + i0 * stride]

    override fun getInt(i0: Index) = data[offset + i0.computeValue(size) * stride]

    override fun setInt(value: Int, i0: Int) {
        data[offset + i0 * stride] = value
    }

    override fun setInt(value: Int, i0: Index) {
        data[offset + i0.computeValue(size) * stride] = value
    }

    override fun setAll(setter: (indexArray: Int) -> Int) {
        for (i in 0 until size) {
            setInt(setter(i), i)
        }
    }

    override fun getDataAsArray() = toTypedArray()

    override fun unsafeGetDataAsArray() = getDataAsArray()

    override fun getShapeAsArray() = intArrayOf(size)

    override fun unsafeGetShapeAsArray() = getShapeAsArray()

    override fun copy() = TODO()

    override fun linearCursor(): IntArrayNDIterator {
        TODO("not implemented")
    }

    override fun cursor(): IntArrayNDCursor {
        TODO("not implemented")
    }

    override fun toString(): String = defaultToString()

}
*/