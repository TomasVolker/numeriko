package tomasvolker.numeriko.core.interfaces

import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.factory.asIntArray1D
import tomasvolker.numeriko.core.interfaces.factory.intArray1D
import tomasvolker.numeriko.core.interfaces.factory.intZeros
import tomasvolker.numeriko.core.interfaces.slicing.get
import tomasvolker.numeriko.core.interfaces.slicing.set
import java.lang.IndexOutOfBoundsException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals

class IntArray1DTest {

    @Test
    fun creation() {

        val dsl = I[1, 2, 3, 4]
        val lambda = intArray1D(4) { i -> i + 1 }
        val array = intArrayOf(1, 2, 3, 4).asIntArray1D()

        assertEquals(dsl, lambda)
        assertEquals(dsl, array)
        assertEquals(lambda, array)

        val dsl2 = I[1, 2, 4, 4]

        assertNotEquals(dsl, dsl2)
        assertNotEquals(lambda, dsl2)

        val zeros = intZeros(10)

        assertEquals(zeros, intArray1D(10) { 0 })
        assertNotEquals(zeros, intArray1D(9) { 0 })

        val empty = intArrayOf().asIntArray1D()
        assertEquals(empty, intZeros(0))

    }

    @Test
    fun access() {

        val lambda = intArray1D(25) { i -> i * 2 }

        assertEquals(lambda[12], 24)
        assertEquals(lambda[0], 0)
        assertNotEquals(lambda[2], -1)

        assertEquals(lambda[Last], 48)
        assertEquals(lambda[Last-2], 44)

        val indices = intArrayOf(6)
        assertEquals(lambda.getInt(indices), 12)
        assertEquals(lambda.getInt(I[7]), 14)

        assertFailsWith<IndexOutOfBoundsException> {
            lambda[-1]
        }

        assertFailsWith<IndexOutOfBoundsException> {
            lambda[25]
        }

        assertFailsWith<IndexOutOfBoundsException> {
            lambda[Last+1]
        }

    }

    @Test
    fun modification() {

        val array = intZeros(10).asMutable()

        assertEquals(array[8], 0)

        array[8] = 123

        assertEquals(array[8], 123)
        assertEquals(array[7], 0)

        array.setInt(intArrayOf(6), 12)
        array.setInt(I[2], -6)

        assertEquals(array[6], 12)
        assertEquals(array[2], -6)

        assertFailsWith<IndexOutOfBoundsException> {
            array[-1] = 25
        }

        assertFailsWith<IndexOutOfBoundsException> {
            array[10] = 25
        }

        assertFailsWith<IndexOutOfBoundsException> {
            array[Size] = 25
        }

    }

    @Test
    fun `access slice`() {

        val array = I[1,2,3,4,5,6,7,8,9]

        assertEquals(array[2..4], I[3, 4, 5])
        assertEquals(array[0 until 5], I[1, 2, 3, 4, 5])
        assertEquals(array[7..Last], I[8, 9])
        assertEquals(array[(0..3).reversed()], I[4, 3, 2, 1])
        assertEquals(array[All], array)
        assertEquals(array[All step 2], I[1, 3, 5, 7, 9])
        assertEquals(array[(4..Last).reversed() step 3], I[9, 6])

    }


}