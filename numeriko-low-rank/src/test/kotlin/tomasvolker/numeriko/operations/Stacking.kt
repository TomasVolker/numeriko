package tomasvolker.numeriko.operations

import org.junit.jupiter.api.Test
import tomasvolker.numeriko.core.dsl.D
import tomasvolker.numeriko.core.dsl.I
import tomasvolker.core.index.All
import tomasvolker.core.index.Size
import tomasvolker.core.index.until
import tomasvolker.core.operations.concatenate
import kotlin.test.assertEquals

class StackingTests {

    @Test
    fun `double stacking 1D`() {

        val a = tomasvolker.numeriko.core.dsl.D[1, 2, 3]
        val b = tomasvolker.numeriko.core.dsl.D[4, 5, 6]

        val stacked0 = stack(a, b, axis = 0)

        assertEquals(a, stacked0[0, All])
        assertEquals(b, stacked0[1, All])

        val stacked1 = stack(a, b, axis = 1)

        assertEquals(a, stacked1[All, 0])
        assertEquals(b, stacked1[All, 1])

    }

    @Test
    fun `double concatenation 1D`() {

        val a = tomasvolker.numeriko.core.dsl.D[1, 2, 3]
        val b = tomasvolker.numeriko.core.dsl.D[4, 5, 6]

        val concat = a concatenate b

        assertEquals(a, concat[0 until a.size])
        assertEquals(b, concat[a.size until Size])

    }

    @Test
    fun `int concatenation 1D`() {

        val a = tomasvolker.numeriko.core.dsl.I[1, 2, 3]
        val b = tomasvolker.numeriko.core.dsl.I[4, 5, 6]

        val concat = a concatenate b

        assertEquals(a, concat[0 until a.size])
        assertEquals(b, concat[a.size until Size])

    }

}