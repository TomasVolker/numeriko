package tomasvolker.numeriko.tests

import tomasvolker.numeriko.lowrank.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.lowrank.interfaces.array1d.generic.forEachIndex
import tomasvolker.numeriko.lowrank.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.lowrank.interfaces.array2d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.iteration.fastForEachIndices
import kotlin.math.abs

const val tolerance = 1e-10

infix fun Double.numericEquals(other: Double): Boolean = abs(other -  this) < tolerance

infix fun Double.notNumericEquals(other: Double): Boolean = !(this numericEquals other)

infix fun DoubleArrayND.numericEquals(other: DoubleArrayND): Boolean {

    if (this.shape != other.shape)
        return false

    fastForEachIndices { indices ->
        if(this.get(*indices) notNumericEquals other.get(*indices))
            return false
    }

    return true
}

infix fun DoubleArray1D.numericEquals(other: DoubleArray1D): Boolean {

    if (this.size != other.size)
        return false

    forEachIndex { i ->
        if(this[i] notNumericEquals other[i])
            return false
    }

    return true
}

infix fun DoubleArray2D.numericEquals(other: DoubleArray2D): Boolean {

    if (this.shape0 != other.shape0 || this.shape1 != shape1)
        return false

    forEachIndex { i0, i1 ->
        if(this[i0, i1] notNumericEquals other[i0, i1])
            return false
    }

    return true
}

fun assertNumericEquals(expected: DoubleArray1D, actual: DoubleArray1D) {
    assert(expected numericEquals actual) {
        "Arrays are not numerically equal.\nexpected: $expected \nactual: $actual\ntolerance: $tolerance"
    }
}

fun assertNumericEquals(expected: DoubleArray2D, actual: DoubleArray2D) {
    assert(expected numericEquals actual) {
        "Arrays are not numerically equal.\nexpected: $expected \nactual: $actual\ntolerance: $tolerance"
    }
}

fun assertNotNumericEquals(expected: DoubleArray1D, actual: DoubleArray1D) {
    assert(!(expected numericEquals actual)) {
        "Arrays are numerically equal.\nexpected: $expected \nactual: $actual\ntolerance: $tolerance"
    }
}

fun assertNumericEquals(expected: Double, actual: Double) {
    assert(expected numericEquals actual) {
        "Values are not numerically equal.\nexpected: $expected \nactual: $actual\ntolerance: $tolerance"
    }
}

fun assertNotNumericEquals(expected: Double, actual: Double) {
    assert(!(expected numericEquals actual)) {
        "Values are numerically equal.\nexpected: $expected \nactual: $actual\ntolerance: $tolerance"
    }
}

fun assertPositive(value: Double) {
    assert(value > 0) { "Value is not positive: $value" }
}

fun assertNumericZero(value: Double) {
    assert(value numericEquals 0.0) { "Value is not zero: $value" }
}

fun assertNegative(value: Double) {
    assert(value < 0.0) { "Value is not negative: $value" }
}
