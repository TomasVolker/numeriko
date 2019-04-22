package tomasvolker.numeriko.core.primitives

inline fun sumFloat(indices: IntProgression, value: (i: Int)->Float): Float {
    var result = 0.0f
    for (i in indices) {
        result += value(i)
    }
    return result
}

inline fun sumFloat(
        indices0: IntProgression,
        indices1: IntProgression,
        value: (i0: Int, i1: Int)->Float
): Float {
    var result = 0.0f
    for (i0 in indices0) {
        for (i1 in indices1) {
            result += value(i0, i1)
        }
    }
    return result
}

inline fun productFloat(indices: IntProgression, value: (i: Int)->Float): Float {
    var result = 1.0f
    for (i in indices) {
        result *= value(i)
    }
    return result
}
