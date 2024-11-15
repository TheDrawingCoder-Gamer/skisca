package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object Codec {
  /**
   * If this data represents an encoded image that we know how to decode,
   * return an Codec that can decode it. Otherwise throws IllegalArgumentException.
   */
  def makeFromData(data: Data): Codec = {
    try {
      Stats.onNativeCall()
      val ptr = _nMakeFromData(Native.getPtr(data))
      if (ptr == 0) throw new IllegalArgumentException("Unsupported format")
      new Codec(ptr)
    } finally {
      ReferenceUtil.reachabilityFence(data)
    }
  }

  @ApiStatus.Internal def _validateResult(result: Int): Unit = {
    result match {
      // kIncompleteInput
      case 1 =>  {
        throw new IllegalArgumentException("Incomplete input: A partial image was generated.")
      }
      // kErrorInInput 
      case 2 => {
        throw new IllegalArgumentException("Error in input")
      }
      // kInvalidConversion
      case 3 =>  {
        throw new IllegalArgumentException("Invalid conversion: The generator cannot convert to match the request, ignoring dimensions")
      }
      // kInvalidScale
      case 4 =>  {
        throw new IllegalArgumentException("Invalid scale: The generator cannot scale to requested size")
      }
      // kInvalidParameters
      case 5 =>  {
        throw new IllegalArgumentException("Invalid parameter: Parameters (besides info) are invalid. e.g. NULL pixels, rowBytes too small, etc")
      }
      // kInvalidInput
      case 6 =>  {
        throw new IllegalArgumentException("Invalid input: The input did not contain a valid image")
      }
      // kCouldNotRewind
      case 7 =>  {
        throw new UnsupportedOperationException("Could not rewind: Fulfilling this request requires rewinding the input, which is not supported for this input")
      }
      // kInternalError
      case 8 =>  {
        throw new RuntimeException("Internal error")
      }
      // kUnimplemented 
      case 9 => {
        throw new UnsupportedOperationException("Unimplemented: This method is not implemented by this codec")
      }

      case _ => ()
    }
  }

  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nMakeFromData(dataPtr: Long): Long

  @ApiStatus.Internal
  @native def _nGetImageInfo(ptr: Long): ImageInfo

  @ApiStatus.Internal
  @native def _nGetSize(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nGetEncodedOrigin(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nGetEncodedImageFormat(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nReadPixels(ptr: Long, bitmapPtr: Long, frame: Int, priorFrame: Int): Int

  @ApiStatus.Internal
  @native def _nGetFrameCount(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nGetFrameInfo(ptr: Long, frame: Int): AnimationFrameInfo

  @ApiStatus.Internal
  @native def _nGetFramesInfo(ptr: Long): Array[AnimationFrameInfo]

  @ApiStatus.Internal
  @native def _nGetRepetitionCount(ptr: Long): Int

  Library.staticLoad()
}

class Codec @ApiStatus.Internal(ptr: Long) extends Managed(ptr, Codec._FinalizerHolder.PTR) with IHasImageInfo {
  @ApiStatus.Internal var _imageInfo: ImageInfo = null

  @NotNull override def getImageInfo: ImageInfo = {
    try {
      if (_imageInfo == null) {
        Stats.onNativeCall()
        _imageInfo = Codec._nGetImageInfo(_ptr)
      }
      _imageInfo
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull
  @Contract("-> new") def getSize: IPoint = {
    try {
      Stats.onNativeCall()
      IPoint._makeFromLong(Codec._nGetSize(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull def getEncodedOrigin: EncodedOrigin = {
    try {
      Stats.onNativeCall()
      EncodedOrigin.fromOrdinal(Codec._nGetEncodedOrigin(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull def getEncodedImageFormat: EncodedImageFormat = {
    try {
      Stats.onNativeCall()
      EncodedImageFormat.fromOrdinal(Codec._nGetEncodedImageFormat(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Decodes an image into a bitmap.</p>
   *
   * @return decoded bitmap
   */
  @NotNull
  @Contract("_ -> new") def readPixels: Bitmap = {
    val bitmap = Bitmap()
    bitmap.allocPixels(getImageInfo)
    readPixels(bitmap)
    bitmap
  }

  /**
   * <p>Decodes an image into a bitmap.</p>
   *
   * <p>Repeated calls to this function should give the same results,
   * allowing the PixelRef to be immutable.</p>
   *
   * <p>Bitmap specifies the description of the format (config, size)
   * expected by the caller.  This can simply be identical
   * to the info returned by getImageInfo().</p>
   *
   * <p>This contract also allows the caller to specify
   * different output-configs, which the implementation can
   * decide to support or not.</p>
   *
   * <p>A size that does not match getImageInfo() implies a request
   * to scale. If the generator cannot perform this scale,
   * it will throw an exception.</p>
   *
   * <p>If the info contains a non-null ColorSpace, the codec
   * will perform the appropriate color space transformation.</p>
   *
   * <p>If the caller passes in the ColorSpace that maps to the
   * ICC profile reported by getICCProfile(), the color space
   * transformation is a no-op.</p>
   *
   * <p>If the caller passes a null SkColorSpace, no color space
   * transformation will be done.</p>
   *
   * @param bitmap the description of the format (config, size) expected by the caller
   * @return this
   */
  @NotNull
  @Contract("_ -> this") def readPixels(bitmap: Bitmap): Codec = {
    try {
      Stats.onNativeCall()
      Codec._validateResult(Codec._nReadPixels(_ptr, Native.getPtr(bitmap), 0, -1))
      this
    } finally {
      ReferenceUtil.reachabilityFence(bitmap)
    }
  }

  /**
   * <p>Decodes a frame in a multi-frame image into a bitmap.</p>
   *
   * <p>Repeated calls to this function should give the same results,
   * allowing the PixelRef to be immutable.</p>
   *
   * <p>Bitmap specifies the description of the format (config, size)
   * expected by the caller.  This can simply be identical
   * to the info returned by getImageInfo().</p>
   *
   * <p>This contract also allows the caller to specify
   * different output-configs, which the implementation can
   * decide to support or not.</p>
   *
   * <p>A size that does not match getImageInfo() implies a request
   * to scale. If the generator cannot perform this scale,
   * it will throw an exception.</p>
   *
   * <p>If the info contains a non-null ColorSpace, the codec
   * will perform the appropriate color space transformation.</p>
   *
   * <p>If the caller passes in the ColorSpace that maps to the
   * ICC profile reported by getICCProfile(), the color space
   * transformation is a no-op.</p>
   *
   * <p>If the caller passes a null SkColorSpace, no color space
   * transformation will be done.</p>
   *
   * @param bitmap the description of the format (config, size) expected by the caller
   * @param frame  index of the frame in multi-frame image to decode
   * @return this
   */
  @NotNull
  @Contract("_ -> this") def readPixels(bitmap: Bitmap, frame: Int): Codec = {
    try {
      Stats.onNativeCall()
      Codec._validateResult(Codec._nReadPixels(_ptr, Native.getPtr(bitmap), frame, -1))
      this
    } finally {
      ReferenceUtil.reachabilityFence(bitmap)
    }
  }

  /**
   * <p>Decodes a frame in a multi-frame image into a bitmap.</p>
   *
   * <p>Repeated calls to this function should give the same results,
   * allowing the PixelRef to be immutable.</p>
   *
   * <p>Bitmap specifies the description of the format (config, size)
   * expected by the caller.  This can simply be identical
   * to the info returned by getImageInfo().</p>
   *
   * <p>This contract also allows the caller to specify
   * different output-configs, which the implementation can
   * decide to support or not.</p>
   *
   * <p>A size that does not match getImageInfo() implies a request
   * to scale. If the generator cannot perform this scale,
   * it will throw an exception.</p>
   *
   * <p>If the info contains a non-null ColorSpace, the codec
   * will perform the appropriate color space transformation.</p>
   *
   * <p>If the caller passes in the ColorSpace that maps to the
   * ICC profile reported by getICCProfile(), the color space
   * transformation is a no-op.</p>
   *
   * <p>If the caller passes a null SkColorSpace, no color space
   * transformation will be done.</p>
   *
   * @param bitmap     the description of the format (config, size) expected by the caller
   * @param frame      index of the frame in multi-frame image to decode
   * @param priorFrame index of the frame already in bitmap, might be used to optimize retrieving current frame
   * @return this
   */
  @NotNull
  @Contract("_ -> this") def readPixels(bitmap: Bitmap, frame: Int, priorFrame: Int): Codec = {
    try {
      Stats.onNativeCall()
      Codec._validateResult(Codec._nReadPixels(_ptr, Native.getPtr(bitmap), frame, priorFrame))
      this
    } finally {
      ReferenceUtil.reachabilityFence(bitmap)
    }
  }

  /**
   * <p>Return the number of frames in the image.</p>
   *
   * <p>May require reading through the stream.</p>
   */
  def getFrameCount: Int = {
    try {
      Stats.onNativeCall()
      Codec._nGetFrameCount(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Return info about a single frame.</p>
   *
   * <p>Only supported by multi-frame images. Does not read through the stream,
   * so it should be called after getFrameCount() to parse any frames that
   * have not already been parsed.</p>
   */
  def getFrameInfo(frame: Int): AnimationFrameInfo = {
    try {
      Stats.onNativeCall()
      Codec._nGetFrameInfo(_ptr, frame)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Return info about all the frames in the image.</p>
   *
   * <p>May require reading through the stream to determine info about the
   * frames (including the count).</p>
   *
   * <p>As such, future decoding calls may require a rewind.</p>
   *
   * <p>For still (non-animated) image codecs, this will return an empty array.</p>
   */
  def getFramesInfo: Array[AnimationFrameInfo] = {
    try {
      Stats.onNativeCall()
      Codec._nGetFramesInfo(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Return the number of times to repeat, if this image is animated. This number does not
   * include the first play through of each frame. For example, a repetition count of 4 means
   * that each frame is played 5 times and then the animation stops.</p>
   *
   * <p>It can return -1, a negative number, meaning that the animation
   * should loop forever.</p>
   *
   * <p>May require reading the stream to find the repetition count.</p>
   *
   * <p>As such, future decoding calls may require a rewind.</p>
   *
   * <p>For still (non-animated) image codecs, this will return 0.</p>
   */
  def getRepetitionCount: Int = {
    try {
      Stats.onNativeCall()
      Codec._nGetRepetitionCount(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}