package tomasvolker.numeriko.complex.interfaces.arraynd

import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.complex.interfaces.array0d.MutableComplexArray0D
import tomasvolker.numeriko.complex.interfaces.array1d.MutableComplexArray1D
import tomasvolker.numeriko.complex.interfaces.array2d.MutableComplexArray2D
import tomasvolker.numeriko.complex.interfaces.arraynd.view.*
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.index.toIndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.MutableNumericArrayND
import tomasvolker.numeriko.core.interfaces.iteration.fastForEachIndices
import tomasvolker.numeriko.core.preconditions.requireSameShape

interface MutableComplexArrayND: ComplexArrayND, MutableNumericArrayND<Complex> {

    override fun getView(vararg indices: IntProgression): MutableComplexArrayND =
            defaultComplexArrayNDView(this, indices)

    override fun getView(vararg indices: IndexProgression): MutableComplexArrayND =
            getView(*indices.computeIndices())

/*
    override fun setDouble(value: Double, vararg indices: Int)
    override fun setFloat (value: Float , vararg indices: Int) = setDouble(value.toDouble(), *indices)
    override fun setLong  (value: Long  , vararg indices: Int) = setDouble(value.toDouble(), *indices)
    override fun setInt   (value: Int   , vararg indices: Int) = setDouble(value.toDouble(), *indices)
    override fun setShort (value: Short , vararg indices: Int) = setDouble(value.toDouble(), *indices)

    fun setDouble(value: Double, vararg indices: Index) = setDouble(value, *indices.computeIndices())
*/
    fun setValue(value: ComplexArrayND) {
        requireSameShape(this, value)
        // Anti alias copy
        val copy = value.copy()
        fastForEachIndices { indices ->
            setValue(indices, copy.getValue(*indices))
        }
    }

    override fun setValue(indices: IntArray, value: Complex) {
        setReal(value.real, *indices)
        setImag(value.imag, *indices)
    }

    fun setReal(value: Double, vararg indices: Int)
    fun setImag(value: Double, vararg indices: Int)

    operator fun set(indices: IntArray1D, value: Complex) = setValue(indices, value)

    override fun as0D(): MutableComplexArray0D = DefaultComplexArrayND0DView(this)
    override fun as1D(): MutableComplexArray1D = DefaultComplexArrayND1DView(this)
    override fun as2D(): MutableComplexArray2D = DefaultComplexArrayND2DView(this)

    fun setView(value: ComplexArrayND, vararg indices: IntProgression): Unit =
            getView(*indices).asMutable().setValue(value)

    fun setView(value: ComplexArrayND, vararg indices: IndexProgression): Unit =
            setView(value, *Array(indices.size) { i -> indices[i].computeProgression(shape[i]) })

    override fun lowerRank(axis: Int): MutableComplexArrayND =
            DefaultComplexArrayNDLowerRankView(this, axis)

    override fun arrayAlongAxis(axis: Int, index: Int): MutableComplexArrayND =
            getView(*Array(rank) { ax -> if (ax == axis) IntRange(index, index).toIndexProgression() else All })

    fun applyPlus (other: Double): MutableComplexArrayND = applyElementWise { it + other }
    fun applyMinus(other: Double): MutableComplexArrayND = applyElementWise { it - other }
    fun applyTimes(other: Double): MutableComplexArrayND = applyElementWise { it * other }
    fun applyDiv  (other: Double): MutableComplexArrayND = applyElementWise { it / other }

    fun applyPlus (other: Int): MutableComplexArrayND = applyPlus (other.toDouble())
    fun applyMinus(other: Int): MutableComplexArrayND = applyMinus(other.toDouble())
    fun applyTimes(other: Int): MutableComplexArrayND = applyTimes(other.toDouble())
    fun applyDiv  (other: Int): MutableComplexArrayND = applyDiv  (other.toDouble())

}
