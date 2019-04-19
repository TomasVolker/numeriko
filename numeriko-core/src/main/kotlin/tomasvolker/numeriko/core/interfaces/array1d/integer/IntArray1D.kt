package tomasvolker.numeriko.core.interfaces.array1d.integer

import tomasvolker.numeriko.core.annotations.CompileTimeError
import tomasvolker.numeriko.core.annotations.Level
import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.interfaces.arraynd.integer.*
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.preconditions.rankError

internal const val rankError1DMessage = "Array is known to be rank 1D in compile time"

interface IntArray1D: IntArrayND {

    override val size: Int

    override val shape: IntArray1D
        get() = I[size]

    @CompileTimeError(rankError1DMessage, level = Level.ERROR)
    override operator fun get(vararg indices: Int): Int = getInt(indices)

    override fun getValue(indices: IntArray): Int = getInt(indices)

    operator fun component1(): Int = get(0)
    operator fun component2(): Int = get(1)
    operator fun component3(): Int = get(2)
    operator fun component4(): Int = get(3)
    operator fun component5(): Int = get(4)
    operator fun component6(): Int = get(5)

    override operator fun get(i0: Int): Int

    @CompileTimeError(rankError1DMessage, level = Level.ERROR)
    override fun get(): Nothing = rankError(1)
    @CompileTimeError(rankError1DMessage)
    override operator fun get(i0: Int, i1: Int): Nothing = rankError(1)
    @CompileTimeError(rankError1DMessage, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int): Nothing = rankError(1)
    @CompileTimeError(rankError1DMessage, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Nothing = rankError(1)
    @CompileTimeError(rankError1DMessage, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Nothing = rankError(1)
    @CompileTimeError(rankError1DMessage, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Nothing = rankError(1)

    override fun getInt(indices: IntArray): Int {
        require(indices.size == 1) { "passed ${indices.size} indices when 1 was required" }
        return get(indices[0])
    }

    override fun getInt(indices: IntArray1D): Int {
        require(indices.size == 1) { "passed ${indices.size} indices when 1 was required" }
        return get(indices[0])
    }

    override fun copy(): IntArray1D = copy(this)

    override fun iterator(): IntIterator = arrayIterator()
    override fun arrayIterator(): IntArrayNDIterator = DefaultIntArrayNDIterator(this)

    override fun asMutable(): MutableIntArray1D = this as MutableIntArray1D
/*
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
*/
}
