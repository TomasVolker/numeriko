package tomasvolker.numeriko.sandbox.generic

import tomasvolker.numeriko.core.interfaces.array2d.double.transpose
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.slicing.get
import tomasvolker.numeriko.core.operations.reduction.reduce


fun main() {

    val array = doubleArray1D(16) { i0 -> i0 }

    println(array.withShape(2, 3).transpose().linearView())

    println(
            array.withShape(2, 3)
                    .transpose()
                    .reduce(axis = 0) { it.sum() }
    )

    val ndArray = array as ArrayND<Double>



}
