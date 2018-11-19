package tomasvolker.numeriko.complex.interfaces.array1d

import tomasvolker.numeriko.complex.Complex
import tomasvolker.numeriko.complex.interfaces.array1d.view.DefaultMutableComplexArray1DView
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.MutableArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.forEachIndex
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

    operator fun set(i0: Int, value: Complex): Unit = setValue(value, i0)
    operator fun set(i0: Index, value: Complex): Unit = setValue(value, i0)
    /*
    fun setValue(value: ComplexArray1D) {
        forEachIndex { i0 ->
            setReal(value.real(i0), i0)
            setImag(value.imag(i0), i0)
        }
    }
    */
    override fun setValue(value: Complex, i0: Int) {
        setReal(value.real, i0)
        setImag(value.imag, i0)
    }

    fun setReal(value: Double, i0: Int)
    fun setImag(value: Double, i0: Int)

    fun setAbs(value: Double, i0: Int) {
        val arg = arg(i0)
        setReal(value * cos(arg), i0)
        setImag(value * sin(arg), i0)
    }

    fun setArg(value: Double, i0: Int) {
        val abs = abs(i0)
        setReal(abs * cos(value), i0)
        setImag(abs * sin(value), i0)
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
