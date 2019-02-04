package tomasvolker.numeriko.core.interfaces.array2d.double

import tomasvolker.numeriko.core.functions.plus
import tomasvolker.numeriko.core.functions.rdiv
import tomasvolker.numeriko.core.functions.times

operator fun Double.times(array: DoubleArray2D): DoubleArray2D = array * this

operator fun Double.plus(array: DoubleArray2D): DoubleArray2D = array + this
operator fun Double.minus(array: DoubleArray2D): DoubleArray2D = array.rdiv(this)

operator fun Int.times(array: DoubleArray2D): DoubleArray2D = array * this

operator fun Int.plus(array: DoubleArray2D): DoubleArray2D = array + this
operator fun Int.minus(array: DoubleArray2D): DoubleArray2D = this.toDouble() - array
