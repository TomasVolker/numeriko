import org.junit.Test
import tomasvolker.numeriko.complex.*
import java.util.*
import kotlin.math.E

class DoubleComplexTest {

    private val epsilon: Double = 1e-3

    private infix fun DoubleComplex.almostEqual(other: DoubleComplex): Boolean {

        val realDiff = this.real - other.real
        val imagDff = this.imag - other.imag

        return realDiff * realDiff + imagDff * imagDff < epsilon
    }

    val generator = Random()

    private fun randomComplex(sigma: Double = 1.0) =
            sigma * generator.nextGaussian() + sigma * generator.nextGaussian().j

    @Test
    fun testSin() {

        val x = randomComplex()

        val sin1 = sin(x)

        val sin2 = (exp(J * x) - exp(-J * x)) / (2 * J)

        val sin3 = (E.pow(J * x) - E.pow(-J * x)) / 2.j

        assert(sin1 almostEqual sin1) {
            "sin(x) is not approximately to (exp(J * x) - exp(-J * x)) / (2 * J)"
        }

        assert(sin1 almostEqual sin3) {
            "sin(x) is not approximately to (E.pow(J * x) - E.pow(-J * x)) / 2.j"
        }

    }

    @Test
    fun testCos() {

        val x = randomComplex()

        val cos1 = cos(x)

        val cos2 = (exp(J * x) + exp(-J * x)) / 2

        val cos3 = (E.pow(J * x) + E.pow(-J * x)) / 2

        assert(cos1 almostEqual cos2) {
            "cos(x) is not approximately to (exp(J * x) + exp(-J * x)) / 2"
        }

        assert(cos1 almostEqual cos3) {
            "cos(x) is not approximately to (E.pow(J * x) + E.pow(-J * x)) / 2"
        }

    }

    @Test
    fun testSinh() {

        val x = randomComplex()

        val sinh1 = sinh(x)

        val sinh2 = (exp(x) - exp(-x)) / 2

        val sinh3 = (E.pow(x) - E.pow(-x)) / 2

        assert(sinh1 almostEqual sinh2) {
            "sinh(x) is not approximately to (exp(x) - exp(-x)) / 2"
        }

        assert(sinh1 almostEqual sinh3) {
            "sin(x) is not approximately to (E.pow(x) - E.pow(-x)) / 2"
        }

    }

    @Test
    fun testCosh() {

        val x = randomComplex()

        val cosh1 = cosh(x)

        val cosh2 = (exp(x) + exp(-x)) / 2

        val cosh3 = (E.pow(x) + E.pow(-x)) / 2

        assert(cosh1 almostEqual cosh2) {
            "cosh(x) is not approximately to (exp(x) + exp(-x)) / 2"
        }

        assert(cosh1 almostEqual cosh3) {
            "cosh(x) is not approximately to (E.pow(x) + E.pow(-x)) / 2"
        }

    }

    @Test
    fun testExp() {

        val x = randomComplex()

        val exp1 = exp(x)

        val exp2 = cosh(x) + sinh(x)

        val exp3 = cos(-J * x) + J * sin(-J * x)

        assert(exp1 almostEqual exp2) {
            "exp(x) is not approximately to cosh(x) + sinh(x)"
        }

        assert(exp1 almostEqual exp3) {
            "cosh(x) is not approximately to cos(-J * x) + J * sin(-J * x)"
        }

    }

}