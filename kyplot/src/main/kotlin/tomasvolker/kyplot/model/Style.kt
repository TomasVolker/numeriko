package tomasvolker.kyplot.model

import tomasvolker.kyplot.model.MarkerType.*
import tomasvolker.kyplot.model.LineType.*
import tomasvolker.kyplot.model.MarkerFillStyle.*

enum class LineType {
    SOLID,
    DASHED,
    DASH_DOT,
    DOTTED
}

enum class MarkerType {
    NONE,
    POINT,
    PIXEL,
    CIRCLE,
    TRIANGLE_DOWN,
    TRIANGLE_UP,
    TRIANGLE_LEFT,
    TRIANGLE_RIGHT,
    TRI_DOWN,
    TRI_UP,
    TRI_LEFT,
    TRI_RIGHT,
    SQUARE,
    PENTAGON,
    STAR,
    HEXAGON_1,
    HEXAGON_2,
    PLUS,
    X,
    X_FILLED,
    DIAMOND,
    THIN_DIAMOND,
    VERTICAL_LINE,
    HORIZONTAL_LINE
}

enum class MarkerFillStyle {
    NONE,
    FULL,
    LEFT,
    RIGHT,
    BOTTOM,
    TOP
}

enum class BarAlignment {
    CENTER,
    EDGE
}
