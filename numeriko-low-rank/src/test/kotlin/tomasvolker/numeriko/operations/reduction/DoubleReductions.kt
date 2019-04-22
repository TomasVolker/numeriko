package tomasvolker.numeriko.operations.reduction

import org.junit.jupiter.api.Test
import tomasvolker.numeriko.core.dsl.D
import tomasvolker.numeriko.tests.assertNumericEquals

class DoubleReductionsTests {

    @Test
    fun `double reduce 1D`() {

        val a = tomasvolker.numeriko.core.dsl.D[1, 2, 3]

        val sum = a.reduce(0.0) { acc, next -> acc + next }

        assertNumericEquals(6.0, sum)

    }

    @Test
    fun `double reduce 2D`() {

        val a = tomasvolker.numeriko.core.dsl.D[tomasvolker.numeriko.core.dsl.D[1, 2, 3],
                  tomasvolker.numeriko.core.dsl.D[4, 5, 6]]

        val sum0 = a.reduce(initial = 0.0, axis = 0) { acc, next -> acc + next }
        assertNumericEquals(tomasvolker.numeriko.core.dsl.D[5, 7, 9], sum0)

        val sum1 = a.reduce(initial = 0.0, axis = 1) { acc, next -> acc + next }
        assertNumericEquals(tomasvolker.numeriko.core.dsl.D[6, 15], sum1)

        val sum3 = a.reduce(axis = 0) { it.sum() }
        assertNumericEquals(tomasvolker.numeriko.core.dsl.D[5, 7, 9], sum3)

        val sum4 = a.reduce(axis = 1) { it.sum() }
        assertNumericEquals(tomasvolker.numeriko.core.dsl.D[6, 15], sum4)

    }

}