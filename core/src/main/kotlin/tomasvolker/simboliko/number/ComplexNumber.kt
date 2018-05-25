package tomasvolker.simboliko.number

interface RealNumber {

    fun getDouble(): Double

    fun getFloat(): Float = getDouble().toFloat()

}

interface IntegerNumber: RealNumber {

    override fun getDouble(): Double =
            getLong().toDouble()

    fun getLong(): Long

    fun getInt(): Int

}

interface MathSet {

    operator fun contains(value: Any?): Boolean

    operator fun contains(other: MathSet) = when(other) {
        is EnumeratedSet<*> -> other.all { this.contains(it) }
        else -> false
    }

}

object RealSet: MathSet {

    override fun contains(value: Any?): Boolean =
            value is RealNumber

    override fun contains(other: MathSet): Boolean = when(other) {
        is EnumeratedSet<*> -> other.all { this.contains(it) }
        is IntegerSet -> true
        else -> false
    }

}

object IntegerSet: MathSet {

    override fun contains(value: Any?): Boolean =
            value is IntegerNumber

    override fun contains(other: MathSet): Boolean = when(other) {
        is IntegerSet -> true
        is EnumeratedSet<*> -> other.all { this.contains(it) }
        else -> false
    }

}

interface SetFunction2 {

    operator fun invoke(input1: MathSet, input2: MathSet): MathSet

}

object Intersection: SetFunction2 {

    override fun invoke(input1: MathSet, input2: MathSet): MathSet =
            TODO()

}

class EnumeratedSet<T>(
        val values: Set<T>
) : MathSet, Iterable<T> by values {

    override fun contains(value: Any?) =
            values.contains(value)

    override fun contains(other: MathSet) = when(other) {
        is EnumeratedSet<*> -> other.values.all { this.contains(it) }
        else -> false
    }

}