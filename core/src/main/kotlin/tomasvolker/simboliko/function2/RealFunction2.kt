package tomasvolker.simboliko.function2

import tomasvolker.simboliko.asLiteral
import tomasvolker.simboliko.expression.*
import tomasvolker.simboliko.number.RealNumber

interface Function2<in I1, in I2, out O> {

    operator fun invoke(input1: I1, input2: I2): O = TODO()

    operator fun invoke(input1: Expression<I1>, input2: Expression<I2>): Expression<O>

}

interface RealFunction2: Function2<RealNumber, RealNumber, RealNumber> {

    override fun invoke(input1: RealNumber, input2: RealNumber) =
            ConstantRealExpression(this(input1.asLiteral(), input2.asLiteral()))

    operator fun invoke(input1: Double, input2: Double): Double =
            compute(input1, input2)

    fun compute(input1: Double, input2: Double) : Double

    fun toString(input1: String, input2: String): String

    override operator fun invoke(input1: Expression<RealNumber>, input2: Expression<RealNumber>): Expression<RealNumber> =
            Function2Application(this, input1, input2)

}

interface DifferentiableFunction2: RealFunction2 {

    fun derivative1(): DifferentiableFunction2

    fun derivative2(): DifferentiableFunction2

    fun derivative1At(input1: Double, input2: Double): Double = derivative1()(input1, input2)

    fun derivative2At(input1: Double, input2: Double): Double = derivative2()(input1, input2)

}


fun RealFunction2.defaultToString() = "x1, x2 -> ${toString("x1", "x2")}"


