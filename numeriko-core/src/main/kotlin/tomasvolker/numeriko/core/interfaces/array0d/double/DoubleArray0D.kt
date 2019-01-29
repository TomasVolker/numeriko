package tomasvolker.numeriko.core.interfaces.array0d.double

import tomasvolker.numeriko.core.annotations.CompileTimeError
import tomasvolker.numeriko.core.interfaces.array0d.numeric.NumericArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy

private const val rankError = "Array is known to be rank 1D in compile time"
internal typealias Level = DeprecationLevel

interface DoubleArray0D: NumericArray0D<Double>, DoubleArrayND {

    override fun getValue(indices: IntArray): Double = getDouble(indices)

    override fun getDouble(): Double = get()

    override fun get(): Double

    override operator fun get(vararg indices: Int): Double = getDouble(indices)

    override fun getDouble(indices: IntArray): Double {
        requireValidIndices(indices)
        return get()
    }

    override fun getFloat(indices: IntArray): Float = getDouble(indices).toFloat()
    override fun getLong (indices: IntArray): Long  = getDouble(indices).toLong()
    override fun getInt  (indices: IntArray): Int   = getDouble(indices).toInt()
    override fun getShort(indices: IntArray): Short = getDouble(indices).toShort()

    private fun rankError(): Nothing = throw IllegalArgumentException("Array is known to be rank 0D in compile time")

    @CompileTimeError(message = rankError, level = Level.ERROR)
    override fun get(i0: Int): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Nothing = rankError()

    @CompileTimeError(message = rankError, level = Level.ERROR)
    override fun as0D(): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override fun as2D(): Nothing = rankError()

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
    
}
