package tomasvolker.numeriko.interfaces.array0d

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


class GenericArray0DTest {


    @Test
    fun constructor() {

        val a1 = array0D("value")
        val a2 = array0DOf("value")

        assertEquals(a1.getValue(), "value")
        assertEquals(a2.getValue(), "value")
        assertEquals(a1, a2)

    }

    @Test
    fun access() {

        val a1 = array0D(5)
        val a2 = array0D(5)

        assertEquals(a1.getValue(), a2.getValue(intArray1D(0) { 0 }))

    }

    @Test
    fun slice() {

        val a1 = array0D(5)
        val a2 = array0D(5)

        assertEquals(a1.getView(), a2)

    }

    @Test
    fun `view copy instance`() {

        val a1 = array0D(100)

        val view = a1.getView()

        val viewCopy = view.copy()

        assert(view !== viewCopy)
        assert(view == viewCopy)


    }

    @Test
    fun modification() {

        val a1 = array0D(100).asMutable()

        val a2 = a1.copy()

        assertEquals(a1, a2)

        a1.setValue(-1)
        assertNotEquals(a1, a2)

        a1.setValue(100)
        assertEquals(a1, a2)

    }

}
