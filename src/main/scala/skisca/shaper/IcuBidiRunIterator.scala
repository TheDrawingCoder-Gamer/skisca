package gay.menkissing.skisca.shaper

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object IcuBidiRunIterator {
  @ApiStatus.Internal
  @native def _nMake(textPtr: Long, bidiLevel: Int): Long

  @ApiStatus.Internal
  @native def _nGetCurrentLevel(ptr: Long): Int

  try Library.staticLoad()
}

class IcuBidiRunIterator(text: ManagedString, manageText: Boolean, bidiLevel: Int) extends ManagedRunIterator[BidiRun](IcuBidiRunIterator
  ._nMake(Native.getPtr(text), bidiLevel), text, manageText) {
  Stats.onNativeCall()
  ReferenceUtil.reachabilityFence(_text)

  def this(text: String, bidiLevel: Int) = {
    this(new ManagedString(text), true, bidiLevel)
  }

  override def next: BidiRun = {
    try {
      ManagedRunIterator._nConsume(_ptr)
      BidiRun(_getEndOfCurrentRun, IcuBidiRunIterator._nGetCurrentLevel(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}