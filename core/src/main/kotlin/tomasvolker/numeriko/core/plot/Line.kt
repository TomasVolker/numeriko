package tomasvolker.numeriko.core.plot

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Stroke

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
            "x.size (${x.size}) and y.size (${y.size}) must be equal"
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
    ) {

        class Builder(
                var color: Color = Color.BLACK,
                var lineWidth: Double = 1.0,
                var lineType: LineStyle = LineStyle.SOLID,
                var pointSize: Double = 4.0,
                var pointType: DotStyle = DotStyle.NONE
        ) {

            fun build() =
                    Style(color, lineWidth, lineType, pointSize, pointType)

        }

    }

}
