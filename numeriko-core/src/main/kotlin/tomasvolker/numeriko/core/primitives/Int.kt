package tomasvolker.numeriko.core.primitives

inline fun sumInt(indices: IntProgression, value: (i: Int)->Int): Int  {
    var result = 0
    for (i in indices) {
        result += value(i)
    }
    return result
}

inline fun sumInt(
        indices0: IntProgression,
        indices1: IntProgression,
        value: (i0: Int, i1: Int)->Int
): Int {
    var result = 0
    for (i0 in indices0) {
        for (i1 in indices1) {
            result += value(i0, i1)
        }
    }
    return result
}

inline fun productInt(indices: IntProgression, value: (i: Int)->Int): Int  {
    var result = 1
    for (i in indices) {
        result *= value(i)
    }
    return result
}
