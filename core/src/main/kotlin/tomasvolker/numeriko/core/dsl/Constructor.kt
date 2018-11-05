package tomasvolker.numeriko.core.dsl

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.MutableIntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.asMutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.asMutableIntArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.mutableDoubleArray2D

object ar {

    operator fun get(vararg values: Int): MutableIntArray1D =
            values.asMutableIntArray1D()

    operator fun get(vararg values: Double): MutableDoubleArray1D =
            values.asMutableDoubleArray1D()

    operator fun get(vararg values: DoubleArray1D): MutableDoubleArray2D {

        if (values.isEmpty()) doubleArray2D(0, 0) { _, _-> 0.0 }

        val firstSize = values.first().size
        require(values.all { it.size == firstSize }) { "All sizes must be the same" }

        return mutableDoubleArray2D(values.size, firstSize) { i0, i1 ->
            values[i0][i1]
        }
    }


}