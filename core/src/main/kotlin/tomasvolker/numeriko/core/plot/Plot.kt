package tomasvolker.numeriko.core.plot

import tomasvolker.numeriko.core.interfaces.double.array1d.DoubleArray1D
import tomasvolker.numeriko.core.linearalgebra.cos
import tomasvolker.numeriko.core.linearalgebra.linearSpace
import tomasvolker.numeriko.core.linearalgebra.sin
import java.awt.*
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants
import kotlin.math.PI
import kotlin.math.roundToInt


class PlotFrame: JFrame() {

    val canvas = Canvas()

    init {

        contentPane.add(canvas)

        background = Color.WHITE

        title = "Plot"
        setSize(800, 800)
        setLocationRelativeTo(null)

        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

    }

    fun add(line: PlotLine) {
        canvas.add(line)
    }

    fun remove(line: PlotLine) {
        canvas.remove(line)
    }

}

class PlotView(
        val region: SquareRegion,
        var screenWidth: Int,
        var screenHeight: Int
) {

    fun screenX(x: Double): Int =
            (screenWidth * (x - region.minX) / (region.maxX - region.minX)).roundToInt()

    fun screenY(y: Double): Int =
            (screenHeight * (region.maxY - y) / (region.maxY - region.minY)).roundToInt()

}

class Canvas : JPanel() {

    private val view = PlotView(
            region = SquareRegion(
                    minX = -5.0,
                    maxX = 5.0,
                    minY = -5.0,
                    maxY = 5.0
            ),
            screenWidth = width,
            screenHeight = height
    )

    private val lineList = mutableListOf<PlotLine>()

    fun add(line: PlotLine) {
        lineList.add(line)
        repaint()
    }
    fun remove(line: PlotLine) {
        lineList.remove(line)
        repaint()
    }

    fun Double.screenX() = view.screenX(this)
    fun Double.screenY() = view.screenY(this)

    override fun paintComponent(graphics: Graphics) {
        graphics as Graphics2D

        view.screenHeight = height
        view.screenWidth = width

        with(graphics) {

            setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            )

            drawAxis()

            drawLines()

        }

    }

    fun Graphics2D.drawAxis() {

        val axisLength = 10.0

        drawLine(
                (-axisLength).screenX(),
                0.0.screenY(),
                axisLength.screenX(),
                0.0.screenY()
        )

        drawLine(
                0.0.screenX(),
                (-axisLength).screenY(),
                0.0.screenX(),
                axisLength.screenY()
        )

    }

    fun Graphics2D.drawLines() {

        for (line in lineList) {

            color = line.style.color
            stroke = line.stroke

            val x = line.x
            val y = line.y

            for (i in 0 until line.lastIndex) {

                drawLine(
                        x[i].screenX(),
                        y[i].screenY(),
                        x[i+1].screenX(),
                        y[i+1].screenY()
                )

            }

        }

    }

}

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

enum class LineStyle {
    NONE, SOLID, DASHED
}

enum class DotStyle {
    NONE, CIRCLE, SQUARE, TRIANGLE
}

class PlotLine(
        var x: DoubleArray1D,
        var y: DoubleArray1D,
        style: Style = Style()
) {

    private var _stroke: Stroke? = null

    var style: Style = style
        set(value) {
            _stroke = null
            field = value
        }

    init {
        require(x.size == y.size) {
            "x and y must have same size"
        }
    }

    val size: Int get() = x.size

    val lastIndex: Int get() = size - 1

    val stroke: Stroke
        get() =
        _stroke ?: buildStroke().also { _stroke = it }


    private fun buildStroke(): Stroke {
        return BasicStroke(
                style.lineWidth.toFloat()
        )
    }

    class Style(
            val color: Color = Color.BLACK,
            val lineWidth: Double = 1.0,
            val lineType: LineStyle = LineStyle.SOLID,
            val pointSize: Double = 4.0,
            val pointType: DotStyle = DotStyle.NONE
    )

}