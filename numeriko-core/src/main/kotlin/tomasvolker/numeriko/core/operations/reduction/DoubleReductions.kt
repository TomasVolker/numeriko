package tomasvolker.numeriko.core.operations.reduction

inline fun reduce(
        indices: IntProgression,
        initial: Double,
        combine: (acc: Double, next: Double)->Double,
        selector: (Int)->Double
): Double {
    var result = initial
    for (i in indices) {
        result = combine(result, selector(i))
    }
    return result
}
/*
inline fun DoubleArrayND.reduce(
        initial: Double,
        axis: Int = 0,
        combine: (acc: Double, next: Double)->Double
): DoubleArrayND =
        doubleArrayND(shape.remove(axis)) { indices ->
            reduce(this.indices(axis), initial, combine) { ir ->
                this[indices.inject(index = axis, value = ir)]
            }
        }

inline fun DoubleArrayND.reduce(
        axis: Int = 0,
        reduction: (acc: DoubleArray1D)->Double
): DoubleArrayND =
        doubleArrayND(shape.remove(axis)) { indices ->
            reduction(
                    this.get(
                        *Array<Any>(rank) { a ->
                            when {
                                a < axis -> indices[a]
                                a == axis -> All
                                else -> indices[a-1]
                            }
                        }
                    ).as1D()
            )
        }


*/