package tomasvolker.numeriko.complex.interfaces.arraynd

import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.complex.interfaces.array0d.ComplexArray0D
import tomasvolker.numeriko.complex.interfaces.array1d.ComplexArray1D
import tomasvolker.numeriko.complex.interfaces.array2d.ComplexArray2D
import tomasvolker.numeriko.complex.interfaces.arraynd.view.*
import tomasvolker.numeriko.complex.interfaces.factory.copy
import tomasvolker.numeriko.complex.primitives.toComplex
import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.lowrank.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.NumericArrayND
import tomasvolker.numeriko.core.interfaces.factory.doubleArrayND

interface ComplexArrayND: NumericArrayND<Complex> {

    override fun cast(value: Number): Complex = value.toComplex()

    override fun toDoubleArrayND(): DoubleArrayND = doubleArrayND(shape) { indices ->
        getValue(indices).real
    }

    operator fun get(indices: IntArray1D): Complex = getValue(indices)

    override fun getValue(vararg indices: Int): Complex =
            Complex(real(*indices), imag(*indices))

    fun real(vararg indices: Int): Double
    fun imag(vararg indices: Int): Double

    override fun as0D(): ComplexArray0D = DefaultComplexArrayND0DView(this.asMutable())
    override fun as1D(): ComplexArray1D = DefaultComplexArrayND1DView(this.asMutable())
    override fun as2D(): ComplexArray2D = DefaultComplexArrayND2DView(this.asMutable())

    override fun getView(vararg indices: IntProgression): ComplexArrayND =
            defaultComplexArrayNDView(this.asMutable(), indices)

    override fun getView(vararg indices: IndexProgression): ComplexArrayND =
            getView(*indices.computeIndices())

    override fun lowerRank(axis: Int): ComplexArrayND =
            DefaultComplexArrayNDLowerRankView(this.asMutable(), axis)

    override fun arrayAlongAxis(axis: Int, index: Int): ComplexArrayND =
            getView(*Array(rank) { ax -> if (ax == axis) IntRange(index, index).toIndexProgression() else All })

    override fun copy(): ComplexArrayND = copy(this)

    override fun asMutable(): MutableComplexArrayND = this as MutableComplexArrayND

    operator fun unaryPlus(): ComplexArrayND = this
    operator fun unaryMinus(): ComplexArrayND = elementWise { -it }

    operator fun plus (other: Double): ComplexArrayND = elementWise { it + other }
    operator fun minus(other: Double): ComplexArrayND = elementWise { it - other }
    operator fun times(other: Double): ComplexArrayND = elementWise { it * other }
    operator fun div  (other: Double): ComplexArrayND = elementWise { it / other }

    operator fun plus (other: Int): ComplexArrayND = elementWise { it + other }
    operator fun minus(other: Int): ComplexArrayND = elementWise { it - other }
    operator fun times(other: Int): ComplexArrayND = elementWise { it * other }
    operator fun div  (other: Int): ComplexArrayND = elementWise { it / other }

}

operator fun ComplexArrayND.plus (other: ComplexArrayND): ComplexArrayND = elementWise(this, other) { t, o -> t + o }
operator fun ComplexArrayND.minus(other: ComplexArrayND): ComplexArrayND = elementWise(this, other) { t, o -> t - o }
operator fun ComplexArrayND.times(other: ComplexArrayND): ComplexArrayND = elementWise(this, other) { t, o -> t * o }
operator fun ComplexArrayND.div  (other: ComplexArrayND): ComplexArrayND = elementWise(this, other) { t, o -> t / o }
