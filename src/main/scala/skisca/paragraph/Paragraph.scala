package gay.menkissing.skisca.paragraph

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object Paragraph {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @native def _nGetFinalizer: Long

  @native def _nGetMaxWidth(ptr: Long): Float

  @native def _nGetHeight(ptr: Long): Float

  @native def _nGetMinIntrinsicWidth(ptr: Long): Float

  @native def _nGetMaxIntrinsicWidth(ptr: Long): Float

  @native def _nGetAlphabeticBaseline(ptr: Long): Float

  @native def _nGetIdeographicBaseline(ptr: Long): Float

  @native def _nGetLongestLine(ptr: Long): Float

  @native def _nDidExceedMaxLines(ptr: Long): Boolean

  @native def _nLayout(ptr: Long, width: Float): Unit

  @native def _nPaint(ptr: Long, canvasPtr: Long, x: Float, y: Float): Long

  @native def _nGetRectsForRange(ptr: Long, start: Int, end: Int, rectHeightMode: Int, rectWidthMode: Int): Array[TextBox]

  @native def _nGetRectsForPlaceholders(ptr: Long): Array[TextBox]

  @native def _nGetGlyphPositionAtCoordinate(ptr: Long, dx: Float, dy: Float): Int

  @native def _nGetWordBoundary(ptr: Long, offset: Int): Long

  @native def _nGetLineMetrics(ptr: Long, textPtr: Long): Array[LineMetrics]

  @native def _nGetLineNumber(ptr: Long): Long

  @native def _nMarkDirty(ptr: Long): Unit

  @native def _nGetUnresolvedGlyphsCount(ptr: Long): Int

  @native def _nUpdateAlignment(ptr: Long, Align: Int): Unit

  // public static native void  _nUpdateText(long ptr, int from, String text);
  @native def _nUpdateFontSize(ptr: Long, from: Int, to: Int, size: Float, textPtr: Long): Unit

  @native def _nUpdateForegroundPaint(ptr: Long, from: Int, to: Int, paintPtr: Long, textPtr: Long): Unit

  @native def _nUpdateBackgroundPaint(ptr: Long, from: Int, to: Int, paintPtr: Long, textPtr: Long): Unit

  try Library.staticLoad()
}

class Paragraph @ApiStatus.Internal(ptr: Long, @ApiStatus.Internal var _text: ManagedString) extends Managed(ptr, Paragraph
  ._FinalizerHolder.PTR, true) {
  Stats.onNativeCall()

  override def close(): Unit = {
    if (_text != null) {
      _text.close()
      _text = null
    }
    super.close()
  }

  def getMaxWidth: Float = {
    try {
      Stats.onNativeCall()
      Paragraph._nGetMaxWidth(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getHeight: Float = {
    try {
      Stats.onNativeCall()
      Paragraph._nGetHeight(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getMinIntrinsicWidth: Float = {
    try {
      Stats.onNativeCall()
      Paragraph._nGetMinIntrinsicWidth(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getMaxIntrinsicWidth: Float = {
    try {
      Stats.onNativeCall()
      Paragraph._nGetMaxIntrinsicWidth(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getAlphabeticBaseline: Float = {
    try {
      Stats.onNativeCall()
      Paragraph._nGetAlphabeticBaseline(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getIdeographicBaseline: Float = {
    try {
      Stats.onNativeCall()
      Paragraph._nGetIdeographicBaseline(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getLongestLine: Float = {
    try {
      Stats.onNativeCall()
      Paragraph._nGetLongestLine(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def didExceedMaxLines: Boolean = {
    try {
      Stats.onNativeCall()
      Paragraph._nDidExceedMaxLines(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def layout(width: Float): Paragraph = {
    Stats.onNativeCall()
    Paragraph._nLayout(_ptr, width)
    this
  }

  def paint(canvas: Canvas, x: Float, y: Float): Paragraph = {
    try {
      Stats.onNativeCall()
      Paragraph._nPaint(_ptr, Native.getPtr(canvas), x, y)
      this
    } finally {
      ReferenceUtil.reachabilityFence(canvas)
    }
  }

  /**
   * Returns a vector of bounding boxes that enclose all text between
   * start and end char indices, including start and excluding end.
   */
  def getRectsForRange(start: Int, end: Int, rectHeightMode: RectHeightMode, rectWidthMode: RectWidthMode): Array[TextBox] = {
    try {
      Stats.onNativeCall()
      Paragraph._nGetRectsForRange(_ptr, start, end, rectHeightMode.ordinal, rectWidthMode.ordinal)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getRectsForPlaceholders: Array[TextBox] = {
    try {
      Stats.onNativeCall()
      Paragraph._nGetRectsForPlaceholders(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getGlyphPositionAtCoordinate(dx: Float, dy: Float): PositionWithAffinity = {
    try {
      Stats.onNativeCall()
      val res = Paragraph._nGetGlyphPositionAtCoordinate(_ptr, dx, dy)
      if (res >= 0) {
        PositionWithAffinity(res, Affinity.DOWNSTREAM)
      } else {
        PositionWithAffinity(-res - 1, Affinity.UPSTREAM)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getWordBoundary(offset: Int): IRange = {
    try {
      Stats.onNativeCall()
      IRange._makeFromLong(Paragraph._nGetWordBoundary(_ptr, offset))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getLineMetrics: Array[LineMetrics] = {
    try {
      if (_text == null) return new Array[LineMetrics](0)
      Stats.onNativeCall()
      Paragraph._nGetLineMetrics(_ptr, Native.getPtr(_text))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(_text)
    }
  }

  def getLineNumber: Long = {
    try {
      Stats.onNativeCall()
      Paragraph._nGetLineNumber(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def markDirty: Paragraph = {
    Stats.onNativeCall()
    Paragraph._nMarkDirty(_ptr)
    this
  }

  def getUnresolvedGlyphsCount: Int = {
    try {
      Stats.onNativeCall()
      Paragraph._nGetUnresolvedGlyphsCount(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def updateAlignment(alignment: Alignment): Paragraph = {
    Stats.onNativeCall()
    Paragraph._nUpdateAlignment(_ptr, alignment.ordinal)
    this
  }

  // public Paragraph updateText(int from, String text) {
  //     Stats.onNativeCall();
  //     _nUpdateText(_ptr, from, text);
  //     // TODO: update _text
  //     return this;
  // }
  def updateFontSize(from: Int, to: Int, size: Float): Paragraph = {
    try {
      if (_text != null) {
        Stats.onNativeCall()
        Paragraph._nUpdateFontSize(_ptr, from, to, size, Native.getPtr(_text))
      }
      this
    } finally {
      ReferenceUtil.reachabilityFence(_text)
    }
  }

  def updateForegroundPaint(from: Int, to: Int, paint: Paint): Paragraph = {
    try {
      if (_text != null) {
        Stats.onNativeCall()
        Paragraph._nUpdateForegroundPaint(_ptr, from, to, Native.getPtr(paint), Native.getPtr(_text))
      }
      this
    } finally {
      ReferenceUtil.reachabilityFence(paint)
      ReferenceUtil.reachabilityFence(_text)
    }
  }

  def updateBackgroundPaint(from: Int, to: Int, paint: Paint): Paragraph = {
    try {
      if (_text != null) {
        Stats.onNativeCall()
        Paragraph._nUpdateBackgroundPaint(_ptr, from, to, Native.getPtr(paint), Native.getPtr(_text))
      }
      this
    } finally {
      ReferenceUtil.reachabilityFence(paint)
      ReferenceUtil.reachabilityFence(_text)
    }
  }
}