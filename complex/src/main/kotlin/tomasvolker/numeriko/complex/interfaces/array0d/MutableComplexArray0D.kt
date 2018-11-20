package tomasvolker.numeriko.complex.interfaces.array0d

import tomasvolker.numeriko.complex.Complex
import tomasvolker.numeriko.complex.toComplex
import tomasvolker.numeriko.core.interfaces.array0d.generic.MutableArray0D
import tomasvolker.numeriko.core.interfaces.array0d.numeric.MutableNumericArray0D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.MutableNumericArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.NumericArrayND

interface MutableComplexArray0D: ComplexArray0D, MutableNumericArray0D<Complex> /*, MutableComplexArrayND */{

    override fun getView(vararg indices: IntProgression): MutableComplexArray0D {
        requireValidIndices(indices)
        return getView()
    }
    /*
    override fun setValue(value: Double, vararg indices: Int): Unit =
            setDouble(value, *indices)

    override fun setDouble(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        setDouble(value)
    }
    */
/*
    override fun setValue(value: Double): Unit = setDouble(value)

    override fun setDouble(value: Double) = setValue(value.toComplex())
    override fun setFloat (value: Float ) = setValue(value.toComplex())
    override fun setLong  (value: Long  ) = setValue(value.toComplex())
    override fun setInt   (value: Int   ) = setValue(value.toComplex())
    override fun setShort (value: Short ) = setValue(value.toComplex())
*/
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
