package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

import java.nio.*

object Bitmap {
  /**
   * Creates an empty Bitmap without pixels, with {@link ColorType# UNKNOWN},
   * {@link ColorAlphaType# UNKNOWN}, and with a width and height of zero.
   * PixelRef origin is set to (0, 0). Bitmap is not volatile.
   *
   * Use {@link # setImageInfo ( ImageInfo, long)} to associate ColorType, ColorAlphaType, width, and height after Bitmap has been created.
   *
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_empty_constructor">https://fiddle.skia.org/c/@Bitmap_empty_constructor</a>
   */
  def apply(): Bitmap = {
    val bitmap = new Bitmap(_nMake())
    Stats.onNativeCall()
    bitmap
  }

  @NotNull
  @Contract("-> new") def makeFromImage(@NotNull image: Image): Bitmap = {
    assert(image != null, "Can’t makeFromImage with image == null")
    val bitmap = Bitmap()
    bitmap.allocPixels(image.getImageInfo)
    if (image.readPixels(bitmap)) {
      bitmap
    } else {
      bitmap.close()
      throw new RuntimeException("Failed to readPixels from " + image)
    }
  }

  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nMake(): Long

  @ApiStatus.Internal
  @native def _nMakeClone(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nSwap(ptr: Long, otherPtr: Long): Unit

  @ApiStatus.Internal
  @native def _nGetPixmap(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nGetImageInfo(ptr: Long): ImageInfo

  @ApiStatus.Internal
  @native def _nGetRowBytesAsPixels(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nIsNull(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nGetRowBytes(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nSetAlphaType(ptr: Long, alphaType: Int): Boolean

  @ApiStatus.Internal
  @native def _nComputeByteSize(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nIsImmutable(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nSetImmutable(ptr: Long): Unit

  @ApiStatus.Internal
  @native def _nIsVolatile(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nSetVolatile(ptr: Long, value: Boolean): Unit

  @ApiStatus.Internal
  @native def _nReset(ptr: Long): Unit

  @ApiStatus.Internal
  @native def _nComputeIsOpaque(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nSetImageInfo(ptr: Long, width: Int, height: Int, colorType: Int, alphaType: Int, colorSpacePtr: Long, rowBytes: Long): Boolean

  @ApiStatus.Internal
  @native def _nAllocPixelsFlags(ptr: Long, width: Int, height: Int, colorType: Int, alphaType: Int, colorSpacePtr: Long, flags: Int): Boolean

  @ApiStatus.Internal
  @native def _nAllocPixelsRowBytes(ptr: Long, width: Int, height: Int, colorType: Int, alphaType: Int, colorSpacePtr: Long, rowBytes: Long): Boolean

  @ApiStatus.Internal
  @native def _nInstallPixels(ptr: Long, width: Int, height: Int, colorType: Int, alphaType: Int, colorSpacePtr: Long, pixels: Array[Byte], rowBytes: Long): Boolean

  @ApiStatus.Internal
  @native def _nAllocPixels(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nGetPixelRef(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nGetPixelRefOrigin(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nSetPixelRef(ptr: Long, pixelRefPtr: Long, dx: Int, dy: Int): Unit

  @ApiStatus.Internal
  @native def _nIsReadyToDraw(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nGetGenerationId(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nNotifyPixelsChanged(ptr: Long): Unit

  @ApiStatus.Internal
  @native def _nErase4f(ptr: Long, r: Float, g: Float, b: Float, a: Float): Unit

  @ApiStatus.Internal
  @native def _nEraseRect4f(ptr: Long, r: Float, g: Float, b: Float, a: Float, left: Int, top: Int, right: Int, bottom: Int): Unit

  @ApiStatus.Internal
  @native def _nErase(ptr: Long, color: Int): Unit

  @ApiStatus.Internal
  @native def _nEraseRect(ptr: Long, color: Int, left: Int, top: Int, right: Int, bottom: Int): Unit

  @ApiStatus.Internal
  @native def _nGetColor(ptr: Long, x: Int, y: Int): Int

  @ApiStatus.Internal
  @native def _nGetColor4f(ptr: Long, x: Int, y: Int): Color4f

  @ApiStatus.Internal
  @native def _nGetAlphaf(ptr: Long, x: Int, y: Int): Float

  @ApiStatus.Internal
  @native def _nExtractSubset(ptr: Long, dstPtr: Long, left: Int, top: Int, right: Int, bottom: Int): Boolean

  @ApiStatus.Internal
  @native def _nReadPixels(ptr: Long, width: Int, height: Int, colorType: Int, alphaType: Int, colorSpacePtr: Long, dstRowBytes: Long, srcX: Int, srcY: Int): Array[Byte]

  @ApiStatus.Internal
  @native def _nExtractAlpha(ptr: Long, dstPtr: Long, paintPtr: Long): IPoint

  @ApiStatus.Internal
  @native def _nPeekPixels(ptr: Long): ByteBuffer

  @ApiStatus.Internal
  @native def _nMakeShader(ptr: Long, tmx: Int, tmy: Int, samplingMode: Long, localMatrix: Array[Float]): Long

  try Library.staticLoad()
}

class Bitmap @ApiStatus.Internal(ptr: Long) extends Managed(ptr, Bitmap._FinalizerHolder.PTR) with IHasImageInfo {
  @ApiStatus.Internal var _imageInfo: ImageInfo = null



  /**
   * Copies settings from src to returned Bitmap. Shares pixels if src has pixels
   * allocated, so both bitmaps reference the same pixels.
   *
   * @return copy of src
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_copy_const_SkBitmap">https://fiddle.skia.org/c/@Bitmap_copy_const_SkBitmap</a>
   */
  @NotNull
  @Contract("-> new") def makeClone: Bitmap = {
    try {
      Stats.onNativeCall()
      new Bitmap(Bitmap._nMakeClone(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Swaps the fields of the two bitmaps.
   *
   * @param other Bitmap exchanged with original
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_swap">https://fiddle.skia.org/c/@Bitmap_swap</a>
   */
  def swap(@NotNull other: Bitmap): Unit = {
    Stats.onNativeCall()
    Bitmap._nSwap(_ptr, Native.getPtr(other))
    _imageInfo = null
    ReferenceUtil.reachabilityFence(this)
    ReferenceUtil.reachabilityFence(other)
  }

  @NotNull override def getImageInfo: ImageInfo = {
    try {
      if (_imageInfo == null) {
        Stats.onNativeCall()
        _imageInfo = Bitmap._nGetImageInfo(_ptr)
      }
      _imageInfo
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns number of pixels that fit on row. Should be greater than or equal to
   * getWidth().
   *
   * @return maximum pixels per row
   */
  def getRowBytesAsPixels: Int = {
    try {
      Stats.onNativeCall()
      Bitmap._nGetRowBytesAsPixels(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns true if PixelRef is null.
   *
   * Does not check if width or height are zero; call {@link # drawsNothing ( )}
   * to check width, height, and PixelRef.
   *
   * @return true if no PixelRef is associated
   */
  def isNull: Boolean = {
    try {
      Stats.onNativeCall()
      Bitmap._nIsNull(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns true if width or height are zero, or if PixelRef is null.
   * If true, Bitmap has no effect when drawn or drawn into.
   *
   * @return true if drawing has no effect
   */
  def drawsNothing: Boolean = {
    isEmpty || isNull
  }

  /**
   * Returns row bytes, the interval from one pixel row to the next. Row bytes
   * is at least as large as: getWidth() * getBytesPerPixel().
   *
   * Returns zero if getColorType() is {@link ColorType# UNKNOWN}, or if row bytes
   * supplied to {@link # setImageInfo ( ImageInfo )} is not large enough to hold a row of pixels.
   *
   * @return byte length of pixel row
   */
  def getRowBytes: Long = {
    try {
      Stats.onNativeCall()
      Bitmap._nGetRowBytes(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Sets alpha type, if argument is compatible with current color type.
   * Returns true unless argument is {@link ColorAlphaType# UNKNOWN} and current
   * value is {@link ColorAlphaType# UNKNOWN}.</p>
   *
   * <p>Returns true if current color type is {@link ColorType# UNKNOWN}.
   * Argument is ignored, and alpha type remains {@link ColorAlphaType# UNKNOWN}.</p>
   *
   * <p>Returns true if current color type is {@link ColorType# RGB_565} or
   * {@link ColorType# GRAY_8}. Argument is ignored, and alpha type remains
   * {@link ColorAlphaType# OPAQUE}.</p>
   *
   * <p>If current color type is {@link ColorType# ARGB_4444}, {@link ColorType# RGBA_8888},
   * {@link ColorType# BGRA_8888}, or {@link ColorType# RGBA_F16}: returns true unless
   * argument is {@link ColorAlphaType# UNKNOWN} and current alpha type is not
   * {@link ColorAlphaType# UNKNOWN}. If current alpha type is
   * {@link ColorAlphaType# UNKNOWN}, argument is ignored.</p>
   *
   * <p>If current color type is {@link ColorType# ALPHA_8}, returns true unless
   * argument is {@link ColorAlphaType# UNKNOWN} and current alpha type is not
   * {@link ColorAlphaType# UNKNOWN}. If current alpha type is
   * {@link ColorAlphaType# UNKNOWN}, argument is ignored. If argument is
   * {@link ColorAlphaType# UNPREMUL}, it is treated as {@link ColorAlphaType# PREMUL}.</p>
   *
   * <p>This changes alpha type in PixelRef; all bitmaps sharing PixelRef
   * are affected.</p>
   *
   * @return true if alpha type is set
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_setAlphaType">https://fiddle.skia.org/c/@Bitmap_setAlphaType</a>
   */
  def setAlphaType(alphaType: ColorAlphaType): Boolean = {
    try {
      Stats.onNativeCall()
      _imageInfo = null
      Bitmap._nSetAlphaType(_ptr, alphaType.ordinal)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns minimum memory required for pixel storage.
   * Does not include unused memory on last row when getRowBytesAsPixels() exceeds getWidth().
   * Returns zero if height() or width() is 0.
   * Returns getHeight() times getRowBytes() if getColorType() is {@link ColorType# UNKNOWN}.
   *
   * @return size in bytes of image buffer
   */
  def computeByteSize: Long = {
    try {
      Stats.onNativeCall()
      Bitmap._nComputeByteSize(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns true if pixels can not change.</p>
   *
   * <p>Most immutable Bitmap checks trigger an assert only on debug builds.</p>
   *
   * @return true if pixels are immutable
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_isImmutable">https://fiddle.skia.org/c/@Bitmap_isImmutable</a>
   */
  def isImmutable: Boolean = {
    try {
      Stats.onNativeCall()
      Bitmap._nIsImmutable(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Sets internal flag to mark Bitmap as immutable. Once set, pixels can not change.
   * Any other bitmap sharing the same PixelRef are also marked as immutable.
   * Once PixelRef is marked immutable, the setting cannot be cleared.</p>
   *
   * <p>Writing to immutable Bitmap pixels triggers an assert on debug builds.</p>
   *
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_setImmutable">https://fiddle.skia.org/c/@Bitmap_setImmutable</a>
   */
  @NotNull
  @Contract("-> this") def setImmutable: Bitmap = {
    Stats.onNativeCall()
    Bitmap._nSetImmutable(_ptr)
    this
  }

  /**
   * <p>Resets to its initial state; all fields are set to zero, as if Bitmap had
   * been initialized by Bitmap().</p>
   *
   * <p>Sets width, height, row bytes to zero; pixel address to nullptr; ColorType to
   * {@link ColorType# UNKNOWN}; and ColorAlphaType to {@link ColorAlphaType# UNKNOWN}.</p>
   *
   * <p>If PixelRef is allocated, its reference count is decreased by one, releasing
   * its memory if Bitmap is the sole owner.</p>
   *
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_reset">https://fiddle.skia.org/c/@Bitmap_reset</a>
   */
  @NotNull
  @Contract("-> this") def reset: Bitmap = {
    Stats.onNativeCall()
    _imageInfo = null
    Bitmap._nReset(_ptr)
    this
  }

  /**
   * <p>Returns true if all pixels are opaque. ColorType determines how pixels
   * are encoded, and whether pixel describes alpha. Returns true for ColorType
   * without alpha in each pixel; for other ColorType, returns true if all
   * pixels have alpha values equivalent to 1.0 or greater.</p>
   *
   * <p>For {@link ColorType# RGB_565} or {@link ColorType# GRAY_8}: always
   * returns true. For {@link ColorType# ALPHA_8}, {@link ColorType# BGRA_8888},
   * {@link ColorType# RGBA_8888}: returns true if all pixel alpha values are 255.
   * For {@link ColorType# ARGB_4444}: returns true if all pixel alpha values are 15.
   * For {@link ColorType# RGBA_F16}: returns true if all pixel alpha values are 1.0 or
   * greater.</p>
   *
   * <p>Returns false for {@link ColorType# UNKNOWN}.
   *
   * @return true if all pixels have opaque values or ColorType is opaque
   */
  def computeIsOpaque: Boolean = {
    try {
      Stats.onNativeCall()
      Bitmap._nComputeIsOpaque(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns IRect { 0, 0, width(), height() }.
   *
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_getBounds_2">https://fiddle.skia.org/c/@Bitmap_getBounds_2</a>
   */
  @NotNull def getBounds: IRect = {
    IRect.makeXYWH(0, 0, getWidth, getHeight)
  }

  /**
   * Returns the bounds of this bitmap, offset by its PixelRef origin.
   */
  @NotNull def getSubset: IRect = {
    val origin = getPixelRefOrigin
    IRect.makeXYWH(origin.getX, origin.getY, getWidth, getHeight)
  }

  /**
   * <p>Sets width, height, ColorAlphaType, ColorType, ColorSpace.
   * Frees pixels, and returns true if successful.</p>
   *
   * <p>imageInfo.getAlphaType() may be altered to a value permitted by imageInfo.getColorSpace().
   * If imageInfo.getColorType() is {@link ColorType# UNKNOWN}, imageInfo.getAlphaType() is
   * set to {@link ColorAlphaType# UNKNOWN}.
   * If imageInfo.colorType() is {@link ColorType# ALPHA_8} and imageInfo.getAlphaType() is
   * {@link ColorAlphaType# UNPREMUL}, imageInfo.getAlphaType() is replaced by {@link ColorAlphaType# PREMUL}.
   * If imageInfo.colorType() is {@link ColorType# RGB_565} or {@link ColorType# GRAY_8},
   * imageInfo.getAlphaType() is set to {@link ColorAlphaType# OPAQUE}.
   * If imageInfo.colorType() is {@link ColorType# ARGB_4444}, {@link ColorType# RGBA_8888},
   * {@link ColorType# BGRA_8888}, or {@link ColorType# RGBA_F16}: imageInfo.getAlphaType() remains
   * unchanged.</p>
   *
   * <p>Calls reset() and returns false if:
   * - rowBytes exceeds 31 bits
   * - imageInfo.getWidth() is negative
   * - imageInfo.getHeight() is negative
   * - rowBytes is positive and less than imageInfo.getWidth() times imageInfo.getBytesPerPixel()</p>
   *
   * @param imageInfo contains width, height, AlphaType, ColorType, ColorSpace
   * @return true if ImageInfo set successfully
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_setInfo">https://fiddle.skia.org/c/@Bitmap_setInfo</a>
   */
  def setImageInfo(@NotNull imageInfo: ImageInfo): Boolean = {
    _imageInfo = null
    setImageInfo(imageInfo, 0)
  }

  /**
   * <p>Sets width, height, ColorAlphaType, ColorType, ColorSpace, and optional
   * rowBytes. Frees pixels, and returns true if successful.</p>
   *
   * <p>imageInfo.getAlphaType() may be altered to a value permitted by imageInfo.getColorSpace().
   * If imageInfo.getColorType() is {@link ColorType# UNKNOWN}, imageInfo.getAlphaType() is
   * set to {@link ColorAlphaType# UNKNOWN}.
   * If imageInfo.colorType() is {@link ColorType# ALPHA_8} and imageInfo.getAlphaType() is
   * {@link ColorAlphaType# UNPREMUL}, imageInfo.getAlphaType() is replaced by {@link ColorAlphaType# PREMUL}.
   * If imageInfo.colorType() is {@link ColorType# RGB_565} or {@link ColorType# GRAY_8},
   * imageInfo.getAlphaType() is set to {@link ColorAlphaType# OPAQUE}.
   * If imageInfo.colorType() is {@link ColorType# ARGB_4444}, {@link ColorType# RGBA_8888},
   * {@link ColorType# BGRA_8888}, or {@link ColorType# RGBA_F16}: imageInfo.getAlphaType() remains
   * unchanged.</p>
   *
   * <p>rowBytes must equal or exceed imageInfo.getMinRowBytes(). If imageInfo.getColorSpace() is
   * {@link ColorType# UNKNOWN}, rowBytes is ignored and treated as zero; for all other
   * ColorSpace values, rowBytes of zero is treated as imageInfo.getMinRowBytes().</p>
   *
   * <p>Calls reset() and returns false if:
   * - rowBytes exceeds 31 bits
   * - imageInfo.getWidth() is negative
   * - imageInfo.getHeight() is negative
   * - rowBytes is positive and less than imageInfo.getWidth() times imageInfo.getBytesPerPixel()</p>
   *
   * @param imageInfo contains width, height, AlphaType, ColorType, ColorSpace
   * @param rowBytes  imageInfo.getMinRowBytes() or larger; or zero
   * @return true if ImageInfo set successfully
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_setInfo">https://fiddle.skia.org/c/@Bitmap_setInfo</a>
   */
  def setImageInfo(@NotNull imageInfo: ImageInfo, rowBytes: Long): Boolean = {
    try {
      _imageInfo = null
      Stats.onNativeCall()
      Bitmap
        ._nSetImageInfo(_ptr, imageInfo.width, imageInfo.height, imageInfo.colorInfo.colorType.ordinal, imageInfo
          .colorInfo.alphaType.ordinal, Native.getPtr(imageInfo.colorInfo.colorSpace), rowBytes)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(imageInfo.colorInfo.colorSpace)
    }
  }

  /**
   * <p>Sets ImageInfo to info following the rules in setImageInfo() and allocates pixel
   * memory. Memory is zeroed.</p>
   *
   * <p>Returns false and calls reset() if ImageInfo could not be set, or memory could
   * not be allocated, or memory could not optionally be zeroed.</p>
   *
   * <p>On most platforms, allocating pixel memory may succeed even though there is
   * not sufficient memory to hold pixels; allocation does not take place
   * until the pixels are written to. The actual behavior depends on the platform
   * implementation of calloc().</p>
   *
   * @param imageInfo  contains width, height, ColorAlphaType, ColorType, ColorSpace
   * @param zeroPixels whether pixels should be zeroed
   * @return true if pixels allocation is successful
   */
  def allocPixelsFlags(@NotNull imageInfo: ImageInfo, zeroPixels: Boolean): Boolean = {
    try {
      _imageInfo = null
      Stats.onNativeCall()
      Bitmap
        ._nAllocPixelsFlags(_ptr, imageInfo.width, imageInfo.height, imageInfo.colorInfo.colorType
                                                                                .ordinal, imageInfo
          .colorInfo.alphaType.ordinal, Native.getPtr(imageInfo.colorInfo.colorSpace), if (zeroPixels) {
          1
        } else {
          0
        })
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(imageInfo.colorInfo.colorSpace)
    }
  }

  /**
   * <p>Sets ImageInfo to info following the rules in setImageInfo() and allocates pixel
   * memory. rowBytes must equal or exceed info.width() times info.bytesPerPixel(),
   * or equal zero.</p>
   *
   * <p>Returns false and calls reset() if ImageInfo could not be set, or memory could
   * not be allocated.</p>
   *
   * <p>On most platforms, allocating pixel memory may succeed even though there is
   * not sufficient memory to hold pixels; allocation does not take place
   * until the pixels are written to. The actual behavior depends on the platform
   * implementation of malloc().</p>
   *
   * @param info     contains width, height, ColorAlphaType, ColorType, ColorSpace
   * @param rowBytes size of pixel row or larger; may be zero
   * @return true if pixel storage is allocated
   */
  def allocPixels(@NotNull info: ImageInfo, rowBytes: Long): Boolean = {
    try {
      _imageInfo = null
      Stats.onNativeCall()
      Bitmap._nAllocPixelsRowBytes(_ptr, info.width, info.height, info.colorInfo.colorType.ordinal, info.colorInfo
                                                                                                            .alphaType
                                                                                                            .ordinal, Native
        .getPtr(info.colorInfo.colorSpace), rowBytes)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(info.colorInfo.colorSpace)
    }
  }

  /**
   * <p>Sets ImageInfo to info following the rules in {@link # setImageInfo ( ImageInfo, long)} and allocates pixel
   * memory.</p>
   *
   * <p>Returns false and calls reset() if ImageInfo could not be set, or memory could
   * not be allocated.</p>
   *
   * <p>On most platforms, allocating pixel memory may succeed even though there is
   * not sufficient memory to hold pixels; allocation does not take place
   * until the pixels are written to. The actual behavior depends on the platform
   * implementation of malloc().</p>
   *
   * @param imageInfo contains width, height, ColorAlphaType, ColorType, ColorSpace
   * @return true if pixel storage is allocated
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_allocPixels_2">https://fiddle.skia.org/c/@Bitmap_allocPixels_2</a>
   */
  def allocPixels(@NotNull imageInfo: ImageInfo): Boolean = {
    allocPixels(imageInfo, imageInfo.getMinRowBytes)
  }

  /**
   * Sets ImageInfo to width, height, and native color type; and allocates
   * pixel memory. Sets ImageInfo to {@link ColorAlphaType# PREMUL}.
   *
   * Calls reset() and returns false if width exceeds 29 bits or is negative,
   * or height is negative.
   *
   * Returns false if allocation fails.
   *
   * Use to create Bitmap that matches the native pixel arrangement on
   * the platform. Bitmap drawn to output device skips converting its pixel format.
   *
   * @param width  pixel column count; must be zero or greater
   * @param height pixel row count; must be zero or greater
   * @return true if pixel storage is allocated
   */
  def allocN32Pixels(width: Int, height: Int): Boolean = {
    allocN32Pixels(width, height, false)
  }

  /**
   * Sets ImageInfo to width, height, and native color type; and allocates
   * pixel memory. If opaque is true, sets ImageInfo to {@link ColorAlphaType# OPAQUE};
   * otherwise, sets to {@link ColorAlphaType# PREMUL}.
   *
   * Calls reset() and returns false if width exceeds 29 bits or is negative,
   * or height is negative.
   *
   * Returns false if allocation fails.
   *
   * Use to create Bitmap that matches the native pixel arrangement on
   * the platform. Bitmap drawn to output device skips converting its pixel format.
   *
   * @param width  pixel column count; must be zero or greater
   * @param height pixel row count; must be zero or greater
   * @param opaque true if pixels do not have transparency
   * @return true if pixel storage is allocated
   */
  def allocN32Pixels(width: Int, height: Int, opaque: Boolean): Boolean = {
    allocPixels(ImageInfo
      .makeN32(width, height, if (opaque) {
        ColorAlphaType.OPAQUE
      } else {
        ColorAlphaType.PREMUL
      }))
  }

  def installPixels(pixels: Array[Byte]): Boolean = installPixels(getImageInfo, pixels, getRowBytes)

  /**
   * <p>Sets ImageInfo to info following the rules in setImageInfo(), and creates PixelRef
   * containing pixels and rowBytes.</p>
   *
   * <p>If ImageInfo could not be set, or rowBytes is less than info.getMinRowBytes():
   * calls reset(), and returns false.</p>
   *
   * @param info     contains width, height, SkAlphaType, SkColorType, SkColorSpace
   * @param pixels   pixel storage
   * @param rowBytes size of pixel row or larger
   * @return true if ImageInfo is set to info
   */
  def installPixels(@NotNull info: ImageInfo, @Nullable pixels: Array[Byte], rowBytes: Long): Boolean = {
    try {
      _imageInfo = null
      Stats.onNativeCall()
      Bitmap
        ._nInstallPixels(_ptr, info.width, info.height, info.colorInfo.colorType.ordinal, info.colorInfo.alphaType
                                                                                                  .ordinal, Native
          .getPtr(info.colorInfo.colorSpace), pixels, rowBytes)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(info.colorInfo.colorSpace)
    }
  }

  /**
   * <p>Allocates pixel memory with HeapAllocator, and replaces existing PixelRef.
   * The allocation size is determined by ImageInfo width, height, and ColorType.</p>
   *
   * <p>Returns false if info().colorType() is {@link ColorType# UNKNOWN}, or allocation fails.</p>
   *
   * @return true if the allocation succeeds
   */
  def allocPixels: Boolean = {
    try {
      _imageInfo = null
      Stats.onNativeCall()
      Bitmap._nAllocPixels(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns PixelRef, which contains: pixel base address; its dimensions; and
   * rowBytes(), the interval from one row to the next.
   * PixelRef may be shared by multiple bitmaps.
   * If PixelRef has not been set, returns null.
   *
   * @return SkPixelRef, or null
   */
  @Nullable def getPixelRef: PixelRef = {
    try {
      Stats.onNativeCall()
      val res = Bitmap._nGetPixelRef(_ptr)
      if (res == 0) {
        null
      } else {
        new PixelRef(res)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns origin of pixels within PixelRef. Bitmap bounds is always contained
   * by PixelRef bounds, which may be the same size or larger. Multiple Bitmap
   * can share the same PixelRef, where each Bitmap has different bounds.</p>
   *
   * <p>The returned origin added to Bitmap dimensions equals or is smaller than the
   * PixelRef dimensions.</p>
   *
   * <p>Returns (0, 0) if PixelRef is nullptr.</p>
   *
   * @return pixel origin within PixelRef
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_pixelRefOrigin">https://fiddle.skia.org/c/@Bitmap_pixelRefOrigin</a>
   */
  @NotNull def getPixelRefOrigin: IPoint = {
    try {
      Stats.onNativeCall()
      val res = Bitmap._nGetPixelRefOrigin(_ptr)
      new IPoint((res & 0xFFFFFFFF).toInt, (res >>> 32).toInt)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Replaces pixelRef and origin in Bitmap. dx and dy specify the offset
   * within the PixelRef pixels for the top-left corner of the bitmap.</p>
   *
   * <p>Asserts in debug builds if dx or dy are out of range. Pins dx and dy
   * to legal range in release builds.</p>
   *
   * <p>The caller is responsible for ensuring that the pixels match the
   * ColorType and ColorAlphaType in ImageInfo.</p>
   *
   * @param pixelRef PixelRef describing pixel address and rowBytes()
   * @param dx       column offset in PixelRef for bitmap origin
   * @param dy       row offset in PixelRef for bitmap origin
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_setPixelRef">https://fiddle.skia.org/c/@Bitmap_setPixelRef</a>
   */
  @NotNull
  @Contract("_, _, _ -> this") def setPixelRef(@Nullable pixelRef: PixelRef, dx: Int, dy: Int): Bitmap = {
    try {
      _imageInfo = null
      Stats.onNativeCall()
      Bitmap._nSetPixelRef(_ptr, Native.getPtr(pixelRef), dx, dy)
      this
    } finally {
      ReferenceUtil.reachabilityFence(pixelRef)
    }
  }

  /**
   * Returns true if Bitmap can be drawn.
   *
   * @return true if getPixels() is not null
   */
  def isReadyToDraw: Boolean = {
    try {
      Stats.onNativeCall()
      Bitmap._nIsReadyToDraw(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns a unique value corresponding to the pixels in PixelRef.
   * Returns a different value after notifyPixelsChanged() has been called.
   * Returns zero if PixelRef is null.</p>
   *
   * <p>Determines if pixels have changed since last examined.</p>
   *
   * @return unique value for pixels in PixelRef
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_getGenerationID">https://fiddle.skia.org/c/@Bitmap_getGenerationID</a>
   */
  def getGenerationId: Int = {
    try {
      Stats.onNativeCall()
      Bitmap._nGetGenerationId(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Marks that pixels in PixelRef have changed. Subsequent calls to
   * getGenerationId() return a different value.</p>
   *
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_notifyPixelsChanged">https://fiddle.skia.org/c/@Bitmap_notifyPixelsChanged</a>
   */
  @NotNull
  @Contract("-> this") def notifyPixelsChanged: Bitmap = {
    try {
      Stats.onNativeCall()
      Bitmap._nNotifyPixelsChanged(_ptr)
      this
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Replaces pixel values with color, interpreted as being in the sRGB.
   * All pixels contained by getBounds() are affected. If the getColorType() is
   * {@link ColorType# GRAY_8} or {@link ColorType# RGB_565}, then alpha is ignored; RGB is
   * treated as opaque. If getColorType() is {@link ColorType# ALPHA_8}, then RGB is ignored.
   *
   * @param color unpremultiplied color
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_eraseColor">https://fiddle.skia.org/c/@Bitmap_eraseColor</a>
   */
  @NotNull
  @Contract("_ -> this") def erase(@NotNull color: Color4f): Bitmap = {
    try {
      assert(color != null, "Bitmap::erase expected color != null")
      Stats.onNativeCall()
      Bitmap._nErase4f(_ptr, color.r, color.g, color.b, color.a)
      this
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Replaces pixel values with color, interpreted as being in the sRGB.
   * All pixels contained by getBounds() are affected. If the getColorType() is
   * {@link ColorType# GRAY_8} or {@link ColorType# RGB_565}, then alpha is ignored; RGB is
   * treated as opaque. If getColorType() is {@link ColorType# ALPHA_8}, then RGB is ignored.
   *
   * @param color unpremultiplied color
   * @param area  rectangle to fill
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_erase">https://fiddle.skia.org/c/@Bitmap_erase</a>
   */
  @NotNull
  @Contract("_, _ -> this") def erase(@NotNull color: Color4f, @NotNull area: IRect): Bitmap = {
    try {
      assert(color != null, "Bitmap::erase expected color != null")
      assert(area != null, "Bitmap::erase expected area != null")
      Stats.onNativeCall()
      Bitmap
        ._nEraseRect4f(_ptr, color.r, color.g, color.b, color.a, area._left, area._top, area._right, area._bottom)
      this
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Replaces pixel values with color, interpreted as being in the sRGB ColorSpace.
   * All pixels contained by getBounds() are affected. If the getColorType() is
   * {@link ColorType# GRAY_8} or {@link ColorType# RGB_565}, then alpha is ignored; RGB is
   * treated as opaque. If getColorType() is {@link ColorType# ALPHA_8}, then RGB is ignored.
   *
   * @param color unpremultiplied color
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_eraseColor">https://fiddle.skia.org/c/@Bitmap_eraseColor</a>
   */
  @NotNull
  @Contract("_ -> this") def erase(color: Int): Bitmap = {
    try {
      Stats.onNativeCall()
      Bitmap._nErase(_ptr, color)
      this
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Replaces pixel values inside area with color, interpreted as being in the sRGB
   * ColorSpace. If area does not intersect getBounds(), call has no effect.
   *
   * If the getColorType() is {@link ColorType# GRAY_8} or {@link ColorType# RGB_565},
   * then alpha is ignored; RGB is treated as opaque. If getColorType() is
   * {@link ColorType# ALPHA_8}, then RGB is ignored.
   *
   * @param color unpremultiplied color
   * @param area  rectangle to fill
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_erase">https://fiddle.skia.org/c/@Bitmap_erase</a>
   */
  @NotNull
  @Contract("_, _ -> this") def erase(color: Int, @NotNull area: IRect): Bitmap = {
    try {
      Stats.onNativeCall()
      Bitmap._nEraseRect(_ptr, color, area._left, area._top, area._right, area._bottom)
      this
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns pixel at (x, y) as unpremultiplied color.
   * Returns black with alpha if ColorType is {@link ColorType# ALPHA_8}.</p>
   *
   * <p>Input is not validated: out of bounds values of x or y returns undefined values
   * or may crash if. Fails if ColorType is {@link ColorType# UNKNOWN} or
   * pixel address is nullptr.</p>
   *
   * <p>ColorSpace in ImageInfo is ignored. Some color precision may be lost in the
   * conversion to unpremultiplied color; original pixel data may have additional
   * precision.</p>
   *
   * @param x column index, zero or greater, and less than getWidth()
   * @param y row index, zero or greater, and less than getHeight()
   * @return pixel converted to unpremultiplied color
   */
  def getColor(x: Int, y: Int): Int = {
    try {
      Stats.onNativeCall()
      Bitmap._nGetColor(_ptr, x, y)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns pixel at (x, y) as unpremultiplied color.
   * Returns black with alpha if ColorType is {@link ColorType# ALPHA_8}.</p>
   *
   * <p>Input is not validated: out of bounds values of x or y returns undefined values
   * or may crash if. Fails if ColorType is {@link ColorType# UNKNOWN} or
   * pixel address is nullptr.</p>
   *
   * <p>ColorSpace in ImageInfo is ignored. Some color precision may be lost in the
   * conversion to unpremultiplied color; original pixel data may have additional
   * precision.</p>
   *
   * @param x column index, zero or greater, and less than getWidth()
   * @param y row index, zero or greater, and less than getHeight()
   * @return pixel converted to unpremultiplied color
   */
  def getColor4f(x: Int, y: Int): Color4f = {
    try {
      Stats.onNativeCall()
      Bitmap._nGetColor4f(_ptr, x, y)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Look up the pixel at (x,y) and return its alpha component, normalized to [0..1].
   * This is roughly equivalent to GetColorA(getColor()), but can be more efficent
   * (and more precise if the pixels store more than 8 bits per component).</p>
   *
   * @param x column index, zero or greater, and less than getWidth()
   * @param y row index, zero or greater, and less than getHeight()
   * @return alpha converted to normalized float
   */
  def getAlphaf(x: Int, y: Int): Float = {
    try {
      Stats.onNativeCall()
      Bitmap._nGetAlphaf(_ptr, x, y)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Shares PixelRef with dst. Pixels are not copied; this and dst point
   * to the same pixels; dst.getBounds() are set to the intersection of subset
   * and the original getBounds().</p>
   *
   * <p>subset may be larger than getBounds(). Any area outside of getBounds() is ignored.</p>
   *
   * <p>Any contents of dst are discarded. isVolatile() setting is copied to dst.
   * dst is set to getColorType(), getAlphaType(), and getColorSpace().</p>
   *
   * <p>Return false if:
   * <ul>
   * <li>dst is null</li>
   * <li>PixelRef is null</li>
   * <li>subset does not intersect getBounds()</li>
   * </ul>
   *
   * @param dst    Bitmap set to subset
   * @param subset rectangle of pixels to reference
   * @return true if dst is replaced by subset
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_extractSubset">https://fiddle.skia.org/c/@Bitmap_extractSubset</a>
   */
  def extractSubset(@NotNull dst: Bitmap, @NotNull subset: IRect): Boolean = {
    try {
      Stats.onNativeCall()
      Bitmap._nExtractSubset(_ptr, Native.getPtr(dst), subset._left, subset._top, subset._right, subset._bottom)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(dst)
    }
  }

  @Nullable def readPixels: Array[Byte] = readPixels(getImageInfo, getRowBytes, 0, 0)

  /**
   * <p>Copies a rect of pixels from Bitmap. Copy starts at (srcX, srcY),
   * and does not exceed Bitmap (getWidth(), getHeight()).</p>
   *
   * <p>dstInfo specifies width, height, ColorType, AlphaType, and ColorSpace of
   * destination. dstRowBytes specifics the gap from one destination row to the next.
   * Returns true if pixels are copied. Returns false if:
   *
   * <ul>
   * <li>dstRowBytes is less than dstInfo.getMinRowBytes()</li>
   * <li>PixelRef is null</li>
   * </ul>
   *
   * <p>Pixels are copied only if pixel conversion is possible. If Bitmap getColorType() is
   * {@link ColorType# GRAY_8}, or {@link ColorType# ALPHA_8}; dstInfo.colorType() must match.
   * If Bitmap getClorType() is {@link ColorType# GRAY_8}, dstInfo.getColorSpace() must match.
   * If Bitmap getAlphaType() is {@link ColorAlphaType# OPAQUE}, dstInfo.getAlphaType() must
   * match. If Bitmap getColorSpace() is null, dstInfo.getColorSpace() must match. Returns
   * false if pixel conversion is not possible.</p>
   *
   * <p>srcX and srcY may be negative to copy only top or left of source. Returns
   * false if getWidth() or getHeight() is zero or negative.
   * Returns false if abs(srcX) &gt;= getWidth(), or if abs(srcY) &gt;= getHeight().</p>
   *
   * @param dstInfo     destination width, height, ColorType, AlphaType, ColorSpace
   * @param dstRowBytes destination row length
   * @param srcX        column index whose absolute value is less than width()
   * @param srcY        row index whose absolute value is less than height()
   * @return pixel data or null
   */
  @Nullable def readPixels(@NotNull dstInfo: ImageInfo, dstRowBytes: Long, srcX: Int, srcY: Int): Array[Byte] = {
    try {
      Stats.onNativeCall()
      Bitmap
        ._nReadPixels(_ptr, dstInfo.width, dstInfo.height, dstInfo.colorInfo.colorType.ordinal, dstInfo.colorInfo
                                                                                                           .alphaType
                                                                                                           .ordinal, Native
          .getPtr(dstInfo.colorInfo.colorSpace), dstRowBytes, srcX, srcY)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(dstInfo.colorInfo.colorSpace)
    }
  }

  /**
   * <p>Sets dst to alpha described by pixels. Returns false if dst cannot
   * be written to or dst pixels cannot be allocated.</p>
   *
   * @param dst holds PixelRef to fill with alpha layer
   * @return true if alpha layer was not constructed in dst PixelRef
   */
  def extractAlpha(@NotNull dst: Bitmap): Boolean = {
    extractAlpha(dst, null) != null
  }

  /**
   * <p>Sets dst to alpha described by pixels. Returns false if dst cannot
   * be written to or dst pixels cannot be allocated.</p>
   *
   * <p>If paint is not null and contains MaskFilter, MaskFilter
   * generates mask alpha from Bitmap. Returns offset to top-left position for dst
   * for alignment with Bitmap; (0, 0) unless MaskFilter generates mask.</p>
   *
   * @param dst   holds PixelRef to fill with alpha layer
   * @param paint holds optional MaskFilter; may be null
   * @return null if alpha layer was not constructed in dst PixelRef, IPoint otherwise
   */
  @Nullable def extractAlpha(@NotNull dst: Bitmap, @Nullable paint: Paint): IPoint = {
    try {
      Stats.onNativeCall()
      Bitmap._nExtractAlpha(_ptr, Native.getPtr(dst), Native.getPtr(paint))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(dst)
      ReferenceUtil.reachabilityFence(paint)
    }
  }

  /**
   * If pixel address is available, return ByteBuffer wrapping it.
   * If pixel address is not available, return null.
   *
   * @return ByteBuffer with direct access to pixels, or null
   * @see <a href="https://fiddle.skia.org/c/@Bitmap_peekPixels">https://fiddle.skia.org/c/@Bitmap_peekPixels</a>
   */
  @Nullable def peekPixels: ByteBuffer = {
    try {
      Stats.onNativeCall()
      Bitmap._nPeekPixels(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull def makeShader: Shader = makeShader(FilterTileMode.CLAMP, FilterTileMode.CLAMP, SamplingMode.DEFAULT, null)

  @NotNull def makeShader(@Nullable localMatrix: Matrix33): Shader = {
    makeShader(FilterTileMode.CLAMP, FilterTileMode
      .CLAMP, SamplingMode.DEFAULT, localMatrix)
  }

  @NotNull def makeShader(@NotNull tm: FilterTileMode): Shader = makeShader(tm, tm, SamplingMode.DEFAULT, null)

  @NotNull def makeShader(@NotNull tmx: FilterTileMode, @NotNull tmy: FilterTileMode): Shader = {
    makeShader(tmx, tmy, SamplingMode
      .DEFAULT, null)
  }

  @NotNull def makeShader(@NotNull tmx: FilterTileMode, @NotNull tmy: FilterTileMode, @Nullable localMatrix: Matrix33): Shader = {
    makeShader(tmx, tmy, SamplingMode
      .DEFAULT, localMatrix)
  }

  @NotNull def makeShader(@NotNull tmx: FilterTileMode, @NotNull tmy: FilterTileMode, @NotNull sampling: SamplingMode, @Nullable localMatrix: Matrix33): Shader = {
    try {
      assert(tmx != null, "Can’t Bitmap.makeShader with tmx == null")
      assert(tmy != null, "Can’t Bitmap.makeShader with tmy == null")
      assert(sampling != null, "Can’t Bitmap.makeShader with sampling == null")
      Stats.onNativeCall()
      new Shader(Bitmap._nMakeShader(_ptr, tmx.ordinal, tmy.ordinal, sampling.packed, if (localMatrix == null) {
        null
      } else {
        localMatrix.mat
      }))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}