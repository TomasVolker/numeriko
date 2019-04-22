package numeriko.lowrank.interfaces.array0d.double

import tomasvolker.numeriko.core.annotations.CompileTimeError
import tomasvolker.core.annotations.*
import numeriko.lowrank.interfaces.array0d.numeric.NumericArray0D
import numeriko.lowrank.interfaces.array1d.double.DoubleArray1D
import tomasvolker.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.core.preconditions.requireValidIndices
import tomasvolker.core.interfaces.factory.copy
import tomasvolker.core.preconditions.rankError
import tomasvolker.core.preconditions.rankError0DMessage

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

    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError0DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
    override fun get(i0: Int): Nothing = rankError(1)
    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError0DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
    override operator fun get(i0: Int, i1: Int): Nothing = rankError(2)
    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError0DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int): Nothing = rankError(3)
    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError0DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Nothing = rankError(4)
    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError0DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Nothing = rankError(5)
    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError0DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Nothing = rankError(6)
    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError0DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
    override operator fun get(vararg indices: Int): Nothing = rankError(-1)

    override fun as0D() = this

    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError0DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
    override fun as1D(): Nothing = rankError(1)
    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError0DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
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
