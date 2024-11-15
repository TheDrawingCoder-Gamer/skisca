package gay.menkissing.skisca.shaper

import gay.menkissing.skisca.*
import org.jetbrains.annotations.*

import java.util.*

class TrivialFontRunIterator(text: String, @ApiStatus.Internal val _font: Font) extends java.util.Iterator[FontRun] {
  @ApiStatus.Internal final var _length = text.length
  @ApiStatus.Internal var _atEnd = _length == 0

  override def next: FontRun = {
    _atEnd = true
    FontRun(_length, _font)
  }

  override def hasNext: Boolean = !_atEnd
}