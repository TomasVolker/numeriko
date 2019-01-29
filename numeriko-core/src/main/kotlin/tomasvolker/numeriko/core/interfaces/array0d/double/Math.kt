package tomasvolker.numeriko.core.interfaces.array0d.double

operator fun DoubleArray0D.unaryPlus(): DoubleArray0D = this
operator fun DoubleArray0D.unaryMinus(): DoubleArray0D = elementWise { -it }

operator fun DoubleArray0D.plus (other: DoubleArray0D): DoubleArray0D = elementWise(this, other) { t, o -> t + o }
operator fun DoubleArray0D.minus(other: DoubleArray0D): DoubleArray0D = elementWise(this, other) { t, o -> t - o }
operator fun DoubleArray0D.times(other: DoubleArray0D): DoubleArray0D = elementWise(this, other) { t, o -> t * o }
operator fun DoubleArray0D.div  (other: DoubleArray0D): DoubleArray0D = elementWise(this, other) { t, o -> t / o }

operator fun DoubleArray0D.plus (other: Double): DoubleArray0D = elementWise { it + other }
operator fun DoubleArray0D.minus(other: Double): DoubleArray0D = elementWise { it - other }
operator fun DoubleArray0D.times(other: Double): DoubleArray0D = elementWise { it * other }
operator fun DoubleArray0D.div  (other: Double): DoubleArray0D = elementWise { it / other }

operator fun DoubleArray0D.plus (other: Int): DoubleArray0D = elementWise { it + other }
operator fun DoubleArray0D.minus(other: Int): DoubleArray0D = elementWise { it - other }
operator fun DoubleArray0D.times(other: Int): DoubleArray0D = elementWise { it * other }
operator fun DoubleArray0D.div  (other: Int): DoubleArray0D = elementWise { it / other }
