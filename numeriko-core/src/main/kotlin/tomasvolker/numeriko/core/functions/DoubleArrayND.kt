package tomasvolker.numeriko.core.functions

import tomasvolker.numeriko.core.index.Size
import tomasvolker.numeriko.core.index.until
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.applyElementWise
import tomasvolker.numeriko.core.interfaces.arraynd.double.elementWise
import tomasvolker.numeriko.core.interfaces.arraynd.generic.indices
import tomasvolker.numeriko.core.preconditions.requireValidAxis
import tomasvolker.numeriko.core.interfaces.factory.doubleArrayND
import tomasvolker.numeriko.core.operations.concatenate
import tomasvolker.numeriko.core.operations.reduction.inject
import tomasvolker.numeriko.core.operations.reduction.remove
import tomasvolker.numeriko.core.primitives.sumDouble
import kotlin.math.max
import kotlin.math.min

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


