package tomasvolker.numeriko.core.interfaces.array1d.generic

fun defaultEquals(array1: Array1D<*>, array2: Array1D<*>): Boolean {

    if (array1.size != array2.size)
        return false

    for (i in array1.indices) {
        if (array1.getValue(i) != array2.getValue(i))
            return false
    }

    return true
}

fun defaultHashCode(array1: Array1D<*>): Int {

    var result = array1.rank.hashCode()
    result += 31 * result + array1.size.hashCode()
    for (x in array1) {
        result += 31 * result + (x?.hashCode() ?: 0)
    }

    return result
}

fun defaultToString(array1: Array1D<*>): String =
        array1.joinToString(
                separator = ", ",
                prefix = "[ ",
                postfix = " ]",
                limit = 20,
                truncated = "..."
        )


class DefaultArray1DIterator<T>(
        val array: Array1D<T>
): Iterator<T> {

    var index = 0

    override fun hasNext(): Boolean =
            index < array.size

    override fun next(): T =
            array.getValue(index).apply { index++ }

}

open class DefaultArray1DView<out T>(
        open val array: Array1D<T>,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : Array1D<T> {

    override fun getValue(index: Int): T {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        return array.getValue(offset + stride * index)
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Array1D<*>) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

open class DefaultMutableArray1DView<T>(
        open val array: MutableArray1D<T>,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : MutableArray1D<T> {

    override fun setValue(value: T, index: Int) {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        array.setValue(value, offset + stride * index)
    }

    override fun getValue(index: Int): T {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        return array.getValue(offset + stride * index)
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Array1D<*>) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}
