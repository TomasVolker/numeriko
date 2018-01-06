import org.junit.Test
import tomasvolker.numeriko.core.array.integer.wrapNDArray
import tomasvolker.numeriko.core.util.computeSizeFromShape
import tomasvolker.numeriko.core.util.indexArrayToLinearIndex
import tomasvolker.numeriko.core.util.linearIndexToIndexArray
import kotlin.test.*

class UtilsTest {

    @Test
    fun testSizeFromShape() {

        var shape = intArrayOf()

        assert(computeSizeFromShape(shape) == 1) {
            "Computing rank 0 array size doesn't return 1 (${computeSizeFromShape(shape)} instead)"
        }

        shape = intArrayOf(2)

        assert(computeSizeFromShape(shape) == 2) {
            "Computing array size of shape [2] doesn't return 2"
        }

        shape = intArrayOf(2, 3)

        assert(computeSizeFromShape(shape) == 6) {
            "Computing array size of shape [2, 3] doesn't return 6"
        }

        shape = intArrayOf(2, 3, 4, 5)

        assert(computeSizeFromShape(shape) == 120) {
            "Computing array size of shape [2, 3, 4, 5] doesn't return 120"
        }

        shape = intArrayOf(2, 3, -4, 5)

        assertFailsWith(IllegalArgumentException::class) {
            computeSizeFromShape(shape)
        }

    }

    @Test
    fun testArrayIndexToLinearIndex() {

        var shape = intArrayOf()

        var index = intArrayOf()

        assert(indexArrayToLinearIndex(shape, index) == 0) {
            "Linear Index in rank 0 array doesn't return 0"
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
