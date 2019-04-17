package tomasvolker.numeriko.core.operations

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.arrayAlongAxis
import tomasvolker.numeriko.core.interfaces.factory.*




fun <T> ArrayND<T>.unstack(axis: Int = 0): List<ArrayND<T>> =
        List(shape(axis)) { i -> this.arrayAlongAxis(axis = axis, index = i) }
