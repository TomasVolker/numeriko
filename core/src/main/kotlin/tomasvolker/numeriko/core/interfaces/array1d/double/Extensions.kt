package tomasvolker.numeriko.core.interfaces.array1d.double

operator fun Double.times(array: DoubleArray1D): DoubleArray1D = array * this

operator fun Double.plus(array: DoubleArray1D): DoubleArray1D = array + this
operator fun Double.minus(array: DoubleArray1D): DoubleArray1D = array.rdiv(this)

operator fun Int.times(array: DoubleArray1D): DoubleArray1D = array * this

operator fun Int.plus(array: DoubleArray1D): DoubleArray1D = array + this
operator fun Int.minus(array: DoubleArray1D): DoubleArray1D = this.toDouble() - array