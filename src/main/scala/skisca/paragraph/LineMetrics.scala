package gay.menkissing.skisca.paragraph

case class LineMetrics(startIndex: Long, endIndex: Long, endExclusingWhitespaces: Long, endIncludingNewline: Long, hardBreak: Boolean,
                       ascent: Double, descent: Double, unscaledAscent: Double, height: Double, width: Double,
                       left: Double, baseline: Double, lineNumber: Long) {


  /**
   * The height of the current line, equals to {@code Math.round(getAscent() + getDescent())}.
   */
  def getLineHeight: Double = {
    ascent + descent
  }

  /**
   * The right edge of the line, equals to {@code getLeft() + getWidth()}
   */
  def getRight: Double = {
    left + width
  }
}