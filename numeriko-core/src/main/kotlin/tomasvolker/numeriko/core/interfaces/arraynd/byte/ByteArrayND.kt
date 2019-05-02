package tomasvolker.numeriko.core.interfaces.arraynd.byte

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.iteration.elementWise
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.interfaces.slicing.sliceReshape
import tomasvolker.numeriko.core.preconditions.illegalArgument

interface ByteArrayND: ArrayND<Byte> {

    operator fun get(vararg indices: Int): Byte = getByte(indices)

    override fun getValue(indices: IntArray): Byte = getByte(indices)

    fun get(): Byte = getByte(intArrayOf())
    operator fun get(i0: Int): Byte = getByte(intArrayOf(i0))
    operator fun get(i0: Int, i1: Int): Byte = getByte(intArrayOf(i0, i1))
    operator fun get(i0: Int, i1: Int, i2: Int): Byte = getByte(intArrayOf(i0, i1, i2))
    operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Byte = getByte(intArrayOf(i0, i1, i2, i3))
    operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Byte = getByte(intArrayOf(i0, i1, i2, i3, i4))
    operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Byte = getByte(intArrayOf(i0, i1, i2, i3, i4, i5))

    fun getByte(indices: IntArray): Byte

    fun getByte(indices: IntArray1D): Byte = getByte(indices.toIntArray())

    override fun copy(): ByteArrayND = copy(this)

    override fun iterator(): ByteIterator = arrayIterator()
    override fun arrayIterator(): ByteArrayNDIterator = DefaultByteArrayNDIterator(this)

    override fun asMutable(): MutableByteArrayND = this as MutableByteArrayND

    override fun getSlice(
            slice: ArraySlice
    ): ByteArrayND = DefaultSliceByteArrayND(
            array = this.asMutable(),
            slice = slice
    )

    override fun reshape(shape: IntArray1D, copyIfNecessary: Boolean): ByteArrayND {

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

    operator fun plus (other: ByteArrayND): ByteArrayND = elementWise(this, other) { t, o -> (t + o).toByte() }
    operator fun minus(other: ByteArrayND): ByteArrayND = elementWise(this, other) { t, o -> (t - o).toByte() }
    operator fun times(other: ByteArrayND): ByteArrayND = elementWise(this, other) { t, o -> (t * o).toByte() }
    operator fun div  (other: ByteArrayND): ByteArrayND = elementWise(this, other) { t, o -> (t / o).toByte() }


    operator fun unaryPlus(): ByteArrayND = this
    operator fun unaryMinus(): ByteArrayND = elementWise { (-it).toByte() }

    operator fun plus (other: Byte): ByteArrayND = elementWise { (it + other).toByte() }
    operator fun minus(other: Byte): ByteArrayND = elementWise { (it - other).toByte() }
    operator fun times(other: Byte): ByteArrayND = elementWise { (it * other).toByte() }
    operator fun div  (other: Byte): ByteArrayND = elementWise { (it / other).toByte() }

}



