package gay.menkissing.skisca.shaper

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object HbIcuScriptRunIterator {
  @ApiStatus.Internal
  @native def _nMake(textPtr: Long): Long

  @ApiStatus.Internal
  @native def _nGetCurrentScriptTag(ptr: Long): Int

  try Library.staticLoad()
}

class HbIcuScriptRunIterator(text: ManagedString, manageText: Boolean) extends ManagedRunIterator[ScriptRun](HbIcuScriptRunIterator
  ._nMake(Native.getPtr(text)), text, manageText) {
  Stats.onNativeCall()
  ReferenceUtil.reachabilityFence(_text)

  def this(text: String) = {
    this(new ManagedString(text), true)
  }

  override def next: ScriptRun = {
    try {
      ManagedRunIterator._nConsume(_ptr)
      new ScriptRun(_getEndOfCurrentRun, HbIcuScriptRunIterator._nGetCurrentScriptTag(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}