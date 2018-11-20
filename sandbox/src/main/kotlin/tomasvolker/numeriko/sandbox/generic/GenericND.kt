package tomasvolker.numeriko.sandbox.generic

import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.unsafeGetView
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.unsafeGetView
import tomasvolker.numeriko.core.interfaces.factory.arrayND


operator fun <T> ArrayND<T>.get(vararg indices: Int): T = getValue(*indices)
operator fun <T> ArrayND<T>.get(vararg indices: Any): ArrayND<T> = unsafeGetView(*indices)

operator fun <T> MutableArrayND<T>.set(vararg indices: Any, array: ArrayND<T>) = unsafeGetView(*indices)

operator fun DoubleArrayND.get(vararg indices: Int): Double = getDouble(*indices)

operator fun DoubleArrayND.get(vararg indices: Any): DoubleArrayND = unsafeGetView(*indices)
operator fun MutableDoubleArrayND.set(vararg indices: Any, array: DoubleArrayND) = unsafeGetView(*indices)

fun main() {

    val array = arrayND(10, 12, 8) { (i0, i1, i2) -> "$i0$i1$i2" }

    println(array[8..Last, 0, 2])

}
