package tomasvolker.numeriko.complex.array

import tomasvolker.numeriko.complex.Complex

object C {

    operator fun get(vararg values: Complex): ComplexArray1D =
            complexArray1D(values)

}