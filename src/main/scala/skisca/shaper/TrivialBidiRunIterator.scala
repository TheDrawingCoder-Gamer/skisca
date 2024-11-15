package gay.menkissing.skisca.shaper

import gay.menkissing.skisca.*
import org.jetbrains.annotations.*

import java.util.*

class TrivialBidiRunIterator(text: String, @ApiStatus.Internal val _level: Int) extends java.util.Iterator[BidiRun] {
  @ApiStatus.Internal final var _length = text.length
  @ApiStatus.Internal var _atEnd = _length == 0

  override def next: BidiRun = {
    _atEnd = true
    BidiRun(_length, _level)
  }

  override def hasNext: Boolean = !_atEnd
}