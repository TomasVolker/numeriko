package tomasvolker.numeriko.core.primitives

/**
 * Returns the Char with the numeric value equal to this number, truncated to 16 bits if appropriate.
 */
val Int.c: Char get() = toChar()

/**
 * Returns the value of this number as a Long, which may involve rounding or truncation.
 */
val Int.l: Long get() = toLong()

/**
 * Returns the value of this number as a Double, which may involve rounding.
 */
val Int.d: Double get() = toDouble()

/**
 * Returns the value of this number as a Float, which may involve rounding.
 */
val Int.f: Float get() = toFloat()

/**
 * Returns the Char with the numeric value equal to this number, truncated to 16 bits if appropriate.
 */
val Long.c: Char get() = toChar()

/**
 * Returns the value of this number as an Int, which may involve rounding or truncation.
 */
val Long.i: Int get() = toInt()

/**
 * Returns the value of this number as a Float, which may involve rounding.
 */
val Long.f: Float get() = toFloat()

/**
 * Returns the value of this number as a Double, which may involve rounding.
 */
val Long.d: Double get() = toDouble()

/**
 * Returns the value of this number as a Double, which may involve rounding.
 */
val Float.d: Double get() = toDouble()

/**
 * Returns the value of this number as a Float, which may involve rounding.
 */
val Double.f: Float get() = toFloat()
