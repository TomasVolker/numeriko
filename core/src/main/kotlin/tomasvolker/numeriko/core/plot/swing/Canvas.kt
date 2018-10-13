package tomasvolker.numeriko.core.plot.swing

import tomasvolker.numeriko.core.plot.*
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import java.awt.image.BufferedImage
import javax.swing.JPanel
import kotlin.math.roundToInt

private fun Double.floor() = kotlin.math.floor(this).toInt()
private fun Double.ceil() = kotlin.math.ceil(this).toInt()

class Canvas : JPanel(), MouseMotionListener, MouseListener {

    val view = PlotView(
            region = SquareRegion(
                    minX = -5.0,
                    maxX = 5.0,
                    minY = -5.0,
                    maxY = 5.0
            ),
            screenWidth = 800,
            screenHeight = 800
    )

    var dragX: Int? = null
    var dragY: Int? = null

    var lastRepaint = System.currentTimeMillis()

    init {
        addMouseMotionListener(this)
        addMouseListener(this)
    }

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

        graphics.clearRect(
                0,
                0,
                width,
                height
        )

        view.resize(width, height)

        with(graphics) {

            setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            )

            drawAxis()

            drawLines()

        }

    }

    private fun Graphics2D.drawAxis() {

        val region = view.region

        drawLine(
                region.minX.screenX(),
                0.0.screenY(),
                region.maxX.screenX(),
                0.0.screenY()
        )

        for (x in region.minX.ceil()..region.maxX.floor()) {

            val xd = x.toDouble()

            drawLine(
                    xd.screenX(),
                    (-0.05).screenY(),
                    xd.screenX(),
                    0.05.screenY()
            )

        }

        drawLine(
                0.0.screenX(),
                region.minY.screenY(),
                0.0.screenX(),
                region.maxY.screenY()
        )

        for (y in region.minY.ceil()..region.maxY.floor()) {

            val yd = y.toDouble()

            drawLine(
                    (-0.05).screenX(),
                    yd.screenY(),
                    0.05.screenX(),
                    yd.screenY()
            )

        }

    }

    private fun Graphics2D.drawLines() {

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


    override fun mouseMoved(e: MouseEvent) {}

    override fun mouseDragged(e: MouseEvent) {

        val now = System.currentTimeMillis()
        if (now - lastRepaint > 100) {
            lastRepaint = now

            val dragX = dragX ?: return
            val dragY = dragY ?: return

            view.region.centerX -= view.plotXDistance(e.x - dragX)
            view.region.centerY += view.plotYDistance(e.y - dragY)

            this.dragX = e.x
            this.dragY = e.y

            repaint()

        }

    }

    override fun mouseReleased(e: MouseEvent) {
        dragX = null
        dragY = null
    }

    override fun mouseEntered(e: MouseEvent) {}

    override fun mouseClicked(e: MouseEvent) {}

    override fun mouseExited(e: MouseEvent) {}

    override fun mousePressed(e: MouseEvent) {
        dragX = e.x
        dragY = e.y
    }

}

class PlotView(
        val region: SquareRegion,
        var screenWidth: Int,
        var screenHeight: Int
) {

    fun screenX(x: Double): Int =
            (screenWidth * (x - region.minX) / region.width).roundToInt()

    fun screenY(y: Double): Int =
            (screenHeight * (region.maxY - y) / region.height).roundToInt()

    fun resize(newWidth: Int, newHeight: Int) {

        region.width *= newWidth/ screenWidth.toDouble()
        region.height *= newHeight / screenHeight.toDouble()

        screenWidth = newWidth
        screenHeight = newHeight
    }

    fun plotXDistance(pixels: Int): Double =
            pixels * region.width / screenWidth.toDouble()

    fun plotYDistance(pixels: Int): Double =
            pixels * region.height / screenHeight.toDouble()

}