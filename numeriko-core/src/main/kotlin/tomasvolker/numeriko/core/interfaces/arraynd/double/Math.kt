package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.dsl.D
import tomasvolker.numeriko.core.index.Size
import tomasvolker.numeriko.core.index.until
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.indices
import tomasvolker.numeriko.core.interfaces.factory.doubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.doubleZeros
import tomasvolker.numeriko.core.operations.concatenate
import tomasvolker.numeriko.core.operations.reduction.inject
import tomasvolker.numeriko.core.operations.reduction.remove
import tomasvolker.numeriko.core.primitives.sumDouble
import kotlin.math.max
import kotlin.math.min

operator fun DoubleArrayND.plus (other: DoubleArrayND): DoubleArrayND = elementWise(this, other) { t, o -> t + o }
operator fun DoubleArrayND.minus(other: DoubleArrayND): DoubleArrayND = elementWise(this, other) { t, o -> t - o }
operator fun DoubleArrayND.times(other: DoubleArrayND): DoubleArrayND = elementWise(this, other) { t, o -> t * o }
operator fun DoubleArrayND.div  (other: DoubleArrayND): DoubleArrayND = elementWise(this, other) { t, o -> t / o }


operator fun DoubleArrayND.unaryPlus(): DoubleArrayND = this
operator fun DoubleArrayND.unaryMinus(): DoubleArrayND = elementWise { -it }

operator fun DoubleArrayND.plus (other: Double): DoubleArrayND = elementWise { it + other }
operator fun DoubleArrayND.minus(other: Double): DoubleArrayND = elementWise { it - other }
operator fun DoubleArrayND.times(other: Double): DoubleArrayND = elementWise { it * other }
operator fun DoubleArrayND.div  (other: Double): DoubleArrayND = elementWise { it / other }

operator fun DoubleArrayND.plus (other: Int): DoubleArrayND = elementWise { it + other }
operator fun DoubleArrayND.minus(other: Int): DoubleArrayND = elementWise { it - other }
operator fun DoubleArrayND.times(other: Int): DoubleArrayND = elementWise { it * other }
operator fun DoubleArrayND.div  (other: Int): DoubleArrayND = elementWise { it / other }

fun MutableDoubleArrayND.applyPlus (other: Double): MutableDoubleArrayND = applyElementWise { it + other }
fun MutableDoubleArrayND.applyMinus(other: Double): MutableDoubleArrayND = applyElementWise { it - other }
fun MutableDoubleArrayND.applyTimes(other: Double): MutableDoubleArrayND = applyElementWise { it * other }
fun MutableDoubleArrayND.applyDiv  (other: Double): MutableDoubleArrayND = applyElementWise { it / other }

fun MutableDoubleArrayND.applyPlus (other: Int): MutableDoubleArrayND = applyPlus (other.toDouble())
fun MutableDoubleArrayND.applyMinus(other: Int): MutableDoubleArrayND = applyMinus(other.toDouble())
fun MutableDoubleArrayND.applyTimes(other: Int): MutableDoubleArrayND = applyTimes(other.toDouble())
fun MutableDoubleArrayND.applyDiv  (other: Int): MutableDoubleArrayND = applyDiv  (other.toDouble())

infix fun DoubleArrayND.outer(other: DoubleArrayND): DoubleArrayND =
        doubleArrayND(this.shape concatenate other.shape) { indices ->
            val thisIndices = indices[0 until this.rank]
            val otherIndices = indices[this.rank until Size]
            this[thisIndices] * other[otherIndices]
        }

fun DoubleArrayND.contract(axis0: Int, axis1: Int): DoubleArrayND {
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

fun DoubleArrayND.contract(other: DoubleArrayND, thisAxis: Int, otherAxis: Int): DoubleArrayND {
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


