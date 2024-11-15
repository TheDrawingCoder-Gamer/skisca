package gay.menkissing.skisca

case class FontExtents(ascender: Float, descender: Float, lineGap: Float) {
  def getAscenderAbs: Float = Math.abs(ascender)

  def getLineHeight: Float = -ascender + descender + lineGap
}