package tomasvolker.numeriko.complex.interfaces.array0d

import tomasvolker.numeriko.complex.Complex
import tomasvolker.numeriko.complex.interfaces.arraynd.MutableComplexArrayND
import tomasvolker.numeriko.complex.toComplex
import tomasvolker.numeriko.core.interfaces.array0d.generic.MutableArray0D
import tomasvolker.numeriko.core.interfaces.array0d.numeric.MutableNumericArray0D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.MutableNumericArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.NumericArrayND

interface MutableComplexArray0D: ComplexArray0D, MutableNumericArray0D<Complex>, MutableComplexArrayND {

    override fun setReal(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        setReal(value, indices[0])
    }

    override fun setImag(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        setImag(value, indices[0])
    }

    override fun setValue(value: Complex) {
        setReal(value.real)
        setImag(value.imag)
    }

    fun setReal(value: Double)
    fun setImag(value: Double)

    override fun setValue(value: Complex, vararg indices: Int) {
        requireValidIndices(indices)
        setValue(value)
    }

    override fun getView(vararg indices: IntProgression): MutableComplexArray0D {
        requireValidIndices(indices)
        return getView()
    }

    override fun getView(): MutableComplexArray0D = this

    override fun lowerRank(axis: Int): Nothing {
        super<ComplexArray0D>.lowerRank(axis)
    }

    override fun arrayAlongAxis(axis: Int, index: Int): Nothing {
        super<ComplexArray0D>.arrayAlongAxis(axis, index)
    }

    fun setValue(other: ComplexArray0D) {
        setValue(other.get())
    }

    fun set(value: Complex): Unit = setValue(value)

}
