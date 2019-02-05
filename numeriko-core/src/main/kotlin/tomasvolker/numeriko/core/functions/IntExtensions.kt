package tomasvolker.numeriko.core.functions

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.elementWise

operator fun Int.times(array: IntArray1D): IntArray1D = array * this

operator fun Int.plus(array: IntArray1D): IntArray1D = array + this
operator fun Int.minus(array: IntArray1D): IntArray1D = array.elementWise { this - it }