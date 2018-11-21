package tomasvolker.kyscript

import java.io.File

inline fun <T> tempFile(prefix: String, suffix: String, block: (file: File)->T): T {
    var file: File? = null
    try {
        file = File.createTempFile(prefix, suffix)
        return block(file)
    } finally {
        file?.delete()
    }
}
