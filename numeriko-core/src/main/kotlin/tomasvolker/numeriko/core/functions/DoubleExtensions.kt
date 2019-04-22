package tomasvolker.numeriko.core.functions

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND


operator fun Double.times(array: DoubleArrayND): DoubleArrayND = array * this

operator fun Double.plus(array: DoubleArrayND): DoubleArrayND = array + this
operator fun Double.minus(array: DoubleArrayND): DoubleArrayND = array.rdiv(this)

operator fun Int.times(array: DoubleArrayND): DoubleArrayND = array * this

operator fun Int.plus(array: DoubleArrayND): DoubleArrayND = array + this
operator fun Int.minus(array: DoubleArrayND): DoubleArrayND = this.toDouble() - array
