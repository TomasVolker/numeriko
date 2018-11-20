package tomasvolker.numeriko.core.interfaces.array0d.double

import tomasvolker.numeriko.core.interfaces.array0d.numeric.NumericArray0D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy

interface DoubleArray0D: NumericArray0D<Double>, DoubleArrayND {

    override fun getValue(vararg indices: Int): Double = getDouble(*indices)

    override fun getDouble(vararg indices: Int): Double {
        requireValidIndices(indices)
        return getDouble()
    }

    override fun getFloat(vararg indices: Int): Float = getDouble(*indices).toFloat()
    override fun getLong (vararg indices: Int): Long  = getDouble(*indices).toLong()
    override fun getInt  (vararg indices: Int): Int   = getDouble(*indices).toInt()
    override fun getShort(vararg indices: Int): Short = getDouble(*indices).toShort()

    fun get(): Double = getDouble()

    override fun getValue(): Double = getDouble()

    override fun getView(): DoubleArray0D = this

    override fun arrayAlongAxis(axis: Int, index: Int): Nothing {
        super<NumericArray0D>.arrayAlongAxis(axis, index)
    }

    override fun lowerRank(axis: Int): Nothing {
        super<NumericArray0D>.lowerRank(axis)
    }

    override fun getDouble(): Double

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
