package gay.menkissing.skisca

import org.jetbrains.annotations.*

/**
 * @deprecated - use {@link EncoderJPEG}, {@link EncoderPNG} or {@link EncoderWEBP} directly
 */
@deprecated enum EncodedImageFormat {
  case BMP, GIF, ICO, JPEG, PNG, WBMP, WEBP, PKM, KTX, ASTC, DNG, HEIF 
}