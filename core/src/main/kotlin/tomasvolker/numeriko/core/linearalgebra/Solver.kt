package tomasvolker.numeriko.core.linearalgebra

import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.Last
import tomasvolker.numeriko.core.index.rangeTo
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices0
import tomasvolker.numeriko.core.interfaces.array2d.generic.isSquare
import tomasvolker.numeriko.core.interfaces.array2d.generic.lastIndex0
import tomasvolker.numeriko.core.interfaces.factory.mutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.mutableDoubleZeros
import tomasvolker.numeriko.core.primitives.indicative

fun MutableDoubleArray2D.inplaceGaussianElimination() {

    require(shape0 <= shape1)

    for (pivotIndex in indices0) {

        val pivot = this[pivotIndex, pivotIndex]

        for (i0 in (pivotIndex + 1) until shape0) {

            val factor = this[i0, pivotIndex] / pivot

            this[i0, pivotIndex] = 0.0

            for (i1 in (pivotIndex + 1) until shape1) {
                this[i0, i1] -= factor * this[pivotIndex, i1]
            }

        }

    }

}


fun DoubleArray2D.solve(image: DoubleArray1D): DoubleArray1D {

    require(this.isSquare() && this.shape0 == image.size)

    val variableCount = this.shape0

    // Pivoting

    val table = mutableDoubleArray2D(variableCount, variableCount + 1) { i0, i1 ->
        if (i1 < this.shape1)
            this[i0, i1]
        else
            image[i0]
    }

    table.inplaceGaussianElimination()

    val result = mutableDoubleZeros(variableCount)

    for (i0 in table.indices0.reversed()) {
        result[i0] = (table[i0, variableCount] - ((i0+1) until variableCount).sumByDouble { i1 -> table[i0, i1] * result[i1] }) / table[i0, i0]
    }

    return result
}

fun MutableDoubleArray2D.inplaceReducedEchelonForm() {

    inplaceGaussianElimination()

    for (pivotIndex in indices0.reversed()) {

        val pivot = this[pivotIndex, pivotIndex]
        this[pivotIndex, pivotIndex] = 1.0

        for (i1 in (pivotIndex+1) until shape1) {
            this[pivotIndex, i1] /= pivot
        }

        for (i0 in 0 until pivotIndex) {
            val factor = this[i0, pivotIndex]
            this[i0, pivotIndex] = 0.0
            for (i1 in shape0 until shape1){
                this[i0, i1] -= factor * this[pivotIndex, i1]
            }
        }

    }

}

fun DoubleArray2D.inverse(): DoubleArray2D {

    require(isSquare())

    val table = mutableDoubleArray2D(shape0, 2 * shape1) { i0, i1 ->
        if (i1 < shape1)
            this[i0, i1]
        else
            (i0 == (i1-shape1)).indicative()
    }

    table.inplaceReducedEchelonForm()

    return table.getView(All, shape1..Last).copy()
}