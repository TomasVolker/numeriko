package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND

fun DoubleArrayND.get(vararg indices: Any): DoubleArrayND =
        (this as ArrayND<Double>).get(*indices) as DoubleArrayND

fun MutableDoubleArrayND.get(vararg indices: Any): MutableDoubleArrayND =
        (this as DoubleArrayND).get(*indices) as MutableDoubleArrayND
