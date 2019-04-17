package tomasvolker.numeriko.core.operations

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.arrayAlongAxis
import tomasvolker.numeriko.core.interfaces.factory.arrayND
import tomasvolker.numeriko.core.interfaces.factory.arrayNDOfNulls
import tomasvolker.numeriko.core.interfaces.factory.intArrayND


fun <T> List<ArrayND<T>>.stack(axis: Int = 0): ArrayND<T> {

    if (isEmpty())
        return arrayND(intArrayOf(0), arrayOf<Any>() as Array<T>)

    val firstShape = first().shape
    require(all { it.shape == firstShape }) { "All shapes must be the same" }

    val result = arrayNDOfNulls<T>(
            intArrayND(firstShape.size+1) { (a0) ->
                when {
                    a0 < axis -> firstShape[a0]
                    a0 == axis -> size
                    else -> firstShape[a0-1]
                }
            }
    ).asMutable()

    forEachIndexed { i, array ->
        result.arrayAlongAxis(axis = axis, index = i).asMutable().setValue(array)
    }

    return result as ArrayND<T>
}
