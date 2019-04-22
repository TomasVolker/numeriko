package tomasvolker.numeriko.core.primitives

inline fun sumDouble(indices: IntProgression, value: (i: Int)->Double): Double {
    var result = 0.0
    for (i in indices) {
        result += value(i)
    }
    return result
}

inline fun sumDouble(
        indices0: IntProgression,
        indices1: IntProgression,
        value: (i0: Int, i1: Int)->Double
): Double {
    var result = 0.0
    for (i0 in indices0) {
        for (i1 in indices1) {
            result += value(i0, i1)
        }
    }
    return result
}

inline fun productDouble(indices: IntProgression, value: (i: Int)->Double): Double {
    var result = 1.0
    for (i in indices) {
        result *= value(i)
    }
    return result
}
