import org.junit.Test
import tomasvolker.numeriko.complex.j
import tomasvolker.numeriko.complex.plus
import tomasvolker.numeriko.core.interfaces.get
import tomasvolker.numeriko.core.interfaces.ndArray

class ComplexArrayTest {

    @Test
    fun arrayTest() {

        val a = ndArray(2, 3) { (r, i) -> r + i.j }

    }

}
