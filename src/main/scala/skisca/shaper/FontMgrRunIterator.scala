package gay.menkissing.skisca.shaper

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

import java.util.*

object FontMgrRunIterator {
  @ApiStatus.Internal
  @native def _nMake(textPtr: Long, fontPtr: Long, languageRunIterator: java.util.Iterator[LanguageRun], opts: ShapingOptions): Long

  @ApiStatus.Internal
  @native def _nGetCurrentFont(ptr: Long): Long

  try Library.staticLoad()
}

class FontMgrRunIterator(text: ManagedString, manageText: Boolean, font: Font, languageRunIterator: java.util.Iterator[LanguageRun], @NotNull opts: ShapingOptions) extends ManagedRunIterator[FontRun](FontMgrRunIterator
  ._nMake(Native.getPtr(text), Native.getPtr(font), languageRunIterator, opts), text, manageText) {
  Stats.onNativeCall()
  ReferenceUtil.reachabilityFence(_text)
  ReferenceUtil.reachabilityFence(font)
  ReferenceUtil.reachabilityFence(opts)

  def this(text: ManagedString, manageText: Boolean, font: Font, @NotNull opts: ShapingOptions) = {
    this(text, manageText, font, null, opts)
  }

  def this(text: String, font: Font, @NotNull opts: ShapingOptions) = {
    this(new ManagedString(text), true, font, null, opts)
  }

  def this(text: String, font: Font) = {
    this(new ManagedString(text), true, font, null, ShapingOptions.DEFAULT)
  }

  override def next: FontRun = {
    try {
      ManagedRunIterator._nConsume(_ptr)
      FontRun(_getEndOfCurrentRun, new Font(FontMgrRunIterator._nGetCurrentFont(_ptr)))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}