package tomasvolker.numeriko.sandbox.generic

import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.factory.doubleArrayND

fun Array<out Any>.convertToIndicesProgression(): Array<IndexProgression> =
        map { index ->
            when (index) {
                is Int -> (index..index).toIndexProgression()
                is Index -> (index..index)
                is IntProgression -> index.toIndexProgression()
                is IndexProgression -> index
                else -> throw IllegalArgumentException("index is not of type Int, Index, IntProgression or IndexProgression")
            }
        }.toTypedArray()

operator fun <T> ArrayND<T>.get(vararg indices: Int): T =
        getValue(*indices)

operator fun <T> ArrayND<T>.get(vararg indices: Any): ArrayND<T> {
    var view = getView(*indices.convertToIndicesProgression())
    var reductions = 0
    for ((axis, index) in indices.withIndex()) {
        if (index is Int || index is Index) {
            view = view.lowerRank(axis-reductions)
            reductions++
        }
    }
    return view
}

operator fun <T> MutableArrayND<T>.set(vararg indices: Any, array: ArrayND<T>) =
        get(*indices).asMutable().setValue(array)

operator fun DoubleArrayND.get(vararg indices: Int): Double = getDouble(*indices)

operator fun DoubleArrayND.get(vararg indices: Any): DoubleArrayND =
        (this as ArrayND<Double>).get(*indices) as DoubleArrayND

operator fun MutableDoubleArrayND.set(vararg indices: Any, array: DoubleArrayND) =
        get(*indices).asMutable().setValue(array)

fun main() {

    val array = doubleArrayND(4, 3, 2) { (i0, i1, i2) -> (i0 + i1 - i2).toDouble() }

    println(array[0, 0, Last])

}
