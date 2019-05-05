package tomasvolker.numeriko.core.interfaces.array1d.integer

import tomasvolker.numeriko.core.annotations.CompileTimeError
import tomasvolker.numeriko.core.annotations.Level
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.DefaultSliceIntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.MutableIntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndex
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.preconditions.rankError
import tomasvolker.numeriko.core.preconditions.requireSameShape

interface MutableIntArray1D: IntArray1D, MutableIntArrayND {

    override operator fun set(i0: Int, value: Int)

    @CompileTimeError(rankError1DMessage, level = Level.ERROR)
    override fun set(value: Int): Nothing = rankError(1)
    @CompileTimeError(rankError1DMessage, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, value: Int): Nothing = rankError(1)
    @CompileTimeError(rankError1DMessage, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, value: Int): Nothing = rankError(1)
    @CompileTimeError(rankError1DMessage, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Int): Nothing = rankError(1)
    @CompileTimeError(rankError1DMessage, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Int): Nothing = rankError(1)
    @CompileTimeError(rankError1DMessage, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Int): Nothing = rankError(1)

    @CompileTimeError(rankError1DMessage, level = Level.ERROR)
    override operator fun set(vararg indices: Int, value: Int) = setInt(indices, value)

    override fun setInt(indices: IntArray, value: Int) {
        require(indices.size == 1) { "passed ${indices.size} indices when 1 was required" }
        set(indices[0], value)
    }

    override fun setInt(indices: IntArray1D, value: Int) {
        require(indices.size == 1) { "passed ${indices.size} indices when 1 was required" }
        set(indices[0], value)
    }

    override fun setValue(value: ArrayND<Int>) =
            if (value is IntArrayND)
                setValue(value)
            else
                super.setValue(value)

    override fun setValue(value: IntArrayND) {
        requireSameShape(this, value)
        // Anti alias copy
        val copy = value.copy()
        copy.unsafeForEachIndex { indices ->
            setValue(indices, copy.getValue(indices))
        }
    }

    override fun slice(
            slice: ArraySlice
    ): MutableIntArrayND = DefaultSliceIntArrayND(
            array = this,
            slice = slice
    )

    override fun setValue(indices: IntArray, value: Int) = setInt(indices, value)

}
