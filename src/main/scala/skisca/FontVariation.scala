package gay.menkissing.skisca

import org.jetbrains.annotations.*

import java.util.regex.*

object FontVariation {
  val EMPTY = new Array[FontVariation](0)
  @ApiStatus.Internal val _splitPattern: Pattern = Pattern.compile("\\s+")
  @ApiStatus.Internal val _variationPattern: Pattern = Pattern.compile("(?<tag>[a-z0-9]{4})=(?<value>\\d+)")

  def parseOne(s: String): FontVariation = {
    val m = _variationPattern.matcher(s)
    if (!m.matches) throw new IllegalArgumentException("Can’t parse FontVariation: " + s)
    val value = java.lang.Float.parseFloat(m.group("value"))
    new FontVariation(m.group("tag"), value)
  }

  def parse(s: String): Array[FontVariation] = _splitPattern.splitAsStream(s).map(FontVariation.parseOne).toArray(Array.ofDim[FontVariation])
}

case class FontVariation(tag: Int, value: Float) {
  def this(feature: String, value: Float) = {
    this(FourByteTag.fromString(feature), value)
  }
  def feature: String = FourByteTag.toString(tag)

  override def toString: String = feature + "=" + value
}