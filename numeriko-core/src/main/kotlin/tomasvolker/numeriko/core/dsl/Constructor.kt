package tomasvolker.numeriko.core.dsl

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.core.operations.stack

object A {

    operator fun <T> get(vararg values: T): ArrayND<T> =
            values.asArrayND()

    operator fun get(vararg values: Int): IntArrayND =
            values.asIntArray1D()

    operator fun get(vararg values: Double): DoubleArrayND =
            values.asDoubleArrayND()

    operator fun <T> get(vararg values: ArrayND<T>): ArrayND<T> = values.toList().stack()

}

object D {

    operator fun get(vararg values: Number): DoubleArrayND =
            values.map { it.toDouble() }.toDoubleArray().asDoubleArrayND()

    operator fun get(vararg values: DoubleArrayND): DoubleArrayND = values.toList().stack()

}

object F {

    operator fun get(vararg values: Number): FloatArrayND =
            values.map { it.toFloat() }.toFloatArray().asFloatArrayND()

    operator fun get(vararg values: FloatArrayND): FloatArrayND = values.toList().stack()

}

object I {

    operator fun get(vararg values: Int): IntArray1D =
            values.asIntArray1D()

    operator fun get(vararg values: IntArrayND): IntArrayND = values.toList().stack()

}

object B {

    operator fun get(vararg values: Boolean): ArrayND<Boolean> =
            arrayND(intArrayOf(values.size), values.toTypedArray())

}