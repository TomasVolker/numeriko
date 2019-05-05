package tomasvolker.numeriko.core.operations

import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayNDContext
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayNDContext
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayNDContext
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayNDContext
import tomasvolker.numeriko.core.interfaces.arraynd.generic.GenericArrayNDContext
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayNDContext
import tomasvolker.numeriko.core.interfaces.slicing.alongAxis

fun <A: ArrayND<*>> ArrayNDContext<A>.unstack(array: A, axis: Int = 0): List<A> =
        List(array.shape(axis)) { i -> alongAxis(array, axis = axis, index = i) }

fun <T> ArrayND<T>.unstack(axis: Int = 0): List<ArrayND<T>> =
        GenericArrayNDContext<T>().unstack(this, axis)

fun IntArrayND.unstack(axis: Int = 0): List<IntArrayND> =
        IntArrayNDContext.unstack(this, axis)

fun FloatArrayND.unstack(axis: Int = 0): List<FloatArrayND> =
        FloatArrayNDContext.unstack(this, axis)

fun DoubleArrayND.unstack(axis: Int = 0): List<DoubleArrayND> =
        DoubleArrayNDContext.unstack(this, axis)

fun ByteArrayND.unstack(axis: Int = 0): List<ByteArrayND> =
        ByteArrayNDContext.unstack(this, axis)
