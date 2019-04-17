package tomasvolker.numeriko.core.operations.reduction

import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.intArrayND

fun IntArrayND.product(): Int = fold(1) { acc, next -> acc * next }

fun IntArrayND.remove(index: Int): IntArrayND =
        intArrayND(size - 1) { (i) ->
            if (i < index)
                this[i]
            else
                this[i + 1]
        }

fun IntArrayND.inject(index: Int, value: Int): IntArrayND =
        intArrayND(size + 1) { (i) ->
            when {
                i < index -> this[i]
                i == index -> value
                i > index -> this[i - 1]
                else -> throw IllegalStateException()
            }
        }
