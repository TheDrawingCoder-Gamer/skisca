package gay.menkissing.skisca

import java.util.regex.*

object FontFeature {
  val GLOBAL_START = 0
  val GLOBAL_END: Long = java.lang.Long.MAX_VALUE
  val EMPTY = new Array[FontFeature](0)
  val _splitPattern: Pattern = Pattern.compile("\\s+")
  val _featurePattern: Pattern = Pattern
    .compile("(?<sign>[-+])?(?<tag>[a-z0-9]{4})(?:\\[(?<start>\\d+)?:(?<end>\\d+)?\\])?(?:=(?<value>\\d+))?")

  def parseOne(s: String): FontFeature = {
    val m = _featurePattern.matcher(s)
    if (!m.matches) throw new IllegalArgumentException("Can’t parse FontFeature: " + s)
    val value = if (m.group("value") != null) {
      m.group("value").toInt
    } else if (m.group("sign") == null) {
      1
    } else if ("-" == m.group("sign")) {
      0
    } else {
      1
    }
    val start = if (m.group("start") == null) {
      0
    } else {
      java.lang.Long.parseLong(m.group("start"))
    }
    val end = if (m.group("end") == null) {
      java.lang.Long.MAX_VALUE
    } else {
      java.lang.Long.parseLong(m.group("end"))
    }
    new FontFeature(m.group("tag"), value, start, end)
  }

  def parse(s: String): Array[FontFeature] = _splitPattern.splitAsStream(s).map(FontFeature.parseOne).toArray(Array.ofDim[FontFeature])
}

case class FontFeature(tag: Int, value: Int, start: Long, end: Long) {

  def this(feature: String, value: Int, start: Long, end: Long) = {
    this(FourByteTag.fromString(feature), value, start, end)
  }

  def this(feature: String, value: Int) = {
    this(FourByteTag.fromString(feature), value, FontFeature.GLOBAL_START, FontFeature.GLOBAL_END)
  }

  def this(feature: String, value: Boolean) = {
    this(FourByteTag.fromString(feature), if (value) {
      1
    } else {
      0
    }, FontFeature.GLOBAL_START, FontFeature.GLOBAL_END)
  }

  def this(feature: String) = {
    this(FourByteTag.fromString(feature), 1, FontFeature.GLOBAL_START, FontFeature.GLOBAL_END)
  }

  def feature: String = FourByteTag.toString(tag)

  override def toString: String = {
    var range = ""
    if (start > 0 || end < Long.MaxValue) {
      range = "[" + (if (start > 0) {
        start
      } else {
        ""
      }) + ":" + (if (end < Long.MaxValue) {
        end
      } else {
        ""
      }) + "]"
    }
    var valuePrefix = ""
    var valueSuffix = ""
    if (value == 0) {
      valuePrefix = "-"
    } else if (value == 1) {
      valuePrefix = "+"
    } else {
      valueSuffix = "=" + value
    }
    "FontFeature(" + valuePrefix + feature + range + valueSuffix + ")"
  }
}