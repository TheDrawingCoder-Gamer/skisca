package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object Canvas {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  /**
   * <p>Constructs a canvas that draws into bitmap.
   * Use props to match the device characteristics, like LCD striping.</p>
   *
   * <p>Bitmap is copied so that subsequently editing bitmap will not affect
   * constructed Canvas.</p>
   *
   * @param bitmap       width, height, ColorType, ColorAlphaType, and pixel
   *                     storage of raster surface
   * @param surfaceProps order and orientation of RGB striping; and whether to use
   *                     device independent fonts
   * @see <a href="https://fiddle.skia.org/c/@Canvas_const_SkBitmap_const_SkSurfaceProps">https://fiddle.skia.org/c/@Canvas_const_SkBitmap_const_SkSurfaceProps</a>
   */
  def apply(@NotNull bitmap: Bitmap, @NotNull surfaceProps: SurfaceProps = SurfaceProps()): Canvas = {
    val canvas = new Canvas(Canvas
      ._nMakeFromBitmap(bitmap._ptr, surfaceProps._getFlags, surfaceProps.pixelGeometry.ordinal), true, bitmap)
    Stats.onNativeCall()
    ReferenceUtil.reachabilityFence(bitmap)
    canvas
  }
  @native def _nGetFinalizer: Long

  @native def _nMakeFromBitmap(bitmapPtr: Long, flags: Int, pixelGeometry: Int): Long

  @native def _nGetBaseProps(ptr: Long): SurfaceProps

  @native def _nGetTopProps(ptr: Long): SurfaceProps

  @native def _nGetSurface(ptr: Long): Long

  @native def _nDrawPoint(ptr: Long, x: Float, y: Float, paintPtr: Long): Unit

  @native def _nDrawPoints(ptr: Long, mode: Int, coords: Array[Float], paintPtr: Long): Unit

  @native def _nDrawLine(ptr: Long, x0: Float, y0: Float, x1: Float, y1: Float, paintPtr: Long): Unit

  @native def _nDrawArc(ptr: Long, left: Float, top: Float, right: Float, bottom: Float, startAngle: Float, sweepAngle: Float, includeCenter: Boolean, paintPtr: Long): Unit

  @native def _nDrawRect(ptr: Long, left: Float, top: Float, right: Float, bottom: Float, paintPtr: Long): Unit

  @native def _nDrawOval(ptr: Long, left: Float, top: Float, right: Float, bottom: Float, paint: Long): Unit

  @native def _nDrawRRect(ptr: Long, left: Float, top: Float, right: Float, bottom: Float, radii: Array[Float], paintPtr: Long): Unit

  @native def _nQuickReject(ptr: Long, left: Float, top: Float, right: Float, bottom: Float): Boolean

  @native def _nQuickRejectPath(ptr: Long, pathPtr: Long): Boolean

  @native def _nDrawDRRect(ptr: Long, ol: Float, ot: Float, or: Float, ob: Float, oradii: Array[Float], il: Float, it: Float, ir: Float, ib: Float, iradii: Array[Float], paintPtr: Long): Unit

  @native def _nDrawPath(ptr: Long, nativePath: Long, paintPtr: Long): Unit

  @native def _nDrawImageRect(ptr: Long, nativeImage: Long, sl: Float, st: Float, sr: Float, sb: Float, dl: Float, dt: Float, dr: Float, db: Float, samplingMode: Long, paintPtr: Long, strict: Boolean): Unit

  @native def _nDrawImageNine(ptr: Long, nativeImage: Long, cl: Int, ct: Int, cr: Int, cb: Int, dl: Float, dt: Float, dr: Float, db: Float, filterMode: Int, paintPtr: Long): Unit

  @native def _nDrawRegion(ptr: Long, nativeRegion: Long, paintPtr: Long): Unit

  @native def _nDrawString(ptr: Long, string: String, x: Float, y: Float, font: Long, paint: Long): Unit

  @native def _nDrawTextBlob(ptr: Long, blob: Long, x: Float, y: Float, paint: Long): Unit

  @native def _nDrawPicture(ptr: Long, picturePtr: Long, matrix: Array[Float], paintPtr: Long): Unit

  @native def _nDrawVertices(ptr: Long, verticesMode: Int, cubics: Array[Float], colors: Array[Int], texCoords: Array[Float], indices: Array[Short], blendMode: Int, paintPtr: Long): Unit

  @native def _nDrawPatch(ptr: Long, cubics: Array[Float], colors: Array[Int], texCoords: Array[Float], blendMode: Int, paintPtr: Long): Unit

  @native def _nDrawDrawable(ptr: Long, drawablePrt: Long, matrix: Array[Float]): Unit

  @native def _nDrawColor(ptr: Long, color: Int, blendMode: Int): Unit

  @native def _nDrawColor4f(ptr: Long, r: Float, g: Float, b: Float, a: Float, blendMode: Int): Unit

  @native def _nClear(ptr: Long, color: Int): Unit

  @native def _nDrawPaint(ptr: Long, paintPtr: Long): Unit

  @native def _nSetMatrix(ptr: Long, matrix: Array[Float]): Unit

  @native def _nSetMatrix44(ptr: Long, matrix: Array[Float]): Unit

  @native def _nGetLocalToDevice(ptr: Long): Array[Float]

  @native def _nResetMatrix(ptr: Long): Unit

  @native def _nClipRect(ptr: Long, left: Float, top: Float, right: Float, bottom: Float, mode: Int, antiAlias: Boolean): Unit

  @native def _nClipRRect(ptr: Long, left: Float, top: Float, right: Float, bottom: Float, radii: Array[Float], mode: Int, antiAlias: Boolean): Unit

  @native def _nClipPath(ptr: Long, nativePath: Long, mode: Int, antiAlias: Boolean): Unit

  @native def _nClipRegion(ptr: Long, nativeRegion: Long, mode: Int): Unit

  @native def _nConcat(ptr: Long, matrix: Array[Float]): Unit

  @native def _nConcat44(ptr: Long, matrix: Array[Float]): Unit

  @native def _nReadPixels(ptr: Long, bitmapPtr: Long, srcX: Int, srcY: Int): Boolean

  @native def _nWritePixels(ptr: Long, bitmapPtr: Long, x: Int, y: Int): Boolean

  @native def _nSave(ptr: Long): Int

  @native def _nSaveLayer(ptr: Long, paintPtr: Long): Int

  @native def _nSaveLayerRect(ptr: Long, left: Float, top: Float, right: Float, bottom: Float, paintPtr: Long): Int

  @native def _nSaveLayerAlpha(ptr: Long, alpha: Int): Int

  @native def _nSaveLayerAlphaRect(ptr: Long, left: Float, top: Float, right: Float, bottom: Float, alpha: Int): Int

  @native def _nSaveLayerRec(ptr: Long, paintPtr: Long, backdropPtr: Long, flags: Int): Int

  @native def _nSaveLayerRecRect(ptr: Long, left: Float, top: Float, right: Float, bottom: Float, paintPtr: Long, backdropPtr: Long, flags: Int): Int

  @native def _nGetSaveCount(ptr: Long): Int

  @native def _nRestore(ptr: Long): Unit

  @native def _nRestoreToCount(ptr: Long, saveCount: Int): Unit

  try Library.staticLoad()
}

class Canvas @ApiStatus.Internal(ptr: Long, managed: Boolean, @ApiStatus.Internal val _owner: AnyRef) extends Managed(ptr, Canvas
  ._FinalizerHolder.PTR, managed) {


  /**
   * Returns the SurfaceProps associated with the canvas (i.e., at the base of the layer
   * stack).
   *
   * @return SurfaceProps
   */
  @NotNull def getBaseProps: SurfaceProps = {
    try {
      assert(_ptr ne 0, "Canvas is closed")
      Stats.onNativeCall()
      Canvas._nGetBaseProps(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns the SurfaceProps associated with the canvas that are currently active (i.e., at
   * the top of the layer stack). This can differ from {getBaseProps()} depending on the flags
   * passed to saveLayer.
   *
   * @return SurfaceProps active in the current/top layer
   */
  @NotNull def getTopProps: SurfaceProps = {
    try {
      assert(_ptr ne 0, "Canvas is closed")
      Stats.onNativeCall()
      Canvas._nGetTopProps(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @Nullable def getSurface: Surface = {
    try {
      assert(_ptr ne 0, "Canvas is closed")
      Stats.onNativeCall()
      val ptr = Canvas._nGetSurface(_ptr)
      if (ptr == 0) {
        null
      } else {
        new Surface(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull
  @Contract("_, _, _ -> this") def drawPoint(x: Float, y: Float, @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(paint != null, "Can’t drawPoint with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawPoint(_ptr, x, y, Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  /**
   * <p>Draws pts using clip, Matrix and Paint paint.</p>
   *
   * <p>The shape of point drawn depends on paint
   * PaintStrokeCap. If paint is set to {@link PaintStrokeCap# ROUND}, each point draws a
   * circle of diameter Paint stroke width. If paint is set to {@link PaintStrokeCap# SQUARE}
   * or {@link PaintStrokeCap# BUTT}, each point draws a square of width and height
   * Paint stroke width.</p>
   *
   * <p>Each line segment respects paint PaintStrokeCap and Paint stroke width.
   * PaintMode is ignored, as if were set to {@link PaintMode# STROKE}.</p>
   *
   * <p>Always draws each element one at a time; is not affected by
   * PaintStrokeJoin, and unlike drawPath(), does not create a mask from all points
   * and lines before drawing.</p>
   *
   * @param coords array of points to draw
   * @param paint  stroke, blend, color, and so on, used to draw
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawPoints">https://fiddle.skia.org/c/@Canvas_drawPoints</a>
   */
  @NotNull
  @Contract("_, _ -> this") def drawPoints(@NotNull coords: Array[Point], @NotNull paint: Paint): Canvas = {
    drawPoints(Point
      .flattenArray(coords), paint)
  }

  /**
   * <p>Draws pts using clip, Matrix and Paint paint.</p>
   *
   * <p>The shape of point drawn depends on paint
   * PaintStrokeCap. If paint is set to {@link PaintStrokeCap# ROUND}, each point draws a
   * circle of diameter Paint stroke width. If paint is set to {@link PaintStrokeCap# SQUARE}
   * or {@link PaintStrokeCap# BUTT}, each point draws a square of width and height
   * Paint stroke width.</p>
   *
   * <p>Each line segment respects paint PaintStrokeCap and Paint stroke width.
   * PaintMode is ignored, as if were set to {@link PaintMode# STROKE}.</p>
   *
   * <p>Always draws each element one at a time; is not affected by
   * PaintStrokeJoin, and unlike drawPath(), does not create a mask from all points
   * and lines before drawing.</p>
   *
   * @param coords array of points to draw
   * @param paint  stroke, blend, color, and so on, used to draw
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawPoints">https://fiddle.skia.org/c/@Canvas_drawPoints</a>
   */
  @NotNull
  @Contract("_, _ -> this") def drawPoints(@NotNull coords: Array[Float], @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(coords != null, "Can’t drawPoints with coords == null")
    assert(paint != null, "Can’t drawPoints with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawPoints(_ptr, 0 /* SkCanvas::PointMode::kPoints_PointMode */ , coords, Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  /**
   * <p>Draws pts using clip, Matrix and Paint paint.</p>
   *
   * <p>Each pair of points draws a line segment.
   * One line is drawn for every two points; each point is used once. If count is odd,
   * the final point is ignored.</p>
   *
   * <p>Each line segment respects paint PaintStrokeCap and Paint stroke width.
   * PaintMode is ignored, as if were set to {@link PaintMode# STROKE}.</p>
   *
   * <p>Always draws each element one at a time; is not affected by
   * PaintStrokeJoin, and unlike drawPath(), does not create a mask from all points
   * and lines before drawing.</p>
   *
   * @param coords array of points to draw
   * @param paint  stroke, blend, color, and so on, used to draw
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawPoints">https://fiddle.skia.org/c/@Canvas_drawPoints</a>
   */
  @NotNull
  @Contract("_, _ -> this") def drawLines(@NotNull coords: Array[Point], @NotNull paint: Paint): Canvas = {
    drawLines(Point
      .flattenArray(coords), paint)
  }

  /**
   * <p>Draws pts using clip, Matrix and Paint paint.</p>
   *
   * <p>Each pair of points draws a line segment.
   * One line is drawn for every two points; each point is used once. If count is odd,
   * the final point is ignored.</p>
   *
   * <p>Each line segment respects paint PaintStrokeCap and Paint stroke width.
   * PaintMode is ignored, as if were set to {@link PaintMode# STROKE}.</p>
   *
   * <p>Always draws each element one at a time; is not affected by
   * PaintStrokeJoin, and unlike drawPath(), does not create a mask from all points
   * and lines before drawing.</p>
   *
   * @param coords array of points to draw
   * @param paint  stroke, blend, color, and so on, used to draw
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawPoints">https://fiddle.skia.org/c/@Canvas_drawPoints</a>
   */
  @NotNull
  @Contract("_, _ -> this") def drawLines(@NotNull coords: Array[Float], @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(coords != null, "Can’t drawLines with coords == null")
    assert(paint != null, "Can’t drawLines with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawPoints(_ptr, 1 /* SkCanvas::PointMode::kLines_PointMode */ , coords, Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  /**
   * <p>Draws pts using clip, Matrix and Paint paint.</p>
   *
   * <p>Each adjacent pair of points draws a line segment.
   * count minus one lines are drawn; the first and last point are used once.</p>
   *
   * <p>Each line segment respects paint PaintStrokeCap and Paint stroke width.
   * PaintMode is ignored, as if were set to {@link PaintMode# STROKE}.</p>
   *
   * <p>Always draws each element one at a time; is not affected by
   * PaintStrokeJoin, and unlike drawPath(), does not create a mask from all points
   * and lines before drawing.</p>
   *
   * @param coords array of points to draw
   * @param paint  stroke, blend, color, and so on, used to draw
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawPoints">https://fiddle.skia.org/c/@Canvas_drawPoints</a>
   */
  @NotNull
  @Contract("_, _ -> this") def drawPolygon(@NotNull coords: Array[Point], @NotNull paint: Paint): Canvas = {
    drawPolygon(Point
      .flattenArray(coords), paint)
  }

  /**
   * <p>Draws pts using clip, Matrix and Paint paint.</p>
   *
   * <p>Each adjacent pair of points draws a line segment.
   * count minus one lines are drawn; the first and last point are used once.</p>
   *
   * <p>Each line segment respects paint PaintStrokeCap and Paint stroke width.
   * PaintMode is ignored, as if were set to {@link PaintMode# STROKE}.</p>
   *
   * <p>Always draws each element one at a time; is not affected by
   * PaintStrokeJoin, and unlike drawPath(), does not create a mask from all points
   * and lines before drawing.</p>
   *
   * @param coords array of points to draw
   * @param paint  stroke, blend, color, and so on, used to draw
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawPoints">https://fiddle.skia.org/c/@Canvas_drawPoints</a>
   */
  @NotNull
  @Contract("_, _ -> this") def drawPolygon(@NotNull coords: Array[Float], @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(coords != null, "Can’t drawPolygon with coords == null")
    assert(paint != null, "Can’t drawPolygon with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawPoints(_ptr, 2 /* SkCanvas::PointMode::kPolygon_PointMode */ , coords, Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  @NotNull
  @Contract("_, _, _, _, _ -> this") def drawLine(x0: Float, y0: Float, x1: Float, y1: Float, @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(paint != null, "Can’t drawLine with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawLine(_ptr, x0, y0, x1, y1, Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  @NotNull
  @Contract("_, _, _, _, _, _, _, _ -> this") def drawArc(left: Float, top: Float, right: Float, bottom: Float, startAngle: Float, sweepAngle: Float, includeCenter: Boolean, @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(paint != null, "Can’t drawArc with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawArc(_ptr, left, top, right, bottom, startAngle, sweepAngle, includeCenter, Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  @NotNull
  @Contract("_, _ -> this") def drawRect(@NotNull r: Rect, @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(r != null, "Can’t drawRect with r == null")
    assert(paint != null, "Can’t drawRect with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawRect(_ptr, r._left, r._top, r._right, r._bottom, Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  @NotNull
  @Contract("_, _ -> this") def drawOval(@NotNull r: Rect, @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(r != null, "Can’t drawOval with r == null")
    assert(paint != null, "Can’t drawOval with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawOval(_ptr, r._left, r._top, r._right, r._bottom, Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  @NotNull
  @Contract("_, _, _, _ -> this") def drawCircle(x: Float, y: Float, radius: Float, @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(paint != null, "Can’t drawCircle with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawOval(_ptr, x - radius, y - radius, x + radius, y + radius, Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  @NotNull
  @Contract("_, _ -> this") def drawRRect(@NotNull r: RRect, @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(r != null, "Can’t drawRRect with r == null")
    assert(paint != null, "Can’t drawRRect with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawRRect(_ptr, r._left, r._top, r._right, r._bottom, r._radii, Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  @NotNull
  @Contract("_ -> this") def quickReject(@NotNull r: Rect): Boolean = {
    try {
      assert(_ptr ne 0, "Canvas is closed")
      assert(r != null, "Can’t quickReject with r == null")
      Stats.onNativeCall()
      Canvas._nQuickReject(_ptr, r._left, r._top, r._right, r._bottom)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull
  @Contract("_ -> this") def quickReject(@NotNull path: Path): Boolean = {
    try {
      assert(_ptr ne 0, "Canvas is closed")
      assert(path != null, "Can’t quickReject with path == null")
      Stats.onNativeCall()
      Canvas._nQuickRejectPath(_ptr, Native.getPtr(path))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(path)
    }
  }

  @NotNull
  @Contract("_, _, _ -> this") def drawDRRect(@NotNull outer: RRect, @NotNull inner: RRect, @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(outer != null, "Can’t drawDRRect with outer == null")
    assert(inner != null, "Can’t drawDRRect with inner == null")
    assert(paint != null, "Can’t drawDRRect with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawDRRect(_ptr, outer._left, outer._top, outer._right, outer._bottom, outer._radii, inner._left, inner
      ._top, inner._right, inner._bottom, inner._radii, Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  @NotNull
  @Contract("_, _ -> this") def drawRectShadow(@NotNull r: Rect, dx: Float, dy: Float, blur: Float, color: Int): Canvas = {
    drawRectShadow(r, dx, dy, blur, 0f, color)
  }

  @NotNull
  @Contract("_, _ -> this") def drawRectShadow(@NotNull r: Rect, dx: Float, dy: Float, blur: Float, spread: Float, color: Int): Canvas = {
    assert(r != null, "Can’t drawRectShadow with r == null")
    val insides = r.inflate(-1)
    if (!insides.isEmpty) {
      save
      if (insides.isInstanceOf[RRect]) {
        clipRRect(insides.asInstanceOf[RRect], ClipMode.DIFFERENCE)
      } else {
        clipRect(insides, ClipMode.DIFFERENCE)
      }
      drawRectShadowNoclip(r, dx, dy, blur, spread, color)
      restore
    }
    else {
      drawRectShadowNoclip(r, dx, dy, blur, spread, color)
    }
    this
  }

  @NotNull
  @Contract("_, _ -> this") def drawRectShadowNoclip(@NotNull r: Rect, dx: Float, dy: Float, blur: Float, spread: Float, color: Int): Canvas = {
    assert(r != null, "Can’t drawRectShadow with r == null")
    val outline = r.inflate(spread)
    try {
      val f = ImageFilter.makeDropShadowOnly(dx, dy, blur / 2f, blur / 2f, color)
      val p = Paint()
      try {
        p.setImageFilter(f)
        if (outline.isInstanceOf[RRect]) {
          drawRRect(outline.asInstanceOf[RRect], p)
        } else {
          drawRect(outline, p)
        }
      } finally {
        if (f != null) f.close()
        if (p != null) p.close()
      }
    }
    this
  }

  @NotNull
  @Contract("_, _ -> this") def drawPath(@NotNull path: Path, @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(path != null, "Can’t drawPath with path == null")
    assert(paint != null, "Can’t drawPath with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawPath(_ptr, Native.getPtr(path), Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(path)
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  @NotNull
  @Contract("_, _, _ -> this") def drawImage(@NotNull image: Image, left: Float, top: Float): Canvas = {
    drawImageRect(image, Rect
      .makeWH(image.getWidth, image.getHeight), Rect.makeXYWH(left, top, image.getWidth, image.getHeight), SamplingMode
      .DEFAULT, null, true)
  }

  @NotNull
  @Contract("_, _, _, _ -> this") def drawImage(@NotNull image: Image, left: Float, top: Float, @Nullable paint: Paint): Canvas = {
    drawImageRect(image, Rect
      .makeWH(image.getWidth, image.getHeight), Rect.makeXYWH(left, top, image.getWidth, image.getHeight), SamplingMode
      .DEFAULT, paint, true)
  }

  @NotNull
  @Contract("_, _ -> this") def drawImageRect(@NotNull image: Image, @NotNull dst: Rect): Canvas = {
    drawImageRect(image, Rect
      .makeWH(image.getWidth, image.getHeight), dst, SamplingMode.DEFAULT, null, true)
  }

  @NotNull
  @Contract("_, _, _ -> this") def drawImageRect(@NotNull image: Image, @NotNull dst: Rect, @Nullable paint: Paint): Canvas = {
    drawImageRect(image, Rect
      .makeWH(image.getWidth, image.getHeight), dst, SamplingMode.DEFAULT, paint, true)
  }

  @NotNull
  @Contract("_, _, _, _ -> this") def drawImageRect(@NotNull image: Image, @NotNull src: Rect, @NotNull dst: Rect): Canvas = {
    drawImageRect(image, src, dst, SamplingMode
      .DEFAULT, null, true)
  }

  @NotNull
  @Contract("_, _, _, _ -> this") def drawImageRect(@NotNull image: Image, @NotNull src: Rect, @NotNull dst: Rect, @Nullable paint: Paint): Canvas = {
    drawImageRect(image, src, dst, SamplingMode
      .DEFAULT, paint, true)
  }

  @NotNull
  @Contract("_, _, _, _, _ -> this") def drawImageRect(@NotNull image: Image, @NotNull src: Rect, @NotNull dst: Rect, @Nullable paint: Paint, strict: Boolean): Canvas = {
    drawImageRect(image, src, dst, SamplingMode
      .DEFAULT, paint, strict)
  }

  @NotNull
  @Contract("_, _, _, _, _ -> this") def drawImageRect(@NotNull image: Image, @NotNull src: Rect, @NotNull dst: Rect, @NotNull samplingMode: SamplingMode, @Nullable paint: Paint, strict: Boolean): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(image != null, "Can’t drawImageRect with image == null")
    assert(src != null, "Can’t drawImageRect with src == null")
    assert(dst != null, "Can’t drawImageRect with dst == null")
    assert(samplingMode != null, "Can’t drawImageRect with samplingMode == null")
    Stats.onNativeCall()
    Canvas._nDrawImageRect(_ptr, Native.getPtr(image), src._left, src._top, src._right, src._bottom, dst._left, dst
      ._top, dst._right, dst._bottom, samplingMode.packed, Native.getPtr(paint), strict)
    ReferenceUtil.reachabilityFence(image)
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  @NotNull
  @Contract("_, _, _, _, _ -> this") def drawImageNine(@NotNull image: Image, @NotNull center: IRect, @NotNull dst: Rect, @NotNull filterMode: FilterMode, @Nullable paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(image != null, "Can’t drawImageNine with image == null")
    assert(center != null, "Can’t drawImageNine with center == null")
    assert(dst != null, "Can’t drawImageNine with dst == null")
    assert(filterMode != null, "Can’t drawImageNine with filterMode == null")
    Stats.onNativeCall()
    Canvas._nDrawImageNine(_ptr, Native.getPtr(image), center._left, center._top, center._right, center._bottom, dst
      ._left, dst._top, dst._right, dst._bottom, filterMode.ordinal, Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(image)
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  @NotNull
  @Contract("_, _ -> this") def drawRegion(@NotNull r: Region, @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(r != null, "Can’t drawRegion with r == null")
    assert(paint != null, "Can’t drawRegion with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawRegion(_ptr, Native.getPtr(r), Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(r)
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  @NotNull
  @Contract("_, _, _, _, _ -> this") def drawString(@NotNull s: String, x: Float, y: Float, font: Font, @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(s != null, "Can’t drawString with s == null")
    assert(paint != null, "Can’t drawString with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawString(_ptr, s, x, y, Native.getPtr(font), Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(font)
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  @NotNull
  @Contract("_, _, _, _ -> this") def drawTextBlob(@NotNull blob: TextBlob, x: Float, y: Float, @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(blob != null, "Can’t drawTextBlob with blob == null")
    assert(paint != null, "Can’t drawTextBlob with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawTextBlob(_ptr, Native.getPtr(blob), x, y, Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(blob)
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  @NotNull
  @Contract("_, _, _, _ -> this") def drawTextLine(@NotNull line: TextLine, x: Float, y: Float, @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(line != null, "Can’t drawTextLine with line == null")
    assert(paint != null, "Can’t drawTextLine with paint == null")
    try {
      val blob = line.getTextBlob
      try {
        if (blob != null) drawTextBlob(blob, x, y, paint)
      } finally {
        if (blob != null) blob.close()
      }
    }
    this
  }

  @NotNull
  @Contract("_ -> this") def drawPicture(@NotNull picture: Picture): Canvas = {
    drawPicture(picture, null, null)
  }

  @NotNull
  @Contract("_, _, _ -> this") def drawPicture(@NotNull picture: Picture, @Nullable matrix: Matrix33, @Nullable paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(picture != null, "Can’t drawPicture with picture == null")
    Stats.onNativeCall()
    Canvas._nDrawPicture(_ptr, Native.getPtr(picture), if (matrix == null) {
      null
    } else {
      matrix.mat
    }, Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(picture)
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  /**
   * <p>Draws a triangle mesh, using clip and Matrix.</p>
   *
   * <p>If paint contains an Shader, the shader is mapped using the vertices' positions.</p>
   *
   * <p>If vertices colors are defined in vertices, and Paint paint contains Shader,
   * BlendMode mode combines vertices colors with Shader.</p>
   *
   * @param positions triangle mesh to draw
   * @param colors    color array, one for each corner; may be null
   * @param paint     specifies the Shader, used as Vertices texture
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawVertices">https://fiddle.skia.org/c/@Canvas_drawVertices</a>
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawVertices_2">https://fiddle.skia.org/c/@Canvas_drawVertices_2</a>
   */
  @NotNull
  @Contract("_, _, _ -> this") def drawTriangles(@NotNull positions: Array[Point], @Nullable colors: Array[Int], @NotNull paint: Paint): Canvas = {
    drawTriangles(positions, colors, null, null, BlendMode
      .MODULATE, paint)
  }

  /**
   * <p>Draws a triangle mesh, using clip and Matrix.</p>
   *
   * <p>If paint contains an Shader and vertices does not contain texCoords, the shader
   * is mapped using the vertices' positions.</p>
   *
   * <p>If vertices colors are defined in vertices, and Paint paint contains Shader,
   * BlendMode mode combines vertices colors with Shader.</p>
   *
   * @param positions triangle mesh to draw
   * @param colors    color array, one for each corner; may be null
   * @param texCoords Point array of texture coordinates, mapping Shader to corners; may be null
   * @param indices   with which indices points should be drawn; may be null
   * @param paint     specifies the Shader, used as Vertices texture
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawVertices">https://fiddle.skia.org/c/@Canvas_drawVertices</a>
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawVertices_2">https://fiddle.skia.org/c/@Canvas_drawVertices_2</a>
   */
  @NotNull
  @Contract("_, _, _, _, _ -> this") def drawTriangles(@NotNull positions: Array[Point], @Nullable colors: Array[Int], @Nullable texCoords: Array[Point], @Nullable indices: Array[Short], @NotNull paint: Paint): Canvas = {
    drawTriangles(positions, colors, texCoords, indices, BlendMode
      .MODULATE, paint)
  }

  /**
   * <p>Draws a triangle mesh, using clip and Matrix.</p>
   *
   * <p>If paint contains an Shader and vertices does not contain texCoords, the shader
   * is mapped using the vertices' positions.</p>
   *
   * <p>If vertices colors are defined in vertices, and Paint paint contains Shader,
   * BlendMode mode combines vertices colors with Shader.</p>
   *
   * @param positions triangle mesh to draw
   * @param colors    color array, one for each corner; may be null
   * @param texCoords Point array of texture coordinates, mapping Shader to corners; may be null
   * @param indices   with which indices points should be drawn; may be null
   * @param mode      combines vertices colors with Shader, if both are present
   * @param paint     specifies the Shader, used as Vertices texture
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawVertices">https://fiddle.skia.org/c/@Canvas_drawVertices</a>
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawVertices_2">https://fiddle.skia.org/c/@Canvas_drawVertices_2</a>
   */
  @NotNull
  @Contract("_, _, _, _, _, _ -> this") def drawTriangles(@NotNull positions: Array[Point], @Nullable colors: Array[Int], @Nullable texCoords: Array[Point], @Nullable indices: Array[Short], @NotNull mode: BlendMode, @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(positions != null, "Can’t drawTriangles with positions == null")
    assert(positions.length % 3 == 0, "Expected positions.length % 3 == 0, got: " + positions.length)
    assert(colors == null || colors.length == positions
      .length, "Expected colors.length == positions.length, got: " + colors.length + " != " + positions.length)
    assert(texCoords == null || texCoords.length == positions
      .length, "Expected texCoords.length == positions.length, got: " + texCoords.length + " != " + positions.length)
    assert(paint != null, "Can’t drawTriangles with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawVertices(_ptr, 0 /* kTriangles_VertexMode */ , Point.flattenArray(positions), colors, Point
      .flattenArray(texCoords), indices, mode.ordinal, Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  /**
   * <p>Draws a triangle strip mesh, using clip and Matrix.</p>
   *
   * <p>If paint contains an Shader, the shader is mapped using the vertices' positions.</p>
   *
   * <p>If vertices colors are defined in vertices, and Paint paint contains Shader,
   * BlendMode mode combines vertices colors with Shader.</p>
   *
   * @param positions triangle mesh to draw
   * @param colors    color array, one for each corner; may be null
   * @param paint     specifies the Shader, used as Vertices texture
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawVertices">https://fiddle.skia.org/c/@Canvas_drawVertices</a>
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawVertices_2">https://fiddle.skia.org/c/@Canvas_drawVertices_2</a>
   */
  @NotNull
  @Contract("_, _, _ -> this") def drawTriangleStrip(@NotNull positions: Array[Point], @Nullable colors: Array[Int], @NotNull paint: Paint): Canvas = {
    drawTriangleStrip(positions, colors, null, null, BlendMode
      .MODULATE, paint)
  }

  /**
   * <p>Draws a triangle strip mesh, using clip and Matrix.</p>
   *
   * <p>If paint contains an Shader and vertices does not contain texCoords, the shader
   * is mapped using the vertices' positions.</p>
   *
   * <p>If vertices colors are defined in vertices, and Paint paint contains Shader,
   * BlendMode mode combines vertices colors with Shader.</p>
   *
   * @param positions triangle mesh to draw
   * @param colors    color array, one for each corner; may be null
   * @param texCoords Point array of texture coordinates, mapping Shader to corners; may be null
   * @param indices   with which indices points should be drawn; may be null
   * @param paint     specifies the Shader, used as Vertices texture
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawVertices">https://fiddle.skia.org/c/@Canvas_drawVertices</a>
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawVertices_2">https://fiddle.skia.org/c/@Canvas_drawVertices_2</a>
   */
  @NotNull
  @Contract("_, _, _, _, _ -> this") def drawTriangleStrip(@NotNull positions: Array[Point], @Nullable colors: Array[Int], @Nullable texCoords: Array[Point], @Nullable indices: Array[Short], @NotNull paint: Paint): Canvas = {
    drawTriangleStrip(positions, colors, texCoords, indices, BlendMode
      .MODULATE, paint)
  }

  /**
   * <p>Draws a triangle strip mesh, using clip and Matrix.</p>
   *
   * <p>If paint contains an Shader and vertices does not contain texCoords, the shader
   * is mapped using the vertices' positions.</p>
   *
   * <p>If vertices colors are defined in vertices, and Paint paint contains Shader,
   * BlendMode mode combines vertices colors with Shader.</p>
   *
   * @param positions triangle mesh to draw
   * @param colors    color array, one for each corner; may be null
   * @param texCoords Point array of texture coordinates, mapping Shader to corners; may be null
   * @param indices   with which indices points should be drawn; may be null
   * @param mode      combines vertices colors with Shader, if both are present
   * @param paint     specifies the Shader, used as Vertices texture
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawVertices">https://fiddle.skia.org/c/@Canvas_drawVertices</a>
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawVertices_2">https://fiddle.skia.org/c/@Canvas_drawVertices_2</a>
   */
  @NotNull
  @Contract("_, _, _, _, _, _ -> this") def drawTriangleStrip(@NotNull positions: Array[Point], @Nullable colors: Array[Int], @Nullable texCoords: Array[Point], @Nullable indices: Array[Short], @NotNull mode: BlendMode, @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(positions != null, "Can’t drawTriangleStrip with positions == null")
    assert(colors == null || colors.length == positions
      .length, "Expected colors.length == positions.length, got: " + colors.length + " != " + positions.length)
    assert(texCoords == null || texCoords.length == positions
      .length, "Expected texCoords.length == positions.length, got: " + texCoords.length + " != " + positions.length)
    assert(mode != null, "Can’t drawTriangles with mode == null")
    assert(paint != null, "Can’t drawTriangles with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawVertices(_ptr, 1 /* kTriangleStrip_VertexMode */ , Point.flattenArray(positions), colors, Point
      .flattenArray(texCoords), indices, mode.ordinal, Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  /**
   * <p>Draws a triangle fan mesh, using clip and Matrix.</p>
   *
   * <p>If paint contains an Shader, the shader is mapped using the vertices' positions.</p>
   *
   * <p>If vertices colors are defined in vertices, and Paint paint contains Shader,
   * BlendMode mode combines vertices colors with Shader.</p>
   *
   * @param positions triangle mesh to draw
   * @param colors    color array, one for each corner; may be null
   * @param paint     specifies the Shader, used as Vertices texture
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawVertices">https://fiddle.skia.org/c/@Canvas_drawVertices</a>
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawVertices_2">https://fiddle.skia.org/c/@Canvas_drawVertices_2</a>
   */
  @NotNull
  @Contract("_, _, _ -> this") def drawTriangleFan(@NotNull positions: Array[Point], @Nullable colors: Array[Int], @NotNull paint: Paint): Canvas = {
    drawTriangleFan(positions, colors, null, null, BlendMode
      .MODULATE, paint)
  }

  /**
   * <p>Draws a triangle fan mesh, using clip and Matrix.</p>
   *
   * <p>If paint contains an Shader and vertices does not contain texCoords, the shader
   * is mapped using the vertices' positions.</p>
   *
   * <p>If vertices colors are defined in vertices, and Paint paint contains Shader,
   * BlendMode mode combines vertices colors with Shader.</p>
   *
   * @param positions triangle mesh to draw
   * @param colors    color array, one for each corner; may be null
   * @param texCoords Point array of texture coordinates, mapping Shader to corners; may be null
   * @param indices   with which indices points should be drawn; may be null
   * @param paint     specifies the Shader, used as Vertices texture
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawVertices">https://fiddle.skia.org/c/@Canvas_drawVertices</a>
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawVertices_2">https://fiddle.skia.org/c/@Canvas_drawVertices_2</a>
   */
  @NotNull
  @Contract("_, _, _, _, _ -> this") def drawTriangleFan(@NotNull positions: Array[Point], @Nullable colors: Array[Int], @Nullable texCoords: Array[Point], @Nullable indices: Array[Short], @NotNull paint: Paint): Canvas = {
    drawTriangleFan(positions, colors, texCoords, indices, BlendMode
      .MODULATE, paint)
  }

  /**
   * <p>Draws a triangle fan mesh, using clip and Matrix.</p>
   *
   * <p>If paint contains an Shader and vertices does not contain texCoords, the shader
   * is mapped using the vertices' positions.</p>
   *
   * <p>If vertices colors are defined in vertices, and Paint paint contains Shader,
   * BlendMode mode combines vertices colors with Shader.</p>
   *
   * @param positions triangle mesh to draw
   * @param colors    color array, one for each corner; may be null
   * @param texCoords Point array of texture coordinates, mapping Shader to corners; may be null
   * @param indices   with which indices points should be drawn; may be null
   * @param mode      combines vertices colors with Shader, if both are present
   * @param paint     specifies the Shader, used as Vertices texture
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawVertices">https://fiddle.skia.org/c/@Canvas_drawVertices</a>
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawVertices_2">https://fiddle.skia.org/c/@Canvas_drawVertices_2</a>
   */
  @NotNull
  @Contract("_, _, _, _, _, _ -> this") def drawTriangleFan(@NotNull positions: Array[Point], @Nullable colors: Array[Int], @Nullable texCoords: Array[Point], @Nullable indices: Array[Short], @NotNull mode: BlendMode, @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(positions != null, "Can’t drawTriangleFan with positions == null")
    assert(colors == null || colors.length == positions
      .length, "Expected colors.length == positions.length, got: " + colors.length + " != " + positions.length)
    assert(texCoords == null || texCoords.length == positions
      .length, "Expected texCoords.length == positions.length, got: " + texCoords.length + " != " + positions.length)
    assert(mode != null, "Can’t drawTriangleFan with mode == null")
    assert(paint != null, "Can’t drawTriangleFan with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawVertices(_ptr, 2 /* kTriangleFan_VertexMode */ , Point.flattenArray(positions), colors, Point
      .flattenArray(texCoords), indices, mode.ordinal, Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  /**
   * <p>Draws a Coons patch: the interpolation of four cubics with shared corners,
   * associating a color, and optionally a texture SkPoint, with each corner.</p>
   *
   * <p>Coons patch uses clip and Matrix, paint Shader, ColorFilter,
   * alpha, ImageFilter, and BlendMode. If Shader is provided it is treated
   * as Coons patch texture.</p>
   *
   * <p>Point array cubics specifies four Path cubic starting at the top-left corner,
   * in clockwise order, sharing every fourth point. The last Path cubic ends at the
   * first point.</p>
   *
   * <p>Color array color associates colors with corners in top-left, top-right,
   * bottom-right, bottom-left order.</p>
   *
   * @param cubics Path cubic array, sharing common points
   * @param colors color array, one for each corner
   * @param paint  Shader, ColorFilter, BlendMode, used to draw
   * @return this
   * @see <a href="https://fiddle.skia.org/c/4cf70f8d194867d053d7e177e5088445">https://fiddle.skia.org/c/4cf70f8d194867d053d7e177e5088445</a>
   */
  @NotNull
  @Contract("_, _, _ -> this") def drawPatch(@NotNull cubics: Array[Point], @NotNull colors: Array[Int], @NotNull paint: Paint): Canvas = {
    drawPatch(cubics, colors, null, paint)
  }

  /**
   * <p>Draws a Coons patch: the interpolation of four cubics with shared corners,
   * associating a color, and optionally a texture SkPoint, with each corner.</p>
   *
   * <p>Coons patch uses clip and Matrix, paint Shader, ColorFilter,
   * alpha, ImageFilter, and BlendMode. If Shader is provided it is treated
   * as Coons patch texture.</p>
   *
   * <p>Point array cubics specifies four Path cubic starting at the top-left corner,
   * in clockwise order, sharing every fourth point. The last Path cubic ends at the
   * first point.</p>
   *
   * <p>Color array color associates colors with corners in top-left, top-right,
   * bottom-right, bottom-left order.</p>
   *
   * <p>If paint contains Shader, Point array texCoords maps Shader as texture to
   * corners in top-left, top-right, bottom-right, bottom-left order. If texCoords is
   * nullptr, Shader is mapped using positions (derived from cubics).</p>
   *
   * @param cubics    Path cubic array, sharing common points
   * @param colors    color array, one for each corner
   * @param texCoords Point array of texture coordinates, mapping Shader to corners;
   *                  may be null
   * @param paint     Shader, ColorFilter, BlendMode, used to draw
   * @return this
   * @see <a href="https://fiddle.skia.org/c/4cf70f8d194867d053d7e177e5088445">https://fiddle.skia.org/c/4cf70f8d194867d053d7e177e5088445</a>
   */
  @NotNull
  @Contract("_, _, _, _ -> this") def drawPatch(@NotNull cubics: Array[Point], @NotNull colors: Array[Int], @Nullable texCoords: Array[Point], @NotNull paint: Paint): Canvas = {
    drawPatch(cubics, colors, texCoords, BlendMode
      .MODULATE, paint)
  }

  /**
   * <p>Draws a Coons patch: the interpolation of four cubics with shared corners,
   * associating a color, and optionally a texture SkPoint, with each corner.</p>
   *
   * <p>Coons patch uses clip and Matrix, paint Shader, ColorFilter,
   * alpha, ImageFilter, and BlendMode. If Shader is provided it is treated
   * as Coons patch texture; BlendMode mode combines color colors and Shader if
   * both are provided.</p>
   *
   * <p>Point array cubics specifies four Path cubic starting at the top-left corner,
   * in clockwise order, sharing every fourth point. The last Path cubic ends at the
   * first point.</p>
   *
   * <p>Color array color associates colors with corners in top-left, top-right,
   * bottom-right, bottom-left order.</p>
   *
   * <p>If paint contains Shader, Point array texCoords maps Shader as texture to
   * corners in top-left, top-right, bottom-right, bottom-left order. If texCoords is
   * nullptr, Shader is mapped using positions (derived from cubics).</p>
   *
   * @param cubics    Path cubic array, sharing common points
   * @param colors    color array, one for each corner
   * @param texCoords Point array of texture coordinates, mapping Shader to corners;
   *                  may be null
   * @param mode      BlendMode for colors, and for Shader if paint has one
   * @param paint     Shader, ColorFilter, BlendMode, used to draw
   * @return this
   * @see <a href="https://fiddle.skia.org/c/4cf70f8d194867d053d7e177e5088445">https://fiddle.skia.org/c/4cf70f8d194867d053d7e177e5088445</a>
   */
  @NotNull
  @Contract("_, _, _, _, _ -> this") def drawPatch(@NotNull cubics: Array[Point], @NotNull colors: Array[Int], @Nullable texCoords: Array[Point], @NotNull mode: BlendMode, @NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(cubics != null, "Can’t drawPatch with cubics == null")
    assert(cubics.length == 12, "Expected cubics.length == 12, got: " + cubics.length)
    assert(colors != null, "Can’t drawPatch with colors == null")
    assert(colors.length == 4, "Expected colors.length == 4, got: " + colors.length)
    assert(texCoords == null || texCoords.length == 4, "Expected texCoords.length == 4, got: " + texCoords.length)
    assert(mode != null, "Can’t drawPatch with mode == null")
    assert(paint != null, "Can’t drawPatch with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawPatch(_ptr, Point.flattenArray(cubics), colors, Point.flattenArray(texCoords), mode.ordinal, Native
      .getPtr(paint))
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  /**
   * <p>Draws Drawable drawable using clip and matrix.</p>
   *
   * <p>If Canvas has an asynchronous implementation, as is the case
   * when it is recording into Picture, then drawable will be referenced,
   * so that Drawable::draw() can be called when the operation is finalized. To force
   * immediate drawing, call Drawable::draw() instead.</p>
   *
   * @param drawable custom struct encapsulating drawing commands
   * @return this
   */
  @NotNull
  @Contract("_ -> this") def drawDrawable(@NotNull drawable: Drawable): Canvas = {
    drawDrawable(drawable, null)
  }

  /**
   * <p>Draws Drawable drawable using clip and matrix, offset by (x, y).</p>
   *
   * <p>If Canvas has an asynchronous implementation, as is the case
   * when it is recording into Picture, then drawable will be referenced,
   * so that Drawable::draw() can be called when the operation is finalized. To force
   * immediate drawing, call Drawable::draw() instead.</p>
   *
   * @param drawable custom struct encapsulating drawing commands
   * @param x        offset into Canvas writable pixels on x-axis
   * @param y        offset into Canvas writable pixels on y-axis
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawDrawable_2">https://fiddle.skia.org/c/@Canvas_drawDrawable_2</a>
   */
  @NotNull
  @Contract("_, _, _ -> this") def drawDrawable(@NotNull drawable: Drawable, x: Float, y: Float): Canvas = {
    drawDrawable(drawable, Matrix33
      .makeTranslate(x, y))
  }

  /**
   * <p>Draws Drawable drawable using clip and matrix, concatenated with
   * optional matrix.</p>
   *
   * <p>If Canvas has an asynchronous implementation, as is the case
   * when it is recording into Picture, then drawable will be referenced,
   * so that Drawable::draw() can be called when the operation is finalized. To force
   * immediate drawing, call Drawable::draw() instead.</p>
   *
   * @param drawable custom struct encapsulating drawing commands
   * @param matrix   transformation applied to drawing; may be null
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Canvas_drawDrawable">https://fiddle.skia.org/c/@Canvas_drawDrawable</a>
   */
  @NotNull
  @Contract("_, _ -> this") def drawDrawable(@NotNull drawable: Drawable, @Nullable matrix: Matrix33): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(drawable != null, "Can’t drawDrawable with drawable == null")
    Stats.onNativeCall()
    Canvas._nDrawDrawable(_ptr, Native.getPtr(drawable), if (matrix == null) {
      null
    } else {
      matrix.mat
    })
    ReferenceUtil.reachabilityFence(drawable)
    this
  }

  @NotNull
  @Contract("_ -> this") def drawColor(color: Int): Canvas = {
    drawColor(color, BlendMode.SRC_OVER)
  }

  @NotNull
  @Contract("_, _ -> this") def drawColor(color: Int, @NotNull mode: BlendMode): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    Stats.onNativeCall()
    Canvas._nDrawColor(_ptr, color, mode.ordinal)
    this
  }

  @NotNull
  @Contract("_ -> this") def drawColor(@NotNull color: Color4f): Canvas = {
    drawColor(color, BlendMode.SRC_OVER)
  }

  @NotNull
  @Contract("_, _ -> this") def drawColor(@NotNull color: Color4f, @NotNull mode: BlendMode): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    Stats.onNativeCall()
    Canvas._nDrawColor4f(_ptr, color.r, color.g, color.b, color.a, mode.ordinal)
    this
  }

  @NotNull
  @Contract("_ -> this") def clear(color: Int): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    Stats.onNativeCall()
    Canvas._nClear(_ptr, color)
    this
  }

  @NotNull
  @Contract("_ -> this") def drawPaint(@NotNull paint: Paint): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(paint != null, "Can’t drawPaint with paint == null")
    Stats.onNativeCall()
    Canvas._nDrawPaint(_ptr, Native.getPtr(paint))
    ReferenceUtil.reachabilityFence(paint)
    this
  }

  /**
   * Replaces Matrix with matrix.
   * Unlike concat(), any prior matrix state is overwritten.
   *
   * @param matrix matrix to copy, replacing existing Matrix
   * @see <a href="https://fiddle.skia.org/c/@Canvas_setMatrix">https://fiddle.skia.org/c/@Canvas_setMatrix</a>
   */
  @NotNull
  @Contract("_ -> this") def setMatrix(@NotNull matrix: Matrix33): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(matrix != null, "Can’t setMatrix with matrix == null")
    Stats.onNativeCall()
    Canvas._nSetMatrix(_ptr, matrix.mat)
    this
  }

  /**
   * Replaces Matrix with matrix.
   * Unlike concat(), any prior matrix state is overwritten.
   *
   * @param matrix matrix to copy, replacing existing Matrix
   * @see <a href="https://fiddle.skia.org/c/@Canvas_setMatrix">https://fiddle.skia.org/c/@Canvas_setMatrix</a>
   */
  @NotNull
  @Contract("_ -> this") def setMatrix(@NotNull matrix: Matrix44): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(matrix != null, "Can’t setMatrix with matrix == null")
    Stats.onNativeCall()
    Canvas._nSetMatrix44(_ptr, matrix.mat)
    this
  }

  /**
   * Sets SkMatrix to the identity matrix.
   * Any prior matrix state is overwritten.
   *
   * @see <a href="https://fiddle.skia.org/c/@Canvas_resetMatrix">https://fiddle.skia.org/c/@Canvas_resetMatrix</a>
   */
  @NotNull
  @Contract("-> this") def resetMatrix: Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    Stats.onNativeCall()
    Canvas._nResetMatrix(_ptr)
    this
  }

  /**
   * Returns the total transformation matrix for the canvas.
   */
  @NotNull
  @Contract("-> new") def getLocalToDevice: Matrix44 = {
    try {
      assert(_ptr ne 0, "Canvas is closed")
      Stats.onNativeCall()
      val mat = Canvas._nGetLocalToDevice(_ptr)
      new Matrix44(mat)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull
  @Contract("-> new") def getLocalToDeviceAsMatrix33: Matrix33 = {
    getLocalToDevice.asMatrix33
  }

  @NotNull
  @Contract("_, _, _ -> this") def clipRect(@NotNull r: Rect, @NotNull mode: ClipMode, antiAlias: Boolean): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(r != null, "Can’t clipRect with r == null")
    assert(mode != null, "Can’t clipRect with mode == null")
    Stats.onNativeCall()
    Canvas._nClipRect(_ptr, r._left, r._top, r._right, r._bottom, mode.ordinal, antiAlias)
    this
  }

  @NotNull
  @Contract("_, _ -> this") def clipRect(@NotNull r: Rect, @NotNull mode: ClipMode): Canvas = {
    clipRect(r, mode, false)
  }

  @NotNull
  @Contract("_, _ -> this") def clipRect(@NotNull r: Rect, antiAlias: Boolean): Canvas = {
    clipRect(r, ClipMode
      .INTERSECT, antiAlias)
  }

  @NotNull
  @Contract("_ -> this") def clipRect(@NotNull r: Rect): Canvas = {
    clipRect(r, ClipMode.INTERSECT, false)
  }

  @NotNull
  @Contract("_, _, _ -> this") def clipRRect(@NotNull r: RRect, @NotNull mode: ClipMode, antiAlias: Boolean): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(r != null, "Can’t clipRRect with r == null")
    assert(mode != null, "Can’t clipRRect with mode == null")
    Stats.onNativeCall()
    Canvas._nClipRRect(_ptr, r._left, r._top, r._right, r._bottom, r._radii, mode.ordinal, antiAlias)
    this
  }

  @NotNull
  @Contract("_, _ -> this") def clipRRect(@NotNull r: RRect, @NotNull mode: ClipMode): Canvas = {
    clipRRect(r, mode, false)
  }

  @NotNull
  @Contract("_, _ -> this") def clipRRect(@NotNull r: RRect, antiAlias: Boolean): Canvas = {
    clipRRect(r, ClipMode
      .INTERSECT, antiAlias)
  }

  @NotNull
  @Contract("_ -> this") def clipRRect(@NotNull r: RRect): Canvas = {
    clipRRect(r, ClipMode.INTERSECT, false)
  }

  @NotNull
  @Contract("_, _, _ -> this") def clipPath(@NotNull p: Path, @NotNull mode: ClipMode, antiAlias: Boolean): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(p != null, "Can’t clipPath with p == null")
    assert(mode != null, "Can’t clipPath with mode == null")
    Stats.onNativeCall()
    Canvas._nClipPath(_ptr, Native.getPtr(p), mode.ordinal, antiAlias)
    ReferenceUtil.reachabilityFence(p)
    this
  }

  @NotNull
  @Contract("_, _ -> this") def clipPath(@NotNull p: Path, @NotNull mode: ClipMode): Canvas = {
    clipPath(p, mode, false)
  }

  @NotNull
  @Contract("_, _ -> this") def clipPath(@NotNull p: Path, antiAlias: Boolean): Canvas = {
    clipPath(p, ClipMode
      .INTERSECT, antiAlias)
  }

  @NotNull
  @Contract("_ -> this") def clipPath(@NotNull p: Path): Canvas = {
    clipPath(p, ClipMode.INTERSECT, false)
  }

  @NotNull
  @Contract("_, _ -> this") def clipRegion(@NotNull r: Region, @NotNull mode: ClipMode): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(r != null, "Can’t clipRegion with r == null")
    assert(mode != null, "Can’t clipRegion with mode == null")
    Stats.onNativeCall()
    Canvas._nClipRegion(_ptr, Native.getPtr(r), mode.ordinal)
    ReferenceUtil.reachabilityFence(r)
    this
  }

  @NotNull
  @Contract("_ -> this") def clipRegion(@NotNull r: Region): Canvas = {
    clipRegion(r, ClipMode.INTERSECT)
  }

  @NotNull
  @Contract("_, _ -> this") def translate(dx: Float, dy: Float): Canvas = {
    concat(Matrix33.makeTranslate(dx, dy))
  }

  @NotNull
  @Contract("_, _ -> this") def scale(sx: Float, sy: Float): Canvas = {
    concat(Matrix33.makeScale(sx, sy))
  }

  /**
   * @param deg angle in degrees
   * @return this
   */
  @NotNull
  @Contract("_ -> this") def rotate(deg: Float): Canvas = {
    concat(Matrix33.makeRotate(deg))
  }

  @NotNull
  @Contract("_, _ -> this") def skew(sx: Float, sy: Float): Canvas = {
    concat(Matrix33.makeSkew(sx, sy))
  }

  @NotNull
  @Contract("_ -> this") def concat(@NotNull matrix: Matrix33): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(matrix != null, "Can’t concat with matrix == null")
    Stats.onNativeCall()
    Canvas._nConcat(_ptr, matrix.mat)
    this
  }

  @NotNull
  @Contract("_ -> this") def concat(@NotNull matrix: Matrix44): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    assert(matrix != null, "Can’t concat with matrix == null")
    Stats.onNativeCall()
    Canvas._nConcat44(_ptr, matrix.mat)
    this
  }

  /**
   * <p>Copies Rect of pixels from Canvas into bitmap. Matrix and clip are
   * ignored.</p>
   *
   * <p>Source Rect corners are (srcX, srcY) and (imageInfo().width(), imageInfo().height()).
   * Destination Rect corners are (0, 0) and (bitmap.width(), bitmap.height()).
   * Copies each readable pixel intersecting both rectangles, without scaling,
   * converting to bitmap.colorType() and bitmap.alphaType() if required.</p>
   *
   * <p>Pixels are readable when BaseDevice is raster, or backed by a GPU.
   * Pixels are not readable when Canvas is returned by Document::beginPage,
   * returned by PictureRecorder::beginRecording, or Canvas is the base of a utility
   * class like DebugCanvas.</p>
   *
   * <p>Caller must allocate pixel storage in bitmap if needed.</p>
   *
   * <p>SkBitmap values are converted only if ColorType and AlphaType
   * do not match. Only pixels within both source and destination rectangles
   * are copied. Bitmap pixels outside Rect intersection are unchanged.</p>
   *
   * <p>Pass negative values for srcX or srcY to offset pixels across or down bitmap.</p>
   *
   * <p>Does not copy, and returns false if:
   * <ul>
   * <li>Source and destination rectangles do not intersect.</li>
   * <li>SkCanvas pixels could not be converted to bitmap.colorType() or bitmap.alphaType().</li>
   * <li>SkCanvas pixels are not readable; for instance, Canvas is document-based.</li>
   * <li>bitmap pixels could not be allocated.</li>
   * <li>bitmap.rowBytes() is too small to contain one row of pixels.</li>
   * </ul>
   *
   * @param bitmap storage for pixels copied from Canvas
   * @param srcX   offset into readable pixels on x-axis; may be negative
   * @param srcY   offset into readable pixels on y-axis; may be negative
   * @return true if pixels were copied
   * @see <a href="https://fiddle.skia.org/c/@Canvas_readPixels_3">https://fiddle.skia.org/c/@Canvas_readPixels_3</a>
   */
  def readPixels(@NotNull bitmap: Bitmap, srcX: Int, srcY: Int): Boolean = {
    try {
      assert(_ptr ne 0, "Canvas is closed")
      assert(bitmap != null, "Can’t readPixels with bitmap == null")
      Stats.onNativeCall()
      Canvas._nReadPixels(_ptr, Native.getPtr(bitmap), srcX, srcY)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(bitmap)
    }
  }

  /**
   * <p>Copies Rect from pixels to Canvas. Matrix and clip are ignored.
   * Source Rect corners are (0, 0) and (bitmap.width(), bitmap.height()).</p>
   *
   * <p>Destination Rect corners are (x, y) and
   * (imageInfo().width(), imageInfo().height()).</p>
   *
   * <p>Copies each readable pixel intersecting both rectangles, without scaling,
   * converting to getImageInfo().getColorType() and getImageInfo().getAlphaType() if required.</p>
   *
   * <p>Pixels are writable when BaseDevice is raster, or backed by a GPU.
   * Pixels are not writable when Canvas is returned by Document::beginPage,
   * returned by PictureRecorder::beginRecording, or Canvas is the base of a utility
   * class like DebugCanvas.</p>
   *
   * <p>Pixel values are converted only if ColorType and AlphaType
   * do not match. Only pixels within both source and destination rectangles
   * are copied. Canvas pixels outside Rect intersection are unchanged.</p>
   *
   * <p>Pass negative values for x or y to offset pixels to the left or
   * above Canvas pixels.</p>
   *
   * <p>Does not copy, and returns false if:
   * <ul>
   * <li>Source and destination rectangles do not intersect.</li>
   * <li>bitmap does not have allocated pixels.</li>
   * <li>bitmap pixels could not be converted to Canvas getImageInfo().getColorType() or
   * getImageInfo().getAlphaType().</li>
   * <li>Canvas pixels are not writable; for instance, Canvas is document based.</li>
   * <li>bitmap pixels are inaccessible; for instance, bitmap wraps a texture.</li>
   * </ul>
   *
   * @param bitmap contains pixels copied to Canvas
   * @param x      offset into Canvas writable pixels on x-axis; may be negative
   * @param y      offset into Canvas writable pixels on y-axis; may be negative
   * @return true if pixels were written to Canvas
   * @see <a href="https://fiddle.skia.org/c/@Canvas_writePixels_2">https://fiddle.skia.org/c/@Canvas_writePixels_2</a>
   * @see <a href="https://fiddle.skia.org/c/@State_Stack_a">https://fiddle.skia.org/c/@State_Stack_a</a>
   * @see <a href="https://fiddle.skia.org/c/@State_Stack_b">https://fiddle.skia.org/c/@State_Stack_b</a>
   */
  def writePixels(@NotNull bitmap: Bitmap, x: Int, y: Int): Boolean = {
    try {
      assert(_ptr ne 0, "Canvas is closed")
      assert(bitmap != null, "Can’t writePixels with bitmap == null")
      Stats.onNativeCall()
      Canvas._nWritePixels(_ptr, Native.getPtr(bitmap), x, y)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(bitmap)
    }
  }

  def save: Int = {
    try {
      assert(_ptr ne 0, "Canvas is closed")
      Stats.onNativeCall()
      Canvas._nSave(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def saveLayer(left: Float, top: Float, right: Float, bottom: Float, @Nullable paint: Paint): Int = {
    try {
      assert(_ptr ne 0, "Canvas is closed")
      Stats.onNativeCall()
      Canvas._nSaveLayerRect(_ptr, left, top, right, bottom, Native.getPtr(paint))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(paint)
    }
  }

  /**
   * <p>Saves Matrix and clip, and allocates a Bitmap for subsequent drawing.
   * Calling restore() discards changes to Matrix and clip, and draws the Bitmap.</p>
   *
   * <p>Matrix may be changed by translate(), scale(), rotate(), skew(), concat(),
   * setMatrix(), and resetMatrix(). Clip may be changed by clipRect(), clipRRect(),
   * clipPath(), clipRegion().</p>
   *
   * <p>Rect bounds suggests but does not define the Bitmap size. To clip drawing to
   * a specific rectangle, use clipRect().</p>
   *
   * <p>Optional Paint paint applies alpha, ColorFilter, ImageFilter, and
   * BlendMode when restore() is called.</p>
   *
   * <p>Call restoreToCount() with returned value to restore this and subsequent saves.</p>
   *
   * @param bounds hint to limit the size of the layer
   * @param paint  graphics state for layer; may be null
   * @return depth of saved stack
   * @see <a href="https://fiddle.skia.org/c/@Canvas_saveLayer">https://fiddle.skia.org/c/@Canvas_saveLayer</a>
   * @see <a href="https://fiddle.skia.org/c/@Canvas_saveLayer_4">https://fiddle.skia.org/c/@Canvas_saveLayer_4</a>
   */
  def saveLayer(@Nullable bounds: Rect, @Nullable paint: Paint): Int = {
    try {
      assert(_ptr ne 0, "Canvas is closed")
      Stats.onNativeCall()
      if (bounds == null) {
        Canvas._nSaveLayer(_ptr, Native.getPtr(paint))
      } else {
        Canvas._nSaveLayerRect(_ptr, bounds._left, bounds._top, bounds._right, bounds._bottom, Native.getPtr(paint))
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(paint)
    }
  }

  /**
   * <p>Saves matrix and clip, and allocates bitmap for subsequent drawing.</p>
   *
   * <p>Calling restore() discards changes to matrix and clip,
   * and blends layer with alpha opacity onto prior layer.</p>
   *
   * <p>matrix may be changed by translate(), scale(), rotate(), skew(), concat(),
   * setMatrix(), and resetMatrix(). Clip may be changed by clipRect(), clipRRect(),
   * clipPath(), clipRegion().</p>
   *
   * <p>Bounds suggests but does not define layer size. To clip drawing to
   * a specific rectangle, use clipRect().</p>
   *
   * <p>Call restoreToCount() with returned value to restore this and subsequent saves.</p>
   *
   * @param bounds hint to limit the size of layer; may be null
   * @param alpha  opacity of layer, zero is fully transparent, 255 is fully opaque.
   * @return depth of saved stack
   * @see <a href="https://fiddle.skia.org/c/@Canvas_saveLayerAlpha">https://fiddle.skia.org/c/@Canvas_saveLayerAlpha</a>
   */
  def saveLayerAlpha(@Nullable bounds: Rect, alpha: Int): Int = {
    try {
      assert(_ptr ne 0, "Canvas is closed")
      Stats.onNativeCall()
      if (bounds == null) {
        Canvas._nSaveLayerAlpha(_ptr, alpha)
      } else {
        Canvas._nSaveLayerAlphaRect(_ptr, bounds._left, bounds._top, bounds._right, bounds._bottom, alpha)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Saves matrix and clip, and allocates Bitmap for subsequent drawing.</p>
   *
   * <p>Calling restore() discards changes to matrix and clip,
   * and blends Bitmap with alpha opacity onto the prior layer.</p>
   *
   * <p>matrix may be changed by translate(), scale(), rotate(), skew(), concat(),
   * setMatrix(), and resetMatrix(). Clip may be changed by clipRect(), clipRRect(),
   * clipPath(), clipRegion().</p>
   *
   * <p>SaveLayerRec contains the state used to create the layer.</p>
   *
   * <p>Call restoreToCount() with returned value to restore this and subsequent saves.</p>
   *
   * @param layerRec layer state
   * @return depth of save state stack before this call was made.
   * @see <a href="https://fiddle.skia.org/c/@Canvas_saveLayer_3">https://fiddle.skia.org/c/@Canvas_saveLayer_3</a>
   */
  def saveLayer(@NotNull layerRec: SaveLayerRec): Int = {
    try {
      assert(_ptr ne 0, "Canvas is closed")
      Stats.onNativeCall()
      val bounds = layerRec.bounds
      if (bounds == null) {
        Canvas
          ._nSaveLayerRec(_ptr, Native.getPtr(layerRec.paint), Native.getPtr(layerRec.backdrop), layerRec.flags)
      } else {
        Canvas._nSaveLayerRecRect(_ptr, bounds._left, bounds._top, bounds._right, bounds._bottom, Native
          .getPtr(layerRec.paint), Native.getPtr(layerRec.backdrop), layerRec.flags)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(layerRec)
    }
  }

  def getSaveCount: Int = {
    try {
      assert(_ptr ne 0, "Canvas is closed")
      Stats.onNativeCall()
      Canvas._nGetSaveCount(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull
  @Contract("-> this") def restore: Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    Stats.onNativeCall()
    Canvas._nRestore(_ptr)
    this
  }

  @NotNull
  @Contract("_ -> this") def restoreToCount(saveCount: Int): Canvas = {
    assert(_ptr ne 0, "Canvas is closed")
    Stats.onNativeCall()
    Canvas._nRestoreToCount(_ptr, saveCount)
    this
  }

  @ApiStatus.Internal def invalidate(): Unit = {
    _ptr = 0
  }
}