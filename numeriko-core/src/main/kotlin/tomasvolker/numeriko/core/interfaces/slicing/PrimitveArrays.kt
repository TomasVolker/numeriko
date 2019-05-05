package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.byte.MutableByteArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.MutableFloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.MutableIntArrayND

operator fun <T> ArrayND<T>.get(vararg indices: Any): ArrayND<T> =
        slice(sliceFromIndices(this, indices))

operator fun <T> MutableArrayND<T>.get(vararg indices: Any): MutableArrayND<T> =
        slice(sliceFromIndices(this, indices))

operator fun <T> MutableArrayND<T>.set(vararg indices: Any, value: ArrayND<T>): Unit =
        this.get(*indices).setValue(value)


operator fun DoubleArrayND.get(vararg indices: Any): DoubleArrayND =
        slice(sliceFromIndices(this, indices))

operator fun MutableDoubleArrayND.get(vararg indices: Any): MutableDoubleArrayND =
        slice(sliceFromIndices(this, indices))

operator fun MutableDoubleArrayND.set(vararg indices: Any, value: MutableDoubleArrayND): Unit =
        this.get(*indices).setValue(value)


operator fun FloatArrayND.get(vararg indices: Any): FloatArrayND = slice(sliceFromIndices(this, indices))

operator fun MutableFloatArrayND.get(vararg indices: Any): MutableFloatArrayND =
        slice(sliceFromIndices(this, indices))

operator fun MutableFloatArrayND.set(vararg indices: Any, value: MutableFloatArrayND): Unit =
        this.get(*indices).setValue(value)


operator fun IntArrayND.get(vararg indices: Any): IntArrayND =
        slice(sliceFromIndices(this, indices))

operator fun MutableIntArrayND.get(vararg indices: Any): MutableIntArrayND =
        slice(sliceFromIndices(this, indices))

operator fun MutableIntArrayND.set(vararg indices: Any, value: MutableIntArrayND): Unit =
        this.get(*indices).setValue(value)


operator fun ByteArrayND.get(vararg indices: Any): ByteArrayND =
        slice(sliceFromIndices(this, indices))

operator fun MutableByteArrayND.get(vararg indices: Any): MutableByteArrayND =
        slice(sliceFromIndices(this, indices))

operator fun MutableByteArrayND.set(vararg indices: Any, value: MutableByteArrayND): Unit =
        this.get(*indices).setValue(value)
