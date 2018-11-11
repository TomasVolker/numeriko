package tomasvolker.numeriko.core.interfaces.array2d.generic.view

import tomasvolker.numeriko.core.interfaces.array2d.generic.*

open class DefaultArray2DTransposeView<out T>(
        open val array: Array2D<T>
) : Array2D<T> {

    override val shape0: Int
        get() = array.shape1

    override val shape1: Int
        get() = array.shape0

    override fun getValue(i0: Int, i1: Int): T {
        checkIndices(i0, i1)
        return array.getValue(i1, i0)
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Array2D<*>) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

open class DefaultMutableArray2DTransposeView<T>(
        open val array: MutableArray2D<T>
) : MutableArray2D<T> {

    override val shape0: Int
        get() = array.shape1

    override val shape1: Int
        get() = array.shape0

    override fun getValue(i0: Int, i1: Int): T {
        checkIndices(i0, i1)
        return array.getValue(i1, i0)
    }

    override fun setValue(value: T, i0: Int, i1: Int) {
        checkIndices(i0, i1)
        return array.setValue(value, i1, i0)
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Array2D<*>) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

