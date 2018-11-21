package tomasvolker.kyscript

sealed class KyExpression(val code: String) {
    override fun toString(): String = code
}

class KyIdentifier(name: String): KyExpression(name) {
    val name get() = code
}

class KyInject(code: String): KyExpression(code)
