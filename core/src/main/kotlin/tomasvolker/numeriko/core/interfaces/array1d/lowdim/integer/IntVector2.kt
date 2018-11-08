package tomasvolker.numeriko.core.interfaces.array1d.lowdim.integer

import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultToString
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.MutableIntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.defaultEquals
import tomasvolker.numeriko.core.interfaces.array1d.integer.defaultHashCode
import tomasvolker.numeriko.core.interfaces.array1d.lowdim.Generic.MutableVector2
import tomasvolker.numeriko.core.interfaces.array1d.lowdim.Generic.Vector2

interface IntVector2: Vector2<Int>, IntArray1D {

    val value0: Int get() = getInt(0)
    val value1: Int get() = getInt(1)

    operator fun plus(other: IntVector2): IntVector2 =
            elementWise(this, other) { t, o -> t + o }

    operator fun minus(other: IntVector2): IntVector2 =
            elementWise(this, other) { t, o -> t - o }

    operator fun times(other: IntVector2): IntVector2 =
            elementWise(this, other) { t, o -> t * o }

    operator fun div(other: IntVector2): IntVector2 =
            elementWise(this, other) { t, o -> t / o }

    override fun copy(): IntArray1D = MutableIntVector2(value0, value1)

}

fun intVector2(value0: Int, value1: Int): IntVector2 =
        MutableIntVector2(value0, value1)

class MutableIntVector2(
        override var value0: Int,
        override var value1: Int
): IntVector2, MutableVector2<Int>, MutableIntArray1D {

    override fun getInt(index: Int) = when(index) {
        0 -> value0
        1 -> value1
        else -> throw IndexOutOfBoundsException(index)
    }

    override fun setInt(value: Int, index: Int) = when(index) {
        0 -> value0 = value
        1 -> value1 = value
        else -> throw IndexOutOfBoundsException(index)
    }

    override fun copy() = MutableIntVector2(value0, value1)

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is IntArray1D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

    operator fun component1(): Int = value0
    operator fun component2(): Int = value1

}


inline fun elementWise(source: IntVector2, destination: MutableIntVector2, operation: (Int) -> Int) {
    destination.setValue(operation(source.value0), 0)
    destination.setValue(operation(source.value1), 0)
}

inline fun elementWise(
        source1: IntVector2,
        source2: IntVector2,
        destination: MutableIntVector2,
        operation: (Int, Int) -> Int
) {
    destination.setValue(operation(source1.value0, source2.value0), 0)
    destination.setValue(operation(source1.value1, source2.value1), 1)
}

inline fun elementWise(
        source1: IntVector2,
        source2: IntVector2,
        operation: (Int, Int) -> Int
) = MutableIntVector2(
        operation(source1.value0, source2.value0),
        operation(source1.value1, source2.value1)
)


inline fun IntVector2.elementWise(operation: (Int) -> Int): IntVector2 {
    val result = MutableIntVector2(0, 0)
    elementWise(
            source = this,
            destination = result,
            operation = operation
    )
    return result

}
