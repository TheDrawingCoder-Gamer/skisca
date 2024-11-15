package gay.menkissing.skisca

import org.jetbrains.annotations.*

case class FontMetrics
  (top: Float, ascent: Float, descent: Float, bottom: Float, leading: Float, avgCharWidth: Float,
    maxCharWidth: Float, xMin: Float, xMax: Float, xHeight: Float, capHeight: Float, underlineThickness: Option[Float],
    underlinePosition: Option[Float], strikeoutThickness: Option[Float], strikeoutPosition: Option[Float]) {
  def getHeight: Float = descent - ascent
  def this(top: Float, ascent: Float, descent: Float, bottom: Float, leading: Float, avgCharWidth: Float,
           maxCharWidth: Float, xMin: Float, xMax: Float, xHeight: Float, capHeight: Float, underlineThickness: java.lang.Float,
           underlinePosition: java.lang.Float, strikeoutThickness: java.lang.Float, strikeoutPosition: java.lang.Float) = {
    this(top,ascent,descent,bottom, leading, avgCharWidth, maxCharWidth, xMin, xMax, xHeight, capHeight,
      Option(underlineThickness).map(_.floatValue()),
      Option(underlinePosition).map(_.floatValue()),
      Option(strikeoutThickness).map(_.floatValue()),
      Option(strikeoutPosition).map(_.floatValue())
    )
  }
}