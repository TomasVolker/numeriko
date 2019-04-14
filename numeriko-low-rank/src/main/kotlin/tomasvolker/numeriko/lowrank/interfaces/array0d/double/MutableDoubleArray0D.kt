package tomasvolker.numeriko.lowrank.interfaces.array0d.double

import tomasvolker.numeriko.core.annotations.CompileTimeError
import tomasvolker.numeriko.core.annotations.Level
import tomasvolker.numeriko.lowrank.interfaces.array0d.numeric.MutableNumericArray0D
import tomasvolker.numeriko.lowrank.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.preconditions.rankError
import tomasvolker.numeriko.core.preconditions.rankError0DMessage

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

    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    fun set(value: Double, i0: Int): Nothing = rankError(1)
    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, value: Double): Nothing = rankError(2)
    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, value: Double): Nothing = rankError(3)
    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Double): Nothing = rankError(4)
    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Double): Nothing = rankError(5)
    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Double): Nothing = rankError(6)
    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override fun set(vararg indices: Int, value: Double): Nothing = rankError(-1)

    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override fun as1D(): Nothing = rankError(1)
    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override fun as2D(): Nothing = rankError(2)

    override fun as0D() = this

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
