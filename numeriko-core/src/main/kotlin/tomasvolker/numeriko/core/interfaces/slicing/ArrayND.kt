package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.index.toIndexProgression
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND

private fun Array<out Any>.convertToIndicesProgression(): Array<IndexProgression> =
        map { index ->
            when (index) {
                is Int -> (index..index).toIndexProgression()
                is Index -> (index..index)
                is IntProgression -> index.toIndexProgression()
                is IndexProgression -> index
                else -> throw IllegalArgumentException("index is not of type Int, Index, IntProgression or IndexProgression")
            }
        }.toTypedArray()



operator fun <T> ArrayND<T>.get(vararg indices: Any): ArrayND<T> {
    TODO()
}


operator fun <T> MutableArrayND<T>.get(vararg indices: Any): MutableArrayND<T> =
        (this as ArrayND<T>).get(*indices).asMutable()

operator fun <T> MutableArrayND<T>.set(vararg indices: Any, value: ArrayND<T>): Unit =
        this.get(*indices).setValue(value)



