package tomasvolker.numeriko.core.functional.double

inline fun function(
        crossinline toString: ((input: String)->String) = { "fun($it)" },
        crossinline lambda: (Double)->Double
): Function =
        object : Function {
            override fun invoke(input: Double): Double = lambda(input)
            override fun toString(input: String): String = toString(input)
        }

fun <L: Function, R: Function> compose(function1: L, function2: R) =
        FunctionComposition(function1, function2)

fun <L: DifferentiableFunction, R: DifferentiableFunction> composeDifferentiable(function1: L, function2: R) =
        DifferentiableComposition(function1, function2)

fun constantFunction(value: Double): ConstantFunction = DefaultConstantFunction(value)

fun affineFunction(y0: Double, m: Double) = DefaultAffineFunction(y0, m)

fun linearFunction(m: Double) = DefaultLinearFunction(m)

inline fun differentialFunction(
        crossinline lambda: (Double)->Double,
        derivative: DifferentiableFunction,
        crossinline toString: ((input: String)->String) = { "fun($it)" }
): DifferentiableFunction =
        object : DifferentiableFunction {
            override fun invoke(input: Double): Double = lambda(input)
            override fun differentiate() = derivative
            override fun toString(input: String): String = toString(input)
        }

fun Double.asConstantFunction() = constantFunction(this)
