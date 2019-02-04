package tomasvolker.numeriko.interfaces.array1d


import org.junit.jupiter.api.Test
import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.functions.div
import tomasvolker.numeriko.core.functions.minus
import tomasvolker.numeriko.core.functions.plus
import tomasvolker.numeriko.core.functions.times
import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.factory.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


class IntArray1DTest {

    @Test
    fun constructors() {

        val a1 = intArray1D(5) { i -> 2*i }
        val a2 = intArrayOf(0, 2, 4, 6, 8).asIntArray1D()
        val a3 = intArray1DOf(0, 2, 4, 6, 8)

        assertEquals(a1, a2)
        assertEquals(a1, a3)
        assertEquals(a2, a3)

    }

    @Test
    fun access() {

        val a1 = intArray1D(5) { i -> 2*i }
        val a2 = intArrayOf(0, 2, 4, 6, 8).asIntArray1D()
        val a3 = intArray1DOf(0, 2, 4, 6, 8)

        assertEquals(a1[2], a2[2])
        assertEquals(a1[4], a3[4])
        assertNotEquals(a2[First], a3[1])
        assertEquals(a2[Last], a3[4])
        assertEquals(a1[All], a3)

    }

    @Test
    fun view() {

        val a1 = intArray1D(100) { i -> 2*i}

        assertEquals(a1[2..4], intArray1DOf(4, 6, 8))
        assertEquals(a1[8..Last].size, 92)

    }

    @Test
    fun `view instance`() {

        val a1 = intArray1D(100) { i -> 2*i }

        val view = a1[10 until 20]

        val viewCopy = view.copy()

        assert(view !== viewCopy)
        assert(view == viewCopy)


    }

    @Test
    fun modification() {

        val a1 = intArray1D(100) { i -> 2*i }.asMutable()

        val a2 = a1.copy()

        assertEquals(a1, a2)

        a1[99] = -1
        assertNotEquals(a1, a2)

        a1[Last] = 198
        assertEquals(a1, a2)

        a1[1..3] = intArray1DOf(-2, -4, -6)
        assertEquals(a1[2..4], intArrayOf(-4, -6, 8).asIntArray1D())

    }

    @Test
    fun aliasing() {

        val a = intArray1D(10) { it }.asMutable()

        a[3..8] = a[0..5]

        val expected = I[0, 1, 2, 0, 1, 2, 3, 4, 5, 9]

        assertEquals(expected, a)
    }

    @Test
    fun algebra() {

        val a1 = intArray1D(100) { i -> i }

        val a2 = intArray1D(100) { i -> 2*i }

        val a3 = intArray1D(100) { i -> 3*i }

        assertEquals(a1 + a2, a3)

        assertEquals(a1 * 3, 2 * a2 - a3 / 3)

    }

}
