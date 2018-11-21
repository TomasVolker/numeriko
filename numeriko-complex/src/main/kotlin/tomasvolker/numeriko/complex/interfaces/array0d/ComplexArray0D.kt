package tomasvolker.numeriko.complex.interfaces.array0d

import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.complex.interfaces.arraynd.ComplexArrayND
import tomasvolker.numeriko.complex.interfaces.factory.copy
import tomasvolker.numeriko.complex.primitives.toComplex
import tomasvolker.numeriko.core.interfaces.array0d.numeric.NumericArray0D

interface ComplexArray0D: NumericArray0D<Complex>, ComplexArrayND {

    override fun real(vararg indices: Int): Double {
        requireValidIndices(indices)
        return real()
    }

    override fun imag(vararg indices: Int): Double {
        requireValidIndices(indices)
        return imag()
    }

    override fun getValue(): Complex = Complex(real(), imag())

    fun real(): Double
    fun imag(): Double

    override fun getValue(vararg indices: Int): Complex {
        requireValidIndices(indices)
        return getValue()
    }

    override fun cast(value: Number): Complex = value.toComplex()

    fun get(): Complex = getValue()

    override fun lowerRank(axis: Int): Nothing {
        super<NumericArray0D>.lowerRank(axis)
    }

    override fun arrayAlongAxis(axis: Int, index: Int): Nothing {
        super<NumericArray0D>.lowerRank(axis)
    }

    override fun getView(): ComplexArray0D = this

    override fun copy(): ComplexArray0D = copy(this)

    override fun asMutable(): MutableComplexArray0D = this as MutableComplexArray0D

    override operator fun unaryPlus(): ComplexArray0D = this
    override operator fun unaryMinus(): ComplexArray0D = elementWise { -it }

    operator fun plus (other: ComplexArray0D): ComplexArray0D = elementWise(this, other) { t, o -> t + o }
    operator fun minus(other: ComplexArray0D): ComplexArray0D = elementWise(this, other) { t, o -> t - o }
    operator fun times(other: ComplexArray0D): ComplexArray0D = elementWise(this, other) { t, o -> t * o }
    operator fun div  (other: ComplexArray0D): ComplexArray0D = elementWise(this, other) { t, o -> t / o }

    override operator fun plus (other: Double): ComplexArray0D = elementWise { it + other }
    override operator fun minus(other: Double): ComplexArray0D = elementWise { it - other }
    override operator fun times(other: Double): ComplexArray0D = elementWise { it * other }
    override operator fun div  (other: Double): ComplexArray0D = elementWise { it / other }

    override operator fun plus (other: Int): ComplexArray0D = elementWise { it + other }
    override operator fun minus(other: Int): ComplexArray0D = elementWise { it - other }
    override operator fun times(other: Int): ComplexArray0D = elementWise { it * other }
    override operator fun div  (other: Int): ComplexArray0D = elementWise { it / other }

}