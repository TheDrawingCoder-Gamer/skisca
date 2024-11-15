package gay.menkissing.skisca.shaper

import gay.menkissing.skisca.*
import io.github.humbleui.types.*

class RunInfo(var fontPtr: Long, val bidiLevel: Int, val advanceX: Float, val advanceY: Float, val glyphCount: Long,

                           /** WARN does not work in Shaper.makeCoreText https://bugs.chromium.org/p/skia/issues/detail?id=10899 */
                           val rangeBegin: Int,

                           /** WARN does not work in Shaper.makeCoreText https://bugs.chromium.org/p/skia/issues/detail?id=10899 */
                           val rangeSize: Int) {
  def getAdvance = new Point(advanceX, advanceY)

  /** WARN does not work in Shaper.makeCoreText https://bugs.chromium.org/p/skia/issues/detail?id=10899 */
  def getRangeEnd: Int = {
    rangeBegin + rangeSize
  }

  def getFont: Font = {
    if (fontPtr == 0) throw new IllegalStateException("getFont() is only valid inside RunHandler callbacks")
    Font.makeClone(fontPtr)
  }
}