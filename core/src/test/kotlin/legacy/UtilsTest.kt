package legacy

import org.junit.Test
import tomasvolker.numeriko.legacy.core.interfaces.factory.intArray1DOf
import tomasvolker.numeriko.legacy.core.util.computeSizeFromShape
import tomasvolker.numeriko.legacy.core.util.indexArrayToLinearIndex
import kotlin.test.*
/*
class UtilsTest {

    @Test
    fun testSizeFromShape() {

        var shape = intArray1DOf()

        assert(computeSizeFromShape(shape) == 1) {
            "Computing rank 0 jvm size doesn't return 1 (${computeSizeFromShape(shape)} instead)"
        }

        shape = intArray1DOf(2)

        assert(computeSizeFromShape(shape) == 2) {
            "Computing jvm size of shape [2] doesn't return 2"
        }

        shape = intArray1DOf(2, 3)

        assert(computeSizeFromShape(shape) == 6) {
            "Computing jvm size of shape [2, 3] doesn't return 6"
        }

        shape = intArray1DOf(2, 3, 4, 5)

        assert(computeSizeFromShape(shape) == 120) {
            "Computing jvm size of shape [2, 3, 4, 5] doesn't return 120"
        }

        shape = intArray1DOf(2, 3, -4, 5)

        assertFailsWith(IllegalArgumentException::class) {
            computeSizeFromShape(shape)
        }

    }

    @Test
    fun testArrayIndexToLinearIndex() {

        var shape = intArrayOf()

        var index = intArrayOf()

        assert(indexArrayToLinearIndex(shape, index) == 0) {
            "Linear LiteralIndex in rank 0 jvm doesn't return 0"
        }

        shape = intArrayOf(2, 2)
        index = intArrayOf(0, 0)

        checkLinearIndex(shape, index, 0)

        index = intArrayOf(0, 1)

        checkLinearIndex(shape, index, 1)

        index = intArrayOf(1, 1)

        checkLinearIndex(shape, index, 3)

    }

    fun checkLinearIndex(shape: IntArray, index: IntArray, expected: Int) {
        assert(indexArrayToLinearIndex(shape, index) == expected) {
            """Expected $expected, got ${indexArrayToLinearIndex(shape, index)}
            |for shape [${shape.joinToString()}] and index [${index.joinToString()}}]""".trimMargin()
        }
    }

}
*/