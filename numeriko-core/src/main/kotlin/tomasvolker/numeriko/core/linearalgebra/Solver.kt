package tomasvolker.numeriko.core.linearalgebra

import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices0
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices1
import tomasvolker.numeriko.core.primitives.sumDouble

fun MutableDoubleArray2D.inplaceGaussianElimination() =
        inplaceGaussianElimination { _, _, _ ->  }

inline fun MutableDoubleArray2D.inplaceGaussianElimination(
        updateImage: (pivotIndex: Int, i0: Int, factor: Double)->Unit
) {

    require(shape0 <= shape1)

    val shape0 = shape0
    val shape1 = shape1

    for (pivotIndex in 0 until shape0) {

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

    val imageShape1 = image.shape1

    inplaceGaussianElimination { pivotIndex, i0, factor ->
        for (i1 in 0 until imageShape1) {
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

fun DoubleArray2D.inplaceBackSubstitution(image: MutableDoubleArray1D) {

    val shape1 = shape1

    for (i0 in indices0.reversed()) {
        image[i0] = (image[i0] -
                sumDouble((i0+1) until shape1) { k1 -> this[i0, k1] * image[k1] }) / this[i0, i0]
    }

}

fun DoubleArray2D.inplaceBackSubstitution(image: MutableDoubleArray2D) {

    val shape1 = shape1
    val imageShape1 = image.shape1

    for (i0 in indices0.reversed()) {
        for (i1 in 0 until imageShape1) {
            image[i0, i1] = (image[i0, i1] -
                    sumDouble((i0 + 1) until shape1) { k1 -> this[i0, k1] * image[k1, i1] }) / this[i0, i0]
        }
    }

}

fun MutableDoubleArray2D.inplaceReducedEchelonForm() {

    inplaceGaussianElimination()

    val shape0 = shape0
    val shape1 = shape1

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

