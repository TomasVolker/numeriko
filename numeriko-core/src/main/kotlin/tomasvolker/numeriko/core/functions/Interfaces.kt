package tomasvolker.numeriko.core.functions

//Non boxed primitive function interfaces

interface FunctionDtoD {
    operator fun invoke(input: Double): Double
}

inline fun DtoD(crossinline function: (input: Double)->Double) =
        object: FunctionDtoD {
            override fun invoke(input: Double): Double = function(input)
        }

interface FunctionDDtoD {
    operator fun invoke(input1: Double, input2: Double): Double
}

inline fun DDtoD(crossinline function: (input1: Double, input2: Double)->Double) =
        object: FunctionDDtoD {
            override fun invoke(input1: Double, input2: Double): Double = function(input1, input2)
        }

interface FunctionIADtoD {
    operator fun invoke(input1: IntArray, input2: Double): Double
}

inline fun IADtoD(crossinline function: (input1: IntArray, input2: Double)->Double) =
        object: FunctionIADtoD {
            override fun invoke(input1: IntArray, input2: Double): Double = function(input1, input2)
        }

interface FunctionFtoF {
    operator fun invoke(input: Float): Float
}

inline fun FtoF(crossinline function: (input: Float)->Float) =
        object: FunctionFtoF {
            override fun invoke(input: Float): Float = function(input)
        }