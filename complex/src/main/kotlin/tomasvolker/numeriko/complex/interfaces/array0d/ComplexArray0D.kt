package tomasvolker.numeriko.complex.interfaces.array0d

import tomasvolker.numeriko.complex.Complex
import tomasvolker.numeriko.complex.interfaces.factory.copy
import tomasvolker.numeriko.complex.toComplex
import tomasvolker.numeriko.core.interfaces.array0d.numeric.NumericArray0D

interface ComplexArray0D: NumericArray0D<Complex> {

    override fun cast(value: Number): Complex = value.toComplex()

    fun get(): Complex = getValue()

    override fun getView(): ComplexArray0D = this

    override fun copy(): ComplexArray0D = copy(this)

    override fun asMutable(): MutableComplexArray0D = this as MutableComplexArray0D

    operator fun unaryPlus(): ComplexArray0D = this
    operator fun unaryMinus(): ComplexArray0D = elementWise { -it }

    operator fun plus (other: ComplexArray0D): ComplexArray0D = elementWise(this, other) { t, o -> t + o }
    operator fun minus(other: ComplexArray0D): ComplexArray0D = elementWise(this, other) { t, o -> t - o }
    operator fun times(other: ComplexArray0D): ComplexArray0D = elementWise(this, other) { t, o -> t * o }
    operator fun div  (other: ComplexArray0D): ComplexArray0D = elementWise(this, other) { t, o -> t / o }

    operator fun plus (other: Double): ComplexArray0D = elementWise { it + other }
    operator fun minus(other: Double): ComplexArray0D = elementWise { it - other }
    operator fun times(other: Double): ComplexArray0D = elementWise { it * other }
    operator fun div  (other: Double): ComplexArray0D = elementWise { it / other }

    operator fun plus (other: Int): ComplexArray0D = elementWise { it + other }
    operator fun minus(other: Int): ComplexArray0D = elementWise { it - other }
    operator fun times(other: Int): ComplexArray0D = elementWise { it * other }
    operator fun div  (other: Int): ComplexArray0D = elementWise { it / other }

}