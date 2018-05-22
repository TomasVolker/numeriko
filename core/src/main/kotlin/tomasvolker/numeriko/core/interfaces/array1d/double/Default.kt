package tomasvolker.numeriko.core.interfaces.array1d.double

import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultToString

fun defaultEquals(array1: DoubleArray1D, array2: DoubleArray1D): Boolean {

    if (array1.size != array2.size)
        return false

    for (i in array1.indices) {
        if (array1.getDouble(i) != array2.getDouble(i))
            return false
    }

    return true
}

fun defaultHashCode(array1: DoubleArray1D): Int {

    var result = array1.rank.hashCode()
    result += 31 * result + array1.size.hashCode()
    for (x in array1) {
        result += 31 * result + x.hashCode()
    }

    return result
}

class DefaultDoubleArray1DIterator(
        val array: DoubleArray1D
): DoubleIterator() {

    var index = 0

    override fun hasNext(): Boolean =
            index < array.size

    override fun nextDouble(): Double =
            array.getDouble(index).apply { index++ }

}

class DefaultDoubleArray1DView(
        val array: DoubleArray1D,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : DoubleArray1D {

    override fun getDouble(index: Int): Double {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        return array.getDouble(offset + stride * index)
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleArray1D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

open class DefaultMutableDoubleArray1DView (
        open val array: MutableDoubleArray1D,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : MutableDoubleArray1D {

    override fun setDouble(value: Double, index: Int) {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        array.setDouble(value, offset + stride * index)
    }

    override fun getDouble(index: Int): Double {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        return array.getDouble(offset + stride * index)
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleArray1D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}
