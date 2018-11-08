package tomasvolker.numeriko.complex.transforms

fun <T> Iterable<T>.partitionByIndex(predicate: (Int)->Boolean): Pair<List<T>, List<T>> {
    val trueList = mutableListOf<T>()
    val falseList = mutableListOf<T>()
    forEachIndexed { i, element ->
        if(predicate(i))
            trueList.add(element)
        else
            falseList.add(element)
    }
    return trueList to falseList
}

fun <T, R> Pair<T, T>.mapBoth(operation: (T)->R): Pair<R, R> =
        Pair(operation(first), operation(second))

inline fun DoubleArray.replaceAll(operation: (Double)->Double) {
    for (n in indices) {
        this[n] = operation(this[n])
    }
}

inline fun <T> Array<T>.replaceAll(operation: (T)->T) {
    for (n in indices) {
        this[n] = operation(this[n])
    }
}