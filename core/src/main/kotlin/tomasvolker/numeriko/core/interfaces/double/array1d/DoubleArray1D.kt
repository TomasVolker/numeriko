package tomasvolker.numeriko.core.interfaces.double.array1d

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.double.util.defaultEquals
import tomasvolker.numeriko.core.interfaces.double.util.defaultHashCode
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.generic.array1d.Array1D
import tomasvolker.numeriko.core.interfaces.generic.util.defaultToString

interface DoubleArray1D: Array1D<Double> {

    override fun getValue(index: Int): Double =
            getDouble(index)

    fun getDouble(index: Int): Double

    fun getDouble(index: Index): Double =
            getDouble(index.computeValue(size))

    override fun getView(indexRange: IntProgression): DoubleArray1D =
            DefaultDoubleArray1DView(
                    array = this,
                    offset = indexRange.first,
                    size = indexRange.count(),
                    stride = indexRange.step
            )

    override fun getView(indexRange: IndexProgression): DoubleArray1D =
            getView(indexRange.computeProgression(size))

    override fun copy(): DoubleArray1D = copy(this)

    override fun iterator(): DoubleIterator =
            DefaultDoubleArray1DIterator(this)

    operator fun get(index: Int): Double = getDouble(index)
    operator fun get(index: Index): Double = getDouble(index)

    operator fun get(index: IntProgression): DoubleArray1D = getView(index)
    operator fun get(index: IndexProgression): DoubleArray1D = getView(index)

    // Return copy?
    operator fun unaryPlus(): DoubleArray1D = this

    operator fun unaryMinus(): MutableDoubleArray1D =
            elementWise { -it }

    operator fun plus(other: DoubleArray1D): MutableDoubleArray1D =
            elementWise(this, other) { t, o -> t + o }

    operator fun minus(other: DoubleArray1D): MutableDoubleArray1D =
            elementWise(this, other) { t, o -> t - o }

    operator fun times(other: DoubleArray1D): MutableDoubleArray1D =
            elementWise(this, other) { t, o -> t * o }

    operator fun div(other: DoubleArray1D): MutableDoubleArray1D =
            elementWise(this, other) { t, o -> t / o }

    operator fun plus(other: Double): MutableDoubleArray1D =
            elementWise { it + other }

    operator fun minus(other: Double): MutableDoubleArray1D =
            elementWise { it - other }

    operator fun times(other: Double): MutableDoubleArray1D =
            elementWise { it * other }

    operator fun div(other: Double): MutableDoubleArray1D =
            elementWise { it / other }

    operator fun plus(other: Int): MutableDoubleArray1D =
            plus(other.toDouble())

    operator fun minus(other: Int): MutableDoubleArray1D =
            minus(other.toDouble())

    operator fun times(other: Int): MutableDoubleArray1D =
            times(other.toDouble())

    operator fun div(other: Int): MutableDoubleArray1D =
            div(other.toDouble())

    fun sum(): Double = sumBy { it }

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
