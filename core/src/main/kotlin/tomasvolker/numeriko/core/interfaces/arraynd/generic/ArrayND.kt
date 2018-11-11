package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.computeIndices

interface ArrayND<out T>: Collection<T> {

    val rank: Int

    val shape: IntArray1D

    override val size: Int

    override fun contains(element:@UnsafeVariance T): Boolean =
            any { it == element }

    override fun containsAll(elements: Collection<@UnsafeVariance T>): Boolean =
            elements.all { this.contains(it) }

    override fun isEmpty(): Boolean = size == 0

    fun getValue(vararg indices: Int): T

    fun getValue(vararg indices: Index): T =
            getValue(*indices.computeIndices(shape))

    fun copy(): ArrayND<T> = TODO()

    override fun iterator(): Iterator<T> = TODO()

}