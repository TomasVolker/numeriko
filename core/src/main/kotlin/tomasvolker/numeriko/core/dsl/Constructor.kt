package tomasvolker.numeriko.core.dsl

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.lowdim.integer.IntVector2
import tomasvolker.numeriko.core.interfaces.array1d.lowdim.integer.MutableIntVector2
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.core.operations.stack

object A {

    operator fun get(vararg values: Int): IntArray1D =
            values.asIntArray1D()

    operator fun get(vararg values: Double): DoubleArray1D =
            values.asDoubleArray1D()

    operator fun get(vararg values: DoubleArray1D): DoubleArray2D =
            stack(*values)

}

object D {

    operator fun get(vararg values: Number): DoubleArray1D =
            values.asDoubleArray1D()

    operator fun get(vararg values: DoubleArray1D): DoubleArray2D =
            stack(*values)

}

object I {

    operator fun get(value0: Int, value1: Int): IntVector2 =
            MutableIntVector2(value0, value1)

    operator fun get(vararg values: Int): IntArray1D =
            values.asIntArray1D()

}