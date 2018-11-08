package tomasvolker.kyplot.model

sealed class Color {

    object Auto: Color()

    data class Explicit(
        val red: Number = 0.0,
        val green: Number = 0.0,
        val blue: Number = 0.0
    ): Color()

    companion object {

        val BLUE = 0x1F77B4.toColor()
        val GREEN = 0x2CA02C.toColor()
        val RED = 0xD62728.toColor()
        val CYAN = 0x17BECF.toColor()
        val MAGENTA = 0xE377C2.toColor()
        val YELLOW = 0xBCBD22.toColor()
        val BLACK = 0x000000.toColor()

        fun rgb(red: Number, green: Number, blue: Number) =
            Color.Explicit(
                red = red,
                green = green,
                blue = blue
            )

        fun gey(factor: Number) = rgb(factor, factor, factor)

    }

}


fun Int.toColor() =
    Color.Explicit(
        red   = (this and 0x00_FF_00_00 shr 16) / 255.toDouble(),
        green = (this and 0x00_00_FF_00 shr 8) / 255.toDouble(),
        blue  = (this and 0x00_00_00_FF) / 255.toDouble()
    )
