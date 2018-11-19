package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.view.DefaultDoubleArrayNDLowerRankView
import tomasvolker.numeriko.core.interfaces.arraynd.double.view.defaultDoubleArrayNDView
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.indices
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.factory.doubleArrayND
import tomasvolker.numeriko.core.operations.concatenate
import tomasvolker.numeriko.core.operations.inject
import tomasvolker.numeriko.core.operations.remove
import tomasvolker.numeriko.core.primitives.sumDouble
import kotlin.math.max
import kotlin.math.min

interface DoubleArrayND: ArrayND<Double> {

    override fun getValue(vararg indices: Int): Double =
            getDouble(*indices)

    fun getDouble(vararg indices: Int): Double

    fun getDouble(): Double = getDouble(*intArrayOf())

    fun getDouble(indices: IntArray1D): Double =
            getValue(*indices.toIntArray())

    fun getDouble(vararg indices: Index): Double =
            getDouble(*indices.computeIndices())

    operator fun get(indices: IntArray1D): Double =
            getDouble(indices)

    override fun getView(vararg indices: IntProgression): DoubleArrayND =
            defaultDoubleArrayNDView(this.asMutable(), indices)

    override fun getView(vararg indices: IndexProgression): DoubleArrayND =
            getView(*indices.computeIndices())

    override fun lowerRank(axis: Int): DoubleArrayND =
            DefaultDoubleArrayNDLowerRankView(this.asMutable(), axis)

    override fun arrayAlongAxis(axis: Int, index: Int): DoubleArrayND =
            getView(*Array(rank) { ax -> if (ax == axis) IntRange(index, index).toIndexProgression() else All })

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
