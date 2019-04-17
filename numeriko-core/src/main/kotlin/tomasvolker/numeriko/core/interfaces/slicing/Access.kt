package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND

fun <T> ArrayND<T>.get(): T = getValue()
operator fun <T> ArrayND<T>.get(i0: Int): T = getValue(i0)
operator fun <T> ArrayND<T>.get(i0: Int, i1: Int): T = getValue(i0, i1)
operator fun <T> ArrayND<T>.get(i0: Int, i1: Int, i2: Int): T = getValue(i0, i1, i2)
operator fun <T> ArrayND<T>.get(i0: Int, i1: Int, i2: Int, i3: Int): T = getValue(i0, i1, i2, i3)
operator fun <T> ArrayND<T>.get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): T = getValue(i0, i1, i2, i3, i4)
operator fun <T> ArrayND<T>.get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): T = getValue(i0, i1, i2, i3, i4, i5)
operator fun <T> ArrayND<T>.get(vararg indices: Int): T = getValue(indices)

operator fun <T> MutableArrayND<T>.set(vararg indices: Int, value: T) = setValue(indices, value)


sealed class SliceEntry

object NewAxis: SliceEntry()

class Shrink(
        val index: Index
): SliceEntry() {

    fun index(size: Int) = index.computeValue(size)

}

class Range(
        val progression: IndexProgression
): SliceEntry() {

    fun progression(size: Int): IntProgression = progression.computeProgression(size)

    fun start(size: Int): Int = progression.first.computeValue(size)
    fun end(size: Int): Int = progression.last.computeValue(size)
    fun length(size: Int): Int {
        val first = progression.first.computeValue(size)
        val last = progression.last.computeValue(size)
        val step = progression.step
        return ((last - first + step) / step).coerceAtLeast(0)
    }

    val step: Int get() = progression.step
}
