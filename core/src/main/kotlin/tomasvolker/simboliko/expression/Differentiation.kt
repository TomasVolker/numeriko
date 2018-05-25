package tomasvolker.simboliko.expression

import tomasvolker.simboliko.constant.Constant
import tomasvolker.simboliko.constant.One
import tomasvolker.simboliko.constant.Zero
import tomasvolker.simboliko.expression.variable.Variable
import tomasvolker.simboliko.function1.DifferentiableFunction1
import tomasvolker.simboliko.function1.RealFunction1
import tomasvolker.simboliko.function2.DifferentiableFunction2
import tomasvolker.simboliko.function2.RealFunction2

fun RealExpression.isDifferentiable(withRespectTo: Variable<*>): Boolean {
    when (this) {
        is Variable<*> -> return true
        is Constant -> return true
        is Function1Application -> {

            if (this.input.dependsOn(withRespectTo)) {

                if(this.function !is DifferentiableFunction1 ||
                   !this.input.isDifferentiable(withRespectTo))
                    return false

            }

            return true
        }
        is Function2Application -> {

            if (this.input1.dependsOn(withRespectTo)) {

                if(this.function !is DifferentiableFunction2) return false

                if(!this.input1.isDifferentiable(withRespectTo)) return false

            }

            if (this.input2.dependsOn(withRespectTo)) {

                if(this.function !is DifferentiableFunction2) return false

                if(!this.input2.isDifferentiable(withRespectTo)) return false


            }
            return true
        }

        else -> return false
    }
}

fun RealExpression.isDifferentiable(vararg withRespectTo: Variable<*>): Boolean =
        withRespectTo.all { isDifferentiable(it) }

class NonDifferentiableFunction1Exception(
        val function: RealFunction1
): Exception("Function $function is not differentiable")

class NonDifferentiableFunction2Exception(
        val function: RealFunction2
): Exception("Function $function is not differentiable")

class NonDifferentiableExpressionException(
        val expression: Expression<*>
): Exception("Expression $expression is not differentiable")

fun RealExpression.derivative(withRespectTo: Variable<*>): RealExpression {

    if (!dependsOn(withRespectTo))
        return Zero

    return when(this) {

        is Constant -> {
            Zero
        }

        is Variable<*> -> {
            if (this == withRespectTo) One else Zero
        }

        is Function1Application -> {
            if (this.function is DifferentiableFunction1)
                this.function.derivative().invoke(input) * input.derivative(withRespectTo)
            else
                throw NonDifferentiableFunction1Exception(this.function)
        }

        is Function2Application -> {
            if (this.function is DifferentiableFunction2)
                function.derivative1()(input1, input2) * input1.derivative(withRespectTo) +
                function.derivative2()(input1, input2) * input2.derivative(withRespectTo)
            else
                throw NonDifferentiableFunction2Exception(this.function)
        }

        else -> throw NonDifferentiableExpressionException(this)
    }

}



fun RealExpression.derivative(withRespectTo: List<Variable<*>>):  List<RealExpression> =
        withRespectTo.map { derivative(it) }