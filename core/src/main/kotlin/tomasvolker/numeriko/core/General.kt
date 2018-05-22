package tomasvolker.numeriko.core

infix fun Int.modulo(other: Int) = this.rem(other).let {
    if (it < 0) other + it else it
}

