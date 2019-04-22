package tomasvolker.numeriko.core.preconditions

fun illegalArgument(message: String): Nothing =
        throw IllegalArgumentException(message)