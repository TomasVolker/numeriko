package tomasvolker.numeriko.core.dsl

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.core.operations.stack

object A {

    operator fun <T> get(vararg values: T): ArrayND<T> =
            values.asArrayND()

    operator fun get(vararg values: Int): IntArrayND =
            values.asIntArrayND()

    operator fun get(vararg values: Double): DoubleArrayND =
            values.asDoubleArrayND()

    operator fun <T> get(vararg values: ArrayND<T>): ArrayND<T> = values.toList().stack()

}

object D {

    operator fun get(vararg values: Number): DoubleArrayND =
            values.map { it.toDouble() }.toDoubleArray().asDoubleArrayND()

    //operator fun get(vararg values: DoubleArrayND): DoubleArrayND = values.toList().stack()

}

object I {

    operator fun get(vararg values: Int): IntArrayND =
            values.asIntArrayND()

}