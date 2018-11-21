package tomasvolker.numeriko.core.reductions

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D

fun IntArray1D.product(): Int = fold(1) { acc, next -> acc * next }
