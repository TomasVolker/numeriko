package tomasvolker.numeriko.zoo.co2

import koma.extensions.get
import koma.fill
import koma.matrix.Matrix
import kotlin.math.pow

fun regressionMatrix(evaluationPoints: Matrix<Double>, basisFunctions: List<(Double)->Double>) =
    fill(evaluationPoints.numRows(), basisFunctions.size) { row, col -> basisFunctions[col](evaluationPoints[row]) }

fun polynomialRegressionMatrix(evaluationPoints: Matrix<Double>, order: Int = 1) =
    regressionMatrix(
        evaluationPoints = evaluationPoints,
        basisFunctions = buildPolynomialBasisFunctions(order)
    )

fun buildPolynomialBasisFunctions(order: Int): List<(Double)->Double> =
    List(order + 1) { i -> { x: Double -> x.pow(i) } }

fun linearRegressionMatrix(evaluationPoints: Matrix<Double>) =
    polynomialRegressionMatrix(evaluationPoints, 1)

fun regression(
    x: List<Number>,
    y: List<Number>,
    basisFunctions: List<(Double)->Double>
): (Double)->Double {
    val xMat = x.toColMatrix()
    val yMat = y.toColMatrix()
    val X = regressionMatrix(xMat, basisFunctions)
    val weights = (X.transpose() * X).solve(X.transpose() * yMat).toList()
    return  { input: Double ->
        val basisFunctionSequence = basisFunctions.asSequence()
        val weightSequence = weights.asSequence()
        basisFunctionSequence.map { it(input) }
            .zip(weightSequence).
                sumByDouble { it.first * it.second }
    }
}

fun polynomialRegression(
    x: List<Number>,
    y: List<Number>,
    order: Int
) =
    regression(
        x = x,
        y = y,
        basisFunctions = buildPolynomialBasisFunctions(order)
    )

fun linearRegression(
    x: List<Number>,
    y: List<Number>
) =
    polynomialRegression(
        x = x,
        y = y,
        order = 1
    )