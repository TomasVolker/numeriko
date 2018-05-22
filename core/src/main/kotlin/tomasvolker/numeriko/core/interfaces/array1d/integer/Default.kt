package tomasvolker.numeriko.core.interfaces.array1d.integer

import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultToString

fun defaultEquals(array1: IntArray1D, array2: IntArray1D): Boolean {

    if (array1.size != array2.size)
        return false

    for (i in array1.indices) {
        if (array1.getInt(i) != array2.getInt(i))
            return false
    }

    return true
}

fun defaultHashCode(array1: IntArray1D): Int {

    var result = array1.rank.hashCode()
    result += 31 * result + array1.size.hashCode()
    for (x in array1) {
        result += 31 * result + x.hashCode()
    }

    return result
}

class DefaultIntArray1DIterator(
        val array: IntArray1D
): IntIterator() {

    var index = 0

    override fun hasNext(): Boolean =
            index < array.size

    override fun nextInt(): Int =
            array.getInt(index).apply { index++ }

}

class DefaultIntArray1DView(
        val array: IntArray1D,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : IntArray1D {

    override fun getInt(index: Int): Int {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        return array.getInt(offset + stride * index)
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is IntArray1D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

open class DefaultMutableIntArray1DView (
        open val array: MutableIntArray1D,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : MutableIntArray1D {

    override fun setInt(value: Int, index: Int) {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        array.setInt(value, offset + stride * index)
    }

    override fun getInt(index: Int): Int {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        return array.getInt(offset + stride * index)
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is IntArray1D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}
