import org.junit.Test
import numeriko.complex.primitives.j
import numeriko.complex.primitives.plus
import numeriko.complex.transforms.fft.fft
import numeriko.complex.transforms.fft.ifft

class FFTTest {

    @Test
    fun testFFT() {

        val array = Array(16) { n ->
            if(n % 2 == 0) 1 + 0.j else -1 + 0.j
        }

        val arrayFtt = fft(array)

        val reconstructed = ifft(arrayFtt)

        println(reconstructed.joinToString())

    }

}