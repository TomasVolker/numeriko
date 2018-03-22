package tomasvolker.numeriko.core.jvm.int.array1d

fun IntArray.asArray1D() = JvmIntArray1D(this)

fun IntArray.toArray1D() = JvmIntArray1D(this.copyOf())
