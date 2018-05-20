package tomasvolker.numeriko.legacy.core.jvm.int.array0d

import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.ReadOnlyArrayND
import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.defaultEquals
import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.defaultHashCode
import tomasvolker.numeriko.legacy.core.interfaces.integer.array0d.IntArray0D
import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.legacy.core.interfaces.integer.arraynd.IntArrayNDCursor
import tomasvolker.numeriko.legacy.core.interfaces.integer.arraynd.IntArrayNDIterator
import tomasvolker.numeriko.legacy.core.interfaces.integer.arraynd.ReadOnlyIntArrayND

class JvmIntArray0D(private var data: Int): IntArray0D {

    override fun getInt(vararg indices: Int): Int {
        TODO("not implemented")
    }

    override fun getInt(indexArray: ReadOnlyIntArray1D): Int {
        TODO("not implemented")
    }

    override fun setInt(value: Int, indexArray: ReadOnlyIntArray1D) {
        TODO("not implemented")
    }

    override fun setInt(value: Int, vararg indices: Int) {
        TODO("not implemented")
    }

    override fun setInt(value: ReadOnlyIntArrayND, vararg indices: Any) {
        TODO("not implemented")
    }

    override fun setValue(value: ReadOnlyArrayND<Int>, vararg indices: Any) {
        TODO("not implemented")
    }

    override fun getInt() = data

    override fun setInt(value: Int) { data = value }

    override fun copy() = JvmIntArray0D(data)

    override fun cursor(): IntArrayNDCursor = TODO()

    override fun toString(): String = data.toString()

    override fun equals(other: Any?) = defaultEquals(other)

    override fun hashCode() = defaultHashCode()

}