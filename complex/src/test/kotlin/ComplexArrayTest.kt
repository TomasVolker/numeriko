import org.junit.Test
import tomasvolker.numeriko.complex.j
import tomasvolker.numeriko.complex.plus
import tomasvolker.numeriko.core.interfaces.factory.arrayND

class ComplexArrayTest {

    @Test
    fun arrayTest() {

        val a = arrayND(2, 3) { (r, i) -> r + i.j }

    }

}
