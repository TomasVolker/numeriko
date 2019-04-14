package tomasvolker.numeriko.core.dsl

import tomasvolker.numeriko.lowrank.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.lowrank.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.lowrank.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.lowrank.interfaces.array1d.lowdim.integer.IntVector2
import tomasvolker.numeriko.lowrank.interfaces.array1d.lowdim.integer.MutableIntVector2
import tomasvolker.numeriko.lowrank.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.lowrank.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.core.operations.stack

object A {

    operator fun <T> get(vararg values: T): Array1D<T> =
            values.asArray1D()

    inline operator fun <reified T> get(vararg values: Array1D<T>): Array2D<T> =
            stack(*values)

    operator fun get(vararg values: Int): IntArray1D =
            values.asIntArray1D()

    operator fun get(vararg values: Double): DoubleArray1D =
            values.asDoubleArray1D()

    operator fun get(vararg values: DoubleArray1D): DoubleArray2D =
            stack(*values, axis = 0)

}

object D {

    operator fun get(vararg values: Number): DoubleArray1D = values.asDoubleArray1D()

    operator fun get(vararg values: DoubleArray1D): DoubleArray2D = stack(*values)

}

object I {

    operator fun get(value0: Int, value1: Int): IntVector2 =
            MutableIntVector2(value0, value1)

    operator fun get(vararg values: Int): IntArray1D =
            values.asIntArray1D()

}