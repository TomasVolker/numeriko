package tomasvolker.numeriko.complex.dsl

import tomasvolker.numeriko.complex.interfaces.array1d.ComplexArray1D
import tomasvolker.numeriko.complex.interfaces.array2d.ComplexArray2D
import tomasvolker.numeriko.complex.interfaces.factory.complexArray1D
import tomasvolker.numeriko.complex.interfaces.factory.complexArray2D
import tomasvolker.numeriko.complex.primitives.toComplex

object C {

    operator fun get(vararg values: Number): ComplexArray1D =
            complexArray1D(values.map { it.toComplex() }.toTypedArray())

    operator fun get(vararg values: ComplexArray1D): ComplexArray2D =
            complexArray2D(values.size, values.first().size) { i0, i1 ->
                values[i0][i1]
            }

}