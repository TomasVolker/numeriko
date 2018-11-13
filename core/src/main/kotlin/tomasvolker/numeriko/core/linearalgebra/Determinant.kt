package tomasvolker.numeriko.core.linearalgebra

import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices0
import tomasvolker.numeriko.core.interfaces.array2d.generic.isSquare
import tomasvolker.numeriko.core.primitives.isEven
import tomasvolker.numeriko.core.primitives.sumDouble

fun minusOnePow(int: Int): Int = if (int.isEven()) 1 else -1

fun DoubleArray2D.determinant(): Double {

    require(isSquare())

    if (shape0 == 1) {
        return this[0, 0]
    }

    return sumDouble(indices0) { i0 ->
        minusOnePow(i0) * this[i0, 0] * SkipRowColumnView(this, i0, 0).determinant()
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

    override fun getDouble(i0: Int, i1: Int): Double =
            array.getDouble(
                    if(i0 < row) i0 else i0 + 1,
                    if(i1 < column) i1 else i1 + 1
            )

}