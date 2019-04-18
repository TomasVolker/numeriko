package tomasvolker.numeriko.core.index

open class IndexProgression(
        val first: Index,
        val last: Index,
        val step: Int = 1
) {

    fun computeProgression(size: Int) =
            IntProgression.fromClosedRange(
                    first.computeValue(size),
                    last.computeValue(size),
                    step
            )

    fun reversed(): IndexProgression =
            IndexProgression(
                    first = last,
                    last = first,
                    step = -step
            )

    override fun toString(): String = "$first..$last step $step"

}

operator fun Int.rangeTo(index: Index) =
        IndexProgression(this.toIndex(), index)

infix fun Int.until(index: Index) =
        IndexProgression(this.toIndex(), index-1)

object All: IndexProgression(0.toIndex(), Last)

infix fun IndexProgression.step(step: Int) = IndexProgression(first, last, this.step * step)

fun IntProgression.toIndexProgression() = IndexProgression(first.toIndex(), last.toIndex(), step)
