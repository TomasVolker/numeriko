package tomasvolker.numeriko.core.interfaces.array0d.double

import tomasvolker.numeriko.core.interfaces.factory.doubleArray0D

inline fun DoubleArray0D.elementWise(operation: (Double)->Double): DoubleArray0D =
        doubleArray0D(operation(get()))

inline fun elementWise(
        array1: DoubleArray0D,
        array2: DoubleArray0D,
        operation: (Double, Double)->Double
): DoubleArray0D =
        doubleArray0D(operation(array1.get(), array2.get()))
