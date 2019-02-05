package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.functions.FunctionDtoD
import tomasvolker.numeriko.core.functions.FunctionIADtoD
import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.array0d.double.DoubleArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.view.*
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.NumericArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.iteration.inlinedElementWise
import tomasvolker.numeriko.core.interfaces.iteration.inlinedForEach
import tomasvolker.numeriko.core.interfaces.iteration.inlinedForEachIndexed
import tomasvolker.numeriko.core.view.ElementOrder

interface DoubleArrayND: NumericArrayND<Double> {

    override fun cast(value: Number): Double = value.toDouble()

    operator fun get(vararg indices: Int): Double = getDouble(indices)

    override fun getValue(indices: IntArray): Double = getDouble(indices)

    override fun getDouble(indices: IntArray): Double
    override fun getFloat (indices: IntArray): Float  = getDouble(indices).toFloat()
    override fun getLong  (indices: IntArray): Long   = getDouble(indices).toLong()
    override fun getInt   (indices: IntArray): Int    = getDouble(indices).toInt()
    override fun getShort (indices: IntArray): Short  = getDouble(indices).toShort()

    fun get(): Double = getDouble(intArrayOf())
    operator fun get(i0: Int): Double = getDouble(intArrayOf(i0))
    operator fun get(i0: Int, i1: Int): Double = getDouble(intArrayOf(i0, i1))
    operator fun get(i0: Int, i1: Int, i2: Int): Double = getDouble(intArrayOf(i0, i1, i2))
    operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Double = getDouble(intArrayOf(i0, i1, i2, i3))
    operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Double = getDouble(intArrayOf(i0, i1, i2, i3, i4))
    operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Double = getDouble(intArrayOf(i0, i1, i2, i3, i4, i5))

    fun getDouble(vararg indices: Index): Double = getDouble(indices.computeIndices())

    override fun toDoubleArrayND(): DoubleArrayND = copy()

    operator fun get(indices: IntArray1D): Double = getDouble(indices)

    override fun as0D(): DoubleArray0D = DefaultDoubleArrayND0DView(this.asMutable())
    override fun as1D(): DoubleArray1D = DefaultDoubleArrayND1DView(this.asMutable())
    override fun as2D(): DoubleArray2D = DefaultDoubleArrayND2DView(this.asMutable())

    override fun getView(vararg indices: IntProgression): DoubleArrayND =
            defaultDoubleArrayNDView(this.asMutable(), indices)

    override fun getView(vararg indices: IndexProgression): DoubleArrayND =
            getView(*indices.computeIndices())

    override fun lowerRank(axis: Int): DoubleArrayND =
            defaultDoubleArrayNDLowerRankView(this.asMutable(), axis)

    override fun higherRank(axis: Int): DoubleArrayND =
            DefaultDoubleArrayNDHigherRankView(this.asMutable(), axis)

    override fun arrayAlongAxis(axis: Int, index: Int): DoubleArrayND =
            getView(*Array(rank) { ax ->
                if (ax == axis)
                    (index..index).toIndexProgression()
                else
                    All
            }).lowerRank(axis = axis)

    override fun linearView(order: ElementOrder): DoubleArray1D =
            DefaultDoubleArrayNDLinearView(this.asMutable(), order)

    override fun copy(): DoubleArrayND = copy(this)

    override fun iterator(): DoubleIterator = arrayIterator()
    override fun arrayIterator(): DoubleArrayNDIterator = DefaultDoubleArrayNDIterator(this)

    override fun asMutable(): MutableDoubleArrayND = this as MutableDoubleArrayND

    fun forEach(function: FunctionDtoD) {
        inlinedForEach { function(it) }
    }

    fun forEachIndexed(function: FunctionIADtoD) {
        inlinedForEachIndexed { indices, value ->  function(indices, value) }
    }

    fun elementWise(function: FunctionDtoD): DoubleArrayND =
            inlinedElementWise { function(it) }

    operator fun plus (other: DoubleArrayND): DoubleArrayND = elementWise(this, other) { t, o -> t + o }
    operator fun minus(other: DoubleArrayND): DoubleArrayND = elementWise(this, other) { t, o -> t - o }
    operator fun times(other: DoubleArrayND): DoubleArrayND = elementWise(this, other) { t, o -> t * o }
    operator fun div  (other: DoubleArrayND): DoubleArrayND = elementWise(this, other) { t, o -> t / o }


    operator fun unaryPlus(): DoubleArrayND = this
    operator fun unaryMinus(): DoubleArrayND = elementWise { -it }

    operator fun plus (other: Double): DoubleArrayND = elementWise { it + other }
    operator fun minus(other: Double): DoubleArrayND = elementWise { it - other }
    operator fun times(other: Double): DoubleArrayND = elementWise { it * other }
    operator fun div  (other: Double): DoubleArrayND = elementWise { it / other }

    operator fun plus (other: Int): DoubleArrayND = elementWise { it + other }
    operator fun minus(other: Int): DoubleArrayND = elementWise { it - other }
    operator fun times(other: Int): DoubleArrayND = elementWise { it * other }
    operator fun div  (other: Int): DoubleArrayND = elementWise { it / other }
    
}



