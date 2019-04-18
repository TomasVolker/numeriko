package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.functions.FunctionDtoD
import tomasvolker.numeriko.core.functions.FunctionIADtoD
import tomasvolker.numeriko.core.interfaces.arraynd.double.view.DefaultPermutedSliceDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.view.DefaultPermutedSliceArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.iteration.inlinedElementWise
import tomasvolker.numeriko.core.interfaces.iteration.inlinedForEach
import tomasvolker.numeriko.core.interfaces.iteration.inlinedForEachIndexed
import tomasvolker.numeriko.core.interfaces.slicing.PermutedSlice

interface DoubleArrayND: ArrayND<Double> {

    operator fun get(vararg indices: Int): Double = getDouble(indices)

    override fun getValue(indices: IntArray): Double = getDouble(indices)

    fun get(): Double = getDouble(intArrayOf())
    operator fun get(i0: Int): Double = getDouble(intArrayOf(i0))
    operator fun get(i0: Int, i1: Int): Double = getDouble(intArrayOf(i0, i1))
    operator fun get(i0: Int, i1: Int, i2: Int): Double = getDouble(intArrayOf(i0, i1, i2))
    operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Double = getDouble(intArrayOf(i0, i1, i2, i3))
    operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Double = getDouble(intArrayOf(i0, i1, i2, i3, i4))
    operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Double = getDouble(intArrayOf(i0, i1, i2, i3, i4, i5))

    fun getDouble(indices: IntArray): Double

    fun getDouble(indices: IntArrayND): Double = getDouble(indices.toIntArray())

    override fun copy(): DoubleArrayND = copy(this)

    override fun iterator(): DoubleIterator = arrayIterator()
    override fun arrayIterator(): DoubleArrayNDIterator = DefaultDoubleArrayNDIterator(this)

    override fun asMutable(): MutableDoubleArrayND = this as MutableDoubleArrayND

    override fun getPermutedSlice(
            slice: PermutedSlice
    ): DoubleArrayND = DefaultPermutedSliceDoubleArrayND(
            array = this.asMutable(),
            permutedSlice = slice
    )

    fun forEach(function: FunctionDtoD) {
        inlinedForEach { function(it) }
    }

    fun forEachIndexed(function: FunctionIADtoD) {
        inlinedForEachIndexed { indices, value ->  function(indices, value) }
    }

    fun elementWise(function: FunctionDtoD): DoubleArrayND =
            inlinedElementWise { function(it) }

    operator fun plus (other: DoubleArrayND): DoubleArrayND = elementWise(this, other) { t, o -> t + o }
    operator fun minus(other: DoubleArrayND): DoubleArrayND = elementWise(this, other) { t, o -> t - o }
    operator fun times(other: DoubleArrayND): DoubleArrayND = elementWise(this, other) { t, o -> t * o }
    operator fun div  (other: DoubleArrayND): DoubleArrayND = elementWise(this, other) { t, o -> t / o }


    operator fun unaryPlus(): DoubleArrayND = this
    operator fun unaryMinus(): DoubleArrayND = elementWise { -it }

    operator fun plus (other: Double): DoubleArrayND = elementWise { it + other }
    operator fun minus(other: Double): DoubleArrayND = elementWise { it - other }
    operator fun times(other: Double): DoubleArrayND = elementWise { it * other }
    operator fun div  (other: Double): DoubleArrayND = elementWise { it / other }

    operator fun plus (other: Int): DoubleArrayND = elementWise { it + other }
    operator fun minus(other: Int): DoubleArrayND = elementWise { it - other }
    operator fun times(other: Int): DoubleArrayND = elementWise { it * other }
    operator fun div  (other: Int): DoubleArrayND = elementWise { it / other }

}



