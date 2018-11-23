package tomasvolker.numeriko.sandbox.generic

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.unsafeGetView
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.unsafeGetView
import tomasvolker.numeriko.core.interfaces.factory.doubleArray0D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.view.ContiguousFirstIndex
import tomasvolker.numeriko.core.view.ContiguousLastIndex


operator fun <T> ArrayND<T>.get(vararg indices: Int): T = getValue(*indices)
operator fun <T> ArrayND<T>.get(vararg indices: Any): ArrayND<T> = unsafeGetView(*indices)

operator fun <T> MutableArrayND<T>.set(vararg indices: Any, array: ArrayND<T>) = unsafeGetView(*indices).setValue(array)

operator fun DoubleArrayND.get(vararg indices: Int): Double = getDouble(*indices)

operator fun DoubleArrayND.get(vararg indices: Any): DoubleArrayND = unsafeGetView(*indices)
operator fun MutableDoubleArrayND.set(vararg indices: Any, array: DoubleArrayND) = unsafeGetView(*indices).setValue(array)

fun main() {

    val array = doubleArray2D(2, 3) { i0, i1 -> i0 * 10 + i1 }

    println(array)
    println(array.linearView(ContiguousFirstIndex).higherRank().lowerRank())
    println(array.linearView(ContiguousLastIndex).higherRank().transpose())
    println(doubleArray0D(5.0))

}
