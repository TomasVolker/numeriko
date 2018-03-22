import org.junit.Test
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.Last
import tomasvolker.numeriko.core.index.rangeTo
import tomasvolker.numeriko.core.interfaces.factory.intArrayND
import tomasvolker.numeriko.core.interfaces.integer.arraynd.ReadOnlyIntArrayND
import tomasvolker.numeriko.core.interfaces.integer.arraynd.set

class ArrayNDTest {

    @Test
    fun printIntArrayND() {

        val array: ReadOnlyIntArrayND = intArrayND(2, 2, 2) {
            (i0, i1, i2) -> i0 + i1 + i2
        }

        println(array)

        assert(array.toString() == "[ [ [ 0, 1] , [ 1, 2] ] , [ [ 1, 2] , [ 2, 3] ] ] ") {
            "Array of shape [2, 2, 2] of added indexes is not printed correctly"
        }

    }

    @Test
    fun copyView() {

        var aux = 0

        val array = intArrayND(4, 3, 5) { aux++ }

        val view = array.view[1..2, All, 2..Last]

        val viewCopy = view.copy()

        assert(view == viewCopy) {
            "View is not equal to a copy of it"
        }

        array[1, 2, 3] = -1

        assert(view != viewCopy) {
            "View of modified jvm array is equal to a copy of it before being modified"
        }

    }

}

