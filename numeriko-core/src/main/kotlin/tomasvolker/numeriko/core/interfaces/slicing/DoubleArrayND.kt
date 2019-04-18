package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.MutableFloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND

operator fun DoubleArrayND.get(vararg indices: Any): DoubleArrayND =
        getPermutedSlice(permutedSliceFromIndices(this, indices))

operator fun MutableDoubleArrayND.get(vararg indices: Any): MutableDoubleArrayND =
        getPermutedSlice(permutedSliceFromIndices(this, indices))

operator fun FloatArrayND.get(vararg indices: Any): FloatArrayND =
        getPermutedSlice(permutedSliceFromIndices(this, indices))

operator fun MutableFloatArrayND.get(vararg indices: Any): MutableFloatArrayND =
        getPermutedSlice(permutedSliceFromIndices(this, indices))
