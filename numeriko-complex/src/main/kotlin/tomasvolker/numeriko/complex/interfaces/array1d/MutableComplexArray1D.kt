package tomasvolker.numeriko.complex.interfaces.array1d

import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.complex.interfaces.array0d.MutableComplexArray0D
import tomasvolker.numeriko.complex.interfaces.array1d.view.DefaultComplexArray1DView
import tomasvolker.numeriko.complex.interfaces.array1d.view.defaultComplexArray0DView
import tomasvolker.numeriko.complex.interfaces.arraynd.MutableComplexArrayND
import tomasvolker.numeriko.complex.interfaces.factory.copy
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.lowrank.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.lowrank.interfaces.array1d.generic.forEachIndex
import tomasvolker.numeriko.lowrank.interfaces.array1d.numeric.MutableNumericArray1D
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import kotlin.math.cos
import kotlin.math.sin

interface MutableComplexArray1D: MutableNumericArray1D<Complex>, ComplexArray1D, MutableComplexArrayND {

    override fun as1D() = this

    override fun as0D(): Nothing = TODO()
    override fun as2D(): Nothing = TODO()

    override fun setValue(indices: IntArray, value: Complex) {
        requireValidIndices(indices)
        setValue(indices[0], value)
    }

    override fun setReal(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        setReal(value, *indices)
    }

    override fun setImag(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        setImag(value, *indices)
    }

    override fun getView(vararg indices: IntProgression): MutableComplexArray1D {
        requireValidIndices(indices)
        return getView(indices[0])
    }

    override fun getView(i0: Int): MutableComplexArray0D =
            defaultComplexArray0DView(this, i0)

    override fun getView(i0: IntProgression): MutableComplexArray1D =
            DefaultComplexArray1DView(
                    array = this,
                    offset = i0.first,
                    size = i0.count(),
                    stride = i0.step
            )

    override fun getView(i0: IndexProgression): MutableComplexArray1D =
            getView(i0.computeProgression(size))

    override fun lowerRank(axis: Int): MutableComplexArray0D =
            super<ComplexArray1D>.lowerRank(axis).asMutable()

    override fun arrayAlongAxis(axis: Int, index: Int): MutableComplexArray0D =
            super<ComplexArray1D>.arrayAlongAxis(axis, index).asMutable()

    operator fun set(i0: Int, value: Complex): Unit = setValue(i0, value)
    operator fun set(i0: Index, value: Complex): Unit = setValue(value, i0)

    fun setValue(value: ComplexArray1D) {
        forEachIndex { i0 ->
            setReal(value.real(i0), i0)
            setImag(value.imag(i0), i0)
        }
    }

    override fun setValue(i0: Int, value: Complex) {
        setReal(value.real, i0)
        setImag(value.imag, i0)
    }

    fun setReal(value: Double, i0: Int)
    fun setImag(value: Double, i0: Int)

    override fun copy(): MutableComplexArray1D = copy(this).asMutable()

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

    override fun applyPlus(other: Double): MutableComplexArray1D =
            applyElementWise { it + other }

    override fun applyMinus(other: Double): MutableComplexArray1D =
            applyElementWise { it - other }

    override fun applyTimes(other: Double): MutableComplexArray1D =
            applyElementWise { it * other }

    override fun applyDiv(other: Double): MutableComplexArray1D =
            applyElementWise { it / other }

    override fun applyPlus(other: Int): MutableComplexArray1D =
            applyPlus(other.toDouble())

    override fun applyMinus(other: Int): MutableComplexArray1D =
            applyMinus(other.toDouble())

    override fun applyTimes(other: Int): MutableComplexArray1D =
            applyTimes(other.toDouble())

    override fun applyDiv(other: Int): MutableComplexArray1D =
            applyDiv(other.toDouble())

}
