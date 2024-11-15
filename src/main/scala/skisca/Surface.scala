package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object Surface {
  /**
   * @deprecated - use {@link # wrapPixels ( ImageInfo, long, long)}
   */
  @deprecated def makeRasterDirect(@NotNull imageInfo: ImageInfo, pixelsPtr: Long, rowBytes: Long): Surface = {
    wrapPixels(imageInfo, pixelsPtr, rowBytes, null)
  }

  /**
   * <p>Allocates raster Surface. Canvas returned by Surface draws directly into pixels.</p>
   *
   * <p>Surface is returned if all parameters are valid. Valid parameters include:</p>
   *
   * <ul><li>info dimensions are greater than zero;</li>
   * <li>info contains ColorType and AlphaType supported by raster surface;</li>
   * <li>pixelsPtr is not 0;</li>
   * <li>rowBytes is large enough to contain info width pixels of ColorType.</li></ul>
   *
   * <p>Pixel buffer size should be info height times computed rowBytes.</p>
   *
   * <p>Pixels are not initialized.</p>
   *
   * <p>To access pixels after drawing, peekPixels() or readPixels().</p>
   *
   * @param imageInfo width, height, ColorType, AlphaType, ColorSpace,
   *                  of raster surface; width and height must be greater than zero
   * @param pixelsPtr pointer to destination pixels buffer
   * @param rowBytes  memory address of destination native pixels buffer
   * @return created Surface
   */
  @NotNull
  @Contract("_, _, _ -> new") def wrapPixels(@NotNull imageInfo: ImageInfo, pixelsPtr: Long, rowBytes: Long): Surface = {
    wrapPixels(imageInfo, pixelsPtr, rowBytes, null)
  }

  /**
   * @deprecated - use {@link # wrapPixels ( ImageInfo, long, long, SurfaceProps)}
   */
  @deprecated def makeRasterDirect(@NotNull imageInfo: ImageInfo, pixelsPtr: Long, rowBytes: Long, @Nullable surfaceProps: SurfaceProps): Surface = {
    wrapPixels(imageInfo, pixelsPtr, rowBytes, surfaceProps)
  }

  /**
   * <p>Allocates raster Surface. Canvas returned by Surface draws directly into pixels.</p>
   *
   * <p>Surface is returned if all parameters are valid. Valid parameters include:</p>
   *
   * <ul><li>info dimensions are greater than zero;</li>
   * <li>info contains ColorType and AlphaType supported by raster surface;</li>
   * <li>pixelsPtr is not 0;</li>
   * <li>rowBytes is large enough to contain info width pixels of ColorType.</li></ul>
   *
   * <p>Pixel buffer size should be info height times computed rowBytes.</p>
   *
   * <p>Pixels are not initialized.</p>
   *
   * <p>To access pixels after drawing, peekPixels() or readPixels().</p>
   *
   * @param imageInfo    width, height, ColorType, AlphaType, ColorSpace,
   *                     of raster surface; width and height must be greater than zero
   * @param pixelsPtr    pointer to destination pixels buffer
   * @param rowBytes     memory address of destination native pixels buffer
   * @param surfaceProps LCD striping orientation and setting for device independent fonts;
   *                     may be null
   * @return created Surface
   */
  @NotNull
  @Contract("_, _, _, _ -> new") def wrapPixels(@NotNull imageInfo: ImageInfo, pixelsPtr: Long, rowBytes: Long, @Nullable surfaceProps: SurfaceProps): Surface = {
    try {
      assert(imageInfo != null, "Can’t wrapPixels with imageInfo == null")
      Stats.onNativeCall()
      val ptr = _nWrapPixels(imageInfo.width, imageInfo.height, imageInfo.colorInfo.colorType.ordinal, imageInfo
        .colorInfo.alphaType.ordinal, Native
        .getPtr(imageInfo.colorInfo.colorSpace), pixelsPtr, rowBytes, surfaceProps)
      if (ptr == 0) {
        throw new IllegalArgumentException(String
          .format("Failed Surface.wrapPixels(%s, %d, %d, %s)", imageInfo, pixelsPtr, rowBytes, surfaceProps))
      }
      new Surface(ptr)
    } finally {
      ReferenceUtil.reachabilityFence(imageInfo.colorInfo.colorSpace)
    }
  }

  /**
   * @deprecated - use {@link # wrapPixels ( Pixmap )}
   */
  @deprecated def makeRasterDirect(@NotNull pixmap: Pixmap): Surface = {
    wrapPixels(pixmap, null)
  }

  @NotNull
  @Contract("_ -> new") def wrapPixels(@NotNull pixmap: Pixmap): Surface = {
    wrapPixels(pixmap, null)
  }

  /**
   * @deprecated - use {@link # wrapPixels ( Pixmap, SurfaceProps)}
   */
  @deprecated def makeRasterDirect(@NotNull pixmap: Pixmap, @Nullable surfaceProps: SurfaceProps): Surface = {
    wrapPixels(pixmap, surfaceProps)
  }

  @NotNull
  @Contract("_, _ -> new") def wrapPixels(@NotNull pixmap: Pixmap, @Nullable surfaceProps: SurfaceProps): Surface = {
    try {
      assert(pixmap != null, "Can’t wrapPixels with pixmap == null")
      Stats.onNativeCall()
      val ptr = _nWrapPixelsPixmap(Native.getPtr(pixmap), surfaceProps)
      if (ptr == 0) {
        throw new IllegalArgumentException(String
          .format("Failed Surface.wrapPixels(%s, %s)", pixmap, surfaceProps))
      }
      new Surface(ptr)
    } finally {
      ReferenceUtil.reachabilityFence(pixmap)
    }
  }

  /**
   * <p>Allocates raster Surface. Canvas returned by Surface draws directly into pixels.
   * Allocates and zeroes pixel memory. Pixel memory size is imageInfo.height() times imageInfo.minRowBytes().
   * Pixel memory is deleted when Surface is deleted.</p>
   *
   * <p>Surface is returned if all parameters are valid. Valid parameters include:</p>
   *
   * <ul><li>info dimensions are greater than zero;</li>
   * <li>info contains ColorType and AlphaType supported by raster surface;</li></ul>
   *
   * @param imageInfo width, height, ColorType, AlphaType, ColorSpace,
   *                  of raster surface; width and height must be greater than zero
   * @return new Surface
   */
  @NotNull
  @Contract("_, _, _ -> new") def makeRaster(@NotNull imageInfo: ImageInfo): Surface = {
    makeRaster(imageInfo, 0, null)
  }

  /**
   * <p>Allocates raster Surface. Canvas returned by Surface draws directly into pixels.
   * Allocates and zeroes pixel memory. Pixel memory size is imageInfo.height() times
   * rowBytes, or times imageInfo.minRowBytes() if rowBytes is zero.
   * Pixel memory is deleted when Surface is deleted.</p>
   *
   * <p>Surface is returned if all parameters are valid. Valid parameters include:</p>
   *
   * <ul><li>info dimensions are greater than zero;</li>
   * <li>info contains ColorType and AlphaType supported by raster surface;</li>
   * <li>rowBytes is large enough to contain info width pixels of ColorType, or is zero.</li></ul>
   *
   * <p>If rowBytes is zero, a suitable value will be chosen internally.</p>
   *
   * @param imageInfo width, height, ColorType, AlphaType, ColorSpace,
   *                  of raster surface; width and height must be greater than zero
   * @param rowBytes  interval from one Surface row to the next; may be zero
   * @return new Surface
   */
  @NotNull
  @Contract("_, _, _ -> new") def makeRaster(@NotNull imageInfo: ImageInfo, rowBytes: Long): Surface = {
    makeRaster(imageInfo, rowBytes, null)
  }

  /**
   * <p>Allocates raster Surface. Canvas returned by Surface draws directly into pixels.
   * Allocates and zeroes pixel memory. Pixel memory size is imageInfo.height() times
   * rowBytes, or times imageInfo.minRowBytes() if rowBytes is zero.
   * Pixel memory is deleted when Surface is deleted.</p>
   *
   * <p>Surface is returned if all parameters are valid. Valid parameters include:</p>
   *
   * <ul><li>info dimensions are greater than zero;</li>
   * <li>info contains ColorType and AlphaType supported by raster surface;</li>
   * <li>rowBytes is large enough to contain info width pixels of ColorType, or is zero.</li></ul>
   *
   * <p>If rowBytes is zero, a suitable value will be chosen internally.</p>
   *
   * @param imageInfo    width, height, ColorType, AlphaType, ColorSpace,
   *                     of raster surface; width and height must be greater than zero
   * @param rowBytes     interval from one Surface row to the next; may be zero
   * @param surfaceProps LCD striping orientation and setting for device independent fonts;
   *                     may be null
   * @return new Surface
   */
  @NotNull
  @Contract("_, _, _ -> new") def makeRaster(@NotNull imageInfo: ImageInfo, rowBytes: Long, @Nullable surfaceProps: SurfaceProps): Surface = {
    try {
      assert(imageInfo != null, "Can’t makeRaster with imageInfo == null")
      Stats.onNativeCall()
      val ptr = _nMakeRaster(imageInfo.width, imageInfo.height, imageInfo.colorInfo.colorType.ordinal, imageInfo
        .colorInfo.alphaType.ordinal, Native.getPtr(imageInfo.colorInfo.colorSpace), rowBytes, surfaceProps)
      if (ptr == 0) {
        throw new IllegalArgumentException(String
          .format("Failed Surface.makeRaster(%s, %d, %s)", imageInfo, rowBytes, surfaceProps))
      }
      new Surface(ptr)
    } finally {
      ReferenceUtil.reachabilityFence(imageInfo.colorInfo.colorSpace)
    }
  }

  /**
   * @deprecated - use {@link # wrapBackendRenderTarget ( DirectContext, BackendRenderTarget, SurfaceOrigin, SurfaceColorFormat, ColorSpace)}
   */
  @deprecated def makeFromBackendRenderTarget(context: DirectContext, rt: BackendRenderTarget, origin: SurfaceOrigin, colorFormat: SurfaceColorFormat, colorSpace: ColorSpace): Surface = {
    wrapBackendRenderTarget(context, rt, origin, colorFormat, colorSpace, null)
  }

  /**
   * <p>Wraps a GPU-backed buffer into {@link Surface}.</p>
   *
   * <p>Caller must ensure backendRenderTarget is valid for the lifetime of returned {@link Surface}.</p>
   *
   * <p>{@link Surface} is returned if all parameters are valid. backendRenderTarget is valid if its pixel
   * configuration agrees with colorSpace and context;
   * for instance, if backendRenderTarget has an sRGB configuration, then context must support sRGB,
   * and colorSpace must be present. Further, backendRenderTarget width and height must not exceed
   * context capabilities, and the context must be able to support back-end render targets.</p>
   *
   * @param context     GPU context
   * @param rt          texture residing on GPU
   * @param origin      surfaceOrigin pins either the top-left or the bottom-left corner to the origin.
   * @param colorFormat color format
   * @param colorSpace  range of colors; may be null
   * @return Surface if all parameters are valid; otherwise, null
   * @see <a href="https://fiddle.skia.org/c/@Surface_MakeFromBackendTexture">https://fiddle.skia.org/c/@Surface_MakeFromBackendTexture</a>
   */
  @NotNull def wrapBackendRenderTarget(@NotNull context: DirectContext, @NotNull rt: BackendRenderTarget, @NotNull origin: SurfaceOrigin, @NotNull colorFormat: SurfaceColorFormat, @Nullable colorSpace: ColorSpace): Surface = {
    wrapBackendRenderTarget(context, rt, origin, colorFormat, colorSpace, null)
  }

  /**
   * @deprecated - use {@link # wrapBackendRenderTarget ( DirectContext, BackendRenderTarget, SurfaceOrigin, SurfaceColorFormat, ColorSpace, SurfaceProps)}
   */
  @deprecated def makeFromBackendRenderTarget(context: DirectContext, rt: BackendRenderTarget, origin: SurfaceOrigin, colorFormat: SurfaceColorFormat, colorSpace: ColorSpace, surfaceProps: SurfaceProps): Surface = {
    wrapBackendRenderTarget(context, rt, origin, colorFormat, colorSpace, surfaceProps)
  }

  /**
   * <p>Wraps a GPU-backed buffer into {@link Surface}.</p>
   *
   * <p>Caller must ensure backendRenderTarget is valid for the lifetime of returned {@link Surface}.</p>
   *
   * <p>{@link Surface} is returned if all parameters are valid. backendRenderTarget is valid if its pixel
   * configuration agrees with colorSpace and context;
   * for instance, if backendRenderTarget has an sRGB configuration, then context must support sRGB,
   * and colorSpace must be present. Further, backendRenderTarget width and height must not exceed
   * context capabilities, and the context must be able to support back-end render targets.</p>
   *
   * @param context      GPU context
   * @param rt           texture residing on GPU
   * @param origin       surfaceOrigin pins either the top-left or the bottom-left corner to the origin.
   * @param colorFormat  color format
   * @param colorSpace   range of colors; may be null
   * @param surfaceProps LCD striping orientation and setting for device independent fonts; may be null
   * @return Surface if all parameters are valid; otherwise, null
   * @see <a href="https://fiddle.skia.org/c/@Surface_MakeFromBackendTexture">https://fiddle.skia.org/c/@Surface_MakeFromBackendTexture</a>
   */
  @NotNull def wrapBackendRenderTarget(@NotNull context: DirectContext, @NotNull rt: BackendRenderTarget, @NotNull origin: SurfaceOrigin, @NotNull colorFormat: SurfaceColorFormat, @Nullable colorSpace: ColorSpace, @Nullable surfaceProps: SurfaceProps): Surface = {
    try {
      assert(context != null, "Can’t wrapBackendRenderTarget with context == null")
      assert(rt != null, "Can’t wrapBackendRenderTarget with rt == null")
      assert(origin != null, "Can’t wrapBackendRenderTarget with origin == null")
      assert(colorFormat != null, "Can’t wrapBackendRenderTarget with colorFormat == null")
      Stats.onNativeCall()
      val ptr = _nWrapBackendRenderTarget(Native.getPtr(context), Native.getPtr(rt), origin.ordinal, colorFormat
        .ordinal, Native.getPtr(colorSpace), surfaceProps)
      if (ptr == 0) {
        throw new IllegalArgumentException(String
          .format("Failed Surface.wrapBackendRenderTarget(%s, %s, %s, %s, %s)", context, rt, origin, colorFormat, colorSpace))
      }
      new Surface(ptr, context, rt)
    } finally {
      ReferenceUtil.reachabilityFence(context)
      ReferenceUtil.reachabilityFence(rt)
      ReferenceUtil.reachabilityFence(colorSpace)
    }
  }

  /**
   * @deprecated - use {@link # wrapMTKView ( DirectContext, long, SurfaceOrigin, int, SurfaceColorFormat, ColorSpace, SurfaceProps)}
   */
  @deprecated def makeFromMTKView(@NotNull context: DirectContext, mtkViewPtr: Long, @NotNull origin: SurfaceOrigin, sampleCount: Int, @NotNull colorFormat: SurfaceColorFormat, @Nullable colorSpace: ColorSpace, @Nullable surfaceProps: SurfaceProps): Surface = {
    wrapMTKView(context, mtkViewPtr, origin, sampleCount, colorFormat, colorSpace, surfaceProps)
  }

  @NotNull def wrapMTKView(@NotNull context: DirectContext, mtkViewPtr: Long, @NotNull origin: SurfaceOrigin, sampleCount: Int, @NotNull colorFormat: SurfaceColorFormat, @Nullable colorSpace: ColorSpace, @Nullable surfaceProps: SurfaceProps): Surface = {
    try {
      assert(context != null, "Can’t wrapMTKView with context == null")
      assert(origin != null, "Can’t wrapMTKView with origin == null")
      assert(colorFormat != null, "Can’t wrapMTKView with colorFormat == null")
      Stats.onNativeCall()
      val ptr = _nWrapMTKView(Native.getPtr(context), mtkViewPtr, origin.ordinal, sampleCount, colorFormat
        .ordinal, Native
        .getPtr(colorSpace), surfaceProps)
      if (ptr == 0) {
        throw new IllegalArgumentException(String
          .format("Failed Surface.WrapMTKView(%s, %s, %s, %s, %s, %s)", context, mtkViewPtr, origin, colorFormat, colorSpace, surfaceProps))
      }
      new Surface(ptr, context)
    } finally {
      ReferenceUtil.reachabilityFence(context)
      ReferenceUtil.reachabilityFence(colorSpace)
    }
  }

  /**
   * @deprecated - use makeRaster(ImageInfo.makeN32Premul(width, height))
   *
   *             <p>Allocates raster {@link Surface}.</p>
   *
   *             <p>Canvas returned by Surface draws directly into pixels. Allocates and zeroes pixel memory.
   *             Pixel memory size is height times width times four. Pixel memory is deleted when Surface is deleted.</p>
   *
   *             <p>Internally, sets ImageInfo to width, height, native color type, and ColorAlphaType.PREMUL.</p>
   *
   *             <p>Surface is returned if width and height are greater than zero.</p>
   *
   *             <p>Use to create Surface that matches PMColor, the native pixel arrangement on the platform.
   *             Surface drawn to output device skips converting its pixel format.</p>
   * @param width  pixel column count; must be greater than zero
   * @param height pixel row count; must be greater than zero
   * @return Surface if all parameters are valid; otherwise, null
   * @see <a href="https://fiddle.skia.org/c/@Surface_MakeRasterN32Premul">https://fiddle.skia.org/c/@Surface_MakeRasterN32Premul</a>
   */
  @NotNull
  @deprecated def makeRasterN32Premul(width: Int, height: Int): Surface = {
    makeRaster(ImageInfo
      .makeN32Premul(width, height))
  }

  /**
   * <p>Returns Surface on GPU indicated by context. Allocates memory for
   * pixels, based on the width, height, and ColorType in ImageInfo.
   * describes the pixel format in ColorType, and transparency in
   * AlphaType, and color matching in ColorSpace.</p>
   *
   * @param context   GPU context
   * @param budgeted  selects whether allocation for pixels is tracked by context
   * @param imageInfo width, height, ColorType, AlphaType, ColorSpace;
   *                  width, or height, or both, may be zero
   * @return new SkSurface
   */
  @NotNull
  @Contract("_, _, _ -> new") def makeRenderTarget(@NotNull context: DirectContext, budgeted: Boolean, @NotNull imageInfo: ImageInfo): Surface = {
    makeRenderTarget(context, budgeted, imageInfo, 0, SurfaceOrigin
      .BOTTOM_LEFT, null, false)
  }

  /**
   * <p>Returns Surface on GPU indicated by context. Allocates memory for
   * pixels, based on the width, height, and ColorType in ImageInfo.
   * describes the pixel format in ColorType, and transparency in
   * AlphaType, and color matching in ColorSpace.</p>
   *
   * <p>sampleCount requests the number of samples per pixel.
   * Pass zero to disable multi-sample anti-aliasing.  The request is rounded
   * up to the next supported count, or rounded down if it is larger than the
   * maximum supported count.</p>
   *
   * @param context      GPU context
   * @param budgeted     selects whether allocation for pixels is tracked by context
   * @param imageInfo    width, height, ColorType, AlphaType, ColorSpace;
   *                     width, or height, or both, may be zero
   * @param sampleCount  samples per pixel, or 0 to disable full scene anti-aliasing
   * @param surfaceProps LCD striping orientation and setting for device independent
   *                     fonts; may be null
   * @return new SkSurface
   */
  @NotNull
  @Contract("_, _, _, _, _ -> new") def makeRenderTarget(@NotNull context: DirectContext, budgeted: Boolean, @NotNull imageInfo: ImageInfo, sampleCount: Int, @Nullable surfaceProps: SurfaceProps): Surface = {
    makeRenderTarget(context, budgeted, imageInfo, sampleCount, SurfaceOrigin
      .BOTTOM_LEFT, surfaceProps, false)
  }

  /**
   * <p>Returns Surface on GPU indicated by context. Allocates memory for
   * pixels, based on the width, height, and ColorType in ImageInfo.
   * describes the pixel format in ColorType, and transparency in
   * AlphaType, and color matching in ColorSpace.</p>
   *
   * <p>sampleCount requests the number of samples per pixel.
   * Pass zero to disable multi-sample anti-aliasing.  The request is rounded
   * up to the next supported count, or rounded down if it is larger than the
   * maximum supported count.</p>
   *
   * @param context      GPU context
   * @param budgeted     selects whether allocation for pixels is tracked by context
   * @param imageInfo    width, height, ColorType, AlphaType, ColorSpace;
   *                     width, or height, or both, may be zero
   * @param sampleCount  samples per pixel, or 0 to disable full scene anti-aliasing
   * @param origin       pins either the top-left or the bottom-left corner to the origin.
   * @param surfaceProps LCD striping orientation and setting for device independent
   *                     fonts; may be null
   * @return new SkSurface
   */
  @NotNull
  @Contract("_, _, _, _, _, _ -> new") def makeRenderTarget(@NotNull context: DirectContext, budgeted: Boolean, @NotNull imageInfo: ImageInfo, sampleCount: Int, @NotNull origin: SurfaceOrigin, @Nullable surfaceProps: SurfaceProps): Surface = {
    makeRenderTarget(context, budgeted, imageInfo, sampleCount, origin, surfaceProps, false)
  }

  /**
   * <p>Returns Surface on GPU indicated by context. Allocates memory for
   * pixels, based on the width, height, and ColorType in ImageInfo.
   * describes the pixel format in ColorType, and transparency in
   * AlphaType, and color matching in ColorSpace.</p>
   *
   * <p>sampleCount requests the number of samples per pixel.
   * Pass zero to disable multi-sample anti-aliasing.  The request is rounded
   * up to the next supported count, or rounded down if it is larger than the
   * maximum supported count.</p>
   *
   * <p>shouldCreateWithMips hints that Image returned by {@link # makeImageSnapshot ( )} is mip map.</p>
   *
   * @param context              GPU context
   * @param budgeted             selects whether allocation for pixels is tracked by context
   * @param imageInfo            width, height, ColorType, AlphaType, ColorSpace;
   *                             width, or height, or both, may be zero
   * @param sampleCount          samples per pixel, or 0 to disable full scene anti-aliasing
   * @param origin               pins either the top-left or the bottom-left corner to the origin.
   * @param surfaceProps         LCD striping orientation and setting for device independent
   *                             fonts; may be null
   * @param shouldCreateWithMips hint that SkSurface will host mip map images
   * @return new SkSurface
   */
  @NotNull
  @Contract("_, _, _, _, _, _, _ -> new") def makeRenderTarget(@NotNull context: DirectContext, budgeted: Boolean, @NotNull imageInfo: ImageInfo, sampleCount: Int, @NotNull origin: SurfaceOrigin, @Nullable surfaceProps: SurfaceProps, shouldCreateWithMips: Boolean): Surface = {
    try {
      assert(context != null, "Can’t makeFromBackendRenderTarget with context == null")
      assert(imageInfo != null, "Can’t makeFromBackendRenderTarget with imageInfo == null")
      assert(origin != null, "Can’t makeFromBackendRenderTarget with origin == null")
      Stats.onNativeCall()
      val ptr = _nMakeRenderTarget(Native.getPtr(context), budgeted, imageInfo.width, imageInfo.height, imageInfo
        .colorInfo.colorType.ordinal, imageInfo.colorInfo.alphaType.ordinal, Native
        .getPtr(imageInfo.colorInfo.colorSpace), sampleCount, origin.ordinal, surfaceProps, shouldCreateWithMips)
      if (ptr == 0) {
        throw new IllegalArgumentException(String
          .format("Failed Surface.makeRenderTarget(%s, %b, %s, %d, %s, %s, %b)", context, budgeted, imageInfo, sampleCount, origin, surfaceProps, shouldCreateWithMips))
      }
      new Surface(ptr, context)
    } finally {
      ReferenceUtil.reachabilityFence(context)
      ReferenceUtil.reachabilityFence(imageInfo.colorInfo.colorSpace)
    }
  }

  /**
   * Returns Surface without backing pixels. Drawing to Canvas returned from Surface
   * has no effect. Calling makeImageSnapshot() on returned Surface returns null.
   *
   * @param width  one or greater
   * @param height one or greater
   * @return Surface if width and height are positive
   * @see <a href="https://fiddle.skia.org/c/@Surface_MakeNull">https://fiddle.skia.org/c/@Surface_MakeNull</a>
   */
  @NotNull
  @Contract("_, _ -> new") def makeNull(width: Int, height: Int): Surface = {
    Stats.onNativeCall()
    val ptr = _nMakeNull(width, height)
    if (ptr == 0) throw new IllegalArgumentException(String.format("Failed Surface.makeNull(%d, %d)", width, height))
    new Surface(ptr)
  }

  @native def _nWrapPixels(width: Int, height: Int, colorType: Int, alphaType: Int, colorSpacePtr: Long, pixelsPtr: Long, rowBytes: Long, surfaceProps: SurfaceProps): Long

  @native def _nWrapPixelsPixmap(pixmapPtr: Long, surfaceProps: SurfaceProps): Long

  @native def _nMakeRaster(width: Int, height: Int, colorType: Int, alphaType: Int, colorSpacePtr: Long, rowBytes: Long, surfaceProps: SurfaceProps): Long

  @native def _nWrapBackendRenderTarget(pContext: Long, pBackendRenderTarget: Long, surfaceOrigin: Int, colorType: Int, colorSpacePtr: Long, surfaceProps: SurfaceProps): Long

  @native def _nWrapMTKView(contextPtr: Long, mtkViewPtr: Long, surfaceOrigin: Int, sampleCount: Int, colorType: Int, colorSpacePtr: Long, surfaceProps: SurfaceProps): Long

  @native def _nMakeRenderTarget(contextPtr: Long, budgeted: Boolean, width: Int, height: Int, colorType: Int, alphaType: Int, colorSpacePtr: Long, sampleCount: Int, surfaceOrigin: Int, surfaceProps: SurfaceProps, shouldCreateWithMips: Boolean): Long

  @native def _nMakeNull(width: Int, height: Int): Long

  @native def _nGetWidth(ptr: Long): Int

  @native def _nGetHeight(ptr: Long): Int

  @native def _nGetImageInfo(ptr: Long): ImageInfo

  @native def _nGenerationId(ptr: Long): Int

  @native def _nNotifyContentWillChange(ptr: Long, mode: Int): Unit

  @native def _nGetRecordingContext(ptr: Long): Long

  @native def _nGetCanvas(ptr: Long): Long

  @native def _nMakeSurfaceI(ptr: Long, width: Int, height: Int, colorType: Int, alphaType: Int, colorSpacePtr: Long): Long

  @native def _nMakeSurface(ptr: Long, width: Int, height: Int): Long

  @native def _nMakeImageSnapshot(ptr: Long): Long

  @native def _nMakeImageSnapshotR(ptr: Long, left: Int, top: Int, right: Int, bottom: Int): Long

  @native def _nDraw(ptr: Long, canvasPtr: Long, x: Float, y: Float, paintPtr: Long): Unit

  @native def _nPeekPixels(ptr: Long, pixmapPtr: Long): Boolean

  @native def _nReadPixelsToPixmap(ptr: Long, pixmapPtr: Long, srcX: Int, srcY: Int): Boolean

  @native def _nReadPixels(ptr: Long, bitmapPtr: Long, srcX: Int, srcY: Int): Boolean

  @native def _nWritePixelsFromPixmap(ptr: Long, pixmapPtr: Long, x: Int, y: Int): Unit

  @native def _nWritePixels(ptr: Long, bitmapPtr: Long, x: Int, y: Int): Unit

  @native def _nFlushAndSubmit(ptr: Long, syncCpu: Boolean): Unit

  @native def _nFlush(ptr: Long): Unit

  @native def _nUnique(ptr: Long): Boolean

  try Library.staticLoad()
}

class Surface(ptr: Long, context: DirectContext = null, renderTarget: BackendRenderTarget = null) extends RefCnt(ptr) {
  @ApiStatus.Internal var _canvas: Canvas = null
  @ApiStatus.Internal final var _context: DirectContext = context
  @ApiStatus.Internal final var _renderTarget: BackendRenderTarget = renderTarget

  /**
   * <p>Returns pixel count in each row; may be zero or greater.</p>
   *
   * @return number of pixel columns
   * @see <a href="https://fiddle.skia.org/c/@Surface_width">https://fiddle.skia.org/c/@Surface_width</a>
   */
  def getWidth: Int = {
    try {
      Stats.onNativeCall()
      Surface._nGetWidth(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns pixel row count; may be zero or greater.</p>
   *
   * @return number of pixel rows
   * @see <a href="https://fiddle.skia.org/c/@Surface_height">https://fiddle.skia.org/c/@Surface_height</a>
   */
  def getHeight: Int = {
    try {
      Stats.onNativeCall()
      Surface._nGetHeight(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns an ImageInfo describing the surface.</p>
   *
   * @return ImageInfo describing the surface.
   */
  @NotNull def getImageInfo: ImageInfo = {
    try {
      Stats.onNativeCall()
      Surface._nGetImageInfo(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns unique value identifying the content of Surface.</p>
   *
   * <p>Returned value changes each time the content changes.
   * Content is changed by drawing, or by calling notifyContentWillChange().</p>
   *
   * @return unique content identifier
   */
  def getGenerationId: Int = {
    try {
      Stats.onNativeCall()
      Surface._nGenerationId(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Notifies that Surface contents will be changed by code outside of Skia.</p>
   *
   * <p>Subsequent calls to generationID() return a different value.</p>
   *
   * @see <a href="https://fiddle.skia.org/c/@Surface_notifyContentWillChange">https://fiddle.skia.org/c/@Surface_notifyContentWillChange</a>
   */
  def notifyContentWillChange(mode: ContentChangeMode): Unit = {
    try {
      Stats.onNativeCall()
      Surface._nNotifyContentWillChange(_ptr, mode.ordinal)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns the recording context being used by the Surface.</p>
   *
   * @return the recording context, if available; null otherwise
   */
  @Nullable def getRecordingContext: DirectContext = {
    try {
      Stats.onNativeCall()
      val ptr = Surface._nGetRecordingContext(_ptr)
      if (ptr == 0) {
        null
      } else {
        new DirectContext(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns Canvas that draws into Surface.</p>
   *
   * <p>Subsequent calls return the same Canvas.
   * Canvas returned is managed and owned by Surface, and is deleted when Surface is deleted.</p>
   *
   * @return Canvas for Surface
   */
  @NotNull def getCanvas: Canvas = {
    try {
      if (_canvas == null) {
        Stats.onNativeCall()
        val ptr = Surface._nGetCanvas(_ptr)
        _canvas = if (ptr == 0) {
          null
        } else {
          new Canvas(ptr, false, this)
        }
      }
      _canvas
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns a compatible Surface, or null.</p>
   *
   * <p>Returned Surface contains the same raster, GPU, or null properties as the original.
   * Returned Surface does not share the same pixels.</p>
   *
   * <p>Returns null if imageInfo width or height are zero, or if imageInfo is incompatible with Surface.</p>
   *
   * @param imageInfo contains width, height, AlphaType, ColorType, ColorSpace
   * @return compatible SkSurface or null
   * @see <a href="https://fiddle.skia.org/c/@Surface_makeSurface">https://fiddle.skia.org/c/@Surface_makeSurface</a>
   */
  @Nullable def makeSurface(imageInfo: ImageInfo): Surface = {
    try {
      Stats.onNativeCall()
      val ptr = Surface
        ._nMakeSurfaceI(_ptr, imageInfo.width, imageInfo.height, imageInfo.colorInfo.colorType.ordinal, imageInfo
          .colorInfo.alphaType.ordinal, Native.getPtr(imageInfo.colorInfo.colorSpace))
      new Surface(ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Calls makeSurface(ImageInfo) with the same ImageInfo as this surface,
   * but with the specified width and height.</p>
   *
   * <p>Returned Surface contains the same raster, GPU, or null properties as the original.
   * Returned Surface does not share the same pixels.</p>
   *
   * <p>Returns null if imageInfo width or height are zero, or if imageInfo is incompatible with Surface.</p>
   *
   * @param width  pixel column count; must be greater than zero
   * @param height pixel row count; must be greater than zero
   * @return compatible SkSurface or null
   */
  @Nullable def makeSurface(width: Int, height: Int): Surface = {
    try {
      Stats.onNativeCall()
      val ptr = Surface._nMakeSurface(_ptr, width, height)
      new Surface(ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns Image capturing Surface contents.</p>
   *
   * <p>Subsequent drawing to Surface contents are not captured.
   * Image allocation is accounted for if Surface was created with SkBudgeted::kYes.</p>
   *
   * @return Image initialized with Surface contents
   * @see <a href="https://fiddle.skia.org/c/@Surface_makeImageSnapshot">https://fiddle.skia.org/c/@Surface_makeImageSnapshot</a>
   */
  @NotNull def makeImageSnapshot: Image = {
    try {
      Stats.onNativeCall()
      new Image(Surface._nMakeImageSnapshot(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Like the no-parameter version, this returns an image of the current surface contents.</p>
   *
   * <p>This variant takes a rectangle specifying the subset of the surface that is of interest.
   * These bounds will be sanitized before being used.</p>
   *
   * <ul>
   * <li>If bounds extends beyond the surface, it will be trimmed to just the intersection of it and the surface.</li>
   * <li>If bounds does not intersect the surface, then this returns null.</li>
   * <li>If bounds == the surface, then this is the same as calling the no-parameter variant.</li>
   * </ul>
   *
   * @return Image initialized with Surface contents or null
   * @see <a href="https://fiddle.skia.org/c/@Surface_makeImageSnapshot_2">https://fiddle.skia.org/c/@Surface_makeImageSnapshot_2</a>
   */
  @Nullable def makeImageSnapshot(@NotNull area: IRect): Image = {
    try {
      assert(area != null, "Can’t Surface.makeImageSnapshot with area == null")
      Stats.onNativeCall()
      val ptr = Surface._nMakeImageSnapshotR(_ptr, area._left, area._top, area._right, area._bottom)
      if (ptr == 0) {
        null
      } else {
        new Image(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Draws Surface contents to canvas, with its top-left corner at (x, y).</p>
   *
   * <p>If Paint paint is not null, apply ColorFilter, alpha, ImageFilter, and BlendMode.</p>
   *
   * @param canvas Canvas drawn into
   * @param x      horizontal offset in Canvas
   * @param y      vertical offset in Canvas
   * @param paint  Paint containing BlendMode, ColorFilter, ImageFilter, and so on; or null
   * @see <a href="https://fiddle.skia.org/c/@Surface_draw">https://fiddle.skia.org/c/@Surface_draw</a>
   */
  def draw(canvas: Canvas, x: Int, y: Int, paint: Paint): Unit = {
    try {
      Stats.onNativeCall()
      Surface._nDraw(_ptr, Native.getPtr(canvas), x, y, Native.getPtr(paint))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(canvas)
      ReferenceUtil.reachabilityFence(paint)
    }
  }

  def peekPixels(@NotNull pixmap: Pixmap): Boolean = {
    try {
      Stats.onNativeCall()
      Surface._nPeekPixels(_ptr, Native.getPtr(pixmap))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(pixmap)
    }
  }

  def readPixels(pixmap: Pixmap, srcX: Int, srcY: Int): Boolean = {
    try {
      Stats.onNativeCall()
      Surface._nReadPixelsToPixmap(_ptr, Native.getPtr(pixmap), srcX, srcY)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(pixmap)
    }
  }

  /**
   * <p>Copies Rect of pixels from Surface into bitmap.</p>
   *
   * <p>Source Rect corners are (srcX, srcY) and Surface (width(), height()).
   * Destination Rect corners are (0, 0) and (bitmap.width(), bitmap.height()).
   * Copies each readable pixel intersecting both rectangles, without scaling,
   * converting to bitmap.colorType() and bitmap.alphaType() if required.</p>
   *
   * <p>Pixels are readable when Surface is raster, or backed by a GPU.</p>
   *
   * <p>The destination pixel storage must be allocated by the caller.</p>
   *
   * <p>Pixel values are converted only if ColorType and AlphaType do not match.
   * Only pixels within both source and destination rectangles are copied.
   * dst contents outside Rect intersection are unchanged.</p>
   *
   * <p>Pass negative values for srcX or srcY to offset pixels across or down destination.</p>
   *
   * <p>Does not copy, and returns false if:</p>
   *
   * <ul>
   * <li>Source and destination rectangles do not intersect.</li>
   * <li>Surface pixels could not be converted to dst.colorType() or dst.alphaType().</li>
   * <li>dst pixels could not be allocated.</li>
   * <li>dst.rowBytes() is too small to contain one row of pixels.</li>
   * </ul>
   *
   * @param bitmap storage for pixels copied from SkSurface
   * @param srcX   offset into readable pixels on x-axis; may be negative
   * @param srcY   offset into readable pixels on y-axis; may be negative
   * @return true if pixels were copied
   * @see <a href="https://fiddle.skia.org/c/@Surface_readPixels_3">https://fiddle.skia.org/c/@Surface_readPixels_3</a>
   */
  def readPixels(bitmap: Bitmap, srcX: Int, srcY: Int): Boolean = {
    try {
      Stats.onNativeCall()
      Surface._nReadPixels(_ptr, Native.getPtr(bitmap), srcX, srcY)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(bitmap)
    }
  }

  def writePixels(pixmap: Pixmap, x: Int, y: Int): Unit = {
    try {
      Stats.onNativeCall()
      Surface._nWritePixelsFromPixmap(_ptr, Native.getPtr(pixmap), x, y)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(pixmap)
    }
  }

  /**
   * <p>Copies Rect of pixels from the src Bitmap to the Surface.</p>
   *
   * <p>Source Rect corners are (0, 0) and (src.width(), src.height()).
   * Destination Rect corners are (dstX, dstY) and (dstX + Surface width(), dstY + Surface height()).</p>
   *
   * <p>Copies each readable pixel intersecting both rectangles, without scaling,
   * converting to Surface colorType() and Surface alphaType() if required.</p>
   *
   * @param bitmap storage for pixels to copy to Surface
   * @param x      x-axis position relative to Surface to begin copy; may be negative
   * @param y      y-axis position relative to Surface to begin copy; may be negative
   * @see <a href="https://fiddle.skia.org/c/@Surface_writePixels_2">https://fiddle.skia.org/c/@Surface_writePixels_2</a>
   */
  def writePixels(bitmap: Bitmap, x: Int, y: Int): Unit = {
    try {
      Stats.onNativeCall()
      Surface._nWritePixels(_ptr, Native.getPtr(bitmap), x, y)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(bitmap)
    }
  }

  /**
   * <p>Call to ensure all reads/writes of the surface have been issued to the underlying 3D API.</p>
   *
   * <p>Skia will correctly order its own draws and pixel operations.
   * This must to be used to ensure correct ordering when the surface backing store is accessed
   * outside Skia (e.g. direct use of the 3D API or a windowing system).
   * DirectContext has additional flush and submit methods that apply to all surfaces and images created from
   * a DirectContext.
   */
  def flushAndSubmit(): Unit = {
    try {
      Stats.onNativeCall()
      Surface._nFlushAndSubmit(_ptr, false)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Call to ensure all reads/writes of the surface have been issued to the underlying 3D API.</p>
   *
   * <p>Skia will correctly order its own draws and pixel operations.
   * This must to be used to ensure correct ordering when the surface backing store is accessed
   * outside Skia (e.g. direct use of the 3D API or a windowing system).
   * DirectContext has additional flush and submit methods that apply to all surfaces and images created from
   * a DirectContext.
   *
   * @param syncCpu a flag determining if cpu should be synced
   */
  def flushAndSubmit(syncCpu: Boolean): Unit = {
    try {
      Stats.onNativeCall()
      Surface._nFlushAndSubmit(_ptr, syncCpu)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def flush(): Unit = {
    try {
      Stats.onNativeCall()
      Surface._nFlush(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>May return true if the caller is the only owner.</p>
   *
   * <p>Ensures that all previous owner's actions are complete.</p>
   */
  def isUnique: Boolean = {
    try {
      Stats.onNativeCall()
      Surface._nUnique(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  override def close(): Unit = {
    if (_canvas != null) {
      _canvas.invalidate()
      _canvas = null
    }
    super.close()
  }
}