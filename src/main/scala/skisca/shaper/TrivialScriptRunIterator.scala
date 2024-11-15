package gay.menkissing.skisca.shaper

import org.jetbrains.annotations.*

import java.util.*

class TrivialScriptRunIterator(text: String, @ApiStatus.Internal val _script: String) extends java.util.Iterator[ScriptRun] {
  @ApiStatus.Internal final var _length = text.length
  @ApiStatus.Internal var _atEnd = _length == 0

  override def next: ScriptRun = {
    _atEnd = true
    ScriptRun(_length, _script)
  }

  override def hasNext: Boolean = !_atEnd
}