package tomasvolker.kyscript

import java.io.File

sealed class KyScript {

    data class FromString(val text: String): KyScript()

    data class FromFile(val file: File): KyScript()

}

fun kyScript(text: String) = KyScript.FromString(text)

fun kyScript(file: File) = KyScript.FromFile(file)

