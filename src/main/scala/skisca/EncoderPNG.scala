package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object EncoderPNG {
  /**
   * Encode the provided image and return the resulting bytes.
   *
   * @param image image to encode
   * @return nullptr if the pixels could not be read or encoding otherwise fails.
   */
  @Nullable def encode(@NotNull image: Image): Data = {
    encode(null, image, EncodePNGOptions.DEFAULT)
  }

  /**
   * Encode the provided image and return the resulting bytes.
   *
   * @param image image to encode
   * @param opts  may be used to control the encoding behavior
   * @return nullptr if the pixels could not be read or encoding otherwise fails.
   */
  @Nullable def encode(@NotNull image: Image, @NotNull opts: EncodePNGOptions): Data = {
    encode(null, image, opts)
  }

  /**
   * Encode the provided image and return the resulting Data.
   *
   * @param ctx   If the image was created as a texture-backed image on a GPU context,
   *              that ctx must be provided so the pixels can be read before being encoded.
   *              For raster-backed images, ctx can be null.
   * @param image image to encode
   * @param opts  may be used to control the encoding behavior
   * @return nullptr if the pixels could not be read or encoding otherwise fails.
   */
  @Nullable def encode(@Nullable ctx: DirectContext, @NotNull image: Image, @NotNull opts: EncodePNGOptions): Data = {
    try {
      assert(image != null, "Can’t EncoderPNG::encode with image == null")
      assert(opts != null, "Can’t EncoderPNG::encode with opts == null")
      Stats.onNativeCall()
      val ptr = _nEncode(Native.getPtr(ctx), Native.getPtr(image), opts._flags, opts._zlibLevel)
      if (ptr == 0) {
        null
      } else {
        new Data(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(ctx)
      ReferenceUtil.reachabilityFence(image)
    }
  }

  @ApiStatus.Internal
  @native def _nEncode(ctxPtr: Long, imagePtr: Long, flags: Int, zlibLevel: Int): Long
}