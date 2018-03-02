package tomasvolker.numeriko.core.debug

import tomasvolker.numeriko.core.array.arrayNDArrayFactory
import tomasvolker.numeriko.core.interfaces.integer.IntNDArray

object DebugArrays {

    fun debugIntArray(vararg shape: Int): IntNDArray {
        var aux = 0
        return arrayNDArrayFactory.array(*shape) { aux++ }

    }

}