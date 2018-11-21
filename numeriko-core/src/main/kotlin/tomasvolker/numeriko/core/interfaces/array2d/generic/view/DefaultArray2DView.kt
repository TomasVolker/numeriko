package tomasvolker.numeriko.core.interfaces.array2d.generic.view

import tomasvolker.numeriko.core.interfaces.array2d.generic.*

class DefaultArray2DView<T>(
        val array: MutableArray2D<T>,
        val offset0: Int,
        val offset1: Int,
        override val shape0: Int,
        override val shape1: Int,
        val stride0: Int,
        val stride1: Int
) : DefaultMutableArray2D<T>() {

    override fun getValue(i0: Int, i1: Int): T {
        requireValidIndices(i0, i1)
        return array.getValue(
                offset0 + stride0 * i0,
                offset1 + stride1 * i1
        )
    }

    override fun setValue(value: T, i0: Int, i1: Int) {
        requireValidIndices(i0, i1)
        array.setValue(
                value,
                offset0 + stride0 * i0,
                offset1 + stride1 * i1
        )
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Array2D<*>) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

fun <T> defaultArray2DView(array: MutableArray2D<T>, i0: IntProgression, i1: IntProgression) =
        DefaultArray2DView(
                array = array,
                offset0 = i0.first,
                offset1 = i1.first,
                shape0 = i0.count(),
                shape1 = i1.count(),
                stride0 = i0.step,
                stride1 = i1.step
        )
