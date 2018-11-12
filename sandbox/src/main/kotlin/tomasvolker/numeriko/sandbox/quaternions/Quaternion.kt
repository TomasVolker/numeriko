package tomasvolker.numeriko.sandbox.quaternions

import kotlin.math.sqrt

class Quaternion(
        val r: Double = 0.0,
        val x: Double = 0.0,
        val y: Double = 0.0,
        val z: Double = 0.0
) {

    private inline fun elementWise(operation: (Double)->Double) =
            Quaternion(
                    operation(r),
                    operation(x),
                    operation(y),
                    operation(z)
            )

    private inline fun elementWise(other: Quaternion, operation: (Double, Double)->Double) =
            Quaternion(
                    operation(this.r, other.r),
                    operation(this.x, other.x),
                    operation(this.y, other.y),
                    operation(this.z, other.z)
            )

    operator fun plus(other: Quaternion): Quaternion =
            elementWise(other) { t, o -> t + o }

    operator fun minus(other: Quaternion): Quaternion =
            elementWise(other) { t, o -> t - o }

    operator fun times(other: Quaternion): Quaternion =
            Quaternion(
                    this.r * other.r - this.x * other.x - this.y * other.y - this.z * other.z,
                    this.r * other.x + this.x * other.r + this.y * other.z - this.z * other.y,
                    this.r * other.y - this.x * other.z + this.y * other.r + this.z * other.x,
                    this.r * other.z + this.x * other.y - this.y * other.x + this.z * other.r
            )

    operator fun plus(other: Double): Quaternion =
            Quaternion(this.r + other, this.x, this.y, this.z)

    operator fun minus(other: Double): Quaternion =
            Quaternion(this.r - other, this.x, this.y, this.z)

    operator fun times(other: Double): Quaternion =
            elementWise { it * other }

    operator fun div(other: Double): Quaternion =
            elementWise { it / other }

    fun conjugate() = Quaternion(r, -x, -y, -z)

    val norm get() = sqrt(r * r + x * x + y * y + z * z)

    override fun toString(): String = "($r, $x, $y, $z)"

}

val Double.i get() = Quaternion(x = this)
val Double.j get() = Quaternion(y = this)
val Double.k get() = Quaternion(z = this)

operator fun Double.plus(other: Quaternion) = other + this
operator fun Double.minus(other: Quaternion) = Quaternion(this - other.r, -other.x, -other.y, -other.z)
operator fun Double.times(other: Quaternion) = other * this
