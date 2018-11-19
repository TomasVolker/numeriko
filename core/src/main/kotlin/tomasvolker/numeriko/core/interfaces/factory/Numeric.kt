package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.NumericArrayND

fun <N: Number> copy(array: NumericArrayND<N>): NumericArrayND<N> =
        NumerikoConfig.defaultFactory.copy(array)