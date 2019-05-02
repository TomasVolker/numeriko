package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.interfaces.arraynd.byte.MutableByteArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.MutableFloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.MutableIntArrayND

fun <T> ArrayND<T>.get(): T = getValue()
operator fun <T> ArrayND<T>.get(i0: Int): T = getValue(i0)
operator fun <T> ArrayND<T>.get(i0: Int, i1: Int): T = getValue(i0, i1)
operator fun <T> ArrayND<T>.get(i0: Int, i1: Int, i2: Int): T = getValue(i0, i1, i2)
operator fun <T> ArrayND<T>.get(i0: Int, i1: Int, i2: Int, i3: Int): T = getValue(i0, i1, i2, i3)
operator fun <T> ArrayND<T>.get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): T = getValue(i0, i1, i2, i3, i4)
operator fun <T> ArrayND<T>.get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): T = getValue(i0, i1, i2, i3, i4, i5)
operator fun <T> ArrayND<T>.get(vararg indices: Int): T = getValue(indices)

operator fun <T> MutableArrayND<T>.set(vararg indices: Int, value: T) = setValue(indices, value)


operator fun <T> ArrayND<T>.get(i0: Index): T      = getValue(i0.computeValue(shape(0)))
operator fun IntArrayND    .get(i0: Index): Int    = get(i0.computeValue(shape(0)))
operator fun DoubleArrayND .get(i0: Index): Double = get(i0.computeValue(shape(0)))
operator fun FloatArrayND  .get(i0: Index): Float  = get(i0.computeValue(shape(0)))

operator fun <T> MutableArrayND<T>.set(i0: Index, value: T)      = setValue(i0.computeValue(shape(0)), value)
operator fun MutableIntArrayND    .set(i0: Index, value: Int)    = set(i0.computeValue(shape(0)), value)
operator fun MutableDoubleArrayND .set(i0: Index, value: Double) = set(i0.computeValue(shape(0)), value)
operator fun MutableFloatArrayND  .set(i0: Index, value: Float)  = set(i0.computeValue(shape(0)), value)
operator fun MutableByteArrayND   .set(i0: Index, value: Byte)  = set(i0.computeValue(shape(0)), value)
