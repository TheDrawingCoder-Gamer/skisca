package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object TextBlobBuilder {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }
  /**
   * Constructs empty TextBlobBuilder. By default, TextBlobBuilder has no runs.
   *
   * @see <a href="https://fiddle.skia.org/c/@TextBlobBuilder_empty_constructor">https://fiddle.skia.org/c/@TextBlobBuilder_empty_constructor</a>
   */
  def apply(): TextBlobBuilder = {
    new TextBlobBuilder(_nMake())
  }
  
  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nMake(): Long

  @ApiStatus.Internal
  @native def _nBuild(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nAppendRun(ptr: Long, fontPtr: Long, glyphs: Array[Short], x: Float, y: Float, bounds: Rect): Unit

  @ApiStatus.Internal
  @native def _nAppendRunPosH(ptr: Long, fontPtr: Long, glyphs: Array[Short], xs: Array[Float], y: Float, bounds: Rect): Unit

  @ApiStatus.Internal
  @native def _nAppendRunPos(ptr: Long, fontPtr: Long, glyphs: Array[Short], pos: Array[Float], bounds: Rect): Unit

  @ApiStatus.Internal
  @native def _nAppendRunRSXform(ptr: Long, fontPtr: Long, glyphs: Array[Short], xform: Array[Float]): Unit

  try Library.staticLoad()
}

class TextBlobBuilder @ApiStatus.Internal(ptr: Long) extends Managed(ptr, TextBlobBuilder._FinalizerHolder.PTR) {

  def this() = {
    this(TextBlobBuilder._nMake())
  }
  
  /**
   * <p>Returns TextBlob built from runs of glyphs added by builder. Returned
   * TextBlob is immutable; it may be copied, but its contents may not be altered.
   * Returns null if no runs of glyphs were added by builder.</p>
   *
   * <p>Resets TextBlobBuilder to its initial empty state, allowing it to be
   * reused to build a new set of runs.</p>
   *
   * @return SkTextBlob or null
   * @see <a href="https://fiddle.skia.org/c/@TextBlobBuilder_make">https://fiddle.skia.org/c/@TextBlobBuilder_make</a>
   */
  @Nullable def build: TextBlob = {
    try {
      Stats.onNativeCall()
      val ptr = TextBlobBuilder._nBuild(_ptr)
      if (ptr == 0) {
        null
      } else {
        new TextBlob(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Glyphs are positioned on a baseline at (x, y), using font metrics to
   * determine their relative placement.
   *
   * @param font Font used for this run
   * @param text Text to append in this run
   * @param x    horizontal offset within the blob
   * @param y    vertical offset within the blob
   * @return this
   */
  def appendRun(font: Font, text: String, x: Float, y: Float): TextBlobBuilder = {
    appendRun(font, font
      .getStringGlyphs(text), x, y, null)
  }

  /**
   * <p>Glyphs are positioned on a baseline at (x, y), using font metrics to
   * determine their relative placement.</p>
   *
   * <p>bounds defines an optional bounding box, used to suppress drawing when TextBlob
   * bounds does not intersect Surface bounds. If bounds is null, TextBlob bounds
   * is computed from (x, y) and glyphs metrics.</p>
   *
   * @param font   Font used for this run
   * @param text   Text to append in this run
   * @param x      horizontal offset within the blob
   * @param y      vertical offset within the blob
   * @param bounds optional run bounding box
   * @return this
   */
  def appendRun(font: Font, text: String, x: Float, y: Float, @Nullable bounds: Rect): TextBlobBuilder = {
    appendRun(font, font
      .getStringGlyphs(text), x, y, bounds)
  }

  /**
   * <p>Glyphs are positioned on a baseline at (x, y), using font metrics to
   * determine their relative placement.</p>
   *
   * <p>bounds defines an optional bounding box, used to suppress drawing when TextBlob
   * bounds does not intersect Surface bounds. If bounds is null, TextBlob bounds
   * is computed from (x, y) and glyphs metrics.</p>
   *
   * @param font   Font used for this run
   * @param glyphs glyphs in this run
   * @param x      horizontal offset within the blob
   * @param y      vertical offset within the blob
   * @param bounds optional run bounding box
   * @return this
   */
  def appendRun(font: Font, glyphs: Array[Short], x: Float, y: Float, @Nullable bounds: Rect): TextBlobBuilder = {
    try {
      Stats.onNativeCall()
      TextBlobBuilder._nAppendRun(_ptr, Native.getPtr(font), glyphs, x, y, bounds)
      this
    } finally {
      ReferenceUtil.reachabilityFence(font)
    }
  }

  /**
   * <p>Glyphs are positioned on a baseline at y, using x-axis positions from xs.</p>
   *
   * @param font   Font used for this run
   * @param glyphs glyphs in this run
   * @param xs     horizontal positions on glyphs within the blob
   * @param y      vertical offset within the blob
   * @return this
   */
  def appendRunPosH(font: Font, glyphs: Array[Short], xs: Array[Float], y: Float): TextBlobBuilder = {
    appendRunPosH(font, glyphs, xs, y, null)
  }

  /**
   * <p>Glyphs are positioned on a baseline at y, using x-axis positions from xs.</p>
   *
   * <p>bounds defines an optional bounding box, used to suppress drawing when TextBlob
   * bounds does not intersect Surface bounds. If bounds is null, TextBlob bounds
   * is computed from (x, y) and glyphs metrics.</p>
   *
   * @param font   Font used for this run
   * @param glyphs glyphs in this run
   * @param xs     horizontal positions of glyphs within the blob
   * @param y      vertical offset within the blob
   * @param bounds optional run bounding box
   * @return this
   */
  def appendRunPosH(font: Font, glyphs: Array[Short], xs: Array[Float], y: Float, @Nullable bounds: Rect): TextBlobBuilder = {
    try {
      assert(glyphs.length == xs.length, "glyphs.length " + glyphs.length + " != xs.length " + xs.length)
      Stats.onNativeCall()
      TextBlobBuilder._nAppendRunPosH(_ptr, Native.getPtr(font), glyphs, xs, y, bounds)
      this
    } finally {
      ReferenceUtil.reachabilityFence(font)
    }
  }

  /**
   * <p>Glyphs are positioned at positions from pos.</p>
   *
   * @param font   Font used for this run
   * @param glyphs glyphs in this run
   * @param pos    positions of glyphs within the blob
   * @return this
   */
  def appendRunPos(font: Font, glyphs: Array[Short], pos: Array[Point]): TextBlobBuilder = {
    appendRunPos(font, glyphs, pos, null)
  }

  /**
   * <p>Glyphs are positioned at positions from pos.</p>
   *
   * <p>bounds defines an optional bounding box, used to suppress drawing when TextBlob
   * bounds does not intersect Surface bounds. If bounds is null, TextBlob bounds
   * is computed from (x, y) and glyphs metrics.</p>
   *
   * @param font   Font used for this run
   * @param glyphs glyphs in this run
   * @param pos    positions of glyphs within the blob
   * @param bounds optional run bounding box
   * @return this
   */
  def appendRunPos(font: Font, glyphs: Array[Short], pos: Array[Point], @Nullable bounds: Rect): TextBlobBuilder = {
    try {
      assert(glyphs.length == pos.length, "glyphs.length " + glyphs.length + " != pos.length " + pos.length)
      val floatPos = new Array[Float](pos.length * 2)
      for (i <- 0 until pos.length) {
        floatPos(i * 2) = pos(i)._x
        floatPos(i * 2 + 1) = pos(i)._y
      }
      Stats.onNativeCall()
      TextBlobBuilder._nAppendRunPos(_ptr, Native.getPtr(font), glyphs, floatPos, bounds)
      this
    } finally {
      ReferenceUtil.reachabilityFence(font)
    }
  }

  def appendRunRSXform(font: Font, glyphs: Array[Short], xform: Array[RSXform]): TextBlobBuilder = {
    try {
      assert(glyphs.length == xform.length, "glyphs.length " + glyphs.length + " != xform.length " + xform.length)
      val floatXform = new Array[Float](xform.length * 4)
      for (i <- xform.indices) {
        floatXform(i * 4) = xform(i).scos
        floatXform(i * 4 + 1) = xform(i).ssin
        floatXform(i * 4 + 2) = xform(i).tx
        floatXform(i * 4 + 3) = xform(i).ty
      }
      Stats.onNativeCall()
      TextBlobBuilder._nAppendRunRSXform(_ptr, Native.getPtr(font), glyphs, floatXform)
      this
    } finally {
      ReferenceUtil.reachabilityFence(font)
    }
  }
}