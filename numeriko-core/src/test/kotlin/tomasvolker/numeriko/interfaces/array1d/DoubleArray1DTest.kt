package tomasvolker.numeriko.interfaces.array1d

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import tomasvolker.numeriko.core.dsl.D
import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.array1d.double.*
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.tests.*
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


class DoubleArray1DTest {

    @Nested
    inner class Constructors {

        @Test
        fun compare() {

            val a1 = doubleArray1D(5) { i -> 2 * i }
            val a2 = doubleArrayOf(0.0, 2.0, 4.0, 6.0, 8.0).asDoubleArray1D()
            val a3 = doubleArray1DOf(0.0, 2.0, 4.0, 6.0, 8.0)
            val a4 = D[0, 2, 4, 6, 8]

            assertEquals(a1, a2)
            assertEquals(a1, a3)
            assertEquals(a2, a3)
            assertEquals(a2, a4)

        }

    }

    @Test
    fun access() {

        val a1 = doubleArray1D(5) { i -> 2 * i }
        val a2 = doubleArrayOf(0.0, 2.0, 4.0, 6.0, 8.0).asDoubleArray1D()
        val a3 = doubleArray1DOf(0.0, 2.0, 4.0, 6.0, 8.0)

        assertEquals(a1[2], a2[2])
        assertEquals(a1[4], a3[4])
        assertNotEquals(a2[First], a3[1])
        assertEquals(a2[Last], a3[4])
        assertEquals(a1[All], a3)

    }

    @Test
    fun slice() {

        val a1 = doubleArray1D(100) { i -> 2 * i}

        assertEquals(a1[2..4], doubleArray1DOf(4.0, 6.0, 8.0))
        assertEquals(a1[8..Last].size, 92)

    }

    @Test
    fun `slice instance`() {

        val a1 = doubleArray1D(100) { i -> 2 * i }

        val view = a1[10 until 20]

        val viewCopy = view.copy()

        assert(view !== viewCopy)
        assert(view == viewCopy)

    }

    @Test
    fun aliasing() {

        val a = doubleArray1D(10) { it }.asMutable()

        a[3..8] = a[0..5]

        val expected = D[0, 1, 2, 0, 1, 2, 3, 4, 5, 9]

        assertEquals(expected, a)
    }

    @Test
    fun modify() {

        val a1 = doubleArray1D(100) { i -> 2 * i }.asMutable()

        val a2 = a1.copy()

        assertNumericEquals(a1, a2)

        a1[99] = -1.0
        assertNotNumericEquals(a1, a2)

        a1[Last] = 198.0
        assertNumericEquals(a1, a2)

        a1[1..3] = doubleArray1DOf(-2.0, -4.0, -6.0)
        assertNumericEquals(a1[2..4], doubleArrayOf(-4.0, -6.0, 8.0).asDoubleArray1D())

    }

    @Test
    fun operators() {

        with(Random(0)) {

            val size = 5

            val a1 = doubleArray1D(size) { nextDouble(from = -12.3, until = 7.5) }
            val a2 = doubleArray1D(size) { nextDouble(from = -6.0, until = 8.9) }
            val a3 = doubleArray1D(size) { nextDouble(from = 1.2, until = 22.8) }
            val zero = doubleZeros(size)
            val one = doubleArray1D(size) { 1.0 }

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

        val random = Random(0)

        val size = 5

        val a1 = doubleArray1D(size) { random.nextDouble() }
        val a2 = doubleArray1D(size) { random.nextDouble() }
        val a3 = doubleArray1D(size) { random.nextDouble() }

        assertPositive(a1.norm1())
        assertPositive(a2.norm2())
        assertPositive(a2.maxNorm())
        assertNumericEquals(a1, a1.cumulativeSum().differences())
        assertNumericEquals(a1 convolve (a2 + a3), (a1 convolve a2) + (a1 convolve a3))
        assertNumericEquals(a1.norm2(), sqrt(a1 inner a1))
        assertNumericEquals(1.0, a1.normalized().norm2())
        assertNumericEquals(a1.sum(), a1.cumulativeSum()[Last])

    }

}


