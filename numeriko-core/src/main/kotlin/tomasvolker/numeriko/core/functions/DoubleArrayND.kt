package tomasvolker.numeriko.core.functions

import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.iteration.applyElementWise

fun MutableDoubleArrayND.applyPlus (other: Double): MutableDoubleArrayND = applyElementWise { it + other }
fun MutableDoubleArrayND.applyMinus(other: Double): MutableDoubleArrayND = applyElementWise { it - other }
fun MutableDoubleArrayND.applyTimes(other: Double): MutableDoubleArrayND = applyElementWise { it * other }
fun MutableDoubleArrayND.applyDiv  (other: Double): MutableDoubleArrayND = applyElementWise { it / other }

fun MutableDoubleArrayND.applyPlus (other: Int): MutableDoubleArrayND = applyPlus (other.toDouble())
fun MutableDoubleArrayND.applyMinus(other: Int): MutableDoubleArrayND = applyMinus(other.toDouble())
fun MutableDoubleArrayND.applyTimes(other: Int): MutableDoubleArrayND = applyTimes(other.toDouble())
fun MutableDoubleArrayND.applyDiv  (other: Int): MutableDoubleArrayND = applyDiv  (other.toDouble())


