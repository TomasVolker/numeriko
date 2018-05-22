package tomasvolker.numeriko.core.interfaces.double.array1d

operator fun Double.times(array: DoubleArray1D): MutableDoubleArray1D = array * this

operator fun Double.plus(array: DoubleArray1D): MutableDoubleArray1D = array + this
operator fun Double.minus(array: DoubleArray1D): MutableDoubleArray1D = array.elementWise { this - it }

operator fun Int.times(array: DoubleArray1D): MutableDoubleArray1D = array * this

operator fun Int.plus(array: DoubleArray1D): MutableDoubleArray1D = array + this
operator fun Int.minus(array: DoubleArray1D): MutableDoubleArray1D = this.toDouble() - array