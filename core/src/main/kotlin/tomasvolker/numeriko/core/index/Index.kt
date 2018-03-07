package tomasvolker.numeriko.core.index

import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.interfaces.integer.get

interface AbstractIndex {

    fun computeValue(shape: ReadOnlyIntNDArray, dimension: Int): Int

    operator fun plus(other: Int): AbstractIndex =
            IndexFunction(this) { it + other }

    operator fun minus(other: Int): AbstractIndex =
            IndexFunction(this) { it - other }

    operator fun times(other: Int): AbstractIndex =
            IndexFunction(this) { it * other }

    operator fun div(other: Int): AbstractIndex =
            IndexFunction(this) { it / other }

    operator fun plus(other: AbstractIndex): AbstractIndex =
            IndexFunction2(this, other) { lhs, rhs -> lhs + rhs }

    operator fun minus(other: AbstractIndex): AbstractIndex =
            IndexFunction2(this, other) { lhs, rhs -> lhs - rhs }

    operator fun times(other: AbstractIndex): AbstractIndex =
            IndexFunction2(this, other) { lhs, rhs -> lhs * rhs }

    operator fun div(other: AbstractIndex): AbstractIndex =
            IndexFunction2(this, other) { lhs, rhs -> lhs / rhs }

    operator fun rangeTo(other: AbstractIndex) =
            IndexProgression(this, other)

    operator fun rangeTo(other: Int) =
            IndexProgression(this, other.toIndex())

    infix fun until(other: AbstractIndex) =
            IndexProgression(this, other-1)

    infix fun until(other: Int) =
            IndexProgression(this, (other-1).toIndex())

}

open class Index(val value: Int): AbstractIndex {

    override fun computeValue(shape: ReadOnlyIntNDArray, dimension: Int) = value

    override operator fun plus(other: Int) = Index(value + other)

    override operator fun minus(other: Int) = Index(value - other)

    override operator fun times(other: Int) = Index(value * other)

    override operator fun div(other: Int) = Index(value / other)

    operator fun plus(other: Index) = Index(this.value + other.value)

    operator fun minus(other: Index) = Index(this.value - other.value)

    operator fun times(other: Index) = Index(this.value * other.value)

    operator fun div(other: Index) = Index(this.value / other.value)

}

object First: Index(0)

object Last: AbstractIndex {
    override fun computeValue(shape: ReadOnlyIntNDArray, dimension: Int): Int =
            shape[dimension] - 1
}

class IndexFunction(val index: AbstractIndex, val function: (index: Int) -> Int): AbstractIndex {
    override fun computeValue(shape: ReadOnlyIntNDArray, dimension: Int): Int =
            function(index.computeValue(shape, dimension))
}

class IndexFunction2(val lhs: AbstractIndex, val rhs: AbstractIndex, val function: (lhs: Int, rhs: Int) -> Int): AbstractIndex {
    override fun computeValue(shape: ReadOnlyIntNDArray, dimension: Int): Int =
            function(lhs.computeValue(shape, dimension), rhs.computeValue(shape, dimension))
}

fun AbstractIndex.map(function: (index: Int) -> Int) = IndexFunction(this, function)

operator fun Int.plus(index: AbstractIndex) = index.map { this + it }
operator fun Int.minus(index: AbstractIndex) = index.map { this - it }
operator fun Int.times(index: AbstractIndex) = index.map { this * it }
operator fun Int.div(index: AbstractIndex) = index.map { this / it }

operator fun Int.plus(index: Index) = Index(this + index.value)
operator fun Int.minus(index: Index) = Index(this - index.value)
operator fun Int.times(index: Index) = Index(this * index.value)
operator fun Int.div(index: Index) = Index(this / index.value)

fun Int.toIndex() = Index(this)