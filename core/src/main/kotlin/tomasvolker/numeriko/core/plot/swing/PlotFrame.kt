package tomasvolker.numeriko.core.plot.swing

import tomasvolker.numeriko.core.plot.PlotLine
import java.awt.Color
import java.awt.Point
import java.awt.event.*
import javax.swing.JFrame
import javax.swing.WindowConstants

class PlotFrame: JFrame(), ComponentListener {

    val canvas = Canvas()

    init {

        contentPane.add(canvas)

        background = Color.WHITE

        title = "Plot"
        setSize(800, 800)

        addComponentListener(this)

        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE

    }

    fun add(line: PlotLine) {
        canvas.add(line)
    }

    fun remove(line: PlotLine) {
        canvas.remove(line)
    }

    override fun componentMoved(e: ComponentEvent) {}

    override fun componentResized(e: ComponentEvent) {
        canvas.repaint()
    }

    override fun componentHidden(e: ComponentEvent) {}

    override fun componentShown(e: ComponentEvent) {}

}