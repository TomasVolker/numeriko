package numeriko.lowrank.interfaces.array1d.lowdim.generic

import numeriko.lowrank.interfaces.array1d.generic.Array1D

interface Vector3<out T>: Array1D<T> {

    override val size: Int get() = 3

}