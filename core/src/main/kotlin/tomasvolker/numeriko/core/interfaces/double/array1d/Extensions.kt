package tomasvolker.numeriko.core.interfaces.double.array1d

operator fun Double.times(array: DoubleArray1D): MutableDoubleArray1D = array * this

operator fun Double.plus(array: DoubleArray1D): MutableDoubleArray1D = array + this
operator fun Double.minus(array: DoubleArray1D): MutableDoubleArray1D = array.elementWise { this - it }