package tomasvolker.numeriko.core.linearalgebra

import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.Last
import tomasvolker.numeriko.core.index.rangeTo
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices0
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices1
import tomasvolker.numeriko.core.interfaces.array2d.generic.isSquare
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.primitives.indicative

fun MutableDoubleArray2D.inplaceGaussianElimination() =
        inplaceGaussianElimination { _, _, _ ->  }

inline fun MutableDoubleArray2D.inplaceGaussianElimination(
        updateImage: (pivotIndex: Int, i0: Int, factor: Double)->Unit
) {

    require(shape0 <= shape1)

    for (pivotIndex in indices0) {

        val pivot = this[pivotIndex, pivotIndex]

        for (i0 in (pivotIndex + 1) until shape0) {

            val factor = this[i0, pivotIndex] / pivot

            this[i0, pivotIndex] = 0.0

            for (i1 in (pivotIndex + 1) until shape1) {
                this[i0, i1] -= factor * this[pivotIndex, i1]
            }

            updateImage(pivotIndex, i0, factor)

        }

    }

}

fun MutableDoubleArray2D.inplaceGaussianElimination(image: MutableDoubleArray2D) {

    require(this.shape0 == image.shape0)

    inplaceGaussianElimination { pivotIndex, i0, factor ->
        for (i1 in image.indices1) {
            image[i0, i1] -= factor * image[pivotIndex, i1]
        }
    }

}

fun MutableDoubleArray2D.inplaceGaussianElimination(image: MutableDoubleArray1D) {

    require(this.shape0 == image.shape0)

    inplaceGaussianElimination { pivotIndex, i0, factor ->
        image[i0] -= factor * image[pivotIndex]
    }

}


fun DoubleArray2D.solve(image: DoubleArray1D): DoubleArray1D {

    require(this.isSquare() && this.shape0 == image.size)

    // Pivoting

    val table = this.copy().asMutable()
    val result = image.copy().asMutable()

    table.inplaceGaussianElimination(result)

    table.inplaceBackSubstitution(result)

    return result
}

fun DoubleArray2D.inplaceBackSubstitution(image: MutableDoubleArray1D) {

    for (i0 in indices0.reversed()) {
        image[i0] = (image[i0] -
                ((i0+1) until shape1).sumByDouble { i1 -> this[i0, i1] * image[i1] }) / this[i0, i0]
    }

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

    val table = doubleArray2D(shape0, 2 * shape1) { i0, i1 ->
        if (i1 < shape1)
            this[i0, i1]
        else
            (i0 == (i1-shape1)).indicative()
    }.asMutable()

    table.inplaceReducedEchelonForm()

    return table.getView(All, shape1..Last).copy()
}