package tomasvolker.numeriko.core.jvm.int.array1d

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.generic.array1d.ReadOnlyArray1D
import tomasvolker.numeriko.core.interfaces.generic.array1d.defaultToString
import tomasvolker.numeriko.core.interfaces.generic.arraynd.NDArrayCursor
import tomasvolker.numeriko.core.interfaces.generic.arraynd.NDArrayLinearCursor
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ReadOnlyNDArrayLinearCursor
import tomasvolker.numeriko.core.interfaces.int.array1d.IntArray1D
import tomasvolker.numeriko.core.interfaces.int.array1d.ReadOnlyIntArray1D

class JvmIntArray1D(val data: IntArray): IntArray1D {

    override fun getView(i0: IntProgression): IntArray1D {
        TODO("not implemented")
    }

    override fun getView(i0: IndexProgression): IntArray1D {
        TODO("not implemented")
    }

    override fun setValue(value: ReadOnlyArray1D<Int>, indices: IntProgression) {
        getView(indices).setAll { i -> value.getValue(i) }
    }

    override fun setValue(value: ReadOnlyArray1D<Int>, indices: IndexProgression) {
        getView(indices).setAll { i -> value.getValue(i) }
    }

    override fun getInt(i0: Int) = data[i0]

    override fun getInt(i0: Index) = data[i0.computeValue(size)]

    override val shape: ReadOnlyIntArray1D get() = JvmIntArray1D(intArrayOf(size))

    override val size: Int get() = data.size

    override fun setInt(value: Int, i0: Int) {
        data[i0] = value
    }

    override fun setInt(value: Int, i0: Index) {
        data[i0.computeValue(size)]
    }

    override fun setAll(setter: (indexArray: Int) -> Int) {
        for(i in data.indices) {
            data[i] = setter(i)
        }
    }

    override fun copy() = JvmIntArray1D(data.copyOf())

    override fun getDataAsIntArray() = data.copyOf()

    override fun getDataAsArray() = data.toTypedArray()

    override fun unsafeGetDataAsArray() = getDataAsArray()

    override fun getShapeAsArray() = intArrayOf(data.size)

    override fun unsafeGetShapeAsArray() = getShapeAsArray()

    override fun linearCursor(): NDArrayLinearCursor<Int> {
        TODO("not implemented")
    }

    override fun cursor(): NDArrayCursor<Int> {
        TODO("not implemented")
    }

    override fun toString(): String = defaultToString()

}