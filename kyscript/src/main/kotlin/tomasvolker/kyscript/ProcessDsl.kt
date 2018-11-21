package tomasvolker.kyscript

import java.io.File
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Starts the process with the given command and reads it's standard output, returning it as a string.
 * The functions blocks until the process exits. By default, the process' standard error is redirected to it's
 * standard output.
 *
 * @param command The command to run
 * @param timeoutMillis Timeout to read the processes output, if reached a [TimeoutException] is thrown. The default
 * is null, which indicates that there is no timeout
 * @param init Initializer block of the [ProcessBuilder] to further configure the execution.
 *
 * @string The standard output of the process as a string
 */
inline fun readProcess(
    command: String = "",
    timeoutMillis: Long? = null,
    init: ProcessBuilder.()->Unit
): String {
    val process = startProcess(command) {
        redirectErrorToOutput = true
        init()
    }

    try {

        if (timeoutMillis == null) {
            process.waitFor()
        } else if(!process.waitFor(timeoutMillis, TimeUnit.MILLISECONDS)) {
            process.destroyForcibly()
            throw TimeoutException()
        }

        if (process.exitValue() != 0)
            throw RuntimeException(process.inputStream.bufferedReader().readText())

        return process.inputStream.bufferedReader().readText()

    } finally {
        process.destroyForcibly()
    }

}

inline fun startProcess(command: String = "", init: ProcessBuilder.()->Unit): Process =
    ProcessBuilder().apply {
        this.command = command
        init()
    }.start()

var ProcessBuilder.command: String
    get() = command()[0]
    set(value) { command(value) }

var ProcessBuilder.arguments: List<String>
    get() = command().drop(1)
    set(value) { command(command, *value.toTypedArray()) }

var ProcessBuilder.workingDirectory: File
    get() = directory()
    set(value) { directory(value) }

var ProcessBuilder.redirectErrorToOutput: Boolean
    get() = redirectErrorStream()
    set(value) { redirectErrorStream(value) }

var ProcessBuilder.redirectInput: ProcessBuilder.Redirect
    get() = redirectInput()
    set(value) { redirectInput(value) }

var ProcessBuilder.redirectOutput: ProcessBuilder.Redirect
    get() = redirectOutput()
    set(value) { redirectOutput(value) }

var ProcessBuilder.redirectError: ProcessBuilder.Redirect
    get() = redirectError()
    set(value) { redirectError(value) }

val ProcessBuilder.PIPE get() = ProcessBuilder.Redirect.PIPE

val ProcessBuilder.INHERIT get() = ProcessBuilder.Redirect.INHERIT

fun ProcessBuilder.toFile(file: File) = ProcessBuilder.Redirect.to(file)
fun ProcessBuilder.fromFile(file: File) = ProcessBuilder.Redirect.from(file)