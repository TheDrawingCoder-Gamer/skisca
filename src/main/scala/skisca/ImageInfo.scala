package gay.menkissing.skisca

import io.github.humbleui.types.*
import org.jetbrains.annotations.*

/**
 * <p>Describes pixel dimensions and encoding. Bitmap, Image, Pixmap, and Surface
 * can be created from ImageInfo. ImageInfo can be retrieved from Bitmap and
 * Pixmap, but not from Image and Surface. For example, Image and Surface
 * implementations may defer pixel depth, so may not completely specify ImageInfo.</p>
 *
 * <p>ImageInfo contains dimensions, the pixel integral width and height. It encodes
 * how pixel bits describe alpha, transparency; color components red, blue,
 * and green; and ColorSpace, the range and linearity of colors.</p>
 */
object ImageInfo {
  val DEFAULT = new ImageInfo(ColorInfo.DEFAULT, 0, 0)

  /**
   * @return ImageInfo with {@link ColorType# N32}
   */
  @NotNull
  @Contract("_, _, _ -> new") def makeN32(width: Int, height: Int, @NotNull alphaType: ColorAlphaType) = {
    new ImageInfo(new ColorInfo(ColorType
      .N32, alphaType, null), width, height)
  }

  /**
   * @return ImageInfo with {@link ColorType# N32}
   */
  @NotNull
  @Contract("_, _, _, _ -> new") def makeN32(width: Int, height: Int, @NotNull alphaType: ColorAlphaType, @Nullable colorSpace: ColorSpace) = {
    new ImageInfo(new ColorInfo(ColorType
      .N32, alphaType, colorSpace), width, height)
  }

  /**
   * @return ImageInfo with {@link ColorType# N32} and {@link ColorSpace# getSRGB ( )}
   * @see <a href="https://fiddle.skia.org/c/@ImageInfo_MakeS32">https://fiddle.skia.org/c/@ImageInfo_MakeS32</a>
   */
  @NotNull
  @Contract("_, _, _ -> new") def makeS32(width: Int, height: Int, @NotNull alphaType: ColorAlphaType) = {
    new ImageInfo(new ColorInfo(ColorType
      .N32, alphaType, ColorSpace.getSRGB), width, height)
  }

  /**
   * @return ImageInfo with {@link ColorType# N32} and {@link ColorAlphaType# PREMUL}
   */
  @NotNull
  @Contract("_, _ -> new") def makeN32Premul(width: Int, height: Int) = {
    new ImageInfo(new ColorInfo(ColorType
      .N32, ColorAlphaType.PREMUL, null), width, height)
  }

  /**
   * @return ImageInfo with {@link ColorType# N32} and {@link ColorAlphaType# PREMUL}
   */
  @NotNull
  @Contract("_, _, _ -> new") def makeN32Premul(width: Int, height: Int, @Nullable colorSpace: ColorSpace) = {
    new ImageInfo(new ColorInfo(ColorType
      .N32, ColorAlphaType.PREMUL, colorSpace), width, height)
  }

  /**
   * @return ImageInfo with {@link ColorType# ALPHA_8} and {@link ColorAlphaType# PREMUL}
   */
  @NotNull
  @Contract("_, _ -> new") def makeA8(width: Int, height: Int) = {
    new ImageInfo(new ColorInfo(ColorType
      .ALPHA_8, ColorAlphaType.PREMUL, null), width, height)
  }

  /**
   * @return ImageInfo with {@link ColorType# UNKNOWN} and {@link ColorAlphaType# UNKNOWN}
   */
  @NotNull
  @Contract("_, _ -> new") def makeUnknown(width: Int, height: Int) = {
    new ImageInfo(new ColorInfo(ColorType
      .UNKNOWN, ColorAlphaType.UNKNOWN, null), width, height)
  }
}

case class ImageInfo(colorInfo: ColorInfo, width: Int, height: Int) {
  def this(width: Int, height: Int, @NotNull colorType: ColorType, @NotNull alphaType: ColorAlphaType) = {
    this(new ColorInfo(colorType, alphaType, null), width, height)
  }

  def this(width: Int, height: Int, @NotNull colorType: ColorType, @NotNull alphaType: ColorAlphaType, @Nullable colorSpace: ColorSpace) = {
    this(new ColorInfo(colorType, alphaType, colorSpace), width, height)
  }

  def this(width: Int, height: Int, colorType: Int, alphaType: Int, colorSpace: Long) = {
    this(width, height, ColorType.fromOrdinal(colorType), ColorAlphaType.fromOrdinal(alphaType), if (colorSpace == 0) {
      null
    } else {
      new ColorSpace(colorSpace)
    })
  }

  @NotNull def getColorType: ColorType = colorInfo.colorType

  @NotNull def withColorType(@NotNull colorType: ColorType): ImageInfo = {
    copy(colorInfo = colorInfo.copy(colorType = colorType))
  }

  @NotNull def getColorAlphaType: ColorAlphaType = colorInfo.alphaType

  @NotNull def withColorAlphaType(@NotNull alphaType: ColorAlphaType): ImageInfo = {
    copy(colorInfo = colorInfo.copy(alphaType = alphaType))
  }

  @Nullable def getColorSpace: ColorSpace = colorInfo.colorSpace

  @NotNull def withColorSpace(@NotNull colorSpace: ColorSpace): ImageInfo = {
    copy(colorInfo = colorInfo.copy(colorSpace = colorSpace))
  }

  /**
   * @return true if either dimension is zero or smaller
   */
  def isEmpty: Boolean = {
    width <= 0 || height <= 0
  }

  /**
   * <p>Returns true if ColorAlphaType is set to hint that all pixels are opaque; their
   * alpha value is implicitly or explicitly 1.0. If true, and all pixels are
   * not opaque, Skia may draw incorrectly.</p>
   *
   * <p>Does not check if ColorType allows alpha, or if any pixel value has
   * transparency.</p>
   *
   * @return true if alphaType is {@link ColorAlphaType# OPAQUE}
   */
  def isOpaque: Boolean = {
    colorInfo.isOpaque
  }

  /**
   * @return integral rectangle from (0, 0) to (getWidth(), getHeight())
   */
  @NotNull def getBounds: IRect = {
    IRect.makeXYWH(0, 0, width, height)
  }

  /**
   * @return true if associated ColorSpace is not null, and ColorSpace gamma
   *         is approximately the same as sRGB.
   */
  def isGammaCloseToSRGB: Boolean = {
    colorInfo.isGammaCloseToSRGB
  }

  @NotNull def withWidthHeight(width: Int, height: Int) = new ImageInfo(colorInfo, width, height)

  /**
   * Returns number of bytes per pixel required by ColorType.
   * Returns zero if {@link # getColorType ( )} is {@link ColorType# UNKNOWN}.
   *
   * @return bytes in pixel
   */
  def getBytesPerPixel: Int = {
    colorInfo.getBytesPerPixel
  }

  /**
   * Returns bit shift converting row bytes to row pixels.
   * Returns zero for {@link ColorType# UNKNOWN}.
   *
   * @return one of: 0, 1, 2, 3, 4; left shift to convert pixels to bytes
   */
  def getShiftPerPixel: Int = {
    colorInfo.getShiftPerPixel
  }

  /**
   * Returns minimum bytes per row, computed from pixel getWidth() and ColorType, which
   * specifies getBytesPerPixel(). Bitmap maximum value for row bytes must fit
   * in 31 bits.
   */
  def getMinRowBytes: Long = {
    width * getBytesPerPixel
  }

  /**
   * <p>Returns byte offset of pixel from pixel base address.</p>
   *
   * <p>Asserts in debug build if x or y is outside of bounds. Does not assert if
   * rowBytes is smaller than {@link # getMinRowBytes ( )}, even though result may be incorrect.</p>
   *
   * @param x        column index, zero or greater, and less than getWidth()
   * @param y        row index, zero or greater, and less than getHeight()
   * @param rowBytes size of pixel row or larger
   * @return offset within pixel array
   * @see <a href="https://fiddle.skia.org/c/@ImageInfo_computeOffset">https://fiddle.skia.org/c/@ImageInfo_computeOffset</a>
   */
  def computeOffset(x: Int, y: Int, rowBytes: Long): Long = {
    colorInfo.colorType.computeOffset(x, y, rowBytes)
  }

  /**
   * <p>Returns storage required by pixel array, given ImageInfo dimensions, ColorType,
   * and rowBytes. rowBytes is assumed to be at least as large as {@link # getMinRowBytes ( )}.</p>
   *
   * <p>Returns zero if height is zero.</p>
   *
   * @param rowBytes size of pixel row or larger
   * @return memory required by pixel buffer
   * @see <a href="https://fiddle.skia.org/c/@ImageInfo_computeByteSize">https://fiddle.skia.org/c/@ImageInfo_computeByteSize</a>
   */
  def computeByteSize(rowBytes: Long): Long = {
    if (0 == height) return 0
    (height - 1) * rowBytes + width * getBytesPerPixel
  }

  /**
   * <p>Returns storage required by pixel array, given ImageInfo dimensions, and
   * ColorType. Uses {@link # getMinRowBytes ( )} to compute bytes for pixel row.</p>
   *
   * Returns zero if height is zero.
   *
   * @return least memory required by pixel buffer
   */
  def computeMinByteSize: Long = {
    computeByteSize(getMinRowBytes)
  }

  /**
   * Returns true if rowBytes is valid for this ImageInfo.
   *
   * @param rowBytes size of pixel row including padding
   * @return true if rowBytes is large enough to contain pixel row and is properly aligned
   */
  def isRowBytesValid(rowBytes: Long): Boolean = {
    if (rowBytes < getMinRowBytes) return false
    val shift = getShiftPerPixel
    rowBytes >> shift << shift == rowBytes
  }
}