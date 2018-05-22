package tomasvolker.numeriko.core.dsl

import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.MutableIntArray1D
import tomasvolker.numeriko.core.interfaces.factory.asMutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.asMutableIntArray1D

object arr {

    operator fun get(vararg values: Int): MutableIntArray1D =
            values.asMutableIntArray1D()

    operator fun get(vararg values: Double): MutableDoubleArray1D =
            values.asMutableDoubleArray1D()

}