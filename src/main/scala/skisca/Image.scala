package gay.menkissing
package skisca

import skisca.impl.*
import org.jetbrains.annotations.*

import java.nio.*

object Image {
  /**
   * @deprecated - use {@link # makeRasterFromBytes ( ImageInfo, byte[], long)}
   */
  @deprecated def makeRaster(imageInfo: ImageInfo, bytes: Array[Byte], rowBytes: Long): Image = {
    makeRasterFromBytes(imageInfo, bytes, rowBytes)
  }

  /**
   * <p>Creates Image from pixels.</p>
   *
   * <p>Image is returned if pixels are valid. Valid Pixmap parameters include:</p>
   * <ul>
   * <li>dimensions are greater than zero;</li>
   * <li>each dimension fits in 29 bits;</li>
   * <li>ColorType and AlphaType are valid, and ColorType is not ColorType.UNKNOWN;</li>
   * <li>row bytes are large enough to hold one row of pixels;</li>
   * <li>pixel address is not null.</li>
   * </ul>
   *
   * @param imageInfo ImageInfo
   * @param bytes     pixels array
   * @param rowBytes  how many bytes in a row
   * @return Image
   * @see <a href="https://fiddle.skia.org/c/@Image_MakeRasterCopy">https://fiddle.skia.org/c/@Image_MakeRasterCopy</a>
   */
  def makeRasterFromBytes(imageInfo: ImageInfo, bytes: Array[Byte], rowBytes: Long): Image = {
    try {
      Stats.onNativeCall()
      val ptr = _nMakeRasterFromBytes(imageInfo.width, imageInfo.height, imageInfo.colorInfo.colorType
                                                                                    .ordinal, imageInfo.colorInfo
                                                                                                       .alphaType
                                                                                                       .ordinal, Native
        .getPtr(imageInfo.colorInfo.colorSpace), bytes, rowBytes)
      if (ptr == 0) throw new RuntimeException("Failed to makeRaster " + imageInfo + " " + bytes + " " + rowBytes)
      new Image(ptr)
    } finally {
      ReferenceUtil.reachabilityFence(imageInfo.colorInfo.colorSpace)
    }
  }

  /**
   * @deprecated - use {@link # makeRasterFromData ( ImageInfo, Data, long)}
   */
  @deprecated def makeRaster(imageInfo: ImageInfo, data: Data, rowBytes: Long): Image = {
    makeRasterFromData(imageInfo, data, rowBytes)
  }

  /**
   * <p>Creates Image from pixels.</p>
   *
   * <p>Image is returned if pixels are valid. Valid Pixmap parameters include:</p>
   * <ul>
   * <li>dimensions are greater than zero;</li>
   * <li>each dimension fits in 29 bits;</li>
   * <li>ColorType and AlphaType are valid, and ColorType is not ColorType.UNKNOWN;</li>
   * <li>row bytes are large enough to hold one row of pixels;</li>
   * <li>pixel address is not null.</li>
   * </ul>
   *
   * @param imageInfo ImageInfo
   * @param data      pixels array
   * @param rowBytes  how many bytes in a row
   * @return Image
   */
  def makeRasterFromData(imageInfo: ImageInfo, data: Data, rowBytes: Long): Image = {
    try {
      Stats.onNativeCall()
      val ptr = _nMakeRasterFromData(imageInfo.width, imageInfo.height, imageInfo.colorInfo.colorType
                                                                                   .ordinal, imageInfo.colorInfo
                                                                                                      .alphaType
                                                                                                      .ordinal, Native
        .getPtr(imageInfo.colorInfo.colorSpace), Native.getPtr(data), rowBytes)
      if (ptr == 0) throw new RuntimeException("Failed to makeRaster " + imageInfo + " " + data + " " + rowBytes)
      new Image(ptr)
    } finally {
      ReferenceUtil.reachabilityFence(imageInfo.colorInfo.colorSpace)
      ReferenceUtil.reachabilityFence(data)
    }
  }

  /**
   * @deprecated - use {@link # makeRasterFromBitmap ( Bitmap )}
   */
  @deprecated def makeFromBitmap(@NotNull bitmap: Bitmap): Image = {
    makeRasterFromBitmap(bitmap)
  }

  /**
   * <p>Creates Image from bitmap, sharing or copying bitmap pixels. If the bitmap
   * is marked immutable, and its pixel memory is shareable, it may be shared
   * instead of copied.</p>
   *
   * <p>Image is returned if bitmap is valid. Valid Bitmap parameters include:</p>
   * <ul>
   * <li>dimensions are greater than zero;</li>
   * <li>each dimension fits in 29 bits;</li>
   * <li>ColorType and AlphaType are valid, and ColorType is not ColorType.UNKNOWN;</li>
   * <li>row bytes are large enough to hold one row of pixels;</li>
   * <li>pixel address is not nullptr.</li>
   * </ul>
   *
   * @param bitmap ImageInfo, row bytes, and pixels
   * @return created Image
   * @see <a href="https://fiddle.skia.org/c/@Image_MakeFromBitmap">https://fiddle.skia.org/c/@Image_MakeFromBitmap</a>
   */
  @NotNull
  @Contract("_ -> new") def makeRasterFromBitmap(@NotNull bitmap: Bitmap): Image = {
    try {
      assert(bitmap != null, "Can’t makeFromBitmap with bitmap == null")
      Stats.onNativeCall()
      val ptr = _nMakeRasterFromBitmap(Native.getPtr(bitmap))
      if (ptr == 0) throw new RuntimeException("Failed to Image::makeFromBitmap " + bitmap)
      new Image(ptr)
    } finally {
      ReferenceUtil.reachabilityFence(bitmap)
    }
  }

  /**
   * @deprecated - use {@link # makeRasterFromPixmap ( Pixmap )}
   */
  @deprecated def makeFromPixmap(@NotNull pixmap: Pixmap): Image = {
    makeRasterFromPixmap(pixmap)
  }

  @NotNull
  @Contract("_ -> new") def makeRasterFromPixmap(@NotNull pixmap: Pixmap): Image = {
    try {
      assert(pixmap != null, "Can’t makeFromPixmap with pixmap == null")
      Stats.onNativeCall()
      val ptr = _nMakeRasterFromPixmap(Native.getPtr(pixmap))
      if (ptr == 0) throw new RuntimeException("Failed to Image::makeFromRaster " + pixmap)
      new Image(ptr)
    } finally {
      ReferenceUtil.reachabilityFence(pixmap)
    }
  }

  /**
   * @deprecated - use {@link # makeDeferredFromEncodedBytes ( byte [ ] )}
   */
  @deprecated def makeFromEncoded(bytes: Array[Byte]): Image = {
    makeDeferredFromEncodedBytes(bytes)
  }

  @NotNull
  @Contract("_ -> new") def makeDeferredFromEncodedBytes(bytes: Array[Byte]): Image = {
    Stats.onNativeCall()
    val ptr = _nMakeDeferredFromEncodedBytes(bytes)
    if (ptr == 0) throw new IllegalArgumentException("Failed to Image::makeFromEncoded")
    new Image(ptr)
  }

  @ApiStatus.Internal
  @native def _nMakeRasterFromBytes(width: Int, height: Int, colorType: Int, alphaType: Int, colorSpacePtr: Long, pixels: Array[Byte], rowBytes: Long): Long

  @ApiStatus.Internal
  @native def _nMakeRasterFromData(width: Int, height: Int, colorType: Int, alphaType: Int, colorSpacePtr: Long, dataPtr: Long, rowBytes: Long): Long

  @ApiStatus.Internal
  @native def _nMakeRasterFromBitmap(bitmapPtr: Long): Long

  @ApiStatus.Internal
  @native def _nMakeRasterFromPixmap(pixmapPtr: Long): Long

  @ApiStatus.Internal
  @native def _nMakeDeferredFromEncodedBytes(bytes: Array[Byte]): Long

  @ApiStatus.Internal
  @native def _nGetImageInfo(ptr: Long): ImageInfo

  @ApiStatus.Internal
  @native def _nMakeShader(ptr: Long, tmx: Int, tmy: Int, samplingMode: Long, localMatrix: Array[Float]): Long

  @ApiStatus.Internal
  @native def _nPeekPixels(ptr: Long): ByteBuffer

  @ApiStatus.Internal
  @native def _nPeekPixelsToPixmap(ptr: Long, pixmapPtr: Long): Boolean

  @ApiStatus.Internal
  @native def _nScalePixels(ptr: Long, pixmapPtr: Long, samplingOptions: Long, cache: Boolean): Boolean

  @ApiStatus.Internal
  @native def _nReadPixelsBitmap(ptr: Long, contextPtr: Long, bitmapPtr: Long, srcX: Int, srcY: Int, cache: Boolean): Boolean

  @ApiStatus.Internal
  @native def _nReadPixelsPixmap(ptr: Long, pixmapPtr: Long, srcX: Int, srcY: Int, cache: Boolean): Boolean

  try Library.staticLoad()
}

class Image @ApiStatus.Internal(ptr: Long) extends RefCnt(ptr) with IHasImageInfo {
  @ApiStatus.Internal var _imageInfo: ImageInfo = null

  /**
   * Returns a ImageInfo describing the width, height, color type, alpha type, and color space
   * of the Image.
   *
   * @return image info of Image.
   */
  @NotNull override def getImageInfo: ImageInfo = {
    try {
      if (_imageInfo == null) {
        this.synchronized {
          if (_imageInfo == null) {
            Stats.onNativeCall()
            _imageInfo = Image._nGetImageInfo(_ptr)
          }
        }
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
    _imageInfo
  }

  /**
   * @deprecated - use {@link EncoderPNG# encode ( Image )}, {@link EncoderJPEG# encode ( Image )} or {@link EncoderWEBP# encode ( Image )}
   */
  @deprecated def encodeToData: Data = {
    encodeToData(EncodedImageFormat.PNG, 100)
  }

  /**
   * @deprecated - use {@link EncoderPNG# encode ( Image )}, {@link EncoderJPEG# encode ( Image )} or {@link EncoderWEBP# encode ( Image )}
   */
  @deprecated def encodeToData(format: EncodedImageFormat): Data = {
    encodeToData(format, 100)
  }

  /**
   * @deprecated - use {@link EncoderPNG# encode ( Image, EncodePNGOptions)}, {@link EncoderJPEG# encode ( Image, EncodeJPEGOptions)} or {@link EncoderWEBP# encode ( Image, EncodeWEBPOptions)}
   */
  @deprecated def encodeToData(format: EncodedImageFormat, quality: Int): Data = {
    format match {
      case EncodedImageFormat.PNG => {
        EncoderPNG.encode(this)
      }
      case EncodedImageFormat.JPEG => {
        EncoderJPEG.encode(this, EncodeJPEGOptions.DEFAULT.copy(quality = quality))
      }
      case EncodedImageFormat.WEBP => {
        EncoderWEBP.encode(this, EncodeWEBPOptions.DEFAULT.copy(quality = quality))
      }
      case _ => {
        throw new IllegalArgumentException("Format " + format + " is not supported")
      }
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
      new Shader(Image._nMakeShader(_ptr, tmx.ordinal, tmy.ordinal, sampling.packed, if (localMatrix == null) {
        null
      } else {
        localMatrix.mat
      }))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * If pixel address is available, return ByteBuffer wrapping it.
   * If pixel address is not available, return null.
   *
   * @return ByteBuffer with direct access to pixels, or null
   * @see <a href="https://fiddle.skia.org/c/@Image_peekPixels">https://fiddle.skia.org/c/@Image_peekPixels</a>
   */
  @Nullable def peekPixels: ByteBuffer = {
    try {
      Stats.onNativeCall()
      Image._nPeekPixels(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def peekPixels(pixmap: Pixmap): Boolean = {
    try {
      Stats.onNativeCall()
      Image._nPeekPixelsToPixmap(_ptr, Native.getPtr(pixmap))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(pixmap)
    }
  }

  def readPixels(@NotNull dst: Bitmap): Boolean = readPixels(null, dst, 0, 0, false)

  def readPixels(@NotNull dst: Bitmap, srcX: Int, srcY: Int): Boolean = readPixels(null, dst, srcX, srcY, false)

  /**
   * <p>Copies Rect of pixels from Image to Bitmap. Copy starts at offset (srcX, srcY),
   * and does not exceed Image (getWidth(), getHeight()).</p>
   *
   * <p>dst specifies width, height, ColorType, AlphaType, and ColorSpace of destination.</p>
   *
   * <p>Returns true if pixels are copied. Returns false if:</p>
   * <ul>
   * <li>dst has no pixels allocated.</li>
   * </ul>
   *
   * <p>Pixels are copied only if pixel conversion is possible. If Image ColorType is
   * ColorType.GRAY_8, or ColorType.ALPHA_8; dst.getColorType() must match.
   * If Image ColorType is ColorType.GRAY_8, dst.getColorSpace() must match.
   * If Image AlphaType is AlphaType.OPAQUE, dst.getAlphaType() must
   * match. If Image ColorSpace is null, dst.getColorSpace() must match. Returns
   * false if pixel conversion is not possible.</p>
   *
   * <p>srcX and srcY may be negative to copy only top or left of source. Returns
   * false if getWidth() or getHeight() is zero or negative.</p>
   *
   * <p>Returns false if abs(srcX) &gt;= Image.getWidth(), or if abs(srcY) &gt;= Image.getHeight().</p>
   *
   * <p>If cache is true, pixels may be retained locally, otherwise pixels are not added to the local cache.</p>
   *
   * @param context the DirectContext in play, if it exists
   * @param dst     destination bitmap
   * @param srcX    column index whose absolute value is less than getWidth()
   * @param srcY    row index whose absolute value is less than getHeight()
   * @param cache   whether the pixels should be cached locally
   * @return true if pixels are copied to dstPixels
   */
  def readPixels(@Nullable context: DirectContext, @NotNull dst: Bitmap, srcX: Int, srcY: Int, cache: Boolean): Boolean = {
    try {
      assert(dst != null, "Can’t readPixels with dst == null")
      Image._nReadPixelsBitmap(_ptr, Native.getPtr(context), Native.getPtr(dst), srcX, srcY, cache)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(context)
      ReferenceUtil.reachabilityFence(dst)
    }
  }

  def readPixels(@NotNull dst: Pixmap, srcX: Int, srcY: Int, cache: Boolean): Boolean = {
    try {
      assert(dst != null, "Can’t readPixels with dst == null")
      Image._nReadPixelsPixmap(_ptr, Native.getPtr(dst), srcX, srcY, cache)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(dst)
    }
  }

  def scalePixels(@NotNull dst: Pixmap, samplingMode: SamplingMode, cache: Boolean): Boolean = {
    try {
      assert(dst != null, "Can’t scalePixels with dst == null")
      Image._nScalePixels(_ptr, Native.getPtr(dst), samplingMode.packed, cache)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(dst)
    }
  }
}