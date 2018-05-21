package tomasvolker.numeriko.core.plot

class SquareRegion(
        var minX: Double,
        var maxX: Double,
        var minY: Double,
        var maxY: Double
) {

    var width: Double
        get() = maxX - minX
        set(value) {
            val centerX = centerX
            minX = centerX - value / 2
            maxX = centerX + value / 2
        }

    var height: Double
        get() = maxY - minY
        set(value) {
            val centerY = centerY
            minY = centerY - value / 2
            maxY = centerY + value / 2
        }

    var centerX: Double
        get() = (minX + maxX) / 2
        set(value) {
            val width = width
            minX = value - width / 2
            maxX = value + width / 2
        }

    var centerY: Double
        get() = (minY + maxY) / 2
        set(value) {
            val height = height
            minY = value - height / 2
            maxY = value + height / 2
        }

}