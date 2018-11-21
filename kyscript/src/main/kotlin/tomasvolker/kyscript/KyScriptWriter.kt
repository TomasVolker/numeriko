package tomasvolker.kyscript

import java.lang.reflect.Array

open class KyScriptWriter {

    private val script = StringBuilder()
    private var indentation: Int = 0

    val True = true
    val False = false

    val None = null

    fun newLine(lines: Int = 1) = repeat(lines) { script.appendln() }

    fun writeLine(line: String) {
        script.append("\t".repeat(indentation))
        script.appendln(line)
    }

    fun write(text: String) {
        script.append(text)
    }

    operator fun KyExpression.unaryPlus() {
        writeLine(this.code)
    }

    fun comment(comment: String) {
        writeLine("# " + comment.replace("\n", "\n# "))
    }

    fun import(identifier: String) {
        writeLine("import $identifier")
    }

    fun importAs(identifier: String, alias: String) {
        writeLine("import $identifier as $alias")
    }

    fun fromImport(from: String, identifier: String) {
        writeLine("from $from import $identifier")
    }

    fun fromImportAs(from: String, identifier: String, alias: String) {
        writeLine("from $from import $identifier as $alias")
    }

    fun ifThen(expression: KyExpression, block: KyScriptWriter.() -> Unit) {
        writeLine("if $expression:")
        indentation++
        block()
        indentation--
    }

    fun whileThen(expression: KyExpression, block: KyScriptWriter.() -> Unit) {
        writeLine("while $expression:")
        indentation++
        block()
        indentation--
    }

    fun forEach(index: KyIdentifier, range: KyExpression, block: KyScriptWriter.() -> Unit) {
        writeLine("for $index in $range:")
        indentation++
        block()
        indentation--
    }

    fun id(name: String) = KyIdentifier(name)

    fun KyIdentifier.id(name: String) = KyIdentifier("$this.$name")

    fun inject(code: String) = KyInject(code)

    operator fun KyIdentifier.invoke(vararg expression: Any?) =
        KyInject("$name(${expression.joinToString { it.toPythonExpression() }})")

    infix fun KyIdentifier.assign(expression: Any?) {
        writeLine("$this = ${expression.toPythonExpression()}")
    }

    infix fun String.setTo(value: Any?) =
            KyNamedArgument(
                name = this,
                value = value
            )

    fun Any?.toPythonExpression(): String = serializeToPython(this)

    protected open fun serializeToPython(value: Any?): String = when(value) {

        null -> "None"

        is KyExpression -> value.code

        is KyNamedArgument -> "${value.name} = ${value.value.toPythonExpression()}"

        is String -> '\'' + value.escapeChars() + '\''

        is Char ->  value.toString().toPythonExpression()

        is Boolean -> if (value) "True" else "False"

        is Number -> if(value is Double && value.isNaN() || value is Float && value.isNaN())
                "float('nan')"
            else
                when(value) {
                    Double.POSITIVE_INFINITY, Float.POSITIVE_INFINITY -> "float('+inf')"
                    Double.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY -> "float('-inf')"
                    else -> value.toString()
                }

        is List<*> -> value.joinToString(
            separator = ", ",
            prefix = "[",
            postfix = "]"
        ) { it.toPythonExpression() }

        is Set<*> -> value.joinToString(
            separator = ", ",
            prefix = "{",
            postfix = "}"
        ) { it.toPythonExpression() }

        is Map<*, *> -> value.entries.joinToString(
            separator = ", ",
            prefix = "{",
            postfix = "}"
        ) { "${it.key.toPythonExpression()}:${it.value.toPythonExpression()}" }

        else -> {

            if (this::class.java.isArray) {
                buildString {

                    append('[')

                    var first = true

                    for (i in 0 until Array.getLength(value)) {
                        if (!first)
                            append(", ")
                        append(Array.get(value, i).toPythonExpression())
                        first = false
                    }

                    append(']')
                }
            } else {
                TODO("Class is not supported: ${this::class.java}")
            }

        }
    }


    fun build(): KyScript = kyScript(script.toString())

    fun String.escapeChars(): String =
        this.asSequence()
            .flatMap {
                when(it) {
                    '\\' -> "\\\\"
                    '\n' -> "\\n"
                    '\r' -> "\\r"
                    '\t' -> "\\t"
                    '"' -> "\\\""
                    '\'' -> "\\\'"
                    '\b' -> "\\\b"
                    else -> it.toString()
                }.asSequence()
            }.joinToString(separator = "")

    data class KyNamedArgument(val name: String, val value: Any?)

}

inline fun kyScript(block: KyScriptWriter.()->Unit): KyScript =
        KyScriptWriter().apply(block).build()

