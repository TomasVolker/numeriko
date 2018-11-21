import org.junit.Test
import tomasvolker.numeriko.complex.primitives.j
import tomasvolker.numeriko.complex.primitives.plus
import tomasvolker.numeriko.complex.transforms.fft.fft
import tomasvolker.numeriko.complex.transforms.fft.ifft

class FFTTest {

    @Test
    fun testFFT() {

        val array = Array(16) { n ->
            if(n % 2 == 0) 1 + 0.j else -1 + 0.j
        }

        val arrayFtt = fft(array)

        val reconstructed = ifft(arrayFtt)

        println(reconstructed)

    }

}