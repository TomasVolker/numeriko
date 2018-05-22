# NumeriKo

NDArray library for Kotlin in development.

Example usage:

```kotlin

fun main(args: Array<String>) {

    val random = Random()

    fun Random.nextDouble(min: Double, max: Double) =
            min + (max - min) * nextDouble()

    val windowSize = 1000

    val squareWindow = doubleArray1D(x.size) { i ->
        if(i < windowSize)
            1.0 / windowSize
        else
            0.0
    }

    // Circular convolution
    val lowPass = squareWindow convolve squareWindow convolve squareWindow

    val speed = doubleArray1D(x.size) { random.nextDouble(-0.1, 0.1) } convolve lowPass
    val position = speed.cumSum() / 2

    plot {

        line(x = x, y = lowPass * windowSize) {
            color = Color.RED
        }

        line(x = x, y = speed * 1000) {
            color = Color.BLUE
        }

        line(x = x, y = position) {
            color = Color.GREEN
        }

    }
}

```