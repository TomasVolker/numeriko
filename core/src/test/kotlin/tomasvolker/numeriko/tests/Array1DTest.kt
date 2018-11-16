package tomasvolker.numeriko.tests

import org.junit.Test
import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.core.interfaces.array1d.generic.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


class Array1DTest {

    @Test
    fun `generic 1D array constructors`() {

        val a1 = array1D(5) { i -> 2*i }
        val a2 = arrayOf(0, 2, 4, 6, 8).asArray1D()
        val a3 = array1DOf(0, 2, 4, 6, 8)

        assertEquals(a1, a2)
        assertEquals(a1, a3)
        assertEquals(a2, a3)

    }

    @Test
    fun `generic 1D array access`() {

        val a1 = array1D(5) { i -> 2*i }
        val a2 = arrayOf(0, 2, 4, 6, 8).asArray1D()
        val a3 = array1DOf(0, 2, 4, 6, 8)

        assertEquals(a1[2], a2[2])
        assertEquals(a1[4], a3[4])
        assertNotEquals(a2[First], a3[1])
        assertEquals(a2[Last], a3[4])
        assertEquals(a1[All], a3)

    }

    @Test
    fun `generic 1D array view`() {

        val a1 = array1D(100) { i -> "${2*i}" }

        assertEquals(a1[2..4], array1DOf("4", "6", "8"))
        assertEquals(a1[8..Last].size, 92)

    }

    @Test
    fun `generic 1D view copy instance`() {

        val a1 = array1D(100) { i -> "${2*i}" }

        val view = a1[10 until 20]

        val viewCopy = view.copy()

        assert(view !== viewCopy)
        assert(view == viewCopy)


    }

    @Test
    fun `generic 1D modification`() {

        val a1 = array1D(100) { i -> 2*i }.asMutable()

        val a2 = a1.copy()

        assertEquals(a1, a2)

        a1[99] = -1
        assertNotEquals(a1, a2)

        a1[Last] = 198
        assertEquals(a1, a2)

        a1[1..3] = array1DOf(-2, -4, -6)
        assertEquals(a1[2..4], arrayOf(-4, -6, 8).asArray1D())

        a1[All] = 0
        assertEquals(a1[56], 0)

    }

}
