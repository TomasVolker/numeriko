package numeriko.complex.interfaces.array0d

import numeriko.complex.primitives.Complex
import numeriko.complex.interfaces.factory.complexArray0D

inline fun ComplexArray0D.elementWise(operation: (Complex)-> Complex) =
        complexArray0D(operation(get()))

inline fun elementWise(
        array1: ComplexArray0D,
        array2: ComplexArray0D,
        operation: (Complex, Complex)-> Complex
): ComplexArray0D =
        complexArray0D(operation(array1.get(), array2.get()))