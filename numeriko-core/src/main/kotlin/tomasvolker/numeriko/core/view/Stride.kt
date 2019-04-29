package tomasvolker.numeriko.core.view


sealed class ElementOrder {

    abstract fun strideArray(shape: IntArray): IntArray

    abstract fun linearToIndices(linearIndex: Int, strideArray: IntArray): IntArray

}

object ContiguousFirstAxis: ElementOrder() {

    override fun strideArray(shape: IntArray): IntArray =
            IntArray(shape.size).apply {
                if (shape.isNotEmpty()) {
                    this[0] = 1
                    for (axis in 1 until shape.size) {
                        this[axis] = this[axis - 1] * shape[axis - 1]
                    }
                }
            }

    override fun linearToIndices(linearIndex: Int, strideArray: IntArray): IntArray =
            IntArray(strideArray.size) { a ->
                (if(a == strideArray.size-1)
                    linearIndex
                else
                    linearIndex % strideArray[a+1]
                ) / strideArray[a]
            }

}

object ContiguousLastAxis: ElementOrder() {

    override fun strideArray(shape: IntArray): IntArray =
            IntArray(shape.size).apply {
                if (shape.isNotEmpty()) {
                    val lastAxis = shape.lastIndex
                    this[lastAxis] = 1
                    for (axis in lastAxis-1 downTo 0) {
                        this[axis] = this[axis+1] * shape[axis+1]
                    }
                }
            }

    override fun linearToIndices(linearIndex: Int, strideArray: IntArray): IntArray =
            IntArray(strideArray.size) { a ->
                (if(a == 0)
                    linearIndex
                else
                    linearIndex % strideArray[a-1]
                ) / strideArray[a]
            }

}

fun elementOrderOf(
        shapeArray: IntArray,
        strideArray: IntArray
): ElementOrder? {

    if (ContiguousLastAxis.strideArray(shapeArray).contentEquals(strideArray))
        return ContiguousLastAxis

    if (ContiguousFirstAxis.strideArray(shapeArray).contentEquals(strideArray))
        return ContiguousFirstAxis

    return null
}