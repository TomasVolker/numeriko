package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression

sealed class SliceEntry

object NewAxis: SliceEntry()

data class Shrink(
        val index: Index
): SliceEntry() {

    fun index(size: Int) = index.computeValue(size)

}

data class Range(
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

object Ellipsis {
    override fun toString(): String = "..."
}

val `~` = Ellipsis
