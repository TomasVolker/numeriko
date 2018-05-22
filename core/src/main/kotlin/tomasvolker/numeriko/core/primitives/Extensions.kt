package tomasvolker.numeriko.core.primitives

infix fun Int.modulo(other: Int) = ((this % other) + other) % other
infix fun Long.modulo(other: Long) = ((this % other) + other) % other
infix fun Float.modulo(other: Float) = ((this % other) + other) % other
infix fun Double.modulo(other: Double) = ((this % other) + other) % other

