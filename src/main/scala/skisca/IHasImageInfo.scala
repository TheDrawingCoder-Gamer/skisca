package gay.menkissing.skisca

import org.jetbrains.annotations.*

trait IHasImageInfo {
  def getImageInfo: ImageInfo

  /**
   * Returns pixel count in each row. Should be equal or less than
   * getRowBytes() / getImageInfo().getBytesPerPixel().
   *
   * May be less than getPixelRef().getWidth(). Will not exceed getPixelRef().getWidth() less
   *
   * @return pixel width in ImageInfo
   */
  def getWidth: Int = {
    getImageInfo.width
  }

  /**
   * Returns pixel row count.
   *
   * Maybe be less than getPixelRef().getHeight(). Will not exceed getPixelRef().getHeight()
   *
   * @return pixel height in ImageInfo
   */
  def getHeight: Int = {
    getImageInfo.height
  }

  @NotNull def getColorInfo: ColorInfo = getImageInfo.colorInfo

  @NotNull def getColorType: ColorType = getImageInfo.colorInfo.colorType

  @NotNull def getAlphaType: ColorAlphaType = getImageInfo.colorInfo.alphaType

  @Nullable def getColorSpace: ColorSpace = getImageInfo.colorInfo.colorSpace

  /**
   * Returns number of bytes per pixel required by ColorType.
   * Returns zero if colorType is {@link ColorType# UNKNOWN}.
   *
   * @return bytes in pixel
   */
  def getBytesPerPixel: Int = {
    getImageInfo.getBytesPerPixel
  }

  /**
   * Returns bit shift converting row bytes to row pixels.
   * Returns zero for {@link ColorType# UNKNOWN}.
   *
   * @return one of: 0, 1, 2, 3; left shift to convert pixels to bytes
   */
  def getShiftPerPixel: Int = {
    getImageInfo.getShiftPerPixel
  }

  /**
   * Returns true if either getWidth() or getHeight() are zero.
   *
   * Does not check if PixelRef is null; call {@link Bitmap# drawsNothing ( )} to check
   * getWidth(), getHeight(), and PixelRef.
   *
   * @return true if dimensions do not enclose area
   */
  def isEmpty: Boolean = {
    getImageInfo.isEmpty
  }

  /**
   * <p>Returns true if ColorAlphaType is set to hint that all pixels are opaque; their
   * alpha value is implicitly or explicitly 1.0. If true, and all pixels are
   * not opaque, Skia may draw incorrectly.</p>
   *
   * <p>Does not check if SkColorType allows alpha, or if any pixel value has
   * transparency.</p>
   *
   * @return true if ImageInfo ColorAlphaType is {@link ColorAlphaType# OPAQUE}
   */
  def isOpaque: Boolean = {
    getImageInfo.colorInfo.isOpaque
  }
}