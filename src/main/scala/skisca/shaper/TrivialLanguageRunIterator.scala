package gay.menkissing.skisca.shaper

import gay.menkissing.skisca.*
import org.jetbrains.annotations.*

import java.util.*

class TrivialLanguageRunIterator(text: String, @ApiStatus.Internal val _language: String) extends java.util.Iterator[LanguageRun] {
  @ApiStatus.Internal final var _length = text.length
  @ApiStatus.Internal var _atEnd = _length == 0

  override def next: LanguageRun = {
    _atEnd = true
    LanguageRun(_length, _language)
  }

  override def hasNext: Boolean = !_atEnd
}