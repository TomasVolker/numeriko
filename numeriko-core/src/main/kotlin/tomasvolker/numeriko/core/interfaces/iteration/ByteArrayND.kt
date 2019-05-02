package tomasvolker.numeriko.core.interfaces.iteration

import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.byte.MutableByteArrayND
import tomasvolker.numeriko.core.interfaces.factory.byteZeros
import tomasvolker.numeriko.core.preconditions.requireSameShape

inline fun elementWise(
        source: ByteArrayND,
        destination: MutableByteArrayND,
        operation: (Byte) -> Byte
) {
    requireSameShape(source, destination)
    source.unsafeForEachIndexed { index, value ->
        destination.setByte(index, operation(value))
    }
}

inline fun ByteArrayND.elementWise(operation: (Byte)->Byte): ByteArrayND {
    val result = byteZeros(shape).asMutable()
    elementWise(
            source = this,
            destination = result,
            operation = operation
    )
    return result
}

inline fun MutableByteArrayND.applyElementWise(
        operation: (Byte) -> Byte
): MutableByteArrayND {
    elementWise(
            source = this,
            destination = this,
            operation = operation
    )
    return this
}

inline fun MutableByteArrayND.applyElementWise(
        other: ByteArrayND,
        operation: (Byte, Byte) -> Byte
): MutableByteArrayND {
    requireSameShape(this, other)
    elementWise(
            source1 = this,
            source2 = other,
            destination = this,
            operation = operation
    )
    return this
}

inline fun elementWise(
        source1: ByteArrayND,
        source2: ByteArrayND,
        destination: MutableByteArrayND,
        operation: (Byte, Byte) -> Byte
) {
    requireSameShape(source1, source2)
    requireSameShape(source1, destination)
    source1.unsafeForEachIndex { index ->
        destination.setByte(index, operation(source1.getByte(index), source2.getByte(index)))
    }
}

inline fun elementWise(
        array1: ByteArrayND,
        array2: ByteArrayND,
        operation: (Byte, Byte) -> Byte
): ByteArrayND {
    requireSameShape(array1, array2)
    val result = byteZeros(array1.shape).asMutable()
    elementWise(
            source1 = array1,
            source2 = array2,
            destination = result,
            operation = operation
    )
    return result

}

