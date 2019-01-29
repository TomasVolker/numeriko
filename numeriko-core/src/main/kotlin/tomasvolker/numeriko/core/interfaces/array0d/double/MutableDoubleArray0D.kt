package tomasvolker.numeriko.core.interfaces.array0d.double

import tomasvolker.numeriko.core.annotations.CompileTimeError
import tomasvolker.numeriko.core.interfaces.array0d.numeric.MutableNumericArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND

private const val rankError = "Array is known to be rank 1D in compile time"

interface MutableDoubleArray0D: DoubleArray0D, MutableNumericArray0D<Double>, MutableDoubleArrayND {

    override fun setValue(indices: IntArray, value: Double): Unit =
            setDouble(indices, value)

    override fun setDouble(indices: IntArray, value: Double) {
        requireValidIndices(indices)
        setDouble(value)
    }

    override fun setValue(value: Double): Unit = setDouble(value)

    override fun setFloat(indices: IntArray, value: Float) = setDouble(indices, value.toDouble())
    override fun setLong (indices: IntArray, value: Long) = setDouble(indices, value.toDouble())
    override fun setInt  (indices: IntArray, value: Int) = setDouble(indices, value.toDouble())
    override fun setShort(indices: IntArray, value: Short) = setDouble(indices, value.toDouble())

    private fun rankError(): Nothing = throw IllegalArgumentException("Array is known to be rank 0D in compile time")

    @CompileTimeError(message = rankError, level = Level.ERROR)
    fun set(value: Double, i0: Int): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, value: Double): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, value: Double): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Double): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Double): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Double): Nothing = rankError()

    @CompileTimeError(message = rankError, level = Level.ERROR)
    override fun as0D(): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override fun as2D(): Nothing = rankError()

    override fun setDouble(value: Double) = set(value)

    override fun setFloat (value: Float ) = setDouble(value.toDouble())
    override fun setLong  (value: Long  ) = setDouble(value.toDouble())
    override fun setInt   (value: Int   ) = setDouble(value.toDouble())
    override fun setShort (value: Short ) = setDouble(value.toDouble())

    override fun getView(): MutableDoubleArray0D = this

    override fun arrayAlongAxis(axis: Int, index: Int): Nothing {
        super<DoubleArray0D>.arrayAlongAxis(axis, index)
    }

    override fun lowerRank(axis: Int): Nothing {
        super<DoubleArray0D>.lowerRank(axis)
    }

    override fun higherRank(): MutableDoubleArray1D = higherRank(0)

    override fun higherRank(axis: Int): MutableDoubleArray1D {
        require(axis == 0)
        return DefaultDoubleArray0DHigherRankView(this.asMutable())
    }

    fun setValue(other: DoubleArray0D) {
        setDouble(other.getDouble())
    }

    override fun set(value: Double)

}
