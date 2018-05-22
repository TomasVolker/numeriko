package tomasvolker.numeriko.core.interfaces.double.array1d

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.double.util.defaultEquals
import tomasvolker.numeriko.core.interfaces.double.util.defaultHashCode
import tomasvolker.numeriko.core.interfaces.factory.mutableCopy
import tomasvolker.numeriko.core.interfaces.generic.array1d.MutableArray1D
import tomasvolker.numeriko.core.interfaces.generic.util.defaultToString

interface MutableDoubleArray1D: DoubleArray1D, MutableArray1D<Double> {

    fun setDouble(value: Double, index: Int)

    fun setDouble(value: Double, index: Index) =
            setDouble(value, index.computeValue(size))

    override fun setValue(value: Double, index: Int) =
            setDouble(value, index)

    fun setValue(other: DoubleArray1D) {

        require(other.size == this.size) {
            "Sizes must much"
        }

        for (i in indices) {
            setDouble(other.getDouble(i), i)
        }

    }

    override fun setValue(value: Double) = setDouble(value)

    fun setDouble(value: Double) {

        for (i in indices) {
            setDouble(value, i)
        }

    }

    override fun getView(indexRange: IntProgression): MutableDoubleArray1D =
            DefaultMutableDoubleArray1DView(
                    array = this,
                    offset = indexRange.first,
                    size = indexRange.count(),
                    stride = indexRange.step
            )

    override fun getView(indexRange: IndexProgression): MutableDoubleArray1D =
            getView(indexRange.computeProgression(size))

    fun setView(value: DoubleArray1D, indexRange: IndexProgression) =
            setView(value, indexRange.computeProgression(size))

    // TODO Avoid copy when possible
    fun setView(value: DoubleArray1D, indexRange: IntProgression) =
            getView(indexRange).setValue(value.copy())

    override fun setView(value: Double, indexRange: IndexProgression) =
            setView(value, indexRange.computeProgression(size))

    override fun setView(value: Double, indexRange: IntProgression) =
            getView(indexRange).setDouble(value)

    override fun copy(): MutableDoubleArray1D = mutableCopy(this)

    override operator fun get(index: IntProgression): MutableDoubleArray1D = getView(index)
    override operator fun get(index: IndexProgression): MutableDoubleArray1D = getView(index)

    operator fun set(index: Int, value: Double) = setValue(value, index)
    operator fun set(index: Index, value: Double) = setValue(value, index)

    operator fun set(index: IntProgression, value: Double) = setView(value, index)
    operator fun set(index: IndexProgression, value: Double) = setView(value, index)

    operator fun set(index: IntProgression, value: DoubleArray1D) = setView(value, index)
    operator fun set(index: IndexProgression, value: DoubleArray1D) = setView(value, index)

    fun applyPlus(other: DoubleArray1D): MutableDoubleArray1D =
            applyElementWise(other) { t, o -> t + o }

    fun applyMinus(other: DoubleArray1D): MutableDoubleArray1D =
            applyElementWise(other) { t, o -> t - o }

    fun applyTimes(other: DoubleArray1D): MutableDoubleArray1D =
            applyElementWise(other) { t, o -> t * o }

    fun applyDiv(other: DoubleArray1D): MutableDoubleArray1D =
            applyElementWise(other) { t, o -> t / o }

    fun applyPlus(other: Double): MutableDoubleArray1D =
            applyElementWise { it + other }

    fun applyMinus(other: Double): MutableDoubleArray1D =
            applyElementWise { it - other }

    fun applyTimes(other: Double): MutableDoubleArray1D =
            applyElementWise { it * other }

    fun applyDiv(other: Double): MutableDoubleArray1D =
            applyElementWise { it / other }

    fun applyPlus(other: Int): MutableDoubleArray1D =
            applyPlus(other.toDouble())

    fun applyMinus(other: Int): MutableDoubleArray1D =
            applyMinus(other.toDouble())

    fun applyTimes(other: Int): MutableDoubleArray1D =
            applyTimes(other.toDouble())

    fun applyDiv(other: Int): MutableDoubleArray1D =
            applyDiv(other.toDouble())

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
