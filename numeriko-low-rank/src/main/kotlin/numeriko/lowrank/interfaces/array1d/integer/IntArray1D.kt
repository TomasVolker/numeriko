package numeriko.lowrank.interfaces.array1d.integer

import tomasvolker.core.index.Index
import tomasvolker.core.index.IndexProgression
import tomasvolker.core.interfaces.factory.copy
import numeriko.lowrank.interfaces.array1d.generic.Array1D
import numeriko.lowrank.interfaces.array1d.integer.view.defaultIntArray1DView

interface IntArray1D: Array1D<Int> {

    override fun getValue(i0: Int): Int = getInt(i0)

    fun getInt(i0: Int): Int

    fun getInt(i0: Index): Int = getInt(i0.computeValue(size))

    override fun getView(i0: IntProgression): IntArray1D = defaultIntArray1DView(this.asMutable(), i0)
    override fun getView(i0: IndexProgression): IntArray1D = getView(i0.compute())

    override fun copy(): IntArray1D = copy(this)

    override fun iterator(): IntIterator = DefaultIntArray1DIterator(this)

    operator fun get(index: Int): Int = getInt(index)
    operator fun get(index: Index): Int = getInt(index)

    operator fun get(index: IntProgression): IntArray1D = getView(index)
    operator fun get(index: IndexProgression): IntArray1D = getView(index)

    override fun asMutable(): MutableIntArray1D = this as MutableIntArray1D

    fun toIntArray(): IntArray = IntArray(size) { i -> this[i] }

    operator fun unaryPlus(): IntArray1D = this
    operator fun unaryMinus(): IntArray1D = elementWise { -it }

    operator fun plus (other: IntArray1D): IntArray1D = elementWise(this, other) { t, o -> t + o }
    operator fun minus(other: IntArray1D): IntArray1D = elementWise(this, other) { t, o -> t - o }
    operator fun times(other: IntArray1D): IntArray1D = elementWise(this, other) { t, o -> t * o }
    operator fun div  (other: IntArray1D): IntArray1D = elementWise(this, other) { t, o -> t / o }

    operator fun plus (other: Int): IntArray1D = elementWise { it + other }
    operator fun minus(other: Int): IntArray1D = elementWise { it - other }
    operator fun times(other: Int): IntArray1D = elementWise { it * other }
    operator fun div  (other: Int): IntArray1D = elementWise { it / other }
    
}
