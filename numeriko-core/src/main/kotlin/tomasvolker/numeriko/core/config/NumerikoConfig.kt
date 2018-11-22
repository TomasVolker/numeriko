package tomasvolker.numeriko.core.config

import tomasvolker.numeriko.core.implementations.numeriko.factory.NumerikoArrayNDFactory
import tomasvolker.numeriko.core.interfaces.factory.ArrayNDFactory
import tomasvolker.numeriko.core.view.ElementOrder

/**
 * Singleton containing global configurations.
 */
object NumerikoConfig {

    /**
     * The default ArrayND factory, configures the backend.
     *
     * To use another backend, set this variable to the corresponding matrix factory.
     * A matrix factory must implement [ArrayNDFactory] interface.
     */
    var defaultFactory: ArrayNDFactory = NumerikoArrayNDFactory()

    /**
     * Default tolerance for numeric equality.
     *
     * For functions that require comparing equality of numeric values such as [isSymetric] or [isDiagonal]
     * the default tolerance can be configured globally with this variable.
     * These functions accept a tolerance parameter which will default to this one.
     */
    var defaultTolerance: Double = 1e-11

    /**
     * Flag to enable check ranges, true by default.
     *
     * For better performance, range checks can be disabled if the user is confident that indices will always
     * be on range.
     */
    var checkRanges: Boolean = true



    var defaultElementOrder: ElementOrder = ElementOrder.ContiguousLastIndex

}