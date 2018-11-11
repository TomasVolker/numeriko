package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.computeIndices
import tomasvolker.numeriko.core.interfaces.factory.intArray1D
import tomasvolker.numeriko.core.reductions.product

interface ArrayND<out T>: Collection<T> {

    val rank: Int get() = shape.size

    val shape: IntArray1D

    fun getShape(dimension: Int): Int = shape[dimension]

    override val size: Int get() = shape.product()

    override fun contains(element:@UnsafeVariance T): Boolean =
            any { it == element }

    override fun containsAll(elements: Collection<@UnsafeVariance T>): Boolean =
            elements.all { this.contains(it) }

    override fun isEmpty(): Boolean = size == 0

    fun getValue(): T = getValue(*intArrayOf())

    fun getValue(vararg indices: Int): T

    fun getValue(indices: IntArray1D): T =
            getValue(*indices.toIntArray())

    fun getValue(vararg indices: Index): T =
            getValue(*indices.computeIndices(shape))

    fun getView(vararg indices: IntProgression): ArrayND<T> =
            DefaultArrayNDView(
                    array = this,
                    offset = intArray1D(indices.map { it.first }.toIntArray()),
                    shape = intArray1D(indices.map { it.count() }.toIntArray()),
                    stride = intArray1D(indices.map { it.step }.toIntArray())
            )

    fun getView(vararg indices: IndexProgression): ArrayND<T> =
            getView(*indices.computeIndices(shape))

    fun copy(): ArrayND<T> = TODO()

    override fun iterator(): Iterator<T> = DefaultArrayNDIterator(this)

}