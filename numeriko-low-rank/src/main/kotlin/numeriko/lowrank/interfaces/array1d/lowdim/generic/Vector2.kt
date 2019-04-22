package numeriko.lowrank.interfaces.array1d.lowdim.generic

import numeriko.lowrank.interfaces.array1d.generic.Array1D
import numeriko.lowrank.interfaces.array1d.generic.MutableArray1D

interface Vector2<out T>: Array1D<T> {

    override val size: Int get() = 2

}

interface MutableVector2<T>: Vector2<T>, MutableArray1D<T>
