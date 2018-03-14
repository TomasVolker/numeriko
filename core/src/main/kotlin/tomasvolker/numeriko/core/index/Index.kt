package tomasvolker.numeriko.core.index

import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.interfaces.int.arraynd.get

interface Index {

    fun computeValue(size: Int): Int

    operator fun plus(other: Int): Index =
            IndexFunction(this) { it + other }

    operator fun minus(other: Int): Index =
            IndexFunction(this) { it - other }

    operator fun times(other: Int): Index =
            IndexFunction(this) { it * other }

    operator fun div(other: Int): Index =
            IndexFunction(this) { it / other }

    operator fun plus(other: Index): Index =
            IndexFunction2(this, other) { lhs, rhs -> lhs + rhs }

    operator fun minus(other: Index): Index =
            IndexFunction2(this, other) { lhs, rhs -> lhs - rhs }

    operator fun times(other: Index): Index =
            IndexFunction2(this, other) { lhs, rhs -> lhs * rhs }

    operator fun div(other: Index): Index =
            IndexFunction2(this, other) { lhs, rhs -> lhs / rhs }

    operator fun rangeTo(other: Index) =
            IndexProgression(this, other)

    operator fun rangeTo(other: Int) =
            IndexProgression(this, other.toIndex())

    infix fun until(other: Index) =
            IndexProgression(this, other-1)

    infix fun until(other: Int) =
            IndexProgression(this, (other-1).toIndex())

}

open class LiteralIndex(val value: Int): Index {

    override fun computeValue(size: Int) = value

    override operator fun plus(other: Int) = LiteralIndex(value + other)

    override operator fun minus(other: Int) = LiteralIndex(value - other)

    override operator fun times(other: Int) = LiteralIndex(value * other)

    override operator fun div(other: Int) = LiteralIndex(value / other)

    operator fun plus(other: LiteralIndex) = LiteralIndex(this.value + other.value)

    operator fun minus(other: LiteralIndex) = LiteralIndex(this.value - other.value)

    operator fun times(other: LiteralIndex) = LiteralIndex(this.value * other.value)

    operator fun div(other: LiteralIndex) = LiteralIndex(this.value / other.value)

}

object First: LiteralIndex(0)

object Last: Index {
    override fun computeValue(size: Int): Int =
            size - 1
}

class IndexFunction(val index: Index, val function: (index: Int) -> Int): Index {
    override fun computeValue(size: Int): Int =
            function(index.computeValue(size))
}

class IndexFunction2(val lhs: Index, val rhs: Index, val function: (lhs: Int, rhs: Int) -> Int): Index {
    override fun computeValue(size: Int): Int =
            function(lhs.computeValue(size), rhs.computeValue(size))
}

fun Index.map(function: (index: Int) -> Int) = IndexFunction(this, function)

operator fun Int.plus(index: Index) = index.map { this + it }
operator fun Int.minus(index: Index) = index.map { this - it }
operator fun Int.times(index: Index) = index.map { this * it }
operator fun Int.div(index: Index) = index.map { this / it }

operator fun Int.plus(index: LiteralIndex) = LiteralIndex(this + index.value)
operator fun Int.minus(index: LiteralIndex) = LiteralIndex(this - index.value)
operator fun Int.times(index: LiteralIndex) = LiteralIndex(this * index.value)
operator fun Int.div(index: LiteralIndex) = LiteralIndex(this / index.value)

fun Int.toIndex() = LiteralIndex(this)