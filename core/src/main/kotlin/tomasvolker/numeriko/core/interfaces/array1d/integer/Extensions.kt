package tomasvolker.numeriko.core.interfaces.array1d.integer

operator fun Int.times(array: IntArray1D): IntArray1D = array * this

operator fun Int.plus(array: IntArray1D): IntArray1D = array + this
operator fun Int.minus(array: IntArray1D): IntArray1D = array.elementWise { this - it }