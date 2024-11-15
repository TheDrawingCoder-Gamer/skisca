package gay.menkissing.skisca

import org.jetbrains.annotations.*

case class FontVariationAxis(tag: Int, minValue: Float, defaultValue: Float, maxValue: Float, hidden: Boolean = false) {

  def getTag: String = FourByteTag.toString(tag)

  def this(tag: String, min: Float, `def`: Float, max: Float, hidden: Boolean) = {
    this(FourByteTag.fromString(tag), min, `def`, max, hidden)
  }
}