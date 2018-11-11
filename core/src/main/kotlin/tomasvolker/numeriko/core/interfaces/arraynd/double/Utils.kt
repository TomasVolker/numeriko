package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D

fun DoubleArrayND.as1D(): DoubleArray1D = DefaultDoubleArrayND1DView(this.asMutable())
fun DoubleArrayND.as2D(): DoubleArray2D = DefaultDoubleArrayND2DView(this.asMutable())