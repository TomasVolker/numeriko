package tomasvolker.numeriko.core.linearalgebra

import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.Last
import tomasvolker.numeriko.core.index.rangeTo
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.functions.determinant
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices0
import tomasvolker.numeriko.core.interfaces.array2d.generic.isSquare
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.primitives.indicator
import tomasvolker.numeriko.core.primitives.isEven
import tomasvolker.numeriko.core.primitives.sumDouble

object DefaultLinearAlgebra {

    fun inverse(matrix: DoubleArray2D): DoubleArray2D {

        require(matrix.isSquare())

        val table = doubleArray2D(matrix.shape0, 2 * matrix.shape1) { i0, i1 ->
            if (i1 < matrix.shape1)
                matrix[i0, i1]
            else
                (i0 == (i1-matrix.shape1)).indicator()
        }.asMutable()

        table.inplaceReducedEchelonForm()

        return table[All, matrix.shape1..Last].copy()


        /*
        val table = matrix.copy().asMutable()

        val result = doubleIdentity(matrix.shape0).asMutable()

        table.inplaceGaussianElimination(result)

        table.inplaceBackSubstitution(result)

        return result
        */
    }

    fun solve(matrix: DoubleArray2D, image: DoubleArray1D): DoubleArray1D {

        require(matrix.isSquare() && matrix.shape0 == image.size)

        // Pivoting

        val table = matrix.copy().asMutable()
        val result = image.copy().asMutable()

        table.inplaceGaussianElimination(result)

        table.inplaceBackSubstitution(result)

        return result
    }

    fun determinant(array: DoubleArray2D): Double {
        require(array.isSquare())

        return when(array.shape0) {
            1 -> array[0, 0]
            2 -> array[0, 0] * array[1, 1] - array[0, 1] * array[1, 0]
            else -> {
                fun minusOnePow(int: Int): Int = if (int.isEven()) 1 else -1
                sumDouble(array.indices0) { i0 ->
                    minusOnePow(i0) * array[i0, 0] * SkipRowColumnView(array, i0, 0).determinant()
                }
            }
        }
    }

    class SkipRowColumnView(
            val array: DoubleArray2D,
            val row: Int,
            val column: Int
    ): DoubleArray2D {

        override val shape0: Int
            get() = array.shape0 - 1

        override val shape1: Int
            get() = array.shape1 - 1

        override fun get(i0: Int, i1: Int): Double =
                array.getDouble(
                        if(i0 < row) i0 else i0 + 1,
                        if(i1 < column) i1 else i1 + 1
                )

    }

}