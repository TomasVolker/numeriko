package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.index.toIndex
import tomasvolker.numeriko.core.index.toIndexProgression
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND

fun sliceFromIndices(array: ArrayND<*>, indices: Array<out Any>): ArraySlice {
    val indexList = indices.toList()

    if (indexList.count { it is Ellipsis } > 1)
        throw IllegalArgumentException("More than one Ellipsis used in slicing")

    val ellipsis = indexList.indexOf(Ellipsis).let { if (it < 0) indexList.size else it }

    val processed = indices.mapNotNull {
        when(it) {
            is Int -> Shrink(it.toIndex())
            is Index -> Shrink(it)
            is IntProgression -> Range(it.toIndexProgression())
            is IndexProgression -> Range(it)
            is NewAxis -> NewAxis
            is Ellipsis -> null
            else -> throw IllegalArgumentException("all entries must one of these types: Int, Index, IntProgression, IndexProgression, NewAxis or Ellipsis")
        }
    }

    return array.arraySlice(
            computeAbsoluteEntries(
                    array = array,
                    first = processed.subList(0, ellipsis),
                    last = processed.subList(ellipsis, processed.size)
            )
    )
}

operator fun <T> ArrayND<T>.get(vararg indices: Any): ArrayND<T> =
        getSlice(sliceFromIndices(this, indices))

operator fun <T> MutableArrayND<T>.get(vararg indices: Any): MutableArrayND<T> =
        getSlice(sliceFromIndices(this, indices))

operator fun <T> MutableArrayND<T>.set(vararg indices: Any, value: ArrayND<T>): Unit =
        this.get(*indices).setValue(value)

