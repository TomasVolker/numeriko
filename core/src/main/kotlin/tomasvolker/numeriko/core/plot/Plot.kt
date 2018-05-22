package tomasvolker.numeriko.core.plot

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.plot.swing.PlotFrame
import java.awt.*
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

class Plot {

    private var frame: PlotFrame? = PlotFrame()

    init {

        frame?.isVisible = true

    }

    fun add(line: PlotLine) {
        frame?.add(line)
    }

    fun remove(line: PlotLine) {
        frame?.remove(line)
    }

    fun addLine(x: DoubleArray1D, y: DoubleArray1D) {
        add(PlotLine(x, y))
    }

    fun addLine(x: DoubleArray1D, y: DoubleArray1D, color: Color) {
        add(PlotLine(x, y, PlotLine.Style(color = color)))
    }

    fun close() {

        frame?.isVisible = false
        frame?.dispose()
        frame = null

    }

    val isClosed: Boolean get() = frame == null

}
