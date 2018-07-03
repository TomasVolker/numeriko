# NumeriKo

NDArray library for Kotlin in development.

Example usage:

```kotlin

fun main(args: Array<String>) {

    val amount = 10000;
    val max = 5.0
    val delta = max / amount

    val time = linearSpace(start = 0.0, stop = max, amount = amount)

    val random = Random()

    val windowSize = 0.1

    val squareWindow = time.elementWise { t ->
        if(t < windowSize)
            1.0 / windowSize
        else
            0.0
    }


    fun Random.nextDouble(min: Double, max: Double) =
            min + (max - min) * nextDouble()

    // Circular convolution
    val lowPass = (squareWindow convolve squareWindow convolve squareWindow) * delta * delta

    val speed = doubleArray1D(time.size) { random.nextDouble(-0.1, 0.1) } convolve lowPass
    val position = speed.cumSum() * delta

    plot {

        line(x = time, y = lowPass * windowSize) {
            color = Color.RED
        }

        line(x = time, y = speed / 10) {
            color = Color.BLUE
        }

        line(x = time, y = position) {
            color = Color.GREEN
        }

    }

}

```