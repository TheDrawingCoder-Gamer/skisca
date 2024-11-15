package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object Paint {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  def apply(): Paint = {
    new Paint(_nMake(), true)
  }

  @native def _nGetFinalizer: Long

  @native def _nMake(): Long

  @native def _nMakeClone(ptr: Long): Long

  @native def _nEquals(ptr: Long, otherPtr: Long): Boolean

  @native def _nReset(ptr: Long): Unit

  @native def _nIsAntiAlias(ptr: Long): Boolean

  @native def _nSetAntiAlias(ptr: Long, value: Boolean): Unit

  @native def _nIsDither(ptr: Long): Boolean

  @native def _nSetDither(ptr: Long, value: Boolean): Unit

  @native def _nGetMode(ptr: Long): Int

  @native def _nSetMode(ptr: Long, value: Int): Unit

  @native def _nGetColor(ptr: Long): Int

  @native def _nGetColor4f(ptr: Long): Color4f

  @native def _nSetColor(ptr: Long, argb: Int): Unit

  @native def _nSetColor4f(ptr: Long, r: Float, g: Float, b: Float, a: Float, colorSpacePtr: Long): Unit

  @native def _nGetStrokeWidth(ptr: Long): Float

  @native def _nSetStrokeWidth(ptr: Long, value: Float): Unit

  @native def _nGetStrokeMiter(ptr: Long): Float

  @native def _nSetStrokeMiter(ptr: Long, value: Float): Unit

  @native def _nGetStrokeCap(ptr: Long): Int

  @native def _nSetStrokeCap(ptr: Long, value: Int): Unit

  @native def _nGetStrokeJoin(ptr: Long): Int

  @native def _nSetStrokeJoin(ptr: Long, value: Int): Unit

  @native def _nGetFillPath(ptr: Long, path: Long, resScale: Float): Long

  @native def _nGetFillPathCull(ptr: Long, path: Long, left: Float, top: Float, right: Float, bottom: Float, resScale: Float): Long

  @native def _nGetShader(ptr: Long): Long

  @native def _nSetShader(ptr: Long, shaderPtr: Long): Unit

  @native def _nGetColorFilter(ptr: Long): Long

  @native def _nSetColorFilter(ptr: Long, colorFilterPtr: Long): Unit

  @native def _nGetBlendMode(ptr: Long): Int

  @native def _nSetBlendMode(ptr: Long, mode: Int): Unit

  @native def _nGetPathEffect(ptr: Long): Long

  @native def _nSetPathEffect(ptr: Long, pathEffectPtr: Long): Unit

  @native def _nGetMaskFilter(ptr: Long): Long

  @native def _nSetMaskFilter(ptr: Long, filterPtr: Long): Unit

  @native def _nGetImageFilter(ptr: Long): Long

  @native def _nSetImageFilter(ptr: Long, filterPtr: Long): Unit

  @native def _nHasNothingToDraw(ptr: Long): Boolean

  try Library.staticLoad()
}

class Paint(ptr: Long, managed: Boolean = true)
  extends Managed(ptr, Paint._FinalizerHolder.PTR, managed) {
  Stats.onNativeCall()

  def this() = {
    this(Paint._nMake())
  }
  /**
   * <p>Makes a shallow copy of Paint. PathEffect, Shader,
   * MaskFilter, ColorFilter, and ImageFilter are shared
   * between the original paint and the copy.</p>
   *
   * <p>The referenced objects PathEffect, Shader, MaskFilter, ColorFilter,
   * and ImageFilter cannot be modified after they are created.</p>
   *
   * @return shallow copy of paint
   * @see <a href="https://fiddle.skia.org/c/@Paint_copy_const_SkPaint">https://fiddle.skia.org/c/@Paint_copy_const_SkPaint</a>
   */
  @NotNull
  @Contract("-> new") def makeClone: Paint = {
    try {
      Stats.onNativeCall()
      new Paint(Paint._nMakeClone(_ptr), true)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @ApiStatus.Internal override def _nativeEquals(other: Native): Boolean = {
    try {
      Paint
        ._nEquals(_ptr, Native.getPtr(other))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(other)
    }
  }

  /**
   * Sets all Paint contents to their initial values. This is equivalent to replacing
   * Paint with the result of Paint().
   *
   * @see <a href="https://fiddle.skia.org/c/@Paint_reset">https://fiddle.skia.org/c/@Paint_reset</a>
   */
  @NotNull
  @Contract("-> this") def reset: Paint = {
    Stats.onNativeCall()
    Paint._nReset(_ptr)
    this
  }

  /**
   * Returns true if pixels on the active edges of Path may be drawn with partial transparency.
   *
   * @return antialiasing state
   */
  def isAntiAlias: Boolean = {
    try {
      Stats.onNativeCall()
      Paint._nIsAntiAlias(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Requests, but does not require, that edge pixels draw opaque or with partial transparency.
   *
   * @param value setting for antialiasing
   */
  @NotNull
  @Contract("_ -> this") def setAntiAlias(value: Boolean): Paint = {
    Stats.onNativeCall()
    Paint._nSetAntiAlias(_ptr, value)
    this
  }

  /**
   * @return true if color error may be distributed to smooth color transition.
   */
  def isDither: Boolean = {
    try {
      Stats.onNativeCall()
      Paint._nIsDither(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Requests, but does not require, to distribute color error.
   *
   * @param value setting for ditering
   * @return this
   */
  @NotNull
  @Contract("_ -> this") def setDither(value: Boolean): Paint = {
    Stats.onNativeCall()
    Paint._nSetDither(_ptr, value)
    this
  }

  /**
   * @return whether the geometry is filled, stroked, or filled and stroked.
   */
  @NotNull def getMode: PaintMode = {
    try {
      Stats.onNativeCall()
      PaintMode.values.apply(Paint._nGetMode(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Sets whether the geometry is filled, stroked, or filled and stroked.
   *
   * @see <a href="https://fiddle.skia.org/c/@Paint_setStyle">https://fiddle.skia.org/c/@Paint_setStyle</a>
   * @see <a href="https://fiddle.skia.org/c/@Stroke_Width">https://fiddle.skia.org/c/@Stroke_Width</a>
   */
  @NotNull
  @Contract("!null -> this; null -> fail") def setMode(@NotNull style: PaintMode): Paint = {
    assert(style != null, "Paint::setMode expected style != null")
    Stats.onNativeCall()
    Paint._nSetMode(_ptr, style.ordinal)
    this
  }

  /**
   * Set paint's mode to STROKE if true, or FILL if false.
   *
   * @param value stroke or fill
   * @return this
   */
  @NotNull
  @Contract("_ -> this") def setStroke(value: Boolean): Paint = {
    setMode(if (value) {
      PaintMode.STROKE
    } else {
      PaintMode.FILL
    })
  }

  /**
   * Retrieves alpha and RGB, unpremultiplied, packed into 32 bits.
   * Use helpers {@link Color# getA ( int )}, {@link Color# getR ( int )}, {@link Color# getG ( int )}, and {@link Color# getB ( int )} to extract
   * a color component.
   *
   * @return unpremultiplied ARGB
   */
  def getColor: Int = {
    try {
      Stats.onNativeCall()
      Paint._nGetColor(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Retrieves alpha and RGB, unpremultiplied, as four floating point values. RGB are
   * extended sRGB values (sRGB gamut, and encoded with the sRGB transfer function).
   *
   * @return unpremultiplied RGBA
   */
  @NotNull def getColor4f: Color4f = {
    try {
      Stats.onNativeCall()
      Paint._nGetColor4f(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Sets alpha and RGB used when stroking and filling. The color is a 32-bit value,
   * unpremultiplied, packing 8-bit components for alpha, red, blue, and green.
   *
   * @param color unpremultiplied ARGB
   * @see <a href="https://fiddle.skia.org/c/@Paint_setColor">https://fiddle.skia.org/c/@Paint_setColor</a>
   */
  @NotNull
  @Contract("_ -> this") def setColor(color: Int): Paint = {
    Stats.onNativeCall()
    Paint._nSetColor(_ptr, color)
    this
  }

  /**
   * Sets alpha and RGB used when stroking and filling. The color is four floating
   * point values, unpremultiplied. The color values are interpreted as being in sRGB.
   *
   * @param color unpremultiplied RGBA
   * @return this
   */
  @NotNull
  @Contract("!null -> this; null -> fail") def setColor4f(@NotNull color: Color4f): Paint = {
    setColor4f(color, null)
  }

  /**
   * Sets alpha and RGB used when stroking and filling. The color is four floating
   * point values, unpremultiplied. The color values are interpreted as being in
   * the colorSpace. If colorSpace is nullptr, then color is assumed to be in the
   * sRGB color space.
   *
   * @param color      unpremultiplied RGBA
   * @param colorSpace SkColorSpace describing the encoding of color
   * @return this
   */
  @NotNull
  @Contract("!null, _ -> this; null, _ -> fail") def setColor4f(@NotNull color: Color4f, @Nullable colorSpace: ColorSpace): Paint = {
    try {
      assert(color != null, "Paint::setColor4f expected color != null")
      Stats.onNativeCall()
      Paint._nSetColor4f(_ptr, color.r, color.g, color.b, color.a, Native.getPtr(colorSpace))
      this
    } finally {
      ReferenceUtil.reachabilityFence(colorSpace)
    }
  }

  /**
   * Retrieves alpha from the color used when stroking and filling.
   *
   * @return alpha ranging from 0f, fully transparent, to 1f, fully opaque
   */
  def getAlphaf: Float = {
    getColor4f.a
  }

  /**
   * Retrieves alpha from the color used when stroking and filling.
   *
   * @return alpha ranging from 0, fully transparent, to 255, fully opaque
   */
  def getAlpha: Int = {
    (getAlphaf * 255f).round
  }

  /**
   * <p>Replaces alpha, leaving RGB unchanged. An out of range value triggers
   * an assert in the debug build. a is a value from 0f to 1f.</p>
   *
   * <p>a set to zero makes color fully transparent; a set to 1.0 makes color
   * fully opaque.</p>
   *
   * @param a alpha component of color
   * @return this
   */
  @NotNull
  @Contract("_ -> this") def setAlphaf(a: Float): Paint = {
    setColor4f(getColor4f.copy(a = a))
  }

  /**
   * <p>Replaces alpha, leaving RGB unchanged. An out of range value triggers
   * an assert in the debug build. a is a value from 0 to 255.</p>
   *
   * <p>a set to zero makes color fully transparent; a set to 255 makes color
   * fully opaque.</p>
   *
   * @param a alpha component of color
   * @return this
   */
  @NotNull
  @Contract("_ -> this") def setAlpha(a: Int): Paint = {
    setAlphaf(a / 255f)
  }

  /**
   * Sets color used when drawing solid fills. The color components range from 0 to 255.
   * The color is unpremultiplied; alpha sets the transparency independent of RGB.
   *
   * @param a amount of alpha, from fully transparent (0) to fully opaque (255)
   * @param r amount of red, from no red (0) to full red (255)
   * @param g amount of green, from no green (0) to full green (255)
   * @param b amount of blue, from no blue (0) to full blue (255)
   * @see <a href="https://fiddle.skia.org/c/@Paint_setARGB">https://fiddle.skia.org/c/@Paint_setARGB</a>
   */
  @NotNull
  @Contract("_, _, _, _ -> this") def setARGB(a: Int, r: Int, g: Int, b: Int): Paint = {
    Stats.onNativeCall()
    Paint._nSetColor4f(_ptr, r / 255f, g / 255f, b / 255f, a / 255f, 0)
    this
  }

  /**
   * Returns the thickness of the pen used by Paint to outline the shape.
   *
   * @return zero for hairline, greater than zero for pen thickness
   */
  def getStrokeWidth: Float = {
    try {
      Stats.onNativeCall()
      Paint._nGetStrokeWidth(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Sets the thickness of the pen used by the paint to outline the shape.
   * A stroke-width of zero is treated as "hairline" width. Hairlines are always exactly one
   * pixel wide in device space (their thickness does not change as the canvas is scaled).
   * Negative stroke-widths are invalid; setting a negative width will have no effect.
   *
   * @param width zero thickness for hairline; greater than zero for pen thickness
   * @see <a href="https://fiddle.skia.org/c/@Miter_Limit">https://fiddle.skia.org/c/@Miter_Limit</a>
   * @see <a href="https://fiddle.skia.org/c/@Paint_setStrokeWidth">https://fiddle.skia.org/c/@Paint_setStrokeWidth</a>
   */
  @NotNull
  @Contract("_ -> this") def setStrokeWidth(width: Float): Paint = {
    Stats.onNativeCall()
    Paint._nSetStrokeWidth(_ptr, width)
    this
  }

  /**
   * Returns the limit at which a sharp corner is drawn beveled.
   *
   * @return zero and greater miter limit
   */
  def getStrokeMiter: Float = {
    try {
      Stats.onNativeCall()
      Paint._nGetStrokeMiter(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Sets the limit at which a sharp corner is drawn beveled.
   * Valid values are zero and greater.
   * Has no effect if miter is less than zero.
   *
   * @param miter zero and greater miter limit
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Paint_setStrokeMiter">https://fiddle.skia.org/c/@Paint_setStrokeMiter</a>
   */
  @NotNull
  @Contract("_ -> this") def setStrokeMiter(miter: Float): Paint = {
    Stats.onNativeCall()
    Paint._nSetStrokeMiter(_ptr, miter)
    this
  }

  /**
   * @return the geometry drawn at the beginning and end of strokes.
   */
  @NotNull
  @Contract("-> this") def getStrokeCap: PaintStrokeCap = {
    try {
      Stats.onNativeCall()
      PaintStrokeCap._values(Paint._nGetStrokeCap(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Sets the geometry drawn at the beginning and end of strokes.
   *
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Paint_setStrokeCap_a">https://fiddle.skia.org/c/@Paint_setStrokeCap_a</a>
   * @see <a href="https://fiddle.skia.org/c/@Paint_setStrokeCap_b">https://fiddle.skia.org/c/@Paint_setStrokeCap_b</a>
   */
  @NotNull
  @Contract("!null -> this; null -> fail") def setStrokeCap(@NotNull cap: PaintStrokeCap): Paint = {
    assert(cap != null, "Paint::setStrokeCap expected cap != null")
    Stats.onNativeCall()
    Paint._nSetStrokeCap(_ptr, cap.ordinal)
    this
  }

  /**
   * @return the geometry drawn at the corners of strokes.
   */
  @NotNull
  @Contract("-> this") def getStrokeJoin: PaintStrokeJoin = {
    try {
      Stats.onNativeCall()
      PaintStrokeJoin._values(Paint._nGetStrokeJoin(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Sets the geometry drawn at the corners of strokes.
   *
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Paint_setStrokeJoin">https://fiddle.skia.org/c/@Paint_setStrokeJoin</a>
   */
  @NotNull
  @Contract("!null -> this; null -> fail") def setStrokeJoin(@NotNull join: PaintStrokeJoin): Paint = {
    assert(join != null, "Paint::setStrokeJoin expected join != null")
    Stats.onNativeCall()
    Paint._nSetStrokeJoin(_ptr, join.ordinal)
    this
  }

  /**
   * Returns the filled equivalent of the stroked path.
   *
   * @param src Path read to create a filled version
   * @return resulting Path
   */
  @NotNull
  @Contract("!null -> new; null -> fail") def getFillPath(@NotNull src: Path): Path = {
    getFillPath(src, null, 1)
  }

  /**
   * Returns the filled equivalent of the stroked path.
   *
   * @param src      Path read to create a filled version
   * @param cull     Optional limit passed to PathEffect
   * @param resScale if &gt; 1, increase precision, else if (0 &lt; resScale &lt; 1) reduce precision
   *                 to favor speed and size
   * @return resulting Path
   */
  @NotNull
  @Contract("!null, _, _ -> new; null, _, _ -> fail") def getFillPath(@NotNull src: Path, @Nullable cull: Rect, resScale: Float): Path = {
    try {
      assert(src != null, "Paint::getFillPath expected src != null")
      Stats.onNativeCall()
      if (cull == null) {
        new Path(Paint._nGetFillPath(_ptr, Native.getPtr(src), resScale))
      } else {
        new Path(Paint
          ._nGetFillPathCull(_ptr, Native.getPtr(src), cull._left, cull._top, cull._right, cull._bottom, resScale))
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(src)
    }
  }

  /**
   * @return {@link Shader} or null
   * @see <a href="https://fiddle.skia.org/c/@Paint_refShader">https://fiddle.skia.org/c/@Paint_refShader</a>
   */
  @Nullable def getShader: Shader = {
    try {
      Stats.onNativeCall()
      val shaderPtr = Paint._nGetShader(_ptr)
      if (shaderPtr == 0) {
        null
      } else {
        new Shader(shaderPtr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @param shader how geometry is filled with color; if null, color is used instead
   * @see <a href="https://fiddle.skia.org/c/@Color_Filter_Methods">https://fiddle.skia.org/c/@Color_Filter_Methods</a>
   * @see <a href="https://fiddle.skia.org/c/@Paint_setShader">https://fiddle.skia.org/c/@Paint_setShader</a>
   */
  @NotNull
  @Contract("_ -> this") def setShader(@Nullable shader: Shader): Paint = {
    try {
      Stats.onNativeCall()
      Paint._nSetShader(_ptr, Native.getPtr(shader))
      this
    } finally {
      ReferenceUtil.reachabilityFence(shader)
    }
  }

  /**
   * @return {@link ColorFilter} or null
   * @see <a href="https://fiddle.skia.org/c/@Paint_refColorFilter">https://fiddle.skia.org/c/@Paint_refColorFilter</a>
   */
  @Nullable def getColorFilter: ColorFilter = {
    try {
      Stats.onNativeCall()
      val colorFilterPtr = Paint._nGetColorFilter(_ptr)
      if (colorFilterPtr == 0) {
        null
      } else {
        new ColorFilter(colorFilterPtr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @param colorFilter {@link ColorFilter} to apply to subsequent draw
   * @see <a href="https://fiddle.skia.org/c/@Blend_Mode_Methods">https://fiddle.skia.org/c/@Blend_Mode_Methods</a>
   * @see <a href="https://fiddle.skia.org/c/@Paint_setColorFilter">https://fiddle.skia.org/c/@Paint_setColorFilter</a>
   */
  @NotNull
  @Contract("_ -> this") def setColorFilter(@Nullable colorFilter: ColorFilter): Paint = {
    try {
      Stats.onNativeCall()
      Paint._nSetColorFilter(_ptr, Native.getPtr(colorFilter))
      this
    } finally {
      ReferenceUtil.reachabilityFence(colorFilter)
    }
  }

  /**
   * Returns BlendMode. By default, returns {@link BlendMode# SRC_OVER}.
   *
   * @return mode used to combine source color with destination color
   */
  @Nullable def getBlendMode: BlendMode = {
    try {
      Stats.onNativeCall()
      BlendMode.fromOrdinal(Paint._nGetBlendMode(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return true if BlendMode is BlendMode.SRC_OVER, the default.
   */
  def isSrcOver: Boolean = {
    getBlendMode eq BlendMode.SRC_OVER
  }

  /**
   * Sets SkBlendMode to mode. Does not check for valid input.
   *
   * @param mode BlendMode used to combine source color and destination
   * @return this
   */
  @NotNull
  @Contract("!null -> this; null -> fail") def setBlendMode(@NotNull mode: BlendMode): Paint = {
    assert(mode != null, "Paint::setBlendMode expected mode != null")
    Stats.onNativeCall()
    Paint._nSetBlendMode(_ptr, mode.ordinal)
    this
  }

  /**
   * @return {@link PathEffect} or null
   * @see <a href="https://fiddle.skia.org/c/@Paint_refPathEffect">https://fiddle.skia.org/c/@Paint_refPathEffect</a>
   */
  @Nullable def getPathEffect: PathEffect = {
    try {
      Stats.onNativeCall()
      val pathEffectPtr = Paint._nGetPathEffect(_ptr)
      if (pathEffectPtr == 0) {
        null
      } else {
        new PathEffect(pathEffectPtr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @param p replace {@link Path} with a modification when drawn
   * @see <a href="https://fiddle.skia.org/c/@Mask_Filter_Methods">https://fiddle.skia.org/c/@Mask_Filter_Methods</a>
   * @see <a href="https://fiddle.skia.org/c/@Paint_setPathEffect">https://fiddle.skia.org/c/@Paint_setPathEffect</a>
   */
  @NotNull
  @Contract("_ -> this") def setPathEffect(@Nullable p: PathEffect): Paint = {
    try {
      Stats.onNativeCall()
      Paint._nSetPathEffect(_ptr, Native.getPtr(p))
      this
    } finally {
      ReferenceUtil.reachabilityFence(p)
    }
  }

  /**
   * @return {@link MaskFilter} if previously set, null otherwise
   * @see <a href="https://fiddle.skia.org/c/@Paint_refMaskFilter">https://fiddle.skia.org/c/@Paint_refMaskFilter</a>
   */
  @Nullable def getMaskFilter: MaskFilter = {
    try {
      Stats.onNativeCall()
      val maskFilterPtr = Paint._nGetMaskFilter(_ptr)
      if (maskFilterPtr == 0) {
        null
      } else {
        new MaskFilter(maskFilterPtr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @param maskFilter modifies clipping mask generated from drawn geometry
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Paint_setMaskFilter">https://fiddle.skia.org/c/@Paint_setMaskFilter</a>
   * @see <a href="https://fiddle.skia.org/c/@Typeface_Methods">https://fiddle.skia.org/c/@Typeface_Methods</a>
   */
  @NotNull
  @Contract("_ -> this") def setMaskFilter(@Nullable maskFilter: MaskFilter): Paint = {
    try {
      Stats.onNativeCall()
      Paint._nSetMaskFilter(_ptr, Native.getPtr(maskFilter))
      this
    } finally {
      ReferenceUtil.reachabilityFence(maskFilter)
    }
  }

  /**
   * @return {@link ImageFilter} or null
   * @see <a href="https://fiddle.skia.org/c/@Paint_refImageFilter">https://fiddle.skia.org/c/@Paint_refImageFilter</a>
   */
  @Nullable def getImageFilter: ImageFilter = {
    try {
      Stats.onNativeCall()
      val imageFilterPtr = Paint._nGetImageFilter(_ptr)
      if (imageFilterPtr == 0) {
        null
      } else {
        new ImageFilter(imageFilterPtr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @param imageFilter how SkImage is sampled when transformed
   * @see <a href="https://fiddle.skia.org/c/@Draw_Looper_Methods">https://fiddle.skia.org/c/@Draw_Looper_Methods</a>
   * @see <a href="https://fiddle.skia.org/c/@Paint_setImageFilter">https://fiddle.skia.org/c/@Paint_setImageFilter</a>
   */
  @NotNull
  @Contract("_ -> this") def setImageFilter(@Nullable imageFilter: ImageFilter): Paint = {
    try {
      Stats.onNativeCall()
      Paint._nSetImageFilter(_ptr, Native.getPtr(imageFilter))
      this
    } finally {
      ReferenceUtil.reachabilityFence(imageFilter)
    }
  }

  /**
   * <p>Returns true if Paint prevents all drawing;
   * otherwise, the Paint may or may not allow drawing.</p>
   *
   * <p>Returns true if, for example, BlendMode combined with alpha computes a
   * new alpha of zero.</p>
   *
   * @return true if Paint prevents all drawing
   * @see <a href="https://fiddle.skia.org/c/@Paint_nothingToDraw">https://fiddle.skia.org/c/@Paint_nothingToDraw</a>
   */
  def hasNothingToDraw: Boolean = {
    try {
      Stats.onNativeCall()
      Paint._nHasNothingToDraw(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}