package gay.menkissing.skisca.shaper

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object TextBlobBuilderRunHandler {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nMake(textPtr: Long, offsetX: Float, offsetY: Float): Long

  @ApiStatus.Internal
  @native def _nMakeBlob(ptr: Long): Long

  Library.staticLoad()
}

class TextBlobBuilderRunHandler @ApiStatus.Internal(text: ManagedString, manageText: Boolean, offsetX: Float, offsetY: Float) extends Managed(TextBlobBuilderRunHandler
  ._nMake(Native.getPtr(text), offsetX, offsetY), TextBlobBuilderRunHandler._FinalizerHolder
                                                                           .PTR, true) with RunHandler {
  _text = if (manageText) {
    _text
  } else {
    null
  }
  ReferenceUtil.reachabilityFence(_text)
  @ApiStatus.Internal final var _text: ManagedString = null

  def this(text: String) = {
    this(new ManagedString(text), true, 0, 0)
  }

  def this(text: String, offset: Point) = {
    this(new ManagedString(text), true, offset._x, offset._y)
  }

  override def close(): Unit = {
    super.close()
    if (_text != null) _text.close()
  }

  override def beginLine(): Unit = {
    throw new UnsupportedOperationException("beginLine")
  }

  override def runInfo(info: RunInfo): Unit = {
    throw new UnsupportedOperationException("runInfo")
  }

  override def commitRunInfo(): Unit = {
    throw new UnsupportedOperationException("commitRunInfo")
  }

  override def runOffset(info: RunInfo): Point = throw new UnsupportedOperationException("runOffset")

  override def commitRun(info: RunInfo, glyphs: Array[Short], positions: Array[Point], clusters: Array[Int]): Unit = {
    throw new UnsupportedOperationException("commitRun")
  }

  override def commitLine(): Unit = {
    throw new UnsupportedOperationException("commitLine")
  }

  @Nullable def makeBlob: TextBlob = {
    try {
      Stats.onNativeCall()
      val ptr = TextBlobBuilderRunHandler._nMakeBlob(_ptr)
      if (0 == ptr) {
        null
      } else {
        new TextBlob(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}