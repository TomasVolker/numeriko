package tomasvolker.numeriko.core.serialization

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndex
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun DoubleArrayND.rawBytes(order: ByteOrder = ByteOrder.nativeOrder()): ByteBuffer {
    val buffer = ByteBuffer.allocate(size * 8).order(order)
    unsafeForEachIndex { indices ->
        buffer.putDouble(getDouble(indices))
    }
    buffer.rewind()
    return buffer
}

fun FloatArrayND.rawBytes(order: ByteOrder = ByteOrder.nativeOrder()): ByteBuffer {
    val buffer = ByteBuffer.allocate(size * 4).order(order)
    unsafeForEachIndex { indices ->
        buffer.putFloat(getFloat(indices))
    }
    buffer.rewind()
    return buffer
}

fun IntArrayND.rawBytes(order: ByteOrder = ByteOrder.nativeOrder()): ByteBuffer {
    val buffer = ByteBuffer.allocate(size * 4).order(order)
    unsafeForEachIndex { indices ->
        buffer.putInt(getInt(indices))
    }
    buffer.rewind()
    return buffer
}
