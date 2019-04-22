package tomasvolker.numeriko.core.operations

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.slicing.alongAxis


fun <T> ArrayND<T>.unstack(axis: Int = 0): List<ArrayND<T>> =
        List(shape(axis)) { i -> alongAxis(axis = axis, index = i) }

fun IntArrayND.unstack(axis: Int = 0): List<IntArrayND> =
        List(shape(axis)) { i -> alongAxis(axis = axis, index = i) }

fun FloatArrayND.unstack(axis: Int = 0): List<FloatArrayND> =
        List(shape(axis)) { i -> alongAxis(axis = axis, index = i) }

fun DoubleArrayND.unstack(axis: Int = 0): List<DoubleArrayND> =
        List(shape(axis)) { i -> alongAxis(axis = axis, index = i) }
