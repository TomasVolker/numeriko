package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.array0d.double.DoubleArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.view.*
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.indices
import tomasvolker.numeriko.core.interfaces.arraynd.generic.view.DefaultArrayNDHigherRankView
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.NumericArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.factory.doubleArrayND
import tomasvolker.numeriko.core.operations.concatenate
import tomasvolker.numeriko.core.operations.inject
import tomasvolker.numeriko.core.operations.remove
import tomasvolker.numeriko.core.primitives.sumDouble
import tomasvolker.numeriko.core.view.ElementOrder
import kotlin.math.max
import kotlin.math.min

interface DoubleArrayND: NumericArrayND<Double> {

    override fun cast(value: Number): Double = value.toDouble()

    override fun getValue(vararg indices: Int): Double = getDouble(*indices)

    override fun getDouble(vararg indices: Int): Double
    override fun getFloat (vararg indices: Int): Float  = getDouble(*indices).toFloat()
    override fun getLong  (vararg indices: Int): Long   = getDouble(*indices).toLong()
    override fun getInt   (vararg indices: Int): Int    = getDouble(*indices).toInt()
    override fun getShort (vararg indices: Int): Short  = getDouble(*indices).toShort()

    fun getDouble(): Double = getDouble(*intArrayOf())
    fun getDouble(vararg indices: Index): Double = getDouble(*indices.computeIndices())

    override fun toDoubleArrayND(): DoubleArrayND = copy()

    operator fun get(indices: IntArray1D): Double =
            getDouble(indices)

    override fun as0D(): DoubleArray0D = DefaultDoubleArrayND0DView(this.asMutable())
    override fun as1D(): DoubleArray1D = DefaultDoubleArrayND1DView(this.asMutable())
    override fun as2D(): DoubleArray2D = DefaultDoubleArrayND2DView(this.asMutable())

    override fun getView(vararg indices: IntProgression): DoubleArrayND =
            defaultDoubleArrayNDView(this.asMutable(), indices)

    override fun getView(vararg indices: IndexProgression): DoubleArrayND =
            getView(*indices.computeIndices())

    override fun lowerRank(axis: Int): DoubleArrayND =
            DefaultDoubleArrayNDLowerRankView(this.asMutable(), axis)

    override fun higherRank(axis: Int): DoubleArrayND =
            DefaultDoubleArrayNDHigherRankView(this.asMutable(), axis)

    override fun arrayAlongAxis(axis: Int, index: Int): DoubleArrayND =
            getView(*Array(rank) { ax ->
                if (ax == axis)
                    IntRange(index, index).toIndexProgression()
                else
                    All
            }).lowerRank(axis = axis)

    override fun linearView(order: ElementOrder): DoubleArray1D =
            DefaultDoubleArrayNDLinearView(this.asMutable(), order)

    override fun copy(): DoubleArrayND = copy(this)

    override fun iterator(): DoubleIterator = DefaultDoubleArrayNDIterator(this)

    override fun asMutable(): MutableDoubleArrayND = this as MutableDoubleArrayND

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

    infix fun outer(other: DoubleArrayND): DoubleArrayND =
            doubleArrayND(this.shape concatenate other.shape) { indices ->
                val thisIndices = indices[0 until this.rank]
                val otherIndices = indices[this.rank until Size]
                this[thisIndices] * other[otherIndices]
            }

    fun contract(axis0: Int, axis1: Int): DoubleArrayND {
        val minAxis = min(axis0, axis1)
        val maxAxis = max(axis0, axis1)
        require(minAxis != maxAxis && shape(minAxis) == shape(maxAxis))
        val newShape = shape.remove(minAxis).remove(maxAxis-1)
        return doubleArrayND(newShape) { indices ->
            sumDouble(indices(minAxis)) { r ->
                this[indices.inject(index = minAxis, value = r).inject(index = maxAxis, value = r)]
            }
        }
    }

    fun contract(other: DoubleArrayND, thisAxis: Int, otherAxis: Int): DoubleArrayND {
        this.requireValidAxis(thisAxis)
        other.requireValidAxis(otherAxis)
        require(this.shape(thisAxis) == other.shape(otherAxis))
        val resultShape = this.shape.remove(thisAxis) concatenate other.shape.remove(otherAxis)
        return doubleArrayND(resultShape) { indices ->
            sumDouble(indices(thisAxis)) { r ->
                this[indices[0 until this.rank-1].inject(index = thisAxis, value = r)] *
                other[indices[this.rank-1 until Size].inject(index = otherAxis, value = r)]
            }
        }
    }

}

operator fun DoubleArrayND.plus (other: DoubleArrayND): DoubleArrayND = elementWise(this, other) { t, o -> t + o }
operator fun DoubleArrayND.minus(other: DoubleArrayND): DoubleArrayND = elementWise(this, other) { t, o -> t - o }
operator fun DoubleArrayND.times(other: DoubleArrayND): DoubleArrayND = elementWise(this, other) { t, o -> t * o }
operator fun DoubleArrayND.div  (other: DoubleArrayND): DoubleArrayND = elementWise(this, other) { t, o -> t / o }
