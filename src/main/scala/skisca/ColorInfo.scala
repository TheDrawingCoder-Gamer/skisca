package gay.menkissing.skisca

import org.jetbrains.annotations.*

/**
 * <p>Describes pixel and encoding. ImageInfo can be created from ColorInfo by
 * providing dimensions.</p>
 *
 * <p>It encodes how pixel bits describe alpha, transparency; color components red, blue,
 * and green; and ColorSpace, the range and linearity of colors.</p>
 */
object ColorInfo {
  /**
   * Creates an ColorInfo with {@link ColorType# UNKNOWN}, {@link ColorAlphaType# UNKNOWN},
   * and no ColorSpace.
   */
    val DEFAULT = new ColorInfo(ColorType.UNKNOWN, ColorAlphaType.UNKNOWN, null)
}

case class ColorInfo(@NotNull colorType: ColorType, @NotNull alphaType: ColorAlphaType, @Nullable colorSpace: ColorSpace) {

  def isOpaque: Boolean = (alphaType eq ColorAlphaType.OPAQUE) || colorType.isAlwaysOpaque

  /**
   * Returns number of bytes per pixel required by ColorType.
   * Returns zero if getColorType() is {@link ColorType# UNKNOWN}.
   *
   * @return bytes in pixel
   * @see <a href="https://fiddle.skia.org/c/@ImageInfo_bytesPerPixel">https://fiddle.skia.org/c/@ImageInfo_bytesPerPixel</a>
   */
  def getBytesPerPixel: Int = {
    colorType.getBytesPerPixel
  }

  /**
   * Returns bit shift converting row bytes to row pixels.
   * Returns zero for {@link ColorType# UNKNOWN}.
   *
   * @return one of: 0, 1, 2, 3, 4; left shift to convert pixels to bytes
   * @see <a href="https://fiddle.skia.org/c/@ImageInfo_shiftPerPixel">https://fiddle.skia.org/c/@ImageInfo_shiftPerPixel</a>
   */
  def getShiftPerPixel: Int = {
    colorType.getShiftPerPixel
  }

  def isGammaCloseToSRGB: Boolean = colorSpace != null && colorSpace.isGammaCloseToSRGB
}