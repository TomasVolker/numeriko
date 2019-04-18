package tomasvolker.numeriko.core.interfaces.arraynd.integer

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.iteration.elementWise
import tomasvolker.numeriko.core.interfaces.slicing.PermutedSlice

interface IntArrayND: ArrayND<Int> {

    operator fun get(vararg indices: Int): Int = getInt(indices)

    override fun getValue(indices: IntArray): Int = getInt(indices)

    operator fun component1(): Int = get(0)
    operator fun component2(): Int = get(1)
    operator fun component3(): Int = get(2)
    operator fun component4(): Int = get(3)
    operator fun component5(): Int = get(4)

    fun get(): Int = getInt(intArrayOf())
    operator fun get(i0: Int): Int = getInt(intArrayOf(i0))
    operator fun get(i0: Int, i1: Int): Int = getInt(intArrayOf(i0, i1))
    operator fun get(i0: Int, i1: Int, i2: Int): Int = getInt(intArrayOf(i0, i1, i2))
    operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Int = getInt(intArrayOf(i0, i1, i2, i3))
    operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Int = getInt(intArrayOf(i0, i1, i2, i3, i4))
    operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Int = getInt(intArrayOf(i0, i1, i2, i3, i4, i5))

    fun getInt(indices: IntArray): Int

    fun getInt(indices: IntArrayND): Int = getInt(indices.toIntArray())

    override fun getPermutedSlice(
            slice: PermutedSlice
    ): IntArrayND = DefaultPermutedSliceIntArrayND(
            array = this.asMutable(),
            permutedSlice = slice
    )

    override fun copy(): IntArrayND = copy(this)

    override fun iterator(): IntIterator = arrayIterator()
    override fun arrayIterator(): IntArrayNDIterator = DefaultIntArrayNDIterator(this)

    override fun asMutable(): MutableIntArrayND = this as MutableIntArrayND

    operator fun plus (other: IntArrayND): IntArrayND = elementWise(this, other) { t, o -> t + o }
    operator fun minus(other: IntArrayND): IntArrayND = elementWise(this, other) { t, o -> t - o }
    operator fun times(other: IntArrayND): IntArrayND = elementWise(this, other) { t, o -> t * o }
    operator fun div  (other: IntArrayND): IntArrayND = elementWise(this, other) { t, o -> t / o }


    operator fun unaryPlus(): IntArrayND = this
    operator fun unaryMinus(): IntArrayND = elementWise { -it }

    operator fun plus (other: Int): IntArrayND = elementWise { it + other }
    operator fun minus(other: Int): IntArrayND = elementWise { it - other }
    operator fun times(other: Int): IntArrayND = elementWise { it * other }
    operator fun div  (other: Int): IntArrayND = elementWise { it / other }

}
