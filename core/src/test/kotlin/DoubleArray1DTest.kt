import org.junit.Test
import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.double.array1d.times
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.core.linearalgebra.cos
import tomasvolker.numeriko.core.linearalgebra.invoke
import tomasvolker.numeriko.core.linearalgebra.linearSpace
import kotlin.math.PI
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


class DoubleArray1DTest {

    @Test
    fun createArray() {

        val a1 = doubleArray1D(5) { i -> 2.0 * i }
        val a2 = doubleArrayOf(0.0, 2.0, 4.0, 6.0, 8.0).asDoubleArray1D()
        val a3 = doubleArray1DOf(0.0, 2.0, 4.0, 6.0, 8.0)

        assertEquals(a1, a2)
        assertEquals(a1, a3)
        assertEquals(a2, a3)

    }

    @Test
    fun accessArray() {

        val a1 = doubleArray1D(5) { i -> 2.0 * i }
        val a2 = doubleArrayOf(0.0, 2.0, 4.0, 6.0, 8.0).asDoubleArray1D()
        val a3 = doubleArray1DOf(0.0, 2.0, 4.0, 6.0, 8.0)

        assertEquals(a1[2], a2[2])
        assertEquals(a1[4], a3[4])
        assertNotEquals(a2[First], a3[1])
        assertEquals(a2[Last], a3[4])
        assertEquals(a1[All], a3)

    }

    @Test
    fun viewArray() {

        val a1 = doubleArray1D(100) { i -> 2.0 * i}

        assertEquals(a1[2..4], doubleArray1DOf(4.0, 6.0, 8.0))
        assertEquals(a1[8..Last].size, 92)

    }

    @Test
    fun viewInstance() {

        val a1 = doubleArray1D(100) { i -> 2.0 * i }

        val view = a1[10 until 20]

        val viewCopy = view.copy()

        assert(view !== viewCopy)
        assert(view == viewCopy)

    }

    @Test
    fun modifyArray() {

        val a1 = mutableDoubleArray1D(100) { i -> 2.0 * i }

        val a2 = a1.copy()

        assertEquals(a1, a2)

        a1[99] = -1.0
        assertNotEquals(a1, a2)

        a1[Last] = 198.0
        assertEquals(a1, a2)

        a1[1..3] = doubleArray1DOf(-2.0, -4.0, -6.0)
        assertEquals(a1[2..4], doubleArrayOf(-4.0, -6.0, 8.0).asDoubleArray1D())

        a1[All] = 0.0
        assertEquals(a1[56], 0.0)

    }

    @Test
    fun algebra() {

        val a1 = mutableDoubleArray1D(100) { i -> i.toDouble() }

        val a2 = mutableDoubleArray1D(100) { i -> 2.0 * i }

        val a3 = mutableDoubleArray1D(100) { i -> 3.0 * i }

        assertEquals(a1 + a2, a3)

        assertEquals(a1 * 3.0, 2.0 * a2 - a3 / 3.0)

        val x = linearSpace(start = 0.0, stop = 2*PI, count = 11)
        val y = cos(x)

        //val op = {input: Double -> 2*input}

        val function = fun(x: Double) = 2 * x

        function(x)

    }

}


