package tomasvolker.numeriko.core.functional.operators

import tomasvolker.numeriko.core.functional.Function1
import tomasvolker.numeriko.core.functional.One
import tomasvolker.numeriko.core.functional.affine.linear.LinearFunction
import java.beans.Expression

object Identity: LinearFunction {

    override val m: Double get() = 1.0

    override fun invoke(input: Double) = input

    override fun derivative() = One
    //override fun derivativeAt(input: Double) = 1.0

    override fun toString(input: String) = input

}

operator fun <T: Function1> T.unaryPlus(): T = this
operator fun <T: Expression> T.unaryPlus(): T = this
