package tomasvolker.numeriko.complex.interfaces.array2d

import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.complex.interfaces.array0d.MutableComplexArray0D
import tomasvolker.numeriko.complex.interfaces.array1d.ComplexArray1D
import tomasvolker.numeriko.complex.interfaces.array1d.MutableComplexArray1D
import tomasvolker.numeriko.complex.interfaces.array2d.view.DefaultMutableComplexArray2DTransposeView
import tomasvolker.numeriko.complex.interfaces.array2d.view.defaultComplexArray2DView
import tomasvolker.numeriko.complex.interfaces.arraynd.MutableComplexArrayND
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.complex.primitives.toComplex
import tomasvolker.numeriko.core.interfaces.array2d.generic.*
import tomasvolker.numeriko.core.preconditions.requireSameShape
import tomasvolker.numeriko.complex.interfaces.factory.*
import tomasvolker.numeriko.core.interfaces.array2d.numeric.MutableNumericArray2D
import kotlin.math.cos
import kotlin.math.sin

interface MutableComplexArray2D: ComplexArray2D, MutableNumericArray2D<Complex>, MutableComplexArrayND {

    override fun setValue(value: Complex, vararg indices: Int) {
        requireValidIndices(indices)
        setValue(value, indices[0], indices[1])
    }

    override fun setReal(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        setReal(value, indices[0], indices[1])
    }

    override fun setImag(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        setImag(value, indices[0], indices[1])
    }

    override fun lowerRank(axis: Int): MutableComplexArray1D =
            super<ComplexArray2D>.lowerRank(axis).asMutable()

    override fun arrayAlongAxis(axis: Int, index: Int): MutableComplexArray1D =
            super<ComplexArray2D>.arrayAlongAxis(axis, index).asMutable()

    override fun getView(vararg indices: IndexProgression): MutableComplexArray2D =
            getView(*indices.computeIndices())

    override fun getView(vararg indices: IntProgression): MutableComplexArray2D {
        requireValidIndices(indices)
        return getView(indices[0], indices[1])
    }

    override fun setValue(value: Complex, i0: Int, i1: Int) {
        setReal(value.real, i0, i1)
        setImag(value.imag, i0, i1)
    }

    fun setReal(value: Double, i0: Int, i1: Int)
    fun setImag(value: Double, i0: Int, i1: Int)

    fun setAbs(value: Double, i0: Int, i1: Int) {
        val arg = arg(i0, i1)
        setReal(value * cos(arg), i0, i1)
        setImag(value * sin(arg), i0, i1)
    }

    fun setArg(value: Double, i0: Int, i1: Int) {
        val abs = abs(i0, i1)
        setReal(abs * cos(value), i0, i1)
        setImag(abs * sin(value), i0, i1)
    }

    fun setValue(other: ComplexArray2D) {

        requireSameShape(this, other)

        forEachIndex { i0, i1 ->
            setReal(other.real(i0, i1), i0, i1)
            setImag(other.imag(i0, i1), i0, i1)
        }

    }

    override fun setValue(value: Complex) {

        forEachIndex { i0, i1 ->
            setReal(value.real, i0, i1)
            setImag(value.imag, i0, i1)
        }

    }

    override fun getView(i0: Int  , i1: Int  ): MutableComplexArray0D = defaultComplexArray2DView(this.asMutable(), i0, i1)
    override fun getView(i0: Int  , i1: Index): MutableComplexArray0D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: Int  ): MutableComplexArray0D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: Index): MutableComplexArray0D = getView(i0.compute(0), i1.compute(1))

    override fun getView(i0: Int  , i1: IntProgression  ): MutableComplexArray1D = defaultComplexArray2DView(this, i0, i1)
    override fun getView(i0: Int  , i1: IndexProgression): MutableComplexArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: IntProgression  ): MutableComplexArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: IndexProgression): MutableComplexArray1D = getView(i0.compute(0), i1.compute(1))

    override fun getView(i0: IntProgression  , i1: Int  ): MutableComplexArray1D = defaultComplexArray2DView(this, i0, i1)
    override fun getView(i0: IndexProgression, i1: Int  ): MutableComplexArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IntProgression  , i1: Index): MutableComplexArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: Index): MutableComplexArray1D = getView(i0.compute(0), i1.compute(1))

    override fun getView(i0: IntProgression, i1: IntProgression): MutableComplexArray2D = defaultComplexArray2DView(this, i0, i1)
    override fun getView(i0: IntProgression  , i1: IndexProgression): MutableComplexArray2D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: IntProgression  ): MutableComplexArray2D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: IndexProgression): MutableComplexArray2D = getView(i0.compute(0), i1.compute(1))

    fun setView(value: ComplexArray1D, i0: Int, i1: IntProgression  ) = getView(i0, i1).setValue(value.copy())
    fun setView(value: ComplexArray1D, i0: Index, i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: ComplexArray1D, i0: Int  , i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: ComplexArray1D, i0: Index, i1: IntProgression  ) = setView(value, i0.compute(0), i1.compute(1))

    fun setView(value: ComplexArray1D, i0: IntProgression  , i1: Int  ) = getView(i0, i1).setValue(value.copy())
    fun setView(value: ComplexArray1D, i0: IntProgression  , i1: Index) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: ComplexArray1D, i0: IndexProgression, i1: Int  ) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: ComplexArray1D, i0: IndexProgression, i1: Index) = setView(value, i0.compute(0), i1.compute(1))

    fun setView(value: ComplexArray2D, i0: IntProgression  , i1: IntProgression  ) = getView(i0, i1).setValue(value.copy())
    fun setView(value: ComplexArray2D, i0: IndexProgression, i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: ComplexArray2D, i0: IntProgression  , i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: ComplexArray2D, i0: IndexProgression, i1: IntProgression  ) = setView(value, i0.compute(0), i1.compute(1))

    override fun setView(value: Complex, i0: IntProgression, i1: IntProgression  ) = getView(i0, i1).setValue(value)
    override fun setView(value: Complex, i0: IntProgression, i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))
    override fun setView(value: Complex, i0: IndexProgression, i1: IntProgression  ) = setView(value, i0.compute(0), i1.compute(1))
    override fun setView(value: Complex, i0: IndexProgression, i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))

    override fun copy(): MutableComplexArray2D = copy(this).asMutable()

    // Get operators

    override operator fun get(i0: Int  , i1: IntProgression  ): MutableComplexArray1D = getView(i0, i1)
    override operator fun get(i0: Index, i1: IntProgression  ): MutableComplexArray1D = getView(i0, i1)
    override operator fun get(i0: Int  , i1: IndexProgression): MutableComplexArray1D = getView(i0, i1)
    override operator fun get(i0: Index, i1: IndexProgression): MutableComplexArray1D = getView(i0, i1)

    override operator fun get(i0: IntProgression  , i1: Int  ): MutableComplexArray1D = getView(i0, i1)
    override operator fun get(i0: IntProgression  , i1: Index): MutableComplexArray1D = getView(i0, i1)
    override operator fun get(i0: IndexProgression, i1: Int  ): MutableComplexArray1D = getView(i0, i1)
    override operator fun get(i0: IndexProgression, i1: Index): MutableComplexArray1D = getView(i0, i1)

    override operator fun get(i0: IntProgression  , i1: IntProgression  ): MutableComplexArray2D = getView(i0, i1)
    override operator fun get(i0: IntProgression  , i1: IndexProgression): MutableComplexArray2D = getView(i0, i1)
    override operator fun get(i0: IndexProgression, i1: IntProgression  ): MutableComplexArray2D = getView(i0, i1)
    override operator fun get(i0: IndexProgression, i1: IndexProgression): MutableComplexArray2D = getView(i0, i1)

    // Set operators

    operator fun set(i0: Int  , i1: Int  , value: Complex) = setValue(value, i0, i1)
    operator fun set(i0: Int  , i1: Index, value: Complex) = setValue(value, i0, i1)
    operator fun set(i0: Index, i1: Int  , value: Complex) = setValue(value, i0, i1)
    operator fun set(i0: Index, i1: Index, value: Complex) = setValue(value, i0, i1)

    operator fun set(i0: IntProgression  , i1: Int  , value: ComplexArray1D) = setView(value, i0, i1)
    operator fun set(i0: IntProgression  , i1: Index, value: ComplexArray1D) = setView(value, i0, i1)
    operator fun set(i0: IndexProgression, i1: Int  , value: ComplexArray1D) = setView(value, i0, i1)
    operator fun set(i0: IndexProgression, i1: Index, value: ComplexArray1D) = setView(value, i0, i1)

    operator fun set(i0: Int  , i1: IntProgression  , value: ComplexArray1D) = setView(value, i0, i1)
    operator fun set(i0: Int  , i1: IndexProgression, value: ComplexArray1D) = setView(value, i0, i1)
    operator fun set(i0: Index, i1: IntProgression  , value: ComplexArray1D) = setView(value, i0, i1)
    operator fun set(i0: Index, i1: IndexProgression, value: ComplexArray1D) = setView(value, i0, i1)

    operator fun set(i0: IntProgression  , i1: IntProgression  , value: ComplexArray2D) = setView(value, i0, i1)
    operator fun set(i0: IntProgression  , i1: IndexProgression, value: ComplexArray2D) = setView(value, i0, i1)
    operator fun set(i0: IndexProgression, i1: IntProgression  , value: ComplexArray2D) = setView(value, i0, i1)
    operator fun set(i0: IndexProgression, i1: IndexProgression, value: ComplexArray2D) = setView(value, i0, i1)


    fun applyPlus (other: ComplexArray2D): MutableComplexArray2D = applyElementWise(other) { t, o -> t + o }
    fun applyMinus(other: ComplexArray2D): MutableComplexArray2D = applyElementWise(other) { t, o -> t - o }
    fun applyTimes(other: ComplexArray2D): MutableComplexArray2D = applyElementWise(other) { t, o -> t * o }
    fun applyDiv  (other: ComplexArray2D): MutableComplexArray2D = applyElementWise(other) { t, o -> t / o }

    fun applyPlus (other: Number): MutableComplexArray2D = applyElementWise { it + other.toComplex() }
    fun applyMinus(other: Number): MutableComplexArray2D = applyElementWise { it - other.toComplex() }
    fun applyTimes(other: Number): MutableComplexArray2D = applyElementWise { it * other.toComplex() }
    fun applyDiv  (other: Number): MutableComplexArray2D = applyElementWise { it / other.toComplex() }

    override fun transpose(): MutableComplexArray2D = DefaultMutableComplexArray2DTransposeView(this.asMutable())

}
