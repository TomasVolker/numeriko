package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND

fun sliceFromIndices(array: ArrayND<*>, indices: Array<out Any>): ArraySlice {
    val indexList = indices.toList()

    require(indexList.count { it is Ellipsis } <= 1) { "More than one Ellipsis used in slicing" }

    val ellipsis = indexList.indexOf(Ellipsis)

    val entries = indices.mapNotNull {
        when(it) {
            is Int -> Shrink(it.toIndex())
            is Index -> Shrink(it)
            is IntProgression -> Range(it.toIndexProgression())
            is IndexProgression -> Range(it)
            is NewAxis -> NewAxis
            is Ellipsis -> null
            else -> throw IllegalArgumentException(
                    "all entries must one of these types: Int, Index, IntProgression, IndexProgression, NewAxis or Ellipsis"
            )
        }
    }
    val accessedAxes = entries.count { it !is NewAxis }

    if (ellipsis < 0) {

        require(accessedAxes == array.rank) {
            "Trying to access $accessedAxes axes in a rank ${array.rank} array"
        }

        return array.arraySlice(entries)

    } else {

        require(accessedAxes <= array.rank) {
            "Trying to access $accessedAxes axes in a rank ${array.rank} array"
        }

        val first = entries.subList(0, ellipsis)
        val remaining = List(array.rank - accessedAxes) { Range(All) }
        val last = entries.subList(ellipsis, entries.size)

        return array.arraySlice(first + remaining + last)
    }

}

operator fun <T> ArrayND<T>.get(vararg indices: Any): ArrayND<T> =
        getSlice(sliceFromIndices(this, indices))

operator fun <T> MutableArrayND<T>.get(vararg indices: Any): MutableArrayND<T> =
        getSlice(sliceFromIndices(this, indices))

operator fun <T> MutableArrayND<T>.set(vararg indices: Any, value: ArrayND<T>): Unit =
        this.get(*indices).setValue(value)

