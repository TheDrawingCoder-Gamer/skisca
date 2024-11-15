package gay.menkissing.skisca.paragraph

import org.jetbrains.annotations.*

object DecorationStyle {
  val NONE = new DecorationStyle(false, false, false, true, 0xFF000000, DecorationLineStyle.SOLID, 1f)
}

case class DecorationStyle(underline: Boolean, overline: Boolean, lineThrough: Boolean, gaps: Boolean, color: Int, lineStyle: DecorationLineStyle, thicknessMultiplier: Float) {
  def this(underline: Boolean, overline: Boolean, lineThrough: Boolean, gaps: Boolean, color: Int, lineStyle: Int, thicknessMultiplier: Float) = {
    this(underline, overline, lineThrough, gaps, color, DecorationLineStyle.fromOrdinal(lineStyle), thicknessMultiplier)
  }
}