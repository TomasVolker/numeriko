package tomasvolker.numeriko.core.dsl

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.MutableIntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.*

object ar {

    operator fun get(vararg values: Int): IntArray1D =
            values.asIntArray1D()

    operator fun get(vararg values: Double): DoubleArray1D =
            values.asDoubleArray1D()

    operator fun get(vararg values: DoubleArray1D): DoubleArray2D {

        if (values.isEmpty()) doubleArray2D(0, 0) { _, _-> 0.0 }

        val firstSize = values.first().size
        require(values.all { it.size == firstSize }) { "All sizes must be the same" }

        return doubleArray2D(values.size, firstSize) { i0, i1 ->
            values[i0][i1]
        }
    }


}