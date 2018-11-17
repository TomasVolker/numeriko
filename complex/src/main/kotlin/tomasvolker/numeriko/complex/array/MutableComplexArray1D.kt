package tomasvolker.numeriko.complex.array

import tomasvolker.numeriko.complex.Complex
import tomasvolker.numeriko.complex.array.view.DefaultMutableComplexArray1DView
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.MutableArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.indices
import tomasvolker.numeriko.core.preconditions.requireSameSize
import kotlin.math.cos
import kotlin.math.sin

interface MutableComplexArray1D: MutableArray1D<Complex>, ComplexArray1D {

    override fun getView(i0: IntProgression): MutableComplexArray1D =
            DefaultMutableComplexArray1DView(
                    array = this,
                    offset = i0.first,
                    size = i0.count(),
                    stride = i0.step
            )

    override fun getView(i0: IndexProgression): MutableComplexArray1D =
            getView(i0.computeProgression(size))

    operator fun set(i: Int, value: Complex): Unit = setValue(value, i)
    operator fun set(i: Index, value: Complex): Unit = setValue(value, i)

    override fun setValue(value: Complex, index: Int) {
        setReal(value.real, index)
        setImag(value.imag, index)
    }

    fun setReal(value: Double, i: Int)
    fun setImag(value: Double, i: Int)

    fun setAbs(value: Double, i: Int) {
        val arg = getArg(i)
        setReal(value * cos(arg), i)
        setImag(value * sin(arg), i)
    }

    fun setArg(value: Double, i: Int) {
        val abs = getAbs(i)
        setReal(abs * cos(value), i)
        setImag(abs * sin(value), i)
    }

    fun applyPlus(other: ComplexArray1D): MutableComplexArray1D =
            applyElementWise(other) { t, o -> t + o }

    fun applyMinus(other: ComplexArray1D): MutableComplexArray1D =
            applyElementWise(other) { t, o -> t - o }

    fun applyTimes(other: ComplexArray1D): MutableComplexArray1D =
            applyElementWise(other) { t, o -> t * o }

    fun applyDiv(other: ComplexArray1D): MutableComplexArray1D =
            applyElementWise(other) { t, o -> t / o }

    fun applyPlus(other: DoubleArray1D): MutableComplexArray1D =
            applyElementWise(other) { t, o -> t + o }

    fun applyMinus(other: DoubleArray1D): MutableComplexArray1D =
            applyElementWise(other) { t, o -> t - o }

    fun applyTimes(other: DoubleArray1D): MutableComplexArray1D =
            applyElementWise(other) { t, o -> t * o }

    fun applyDiv(other: DoubleArray1D): MutableComplexArray1D =
            applyElementWise(other) { t, o -> t / o }

    fun applyPlus(other: Complex): MutableComplexArray1D =
            applyElementWise { it + other }

    fun applyMinus(other: Complex): MutableComplexArray1D =
            applyElementWise { it - other }

    fun applyTimes(other: Complex): MutableComplexArray1D =
            applyElementWise { it * other }

    fun applyDiv(other: Complex): MutableComplexArray1D =
            applyElementWise { it / other }

    fun applyPlus(other: Double): MutableComplexArray1D =
            applyElementWise { it + other }

    fun applyMinus(other: Double): MutableComplexArray1D =
            applyElementWise { it - other }

    fun applyTimes(other: Double): MutableComplexArray1D =
            applyElementWise { it * other }

    fun applyDiv(other: Double): MutableComplexArray1D =
            applyElementWise { it / other }

    fun applyPlus(other: Int): MutableComplexArray1D =
            applyPlus(other.toDouble())

    fun applyMinus(other: Int): MutableComplexArray1D =
            applyMinus(other.toDouble())

    fun applyTimes(other: Int): MutableComplexArray1D =
            applyTimes(other.toDouble())

    fun applyDiv(other: Int): MutableComplexArray1D =
            applyDiv(other.toDouble())

}

inline fun MutableComplexArray1D.applyElementWise(
        operation: (Complex) -> Complex
): MutableComplexArray1D {
    for (i in indices) {
        this[i] = operation(this[i])
    }
    return this
}

inline fun MutableComplexArray1D.applyElementWise(
        other: ComplexArray1D,
        operation: (Complex, Complex) -> Complex
): MutableComplexArray1D {
    requireSameSize(this, other)
    for (i in indices) {
        this[i] = operation(this[i], other[i])
    }
    return this
}

inline fun MutableComplexArray1D.applyElementWise(
        other: DoubleArray1D,
        operation: (Complex, Double) -> Complex
): MutableComplexArray1D {
    requireSameSize(this, other)
    for (i in indices) {
        this[i] = operation(this[i], other[i])
    }
    return this
}