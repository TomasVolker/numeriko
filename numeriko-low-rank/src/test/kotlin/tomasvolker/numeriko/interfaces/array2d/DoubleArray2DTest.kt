package tomasvolker.numeriko.interfaces.array2d

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import tomasvolker.numeriko.core.dsl.D
import tomasvolker.core.index.All
import tomasvolker.core.interfaces.factory.*
import numeriko.tests.*
import tomasvolker.numeriko.tests.assertNotNumericEquals
import tomasvolker.numeriko.tests.assertNumericEquals
import tomasvolker.numeriko.tests.assertPositive
import kotlin.random.Random
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse


class DoubleArray2DTest {

    @Nested
    inner class Constructors {

        @Test
        fun `lambda and dsl`() {

            with(Random(0)) {

                val shape0 = 3
                val shape1 = 2

                val values = Array(shape0) { DoubleArray(shape0) { nextDouble() } }

                val a1 = doubleArray2D(shape0, shape1) { i0, i1 -> values[i0][i1] }
                val a2 = tomasvolker.numeriko.core.dsl.D[tomasvolker.numeriko.core.dsl.D[values[0][0], values[0][1]],
                           tomasvolker.numeriko.core.dsl.D[values[1][0], values[1][1]],
                           tomasvolker.numeriko.core.dsl.D[values[2][0], values[2][1]]]

                assertNumericEquals(a1, a2)

            }

        }
    }

    @Test
    fun operators() {

        with(Random(0)) {

            val shape0 = 4
            val shape1 = 3

            val a1 = nextDoubleArray2D(shape0, shape1, from = -12.3, until = 7.5)
            val a2 = nextDoubleArray2D(shape0, shape1, from = -6.0, until = 8.9)
            val a3 = nextDoubleArray2D(shape0, shape1, from = -12.3, until = 7.5)
            val zero = doubleZeros(shape0, shape1)
            val one = doubleArray2D(shape0, shape1) { _, _ -> 1.0 }

            assertNumericEquals(a1 + a2, a2 + a1)
            assertNumericEquals(a1, a1 + zero)
            assertNumericEquals((a1 + a2) + a3, a1 + (a2 + a3))
            assertNumericEquals(a1, a1 + a2 - a2)
            assertNumericEquals(zero, a1 - a1)
            assertNumericEquals(-a1, zero - a1)

            assertNumericEquals(a1 * a2, a2 * a1)
            assertNumericEquals((a1 * a2) * a3, a1 * (a2 * a3))
            assertNumericEquals(a1, a1 * one)
            assertNumericEquals(zero, a1 * zero)

            assertNumericEquals((a1 + a2) * a3, a1 * a3 + a2 * a3)
            assertNumericEquals((a1 + a2) * 5.4, a1 * 5.4 + a2 * 5.4)
            assertNumericEquals(a1, a1 * a2 / a2)
            assertNumericEquals((a1 + a2) / a3, a1 / a3 + a2 / a3)
            assertNumericEquals((a1 + a2) / 14.6, a1 / 14.6 + a2 / 14.6)

        }

    }

    @Test
    fun functions() {

        with(Random(0)) {

            val shape0 = 4
            val shape1 = 3

            val a1 = nextDoubleArray2D(shape0, shape1, from = -12.3, until = 7.5)
            val a2 = nextDoubleArray1D(shape0, from = -6.0, until = 8.9)
            val identity = doubleIdentity(shape0)
            val diagonal = doubleDiagonal(tomasvolker.numeriko.core.dsl.D[1, 2, 3, 4])
            val zero = doubleZeros(shape0, shape1)

            assertPositive(a1.frobeniusNorm())
            assertPositive(a2.maxNorm())
            assertFalse(a1.isSquare())

            assertFailsWith<IllegalArgumentException> {
                a1.inverse()
            }
            assertFailsWith<IllegalArgumentException> {
                a1.trace()
            }

            assert(identity.isIdentity())
            assert(identity.isSymmetric())
            assert(diagonal.isSymmetric())
            assert(zero.isZero())
            assert(zero.isConstant(0.0))

        }

    }

    @Test
    fun `linear algebra`() {

        with(Random(seed = 0)) {

            repeat(10) {

                val n = nextInt(from = 4, until = 6)
                val m = n - 2

                val matnxn = nextDoubleArray2D(n, n, from = -12.3, until = 7.5)
                val vecn1  = nextDoubleArray1D(n, from = -6.0, until = 8.9)
                val matmxn = nextDoubleArray2D(m, n, from = -12.3, until = 7.5)
                val vecn2  = nextDoubleArray1D(n, from = -6.0, until = 8.9)
                val identityn = doubleIdentity(n)

                assert(matnxn.isSquare())
                assertNumericEquals(matnxn.trace(), matnxn.diagonal().sum())
                assertNotNumericEquals(0.0, matnxn.determinant())
                assertNumericEquals(vecn1, matnxn matMul matnxn.solve(vecn1))
                assertNumericEquals(matnxn, matnxn matMul identityn)
                assertNumericEquals((matmxn matMul matnxn) matMul vecn2, matmxn matMul (matnxn matMul vecn2))
                assertNumericEquals(vecn2 matMul (matnxn matMul matmxn.transpose()), (vecn2 matMul matnxn) matMul matmxn.transpose())
                assertNumericEquals(identityn, matnxn matMul matnxn.inverse())

                assertNumericEquals(vecn1 inner (matnxn matMul vecn2), matnxn.bilinearForm(vecn1, vecn2))
                assertNumericEquals(vecn1 inner vecn1, identityn.quadraticForm(vecn1))

                assertNumericEquals(vecn1 inner vecn1, (vecn1 outer vecn1).trace())
                assert((vecn1 outer vecn1).isSymmetric())
                assertNumericEquals(0.0, (vecn1 outer vecn1).determinant())

                val mutableMat = matnxn.asMutable()

                mutableMat[0, All] = 2.1 * mutableMat[1, All] - 2.3 * mutableMat[3, All]

                assertNumericEquals(0.0, mutableMat.determinant())

            }

        }

    }

}


