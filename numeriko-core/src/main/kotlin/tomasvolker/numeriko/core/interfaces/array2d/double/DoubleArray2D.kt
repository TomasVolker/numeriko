package tomasvolker.numeriko.core.interfaces.array2d.double

import tomasvolker.numeriko.core.annotations.CompileTimeError
import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array0d.double.DoubleArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.view.*
import tomasvolker.numeriko.core.interfaces.array2d.generic.*
import tomasvolker.numeriko.core.interfaces.array2d.numeric.NumericArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.core.linearalgebra.DefaultLinearAlgebra
import tomasvolker.numeriko.core.primitives.indicative
import tomasvolker.numeriko.core.primitives.numericEqualsTo
import tomasvolker.numeriko.core.primitives.squared
import tomasvolker.numeriko.core.primitives.sumDouble
import kotlin.math.abs
import kotlin.math.sqrt

private const val rankError = "Array is known to be rank 2D in compile time"
private typealias Level = DeprecationLevel

interface DoubleArray2D: NumericArray2D<Double>, DoubleArrayND {

    override fun cast(value: Number): Double = value.toDouble()

    override operator fun get(vararg indices: Int) = getDouble(indices)

    override fun getDouble(indices: IntArray): Double {
        require(indices.size == rank)
        return get(indices[0], indices[1])
    }

    override fun getFloat(indices: IntArray): Float = getDouble(indices).toFloat()
    override fun getLong (indices: IntArray): Long  = getDouble(indices).toLong()
    override fun getInt  (indices: IntArray): Int   = getDouble(indices).toInt()
    override fun getShort(indices: IntArray): Short = getDouble(indices).toShort()

    override fun getValue(indices: IntArray): Double = getDouble(indices)

    override fun getValue(i0: Int, i1: Int): Double = getDouble(i0, i1)

    override fun lowerRank(axis: Int): DoubleArray1D =
            MutableDoubleArray2DLowerRankView(this.asMutable(), axis)

    override fun getFloat(i0: Int, i1: Int  ): Float = getDouble(i0, i1).toFloat()

    private fun rankError(): Nothing = throw IllegalArgumentException("Array is known to be rank 2D in compile time")

    @CompileTimeError(message = rankError, level = Level.ERROR)
    override fun get(): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun get(i0: Int): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Nothing = rankError()

    override fun get(i0: Int, i1: Int): Double

    override fun getDouble(i0: Int, i1: Int  ): Double = get(i0, i1)
    fun getDouble(i0: Int  , i1: Index): Double = getDouble(i0.compute(0), i1.compute(1))
    fun getDouble(i0: Index, i1: Int  ): Double = getDouble(i0.compute(0), i1.compute(1))
    fun getDouble(i0: Index, i1: Index): Double = getDouble(i0.compute(0), i1.compute(1))

    override fun arrayAlongAxis(axis: Int, index: Int): DoubleArray1D =
            when(axis) {
                0 -> getView(index, All)
                1 -> getView(All, index)
                else -> {
                    requireValidAxis(axis)
                    error("requireValidAxis should fail")
                }
            }

    override fun getView(vararg indices: IntProgression): DoubleArray2D {
        requireValidIndices(indices)
        return getView(indices[0], indices[1])
    }

    override fun getView(vararg indices: IndexProgression): DoubleArray2D =
            getView(*indices.computeIndices())

    override fun getView(i0: Int  , i1: Int)  : DoubleArray0D = defaultDoubleArray2DView(asMutable(), i0, i1)
    override fun getView(i0: Int  , i1: Index): DoubleArray0D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: Int  ): DoubleArray0D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: Index): DoubleArray0D = getView(i0.compute(0), i1.compute(1))

    override fun getView(i0: Int  , i1: IntProgression): DoubleArray1D = defaultDoubleArray2DView(asMutable(), i0, i1)
    override fun getView(i0: Int  , i1: IndexProgression): DoubleArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: IntProgression  ): DoubleArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: IndexProgression): DoubleArray1D = getView(i0.compute(0), i1.compute(1))

    override fun getView(i0: IntProgression, i1: Int): DoubleArray1D = defaultDoubleArray2DView(asMutable(), i0, i1)
    override fun getView(i0: IntProgression  , i1: Index): DoubleArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: Int  ): DoubleArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: Index): DoubleArray1D = getView(i0.compute(0), i1.compute(1))


    override fun getView(i0: IntProgression, i1: IntProgression): DoubleArray2D = defaultDoubleArray2DView(asMutable(), i0, i1)
    override fun getView(i0: IntProgression  , i1: IndexProgression): DoubleArray2D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: IntProgression  ): DoubleArray2D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: IndexProgression): DoubleArray2D = getView(i0.compute(0), i1.compute(1))


    operator fun get(i0: Int  , i1: Index): Double = getDouble(i0, i1)
    operator fun get(i0: Index, i1: Int  ): Double = getDouble(i0, i1)
    operator fun get(i0: Index, i1: Index): Double = getDouble(i0, i1)

    operator fun get(i0: Int  , i1: IntProgression  ): DoubleArray1D = getView(i0, i1)
    operator fun get(i0: Index, i1: IntProgression  ): DoubleArray1D = getView(i0, i1)
    operator fun get(i0: Int  , i1: IndexProgression): DoubleArray1D = getView(i0, i1)
    operator fun get(i0: Index, i1: IndexProgression): DoubleArray1D = getView(i0, i1)

    operator fun get(i0: IntProgression  , i1: Int  ): DoubleArray1D = getView(i0, i1)
    operator fun get(i0: IntProgression  , i1: Index): DoubleArray1D = getView(i0, i1)
    operator fun get(i0: IndexProgression, i1: Int  ): DoubleArray1D = getView(i0, i1)
    operator fun get(i0: IndexProgression, i1: Index): DoubleArray1D = getView(i0, i1)

    operator fun get(i0: IntProgression  , i1: IntProgression  ): DoubleArray2D = getView(i0, i1)
    operator fun get(i0: IntProgression  , i1: IndexProgression): DoubleArray2D = getView(i0, i1)
    operator fun get(i0: IndexProgression, i1: IntProgression  ): DoubleArray2D = getView(i0, i1)
    operator fun get(i0: IndexProgression, i1: IndexProgression): DoubleArray2D = getView(i0, i1)

    override fun toDoubleArrayND(): DoubleArray2D = this

    override fun copy(): DoubleArray2D = copy(this)

    override fun asMutable(): MutableDoubleArray2D = this as MutableDoubleArray2D

    override fun iterator(): DoubleIterator =
            DefaultDoubleArray2DIterator(this)

}
