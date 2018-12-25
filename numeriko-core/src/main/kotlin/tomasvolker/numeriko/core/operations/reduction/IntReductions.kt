package tomasvolker.numeriko.core.operations.reduction

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.factory.intArray1D

fun IntArray1D.product(): Int = fold(1) { acc, next -> acc * next }

fun IntArray1D.remove(index: Int): IntArray1D =
        intArray1D(size - 1) { i ->
            if (i < index)
                this[i]
            else
                this[i + 1]
        }

fun IntArray1D.inject(index: Int, value: Int): IntArray1D =
        intArray1D(size + 1) { i ->
            when {
                i < index -> this[i]
                i == index -> value
                i > index -> this[i - 1]
                else -> throw IllegalStateException()
            }
        }
