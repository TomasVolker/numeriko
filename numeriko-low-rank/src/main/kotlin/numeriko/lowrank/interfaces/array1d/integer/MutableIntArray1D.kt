package numeriko.lowrank.interfaces.array1d.integer

import tomasvolker.core.index.Index
import tomasvolker.core.index.IndexProgression
import numeriko.lowrank.interfaces.array1d.generic.MutableArray1D
import numeriko.lowrank.interfaces.array1d.generic.indices
import numeriko.lowrank.interfaces.array1d.integer.view.DefaultMutableIntArray1DView
import tomasvolker.core.interfaces.factory.copy
import tomasvolker.core.preconditions.requireSameSize

interface MutableIntArray1D: IntArray1D, MutableArray1D<Int> {

    fun setInt(i0: Int, value: Int)

    fun setInt(i0: Index, value: Int) = setInt(i0.computeValue(size), value)

    override fun setValue(i0: Int, value: Int) = setInt(i0, value)

    fun setValue(other: IntArray1D) {
        requireSameSize(other, this)
        // Anti alias copy
        val copy = other.copy()
        for (i in indices) {
            setInt(i, copy.getInt(i))
        }

    }

    override fun getView(i0: IntProgression): MutableIntArray1D =
            DefaultMutableIntArray1DView(
                    array = this,
                    offset = i0.first,
                    size = i0.count(),
                    stride = i0.step
            )

    override fun getView(i0: IndexProgression): MutableIntArray1D =
            getView(i0.computeProgression(size))

    fun setView(value: IntArray1D, indexRange: IndexProgression) =
            setView(value, indexRange.computeProgression(size))

    fun setView(value: IntArray1D, indexRange: IntProgression) =
            getView(indexRange).setValue(value)

    override fun copy(): MutableIntArray1D = copy(this).asMutable()

    override operator fun get(index: IntProgression): MutableIntArray1D = getView(index)
    override operator fun get(index: IndexProgression): MutableIntArray1D = getView(index)

    operator fun set(index: Int, value: Int) = setValue(index, value)
    operator fun set(index: Index, value: Int) = setValue(index, value)

    operator fun set(index: IntProgression, value: IntArray1D) = setView(value, index)
    operator fun set(index: IndexProgression, value: IntArray1D) = setView(value, index)

}
