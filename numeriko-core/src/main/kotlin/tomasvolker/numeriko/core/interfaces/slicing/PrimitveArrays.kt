package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.MutableFloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.MutableIntArrayND

operator fun DoubleArrayND.get(vararg indices: Any): DoubleArrayND =
        getPermutedSlice(permutedSliceFromIndices(this, indices))

operator fun MutableDoubleArrayND.get(vararg indices: Any): MutableDoubleArrayND =
        getPermutedSlice(permutedSliceFromIndices(this, indices))

operator fun MutableDoubleArrayND.set(vararg indices: Any, value: MutableDoubleArrayND): Unit =
        this.get(*indices).setValue(value)


operator fun FloatArrayND.get(vararg indices: Any): FloatArrayND =
        getPermutedSlice(permutedSliceFromIndices(this, indices))

operator fun MutableFloatArrayND.get(vararg indices: Any): MutableFloatArrayND =
        getPermutedSlice(permutedSliceFromIndices(this, indices))

operator fun MutableFloatArrayND.set(vararg indices: Any, value: MutableFloatArrayND): Unit =
        this.get(*indices).setValue(value)


operator fun IntArrayND.get(vararg indices: Any): IntArrayND =
        getPermutedSlice(permutedSliceFromIndices(this, indices))

operator fun MutableIntArrayND.get(vararg indices: Any): MutableIntArrayND =
        getPermutedSlice(permutedSliceFromIndices(this, indices))

operator fun MutableIntArrayND.set(vararg indices: Any, value: MutableIntArrayND): Unit =
        this.get(*indices).setValue(value)
