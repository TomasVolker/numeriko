package tomasvolker.numeriko.complex.dsl

import tomasvolker.numeriko.complex.interfaces.array1d.ComplexArray1D
import tomasvolker.numeriko.complex.interfaces.factory.complexArray1D
import tomasvolker.numeriko.complex.toComplex

object C {

    operator fun get(vararg values: Number): ComplexArray1D =
            complexArray1D(values.map { it.toComplex() }.toTypedArray())

}