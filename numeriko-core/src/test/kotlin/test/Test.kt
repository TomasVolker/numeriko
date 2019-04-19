package test

import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.interfaces.factory.intArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.iteration.forEachIndex
import tomasvolker.numeriko.core.interfaces.slicing.*
import tomasvolker.numeriko.core.operations.concat

fun main() {

    val matrix = intArrayOf(
            1, 2, 3,
            4, 6, 5,
            7, 8, 8,
            7, 8, 8
    ).toIntArray1D(I[4, 3]).asMutable()

    val vector = I[1, 2, 3 ,4].expandRank(1)

}
