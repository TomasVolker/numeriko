package tomasvolker.numeriko.sandbox.generic

import tomasvolker.numeriko.core.interfaces.array2d.double.transpose
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.unsafeGetView
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.unsafeGetView
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.operations.reduction.reduce


operator fun <T> ArrayND<T>.get(vararg indices: Int): T = getValue(indices)
operator fun <T> ArrayND<T>.get(vararg indices: Any): ArrayND<T> = unsafeGetView(*indices)

operator fun <T> MutableArrayND<T>.set(vararg indices: Any, array: ArrayND<T>) = unsafeGetView(*indices).setValue(array)

operator fun DoubleArrayND.get(vararg indices: Any): DoubleArrayND = unsafeGetView(*indices)
operator fun MutableDoubleArrayND.set(vararg indices: Any, array: DoubleArrayND) = unsafeGetView(*indices).setValue(array)

fun main() {

    val array = doubleArray1D(16) { i0 -> i0 }

    println(array.withShape(2, 3).transpose().linearView())

    println(
            array.withShape(2, 3)
                    .transpose()
                    .reduce(axis = 0) { it.sum() }
    )

}
