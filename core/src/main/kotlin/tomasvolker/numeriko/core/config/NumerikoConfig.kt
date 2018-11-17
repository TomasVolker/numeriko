package tomasvolker.numeriko.core.config

import tomasvolker.numeriko.core.implementations.numeriko.factory.NumerikoArrayNDFactory
import tomasvolker.numeriko.core.interfaces.factory.ArrayNDFactory

object NumerikoConfig {

    var defaultFactory: ArrayNDFactory = NumerikoArrayNDFactory()

    var defaultTolerance: Double = 1e-11

    const val checkRanges: Boolean = true

}