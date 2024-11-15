package gay.menkissing.skisca.shaper

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

import java.util.*

object ManagedRunIterator {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nConsume(ptr: Long): Unit

  @ApiStatus.Internal
  @native def _nGetEndOfCurrentRun(ptr: Long, textPtr: Long): Int

  @ApiStatus.Internal
  @native def _nIsAtEnd(ptr: Long): Boolean

  try Library.staticLoad()
}

abstract class ManagedRunIterator[T] @ApiStatus.Internal(ptr: Long, text: ManagedString, manageText: Boolean) extends Managed(ptr, ManagedRunIterator
  ._FinalizerHolder.PTR, true) with java.util.Iterator[T] {
  _text = if (manageText) {
    _text
  } else {
    null
  }
  @ApiStatus.Internal final var _text: ManagedString = null

  override def close(): Unit = {
    super.close()
    if (_text != null) _text.close()
  }

  @ApiStatus.Internal def _getEndOfCurrentRun: Int = {
    try {
      ManagedRunIterator
        ._nGetEndOfCurrentRun(_ptr, Native.getPtr(_text))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(_text)
    }
  }

  override def hasNext: Boolean = {
    try {
      !ManagedRunIterator._nIsAtEnd(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}