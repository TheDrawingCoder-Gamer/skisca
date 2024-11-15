package gay.menkissing.skisca

import org.jetbrains.annotations.*

object FontStyle {
  val NORMAL = new FontStyle(FontWeight.NORMAL, FontWidth.NORMAL, FontSlant.UPRIGHT)
  val BOLD = new FontStyle(FontWeight.BOLD, FontWidth.NORMAL, FontSlant.UPRIGHT)
  val ITALIC = new FontStyle(FontWeight.NORMAL, FontWidth.NORMAL, FontSlant.ITALIC)
  val BOLD_ITALIC = new FontStyle(FontWeight.BOLD, FontWidth.NORMAL, FontSlant.ITALIC)
}

case class FontStyle(value: Int) {
  def this(weight: Int, width: Int, slant: FontSlant) = {
    this((weight & 0xFFFF) | ((width & 0xFF) << 16) | (slant.ordinal << 24))
  }


  def getWeight: Int = value & 0xFFFF

  def withWeight(weight: Int) = new FontStyle(weight, getWidth, getSlant)

  def getWidth: Int = (value >> 16) & 0xFF

  def withWidth(width: Int) = new FontStyle(getWeight, width, getSlant)

  def getSlant: FontSlant = FontSlant._values((value >> 24) & 0xFF)

  def withSlant(slant: FontSlant) = new FontStyle(getWeight, getWidth, slant)

  override def toString: String = "FontStyle(" + "weight=" + getWeight + ", width=" + getWidth + ", slant='" + getSlant + ')'
}