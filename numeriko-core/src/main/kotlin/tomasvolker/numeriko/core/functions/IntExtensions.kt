package tomasvolker.numeriko.core.functions

import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.elementWise


operator fun Int.times(array: IntArrayND): IntArrayND = array * this

operator fun Int.plus(array: IntArrayND): IntArrayND = array + this
operator fun Int.minus(array: IntArrayND): IntArrayND = array.elementWise { this - it }

