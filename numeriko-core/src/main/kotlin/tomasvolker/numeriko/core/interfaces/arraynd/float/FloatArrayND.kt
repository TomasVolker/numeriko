package tomasvolker.numeriko.core.interfaces.arraynd.float

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.iteration.elementWise
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.interfaces.slicing.sliceReshape
import tomasvolker.numeriko.core.preconditions.illegalArgument

interface FloatArrayND: ArrayND<Float> {

    operator fun get(vararg indices: Int): Float = getFloat(indices)

    override fun getValue(indices: IntArray): Float = getFloat(indices)

    fun get(): Float = getFloat(intArrayOf())
    operator fun get(i0: Int): Float = getFloat(intArrayOf(i0))
    operator fun get(i0: Int, i1: Int): Float = getFloat(intArrayOf(i0, i1))
    operator fun get(i0: Int, i1: Int, i2: Int): Float = getFloat(intArrayOf(i0, i1, i2))
    operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Float = getFloat(intArrayOf(i0, i1, i2, i3))
    operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Float = getFloat(intArrayOf(i0, i1, i2, i3, i4))
    operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Float = getFloat(intArrayOf(i0, i1, i2, i3, i4, i5))

    fun getFloat(indices: IntArray): Float

    fun getFloat(indices: IntArray1D): Float = getFloat(indices.toIntArray())

    override fun copy(): FloatArrayND = copy(this)

    override fun iterator(): FloatIterator = arrayIterator()
    override fun arrayIterator(): FloatArrayNDIterator = DefaultFloatArrayNDIterator(this)

    override fun asMutable(): MutableFloatArrayND = this as MutableFloatArrayND

    override fun getSlice(
            slice: ArraySlice
    ): FloatArrayND = DefaultSliceFloatArrayND(
            array = this.asMutable(),
            slice = slice
    )

    override fun reshape(shape: IntArray1D, copyIfNecessary: Boolean): FloatArrayND {

        if (canReshapeTo(shape)) {
            return sliceReshape(shape)
        } else {

            val copy = if (copyIfNecessary)
                copy()
            else
                illegalArgument("Cannot reshape ${this.shape} to $shape without copying")

            if (!copy.canReshapeTo(shape))
                error("Cannot reshape a copy of the array")

            return copy.reshape(shape, copyIfNecessary = false)

        }

    }

    operator fun plus (other: FloatArrayND): FloatArrayND = elementWise(this, other) { t, o -> t + o }
    operator fun minus(other: FloatArrayND): FloatArrayND = elementWise(this, other) { t, o -> t - o }
    operator fun times(other: FloatArrayND): FloatArrayND = elementWise(this, other) { t, o -> t * o }
    operator fun div  (other: FloatArrayND): FloatArrayND = elementWise(this, other) { t, o -> t / o }


    operator fun unaryPlus(): FloatArrayND = this
    operator fun unaryMinus(): FloatArrayND = elementWise { -it }

    operator fun plus (other: Float): FloatArrayND = elementWise { it + other }
    operator fun minus(other: Float): FloatArrayND = elementWise { it - other }
    operator fun times(other: Float): FloatArrayND = elementWise { it * other }
    operator fun div  (other: Float): FloatArrayND = elementWise { it / other }

    operator fun plus (other: Int): FloatArrayND = elementWise { it + other }
    operator fun minus(other: Int): FloatArrayND = elementWise { it - other }
    operator fun times(other: Int): FloatArrayND = elementWise { it * other }
    operator fun div  (other: Int): FloatArrayND = elementWise { it / other }

}



