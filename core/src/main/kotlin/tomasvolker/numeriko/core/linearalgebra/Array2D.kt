package tomasvolker.numeriko.core.linearalgebra

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.*
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.mutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.mutableDoubleZeros
import tomasvolker.numeriko.core.preconditions.requireSameShape
import tomasvolker.numeriko.core.primitives.modulo

infix fun DoubleArray2D.matMul(other: DoubleArray1D): DoubleArray1D {
    require(this.shape1 == other.shape0) {
        "sizes dont match"
    }
    return doubleArray1D(this.shape0) { i0 ->
        this.indices1.sumByDouble { k -> this[i0, k] * other[k] }
    }
}

fun DoubleArray2D.cuadraticForm(other: DoubleArray1D): Double {
    require(this.isSquare()) {
        "Array is not square ${this.shape}"
    }
    require(this.shape0 == other.size) {
        "Sizes dont match"
    }

    var result = 0.0
    forEachIndex { i0, i1 ->
        result += this[i0, i1] * other[i0] * other[i1]
    }
    return result
}

infix fun DoubleArray2D.matMul(other: DoubleArray2D): DoubleArray2D {
    require(this.shape1 == other.shape0) {
        "sizes dont match"
    }
    return doubleArray2D(this.shape0, other.shape1) { i0, i1 ->
        this.indices0.sumByDouble { k -> this[i0, k] * other[k, i1] }
    }
}

infix fun DoubleArray2D.circularConvolve(other: DoubleArray2D): DoubleArray2D {
    requireSameShape(this, other)

    val result = mutableDoubleArray2D(shape0, shape1) { _, _ -> 0.0 }

    forEachIndex { i0, i1 ->
        result[i0, i1] = (other.indices).sumByDouble { j0 ->
            (other.indices).sumByDouble { j1 ->
                this[(i0 - j0) modulo shape0, (i1 - j1) modulo shape1] * other[j0, j1]
            }
        }
    }
    return result
}

fun DoubleArray2D.filter2D(filter: DoubleArray2D, padding: Double = 0.0): DoubleArray2D {

    val result = mutableDoubleZeros(this.shape0, this.shape1)

    val filterCenter0 = filter.shape0 / 2
    val filterCenter1 = filter.shape1 / 2

    forEachIndex { i0, i1 ->
        result[i0, i1] = (filter.indices(0)).sumByDouble { j0 ->
            (filter.indices(1)).sumByDouble { j1 ->
                val k0 = i0 - j0 - filterCenter0
                val k1 = i1 - j1 - filterCenter1
                if (k0 in 0 until this.shape0 && k1 in 0 until this.shape1) {
                    this[k0, k1] * filter[j0, j1]
                } else {
                    padding
                }
            }
        }
    }
    return result
}
