import org.junit.Test
import tomasvolker.numeriko.complex.DoubleComplex
import tomasvolker.numeriko.complex.*
import tomasvolker.numeriko.complex.fft.fft
import tomasvolker.numeriko.complex.fft.ifft

class FFTTest {

    @Test
    fun testFFT() {

        val array = Array<DoubleComplex>(16) { n ->
            if(n % 2 == 0) 1 + 0.j else -1 + 0.j
        }

        val arrayFtt = fft(array)

        val reconstructed = ifft(arrayFtt)

    }

}