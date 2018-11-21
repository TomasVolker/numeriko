package tomasvolker.kyscript

import java.io.File

object KyScriptConfig {

    var defaultPythonPath: String = "python3"
    var defaultTempFilePrefix: String = "kyscript_generated"

}

class KyScriptExecutor(
    var script: KyScript = kyScript(""),
    var pythonPath: String = KyScriptConfig.defaultPythonPath,
    var scriptArguments: MutableList<String> = mutableListOf(),
    var workingDirectory: File? = null,
    var timeoutMillis: Long? = null
) {

    fun executeAndRead(): String = when(val script = script) {

        is KyScript.FromString -> withScript(script) { file ->
            readScript(file)
        }

        is KyScript.FromFile -> readScript(script.file)

    }

    fun execute(): Process = when(val script = script) {

        is KyScript.FromString -> withScript(script) { file ->
            runScript(file)
        }

        is KyScript.FromFile -> runScript(script.file)

    }

    private inline fun <T> withScript(script: KyScript.FromString, block: (File)->T) =
        tempFile(
            prefix = KyScriptConfig.defaultTempFilePrefix,
            suffix = ".py"
        ) { file ->
            file.writeText(script.text)
            block(file)
        }

    private fun readScript(file: File): String =
        readProcess(pythonPath, timeoutMillis) { configure(file) }

    private fun runScript(file: File): Process =
        startProcess(pythonPath) { configure(file) }

    private fun ProcessBuilder.configure(file: File) {

        arguments += file.absolutePath
        arguments += scriptArguments

        val scriptWorkingDirectory = this@KyScriptExecutor.workingDirectory

        if (scriptWorkingDirectory != null)
            workingDirectory = scriptWorkingDirectory

    }

}

inline fun readKyScript(script: File, config: KyScriptExecutor.()->Unit = {}): String =
    readKyScript(kyScript(script), config)

inline fun readKyScript(script: String, config: KyScriptExecutor.()->Unit = {}): String =
    readKyScript(kyScript(script), config)

inline fun readKyScript(script: KyScript = kyScript(""), config: KyScriptExecutor.()->Unit = {}): String =
    KyScriptExecutor(script = script)
        .apply(config)
        .executeAndRead()

inline fun runKyScript(script: File, config: KyScriptExecutor.()->Unit = {}): Process =
    runKyScript(kyScript(script), config)

inline fun runKyScript(script: String, config: KyScriptExecutor.()->Unit = {}): Process =
    runKyScript(kyScript(script), config)

inline fun runKyScript(script: KyScript = kyScript(""), config: KyScriptExecutor.()->Unit = {}): Process =
    KyScriptExecutor(script = script)
        .apply(config)
        .execute()
