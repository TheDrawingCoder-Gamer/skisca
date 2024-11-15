package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object Font {
  @ApiStatus.Internal def makeClone(ptr: Long): Font = {
    Stats.onNativeCall()
    new Font(_nMakeClone(ptr))
  }

  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nMakeDefault: Long

  @ApiStatus.Internal
  @native def _nMakeTypeface(typefacePtr: Long): Long

  @ApiStatus.Internal
  @native def _nMakeTypefaceSize(typefacePtr: Long, size: Float): Long

  @ApiStatus.Internal
  @native def _nMakeTypefaceSizeScaleSkew(typefacePtr: Long, size: Float, scaleX: Float, skewX: Float): Long

  @ApiStatus.Internal
  @native def _nMakeClone(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nEquals(ptr: Long, otherPtr: Long): Boolean

  @ApiStatus.Internal
  @native def _nIsAutoHintingForced(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nAreBitmapsEmbedded(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nIsSubpixel(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nAreMetricsLinear(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nIsEmboldened(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nIsBaselineSnapped(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nSetAutoHintingForced(ptr: Long, value: Boolean): Unit

  @ApiStatus.Internal
  @native def _nSetBitmapsEmbedded(ptr: Long, value: Boolean): Unit

  @ApiStatus.Internal
  @native def _nSetSubpixel(ptr: Long, value: Boolean): Unit

  @ApiStatus.Internal
  @native def _nSetMetricsLinear(ptr: Long, value: Boolean): Unit

  @ApiStatus.Internal
  @native def _nSetEmboldened(ptr: Long, value: Boolean): Unit

  @ApiStatus.Internal
  @native def _nSetBaselineSnapped(ptr: Long, value: Boolean): Unit

  @ApiStatus.Internal
  @native def _nGetEdging(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nSetEdging(ptr: Long, value: Int): Unit

  @ApiStatus.Internal
  @native def _nGetHinting(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nSetHinting(ptr: Long, value: Int): Unit

  @ApiStatus.Internal
  @native def _nGetTypeface(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nGetTypefaceOrDefault(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nGetSize(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nGetScaleX(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nGetSkewX(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nSetTypeface(ptr: Long, typefacePtr: Long): Unit

  @ApiStatus.Internal
  @native def _nSetSize(ptr: Long, value: Float): Unit

  @ApiStatus.Internal
  @native def _nSetScaleX(ptr: Long, value: Float): Unit

  @ApiStatus.Internal
  @native def _nSetSkewX(ptr: Long, value: Float): Unit

  @ApiStatus.Internal
  @native def _nGetStringGlyphs(ptr: Long, str: String): Array[Short]

  @ApiStatus.Internal
  @native def _nGetUTF32Glyph(ptr: Long, uni: Int): Short

  @ApiStatus.Internal
  @native def _nGetUTF32Glyphs(ptr: Long, uni: Array[Int]): Array[Short]

  @ApiStatus.Internal
  @native def _nGetStringGlyphsCount(ptr: Long, str: String): Int

  @ApiStatus.Internal
  @native def _nMeasureText(ptr: Long, str: String, paintPtr: Long): Rect

  @ApiStatus.Internal
  @native def _nMeasureTextWidth(ptr: Long, str: String, paintPtr: Long): Float

  @ApiStatus.Internal
  @native def _nGetWidths(ptr: Long, glyphs: Array[Short]): Array[Float]

  @ApiStatus.Internal
  @native def _nGetBounds(ptr: Long, glyphs: Array[Short], paintPtr: Long): Array[Rect]

  @ApiStatus.Internal
  @native def _nGetPositions(ptr: Long, glyphs: Array[Short], x: Float, y: Float): Array[Point]

  @ApiStatus.Internal
  @native def _nGetXPositions(ptr: Long, glyphs: Array[Short], x: Float): Array[Float]

  @ApiStatus.Internal
  @native def _nGetPath(ptr: Long, glyph: Short): Long

  @ApiStatus.Internal
  @native def _nGetPaths(ptr: Long, glyphs: Array[Short]): Array[Path]

  @ApiStatus.Internal
  @native def _nGetMetrics(ptr: Long): FontMetrics

  @ApiStatus.Internal
  @native def _nGetSpacing(ptr: Long): Float

  Library.staticLoad()
}

class Font(ptr: Long, managed: Boolean = true) extends Managed(ptr, Font._FinalizerHolder.PTR, managed) {

  @ApiStatus.Internal var _metrics: FontMetrics = null
  @ApiStatus.Internal var _spacing: Option[Float] = None

  def this() = {
    this(Font._nMakeDefault)
    Stats.onNativeCall()
  }

  /**
   * Returns Font with Typeface and default size
   *
   * @param typeface typeface and style used to draw and measure text. Pass null for default
   */
  def this(@Nullable typeface: Typeface) = {
    this(Font._nMakeTypeface(Native.getPtr(typeface)))
    Stats.onNativeCall()
    ReferenceUtil.reachabilityFence(typeface)
  }

  /**
   * @param typeface typeface and style used to draw and measure text. Pass null for default
   * @param size     typographic size of the text
   */
  def this(@Nullable typeface: Typeface, size: Float) = {
    this(Font._nMakeTypefaceSize(Native.getPtr(typeface), size))
    Stats.onNativeCall()
    ReferenceUtil.reachabilityFence(typeface)
  }

  /**
   * Constructs Font with default values with Typeface and size in points,
   * horizontal scale, and horizontal skew. Horizontal scale emulates condensed
   * and expanded fonts. Horizontal skew emulates oblique fonts.
   *
   * @param typeface typeface and style used to draw and measure text. Pass null for default
   * @param size     typographic size of the text
   * @param scaleX   text horizonral scale
   * @param skewX    additional shear on x-axis relative to y-axis
   */
  def this(@Nullable typeface: Typeface, size: Float, scaleX: Float, skewX: Float) = {
    this(Font._nMakeTypefaceSizeScaleSkew(Native.getPtr(typeface), size, scaleX, skewX))
    Stats.onNativeCall()
    ReferenceUtil.reachabilityFence(typeface)
  }

  /**
   * Compares fonts, and returns true if they are equivalent.
   * May return false if Typeface has identical contents but different pointers.
   */
  @ApiStatus.Internal override def _nativeEquals(other: Native): Boolean = {
    try {
      Font._nEquals(_ptr, Native.getPtr(other))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(other)
    }
  }

  @ApiStatus.Internal def _resetMetrics(): Unit = {
    _metrics = null
    _spacing = None
  }

  /**
   * If true, instructs the font manager to always hint glyphs.
   * Returned value is only meaningful if platform uses FreeType as the font manager.
   *
   * @return true if all glyphs are hinted
   */
  def isAutoHintingForced: Boolean = {
    try {
      Stats.onNativeCall()
      Font._nIsAutoHintingForced(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return true if font engine may return glyphs from font bitmaps instead of from outlines
   */
  def areBitmapsEmbedded: Boolean = {
    try {
      Stats.onNativeCall()
      Font._nAreBitmapsEmbedded(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return true if glyphs may be drawn at sub-pixel offsets
   */
  def isSubpixel: Boolean = {
    try {
      Stats.onNativeCall()
      Font._nIsSubpixel(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return true if font and glyph metrics are requested to be linearly scalable
   */
  def areMetricsLinear: Boolean = {
    try {
      Stats.onNativeCall()
      Font._nAreMetricsLinear(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns true if bold is approximated by increasing the stroke width when creating glyph
   * bitmaps from outlines.
   *
   * @return true if bold is approximated through stroke width
   */
  def isEmboldened: Boolean = {
    try {
      Stats.onNativeCall()
      Font._nIsEmboldened(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns true if baselines will be snapped to pixel positions when the current transformation
   * matrix is axis aligned.
   *
   * @return true if baselines may be snapped to pixels
   */
  def isBaselineSnapped: Boolean = {
    try {
      Stats.onNativeCall()
      Font._nIsBaselineSnapped(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Sets whether to always hint glyphs. If forceAutoHinting is set, instructs the font manager to always hint glyphs. Only affects platforms that use FreeType as the font manager.
   *
   * @param value setting to always hint glyphs
   * @return this
   */
  def setAutoHintingForced(value: Boolean): Font = {
    _resetMetrics()
    Stats.onNativeCall()
    Font._nSetAutoHintingForced(_ptr, value)
    this
  }

  /**
   * Requests, but does not require, to use bitmaps in fonts instead of outlines.
   *
   * @param value setting to use bitmaps in fonts
   * @return this
   */
  def setBitmapsEmbedded(value: Boolean): Font = {
    _resetMetrics()
    Stats.onNativeCall()
    Font._nSetBitmapsEmbedded(_ptr, value)
    this
  }

  /**
   * Requests, but does not require, that glyphs respect sub-pixel positioning.
   *
   * @param value setting for sub-pixel positioning
   * @return this
   */
  def setSubpixel(value: Boolean): Font = {
    _resetMetrics()
    Stats.onNativeCall()
    Font._nSetSubpixel(_ptr, value)
    this
  }

  /**
   * Requests, but does not require, linearly scalable font and glyph metrics.
   *
   * For outline fonts 'true' means font and glyph metrics should ignore hinting and rounding.
   * Note that some bitmap formats may not be able to scale linearly and will ignore this flag.
   *
   * @param value setting for linearly scalable font and glyph metrics.
   * @return this
   */
  def setMetricsLinear(value: Boolean): Font = {
    _resetMetrics()
    Stats.onNativeCall()
    Font._nSetMetricsLinear(_ptr, value)
    this
  }

  /**
   * Increases stroke width when creating glyph bitmaps to approximate a bold typeface.
   *
   * @param value setting for bold approximation
   * @return this
   */
  def setEmboldened(value: Boolean): Font = {
    _resetMetrics()
    Stats.onNativeCall()
    Font._nSetEmboldened(_ptr, value)
    this
  }

  /**
   * Requests that baselines be snapped to pixels when the current transformation matrix is axis
   * aligned.
   *
   * @param value setting for baseline snapping to pixels
   * @return this
   */
  def setBaselineSnapped(value: Boolean): Font = {
    _resetMetrics()
    Stats.onNativeCall()
    Font._nSetBaselineSnapped(_ptr, value)
    this
  }

  /**
   * Whether edge pixels draw opaque or with partial transparency.
   */
  def getEdging: FontEdging = {
    try {
      Stats.onNativeCall()
      FontEdging._values(Font._nGetEdging(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Requests, but does not require, that edge pixels draw opaque or with
   * partial transparency.
   */
  def setEdging(value: FontEdging): Font = {
    _resetMetrics()
    Stats.onNativeCall()
    Font._nSetEdging(_ptr, value.ordinal)
    this
  }

  /**
   * @return level of glyph outline adjustment
   */
  def getHinting: FontHinting = {
    try {
      Stats.onNativeCall()
      FontHinting._values(Font._nGetHinting(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Sets level of glyph outline adjustment. Does not check for valid values of hintingLevel.
   */
  def setHinting(value: FontHinting): Font = {
    _resetMetrics()
    Stats.onNativeCall()
    Font._nSetHinting(_ptr, value.ordinal)
    this
  }

  /**
   * Returns a font with the same attributes of this font, but with the specified size.
   */
  def makeWithSize(size: Float) = {
    new Font(getTypeface, size, getScaleX, getSkewX)
  }

  /**
   * @return {@link Typeface} if set, or null
   */
  @Nullable def getTypeface: Typeface = {
    try {
      Stats.onNativeCall()
      val ptr = Font._nGetTypeface(_ptr)
      if (ptr == 0) {
        null
      } else {
        new Typeface(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return {@link Typeface} if set, or the default typeface.
   */
  @NotNull def getTypefaceOrDefault: Typeface = {
    try {
      Stats.onNativeCall()
      new Typeface(Font._nGetTypefaceOrDefault(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return text size in points
   */
  def getSize: Float = {
    try {
      Stats.onNativeCall()
      Font._nGetSize(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return text scale on x-axis. Default value is 1
   */
  def getScaleX: Float = {
    try {
      Stats.onNativeCall()
      Font._nGetScaleX(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return text skew on x-axis. Default value is 0
   */
  def getSkewX: Float = {
    try {
      Stats.onNativeCall()
      Font._nGetSkewX(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Sets Typeface to typeface. Pass null to use the default typeface.
   */
  def setTypeface(@Nullable typeface: Typeface): Font = {
    try {
      _resetMetrics()
      Stats.onNativeCall()
      Font._nSetTypeface(_ptr, Native.getPtr(typeface))
      this
    } finally {
      ReferenceUtil.reachabilityFence(typeface)
    }
  }

  /**
   * Sets text size in points. Has no effect if value is not greater than or equal to zero.
   */
  def setSize(value: Float): Font = {
    _resetMetrics()
    Stats.onNativeCall()
    Font._nSetSize(_ptr, value)
    this
  }

  /**
   * Sets text scale on x-axis. Default value is 1.
   */
  def setScaleX(value: Float): Font = {
    _resetMetrics()
    Stats.onNativeCall()
    Font._nSetScaleX(_ptr, value)
    this
  }

  /**
   * Sets text skew on x-axis. Default value is 0.
   */
  def setSkewX(value: Float): Font = {
    _resetMetrics()
    Stats.onNativeCall()
    Font._nSetSkewX(_ptr, value)
    this
  }

  /**
   * Converts text into glyph indices.
   *
   * @return the corresponding glyph ids for each character.
   */
  def getStringGlyphs(s: String): Array[Short] = {
    getUTF32Glyphs(s.codePoints.toArray)
  }

  /**
   * Given an array of UTF32 character codes, return their corresponding glyph IDs.
   *
   * @return the corresponding glyph IDs for each character.
   */
  def getUTF32Glyphs(uni: Array[Int]): Array[Short] = {
    try {
      Stats.onNativeCall()
      Font._nGetUTF32Glyphs(_ptr, uni)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return the glyph that corresponds to the specified unicode code-point (in UTF32 encoding). If the unichar is not supported, returns 0
   */
  def getUTF32Glyph(unichar: Int): Short = {
    try {
      Stats.onNativeCall()
      Font._nGetUTF32Glyph(_ptr, unichar)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return number of glyphs represented by text
   */
  def getStringGlyphsCount(s: String): Int = {
    try {
      Stats.onNativeCall()
      Font._nGetStringGlyphsCount(_ptr, s)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return the bounding box of text
   */
  def measureText(s: String): Rect = {
    measureText(s, null)
  }

  /**
   * @param p stroke width or PathEffect may modify the advance with
   * @return the bounding box of text
   */
  def measureText(s: String, p: Paint): Rect = {
    try {
      Stats.onNativeCall()
      Font._nMeasureText(_ptr, s, Native.getPtr(p))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(p)
    }
  }

  def measureTextWidth(s: String): Float = {
    Stats.onNativeCall()
    measureTextWidth(s, null)
  }

  def measureTextWidth(s: String, p: Paint): Float = {
    try {
      Stats.onNativeCall()
      Font._nMeasureTextWidth(_ptr, s, Native.getPtr(p))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(p)
    }
  }

  /**
   * Retrieves the advances for each glyph
   */
  def getWidths(glyphs: Array[Short]): Array[Float] = {
    try {
      Stats.onNativeCall()
      Font._nGetWidths(_ptr, glyphs)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Retrieves the bounds for each glyph
   */
  def getBounds(glyphs: Array[Short]): Array[Rect] = {
    getBounds(glyphs, null)
  }

  /**
   * Retrieves the bounds for each glyph
   */
  def getBounds(glyphs: Array[Short], p: Paint): Array[Rect] = {
    try {
      Stats.onNativeCall()
      Font._nGetBounds(_ptr, glyphs, Native.getPtr(p))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(p)
    }
  }

  /**
   * Retrieves the positions for each glyph.
   */
  def getPositions(glyphs: Array[Short]): Array[Point] = {
    try {
      Stats.onNativeCall()
      Font._nGetPositions(_ptr, glyphs, 0, 0)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Retrieves the positions for each glyph, beginning at the specified origin.
   */
  def getPositions(glyphs: Array[Short], offset: Point): Array[Point] = {
    try {
      Stats.onNativeCall()
      Font._nGetPositions(_ptr, glyphs, offset._x, offset._y)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Retrieves the x-positions for each glyph.
   */
  def getXPositions(glyphs: Array[Short]): Array[Float] = {
    try {
      Stats.onNativeCall()
      Font._nGetXPositions(_ptr, glyphs, 0)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Retrieves the x-positions for each glyph, beginning at the specified origin.
   */
  def getXPositions(glyphs: Array[Short], offset: Float): Array[Float] = {
    try {
      Stats.onNativeCall()
      Font._nGetXPositions(_ptr, glyphs, offset)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * If the glyph has an outline, returns it. The glyph outline may be empty.
   * Degenerate contours in the glyph outline will be skipped. If glyph is described by a bitmap, returns null.
   */
  @Nullable def getPath(glyph: Short): Path = {
    try {
      Stats.onNativeCall()
      val ptr = Font._nGetPath(_ptr, glyph)
      if (ptr == 0) {
        null
      } else {
        new Path(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Return glyph outlines, some of which might be null.
   */
  def getPaths(glyphs: Array[Short]): Array[Path] = {
    try {
      Stats.onNativeCall()
      Font._nGetPaths(_ptr, glyphs)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns FontMetrics associated with Typeface. Results are scaled by text size but does not take into account
   * dimensions required by text scale, text skew, fake bold, style stroke, and {@link PathEffect}.
   */
  @NotNull def getMetrics: FontMetrics = {
    try {
      if (_metrics == null) {
        Stats.onNativeCall()
        _metrics = Font._nGetMetrics(_ptr)
      }
      _metrics
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns the recommended spacing between lines: the sum of metrics descent, ascent, and leading.
   * Result is scaled by text size but does not take into account dimensions required by stroking and SkPathEffect.
   */
  def getSpacing: Float = {
    try {
      if (_spacing.isEmpty) {
        Stats.onNativeCall()
        _spacing = Some(Font._nGetSpacing(_ptr))
      }
      _spacing.get
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}