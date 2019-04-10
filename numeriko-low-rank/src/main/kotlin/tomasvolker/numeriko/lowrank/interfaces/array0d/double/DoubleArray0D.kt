package tomasvolker.numeriko.lowrank.interfaces.array0d.double

import tomasvolker.numeriko.core.annotations.CompileTimeError
import tomasvolker.numeriko.core.annotations.*
import tomasvolker.numeriko.lowrank.interfaces.array0d.numeric.NumericArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.preconditions.rankError
import tomasvolker.numeriko.core.preconditions.rankError0DMessage

interface DoubleArray0D: NumericArray0D<Double>, DoubleArrayND {

    override fun getValue(indices: IntArray): Double = getDouble(indices)

    override fun getDouble(): Double = get()

    override fun get(): Double

    override fun getDouble(indices: IntArray): Double {
        requireValidIndices(indices)
        return get()
    }

    override fun getFloat(indices: IntArray): Float = getDouble(indices).toFloat()
    override fun getLong (indices: IntArray): Long  = getDouble(indices).toLong()
    override fun getInt  (indices: IntArray): Int   = getDouble(indices).toInt()
    override fun getShort(indices: IntArray): Short = getDouble(indices).toShort()

    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override fun get(i0: Int): Nothing = rankError(1)
    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int): Nothing = rankError(2)
    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int): Nothing = rankError(3)
    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Nothing = rankError(4)
    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Nothing = rankError(5)
    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Nothing = rankError(6)
    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override operator fun get(vararg indices: Int): Nothing = rankError(-1)

    override fun as0D() = this

    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override fun as1D(): Nothing = rankError(1)
    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override fun as2D(): Nothing = rankError(2)

    override fun getValue(): Double = get()

    override fun getView(): DoubleArray0D = this

    override fun arrayAlongAxis(axis: Int, index: Int): Nothing {
        super<NumericArray0D>.arrayAlongAxis(axis, index)
    }

    override fun lowerRank(axis: Int): Nothing {
        super<NumericArray0D>.lowerRank(axis)
    }

    override fun higherRank(): DoubleArray1D = higherRank(0)

    override fun higherRank(axis: Int): DoubleArray1D {
        require(axis == 0)
        return DefaultDoubleArray0DHigherRankView(this.asMutable())
    }

    override fun copy(): DoubleArray0D = copy(this)

    override fun asMutable(): MutableDoubleArray0D = this as MutableDoubleArray0D

    override fun iterator(): DoubleIterator = DefaultDoubleArray0DIterator(this)

    override operator fun unaryPlus(): DoubleArray0D = this
    override operator fun unaryMinus(): DoubleArray0D = elementWise { -it }

    operator fun plus (other: DoubleArray0D): DoubleArray0D = elementWise(this, other) { t, o -> t + o }
    operator fun minus(other: DoubleArray0D): DoubleArray0D = elementWise(this, other) { t, o -> t - o }
    operator fun times(other: DoubleArray0D): DoubleArray0D = elementWise(this, other) { t, o -> t * o }
    operator fun div  (other: DoubleArray0D): DoubleArray0D = elementWise(this, other) { t, o -> t / o }

    override operator fun plus (other: Double): DoubleArray0D = elementWise { it + other }
    override operator fun minus(other: Double): DoubleArray0D = elementWise { it - other }
    override operator fun times(other: Double): DoubleArray0D = elementWise { it * other }
    override operator fun div  (other: Double): DoubleArray0D = elementWise { it / other }

    override operator fun plus (other: Int): DoubleArray0D = elementWise { it + other }
    override operator fun minus(other: Int): DoubleArray0D = elementWise { it - other }
    override operator fun times(other: Int): DoubleArray0D = elementWise { it * other }
    override operator fun div  (other: Int): DoubleArray0D = elementWise { it / other }
    
}
