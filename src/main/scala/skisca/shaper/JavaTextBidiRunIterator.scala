package gay.menkissing.skisca.shaper

import gay.menkissing.skisca.*
import org.jetbrains.annotations.*

import java.text.*
import java.util.*

class JavaTextBidiRunIterator(text: String, flags: Int) extends java.util.Iterator[BidiRun] {
  _bidi = new Bidi(text, flags)
  _runsCount = _bidi.getRunCount
  _run = -1
  @ApiStatus.Internal final var _bidi: Bidi = null
  @ApiStatus.Internal final var _runsCount = 0
  @ApiStatus.Internal var _run = 0

  def this(text: String) = {
    this(text, Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT)
  }

  override def next: BidiRun = {
    _run += 1
    new BidiRun(_bidi.getRunLimit(_run), _bidi.getRunLevel(_run))
  }

  override def hasNext: Boolean = _run + 1 < _runsCount
}