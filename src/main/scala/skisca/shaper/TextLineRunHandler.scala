package gay.menkissing.skisca.shaper

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object TextLineRunHandler {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nMake(textPtr: Long): Long

  @ApiStatus.Internal
  @native def _nMakeLine(ptr: Long): Long

  try Library.staticLoad()
}

class TextLineRunHandler[T] @ApiStatus.Internal(text: ManagedString, manageText: Boolean) extends Managed(TextLineRunHandler
  ._nMake(Native.getPtr(text)), TextLineRunHandler._FinalizerHolder.PTR, true) with RunHandler {

  ReferenceUtil.reachabilityFence(text)
  @ApiStatus.Internal final var _text: ManagedString = if (manageText) {
    text
  } else {
    null
  }

  def this(text: String) = {
    this(new ManagedString(text), true)
  }

  override def close(): Unit = {
    super.close()
    if (_text != null) _text.close()
  }

  override def beginLine(): Unit = {
    throw new UnsupportedOperationException("TextLineRunHandler::beginLine")
  }

  override def runInfo(info: RunInfo): Unit = {
    throw new UnsupportedOperationException("TextLineRunHandler::runInfo")
  }

  override def commitRunInfo(): Unit = {
    throw new UnsupportedOperationException("TextLineRunHandler::commitRunInfo")
  }

  override def runOffset(info: RunInfo): Point = throw new UnsupportedOperationException("TextLineRunHandler::runOffset")

  override def commitRun(info: RunInfo, glyphs: Array[Short], positions: Array[Point], clusters: Array[Int]): Unit = {
    throw new UnsupportedOperationException("TextLineRunHandler::commitRun")
  }

  override def commitLine(): Unit = {
    throw new UnsupportedOperationException("TextLineRunHandler::commitLine")
  }

  @Nullable def makeLine: TextLine = {
    try {
      Stats.onNativeCall()
      val ptr = TextLineRunHandler._nMakeLine(_ptr)
      if (0 == ptr) {
        null
      } else {
        new TextLine(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}