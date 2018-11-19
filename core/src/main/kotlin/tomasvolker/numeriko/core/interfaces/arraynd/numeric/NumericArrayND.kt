package tomasvolker.numeriko.core.interfaces.arraynd.numeric

import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.view.DefaultDoubleArrayNDLowerRankView
import tomasvolker.numeriko.core.interfaces.arraynd.double.view.defaultDoubleArrayNDView
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.DefaultArrayNDIterator
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.indices
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.view.DefaultNumericArrayNDLowerRankView
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.view.defaultNumericArrayNDView
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.factory.doubleArrayND
import tomasvolker.numeriko.core.operations.concatenate
import tomasvolker.numeriko.core.operations.inject
import tomasvolker.numeriko.core.operations.remove
import tomasvolker.numeriko.core.primitives.sumDouble
import kotlin.math.max
import kotlin.math.min

interface NumericArrayND<out N: Number>: ArrayND<N> {

    override fun getValue(vararg indices: Int): N

    fun cast(value: Number): N

    fun getDouble(vararg indices: Int): Double = getValue(*indices).toDouble()
    fun getDouble(indices: IntArray1D): Double = getDouble(*indices.toIntArray())

    fun getFloat(vararg indices: Int): Float = getValue(*indices).toFloat()
    fun getFloat(indices: IntArray1D): Float = getFloat(*indices.toIntArray())

    fun getLong(vararg indices: Int): Long = getValue(*indices).toLong()
    fun getLong(indices: IntArray1D): Long = getLong(*indices.toIntArray())

    fun getInt(vararg indices: Int): Int = getValue(*indices).toInt()
    fun getInt(indices: IntArray1D): Int = getInt(*indices.toIntArray())

    fun getShort(vararg indices: Int): Short = getValue(*indices).toShort()
    fun getShort(indices: IntArray1D): Short = getShort(*indices.toIntArray())

    override fun getView(vararg indices: IntProgression): NumericArrayND<N> =
            defaultNumericArrayNDView(this.asMutable(), indices)

    override fun getView(vararg indices: IndexProgression): NumericArrayND<N> =
            getView(*indices.computeIndices())

    override fun lowerRank(axis: Int): NumericArrayND<N> =
            DefaultNumericArrayNDLowerRankView(this.asMutable(), axis)

    override fun arrayAlongAxis(axis: Int, index: Int): NumericArrayND<N> =
            getView(*Array(rank) { ax -> if (ax == axis) IntRange(index, index).toIndexProgression() else All })

    override fun copy(): NumericArrayND<N> = copy(this)

    override fun asMutable(): MutableNumericArrayND<@UnsafeVariance N> = this as MutableNumericArrayND

    override fun iterator(): Iterator<N> = DefaultArrayNDIterator(this)
    /*
    operator fun unaryPlus(): NumericArrayND = this
    operator fun unaryMinus(): NumericArrayND = elementWise { -it }

    operator fun plus (other: Double): NumericArrayND = elementWise { it + other }
    operator fun minus(other: Double): NumericArrayND = elementWise { it - other }
    operator fun times(other: Double): NumericArrayND = elementWise { it * other }
    operator fun div  (other: Double): NumericArrayND = elementWise { it / other }

    operator fun plus (other: Int): NumericArrayND = elementWise { it + other }
    operator fun minus(other: Int): NumericArrayND = elementWise { it - other }
    operator fun times(other: Int): NumericArrayND = elementWise { it * other }
    operator fun div  (other: Int): NumericArrayND = elementWise { it / other }

    infix fun outer(other: NumericArrayND): NumericArrayND =
            doubleArrayND(this.shape concatenate other.shape) { indices ->
                val thisIndices = indices[0 until this.rank]
                val otherIndices = indices[this.rank until Size]
                this[thisIndices] * other[otherIndices]
            }


    fun contract(axis0: Int, axis1: Int): NumericArrayND {
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

    fun contract(other: NumericArrayND, thisAxis: Int, otherAxis: Int): NumericArrayND {
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
*/
}
/*
operator fun NumericArrayND.plus (other: NumericArrayND): NumericArrayND = elementWise(this, other) { t, o -> t + o }
operator fun NumericArrayND.minus(other: NumericArrayND): NumericArrayND = elementWise(this, other) { t, o -> t - o }
operator fun NumericArrayND.times(other: NumericArrayND): NumericArrayND = elementWise(this, other) { t, o -> t * o }
operator fun NumericArrayND.div  (other: NumericArrayND): NumericArrayND = elementWise(this, other) { t, o -> t / o }
*/