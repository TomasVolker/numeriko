package tomasvolker.numeriko.core.index

import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntNDArray

open class IndexProgression(val first: Index, val last: Index, val stride: Int = 1) {

    fun computeProgression(shape: ReadOnlyIntNDArray, dimension: Int) =
            IntProgression.fromClosedRange(
                    first.computeValue(shape, dimension),
                    last.computeValue(shape, dimension),
                    stride
            )

}

operator fun Int.rangeTo(index: Index) =
        IndexProgression(this.toIndex(), index)

infix fun Int.until(index: Index) =
        IndexProgression(this.toIndex(), index-1)

object All: IndexProgression(0.toIndex(), Last)

infix fun IndexProgression.step(step: Int) = IndexProgression(first, last, step)
