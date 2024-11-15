package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*


/**
 * Describes how pixel bits encode color. A pixel may be an alpha mask, a
 * grayscale, RGB, or ARGB.
 */
enum ColorType {
  case

  /**
   * Uninitialized
   */
  UNKNOWN,

  /**
   * Pixel with alpha in 8-bit byte
   */
  ALPHA_8,

  /**
   * Pixel with 5 bits red, 6 bits green, 5 bits blue, in 16-bit word
   */
  RGB_565,

  /**
   * Pixel with 4 bits for alpha, red, green, blue; in 16-bit word
   */
  ARGB_4444,

  /**
   * Pixel with 8 bits for red, green, blue, alpha; in 32-bit word
   */
  RGBA_8888,

  /**
   * Pixel with 8 bits each for red, green, blue; in 32-bit word
   */
  RGB_888X,

  /**
   * Pixel with 8 bits for blue, green, red, alpha; in 32-bit word
   */
  BGRA_8888,

  /**
   * 10 bits for red, green, blue; 2 bits for alpha; in 32-bit word
   */
  RGBA_1010102,

  /**
   * 10 bits for blue, green, red; 2 bits for alpha; in 32-bit word
   */
  BGRA_1010102,

  /**
   * Pixel with 10 bits each for red, green, blue; in 32-bit word
   */
  RGB_101010X,

  /**
   * Pixel with 10 bits each for blue, green, red; in 32-bit word
   */
  BGR_101010X,

  /**
   * Pixel with grayscale level in 8-bit byte
   */
  GRAY_8,

  /**
   * Pixel with half floats in [0,1] for red, green, blue, alpha; in 64-bit word
   */
  RGBA_F16NORM,

  /**
   * Pixel with half floats for red, green, blue, alpha; in 64-bit word
   */
  RGBA_F16,

  /**
   * Pixel using C float for red, green, blue, alpha; in 128-bit word
   */
  RGBA_F32,

  /**
   * Pixel with a uint8_t for red and green
   */
  // The following 6 colortypes are just for reading from - not for rendering to
  R8G8_UNORM,

  /**
   * Pixel with a half float for alpha
   */
  A16_FLOAT,

  /**
   * Pixel with a half float for red and green
   */
  R16G16_FLOAT,

  /**
   * Pixel with a little endian uint16_t for alpha
   */
  A16_UNORM,

  /**
   * Pixel with a little endian uint16_t for red and green
   */
  R16G16_UNORM,

  /**
   * Pixel with a little endian uint16_t for red, green, blue, and alpha
   */
  R16G16B16A16_UNORM


  /**
   * Returns the number of bytes required to store a pixel, including unused padding.
   * Returns zero for {@link # UNKNOWN}.
   *
   * @return bytes per pixel
   */
  def getBytesPerPixel: Int = {
    this match {
      case UNKNOWN => {
        return 0
      }
      case ALPHA_8 => {
        return 1
      }
      case RGB_565 => {
        return 2
      }
      case ARGB_4444 => {
        return 2
      }
      case RGBA_8888 => {
        return 4
      }
      case BGRA_8888 => {
        return 4
      }
      case RGB_888X => {
        return 4
      }
      case RGBA_1010102 => {
        return 4
      }
      case RGB_101010X => {
        return 4
      }
      case BGRA_1010102 => {
        return 4
      }
      case BGR_101010X => {
        return 4
      }
      case GRAY_8 => {
        return 1
      }
      case RGBA_F16NORM => {
        return 8
      }
      case RGBA_F16 => {
        return 8
      }
      case RGBA_F32 => {
        return 16
      }
      case R8G8_UNORM => {
        return 2
      }
      case A16_UNORM => {
        return 2
      }
      case R16G16_UNORM => {
        return 4
      }
      case A16_FLOAT => {
        return 2
      }
      case R16G16_FLOAT => {
        return 4
      }
      case R16G16B16A16_UNORM => {
        return 8
      }
    }
    throw new RuntimeException("Unreachable")
  }

  def getShiftPerPixel: Int = {
    this match {
      case UNKNOWN => {
        return 0
      }
      case ALPHA_8 => {
        return 0
      }
      case RGB_565 => {
        return 1
      }
      case ARGB_4444 => {
        return 1
      }
      case RGBA_8888 => {
        return 2
      }
      case RGB_888X => {
        return 2
      }
      case BGRA_8888 => {
        return 2
      }
      case RGBA_1010102 => {
        return 2
      }
      case RGB_101010X => {
        return 2
      }
      case BGRA_1010102 => {
        return 2
      }
      case BGR_101010X => {
        return 2
      }
      case GRAY_8 => {
        return 0
      }
      case RGBA_F16NORM => {
        return 3
      }
      case RGBA_F16 => {
        return 3
      }
      case RGBA_F32 => {
        return 4
      }
      case R8G8_UNORM => {
        return 1
      }
      case A16_UNORM => {
        return 1
      }
      case R16G16_UNORM => {
        return 2
      }
      case A16_FLOAT => {
        return 1
      }
      case R16G16_FLOAT => {
        return 2
      }
      case R16G16B16A16_UNORM => {
        return 3
      }
    }
    throw new RuntimeException("Unreachable")
  }

  /**
   * Returns true if ColorType always decodes alpha to 1.0, making the pixel
   * fully opaque. If true, ColorType does not reserve bits to encode alpha.
   *
   * @return true if alpha is always set to 1.0
   */
  def isAlwaysOpaque: Boolean = {
    Stats.onNativeCall()
    ColorType._nIsAlwaysOpaque(ordinal)
  }

  /**
   * <p>Returns a valid ColorAlphaType for colorType. If there is more than one valid canonical
   * ColorAlphaType, set to alphaType, if valid.</p>
   *
   * <p>Returns null only if alphaType is {@link ColorAlphaType# UNKNOWN}, color type is not
   * {@link # UNKNOWN}, and ColorType is not always opaque.</p>
   *
   * @return ColorAlphaType if can be associated with colorType
   */
  @Nullable def validateAlphaType(daAlphaType: ColorAlphaType): ColorAlphaType = {
    var alphaType = daAlphaType
    this match {
      case ARGB_4444 | RGBA_8888 | BGRA_8888 | RGBA_1010102 | BGRA_1010102 | RGBA_F16NORM | RGBA_F16 | RGBA_F32 | R16G16B16A16_UNORM => {
        if (ColorAlphaType.UNKNOWN eq alphaType) return null
      }
      case _ => ()
    }
    this match {
      case UNKNOWN => {
        alphaType = ColorAlphaType.UNKNOWN
      }
      case ALPHA_8 | A16_UNORM | A16_FLOAT
            | ARGB_4444 | RGBA_8888 | BGRA_8888 | RGBA_1010102 | BGRA_1010102 | RGBA_F16NORM | RGBA_F16 | RGBA_F32 | R16G16B16A16_UNORM => {
        if (ColorAlphaType.UNPREMUL eq alphaType) alphaType = ColorAlphaType.PREMUL
      }

      case GRAY_8 | R8G8_UNORM | R16G16_UNORM | R16G16_FLOAT | RGB_565 | RGB_888X | RGB_101010X | BGR_101010X => {
        alphaType = ColorAlphaType.OPAQUE
      }
    }
    alphaType
  }

  def computeOffset(x: Int, y: Int, rowBytes: Long): Long = {
    if (this eq UNKNOWN) return 0
    y * rowBytes + (x << getShiftPerPixel)
  }

  def getR(color: Byte): Float = {
    this match {
      case GRAY_8 => {
        java.lang.Byte.toUnsignedInt(color) / 255f
      }
      case _ => {
        throw new IllegalArgumentException("getR(byte) is not supported on ColorType." + this)
      }
    }
  }

  def getR(color: Short): Float = {
    this match {
      case RGB_565 => {
        ((color >> 11) & 0b11111) / 31f
      }
      case ARGB_4444 => {
        ((color >> 8) & 0xF) / 15f
      }
      case _ => {
        throw new IllegalArgumentException("getR(short) is not supported on ColorType." + this)
      }
    }
  }

  def getR(color: Int): Float = {
    this match {
      case RGBA_8888 => {
        ((color >> 24) & 0xFF) / 255f
      }
      case RGB_888X => {
        ((color >> 24) & 0xFF) / 255f
      }
      case BGRA_8888 => {
        ((color >> 8) & 0xFF) / 255f
      }
      case RGBA_1010102 => {
        ((color >> 22) & 0b1111111111) / 1023f
      }
      case RGB_101010X => {
        ((color >> 22) & 0b1111111111) / 1023f
      }
      case BGRA_1010102 => {
        ((color >> 2) & 0b1111111111) / 1023f
      }
      case BGR_101010X => {
        ((color >> 2) & 0b1111111111) / 1023f
      }
      case _ => {
        throw new IllegalArgumentException("getR(int) is not supported on ColorType." + this)
      }
    }
  }

  def getG(color: Byte): Float = {
    this match {
      case GRAY_8 => {
        java.lang.Byte.toUnsignedInt(color) / 255f
      }
      case _ => {
        throw new IllegalArgumentException("getG(byte) is not supported on ColorType." + this)
      }
    }
  }

  def getG(color: Short): Float = {
    this match {
      case RGB_565 => {
        ((color >> 5) & 0b111111) / 63f
      }
      case ARGB_4444 => {
        ((color >> 4) & 0xF) / 15f
      }
      case _ => {
        throw new IllegalArgumentException("getG(short) is not supported on ColorType." + this)
      }
    }
  }

  def getG(color: Int): Float = {
    this match {
      case RGBA_8888 => {
        ((color >> 16) & 0xFF) / 255f
      }
      case RGB_888X => {
        ((color >> 16) & 0xFF) / 255f
      }
      case BGRA_8888 => {
        ((color >> 16) & 0xFF) / 255f
      }
      case RGBA_1010102 => {
        ((color >> 12) & 0b1111111111) / 1023f
      }
      case RGB_101010X => {
        ((color >> 12) & 0b1111111111) / 1023f
      }
      case BGRA_1010102 => {
        ((color >> 12) & 0b1111111111) / 1023f
      }
      case BGR_101010X => {
        ((color >> 12) & 0b1111111111) / 1023f
      }
      case _ => {
        throw new IllegalArgumentException("getG(int) is not supported on ColorType." + this)
      }
    }
  }

  def getB(color: Byte): Float = {
    this match {
      case GRAY_8 => {
        java.lang.Byte.toUnsignedInt(color) / 255f
      }
      case _ => {
        throw new IllegalArgumentException("getB(byte) is not supported on ColorType." + this)
      }
    }
  }

  def getB(color: Short): Float = {
    this match {
      case RGB_565 => {
        (color & 0b11111) / 31f
      }
      case ARGB_4444 => {
        (color & 0xF) / 15f
      }
      case _ => {
        throw new IllegalArgumentException("getB(short) is not supported on ColorType." + this)
      }
    }
  }

  def getB(color: Int): Float = {
    this match {
      case RGBA_8888 => {
        ((color >> 8) & 0xFF) / 255f
      }
      case RGB_888X => {
        ((color >> 8) & 0xFF) / 255f
      }
      case BGRA_8888 => {
        ((color >> 24) & 0xFF) / 255f
      }
      case RGBA_1010102 => {
        ((color >> 2) & 0b1111111111) / 1023f
      }
      case RGB_101010X => {
        ((color >> 2) & 0b1111111111) / 1023f
      }
      case BGRA_1010102 => {
        ((color >> 22) & 0b1111111111) / 1023f
      }
      case BGR_101010X => {
        ((color >> 22) & 0b1111111111) / 1023f
      }
      case _ => {
        throw new IllegalArgumentException("getB(int) is not supported on ColorType." + this)
      }
    }
  }

  def getA(color: Byte): Float = {
    this match {
      case ALPHA_8 => {
        java.lang.Byte.toUnsignedInt(color) / 255f
      }
      case _ => {
        throw new IllegalArgumentException("getA(byte) is not supported on ColorType." + this)
      }
    }
  }

  def getA(color: Short): Float = {
    this match {
      case ARGB_4444 => {
        ((color >> 12) & 0xF) / 15f
      }
      case _ => {
        throw new IllegalArgumentException("getA(short) is not supported on ColorType." + this)
      }
    }
  }

  def getA(color: Int): Float = {
    this match {
      case RGBA_8888 => {
        (color & 0xFF) / 255f
      }
      case BGRA_8888 => {
        (color & 0xFF) / 255f
      }
      case RGBA_1010102 => {
        (color & 0b11) / 3f
      }
      case BGRA_1010102 => {
        (color & 0b11) / 3f
      }
      case _ => {
        throw new IllegalArgumentException("getA(int) is not supported on ColorType." + this)
      }
    }
  }

}

object ColorType {
  /**
   * Native ARGB 32-bit encoding
   */
  var N32: ColorType = BGRA_8888
  @ApiStatus.Internal
  @native def _nIsAlwaysOpaque(value: Int): Boolean
}