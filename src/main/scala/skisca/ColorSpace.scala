package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object ColorSpace {
  object _SRGBHolder {
    val INSTANCE = new ColorSpace(_nMakeSRGB, false)
    Stats.onNativeCall()
  }

  def getSRGB: ColorSpace = _SRGBHolder.INSTANCE

  object _SRGBLinearHolder {
    val INSTANCE = new ColorSpace(_nMakeSRGBLinear, false)
    Stats.onNativeCall()
  }

  def getSRGBLinear: ColorSpace = _SRGBLinearHolder.INSTANCE

  object _DisplayP3Holder {
    val INSTANCE = new ColorSpace(_nMakeDisplayP3, false)
    Stats.onNativeCall()
  }

  def getDisplayP3: ColorSpace = _DisplayP3Holder.INSTANCE

  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @native def _nGetFinalizer: Long

  @native def _nMakeSRGB: Long

  @native def _nMakeDisplayP3: Long

  @native def _nMakeSRGBLinear: Long

  @native def _nConvert(fromPtr: Long, toPtr: Long, r: Float, g: Float, b: Float, a: Float): Array[Float]

  @native def _nIsGammaCloseToSRGB(ptr: Long): Boolean

  @native def _nIsGammaLinear(ptr: Long): Boolean

  @native def _nIsSRGB(ptr: Long): Boolean

  Library.staticLoad()
}

class ColorSpace(ptr: Long, managed: Boolean = true) extends Managed(ptr, ColorSpace._FinalizerHolder.PTR, managed) {
  def convert(to: ColorSpace, color: Color4f): Color4f = {
    val goodTo = if (to == null) {
      ColorSpace.getSRGB
    } else {
      to
    }
    try {
      Color4f.apply(ColorSpace._nConvert(_ptr, Native.getPtr(goodTo), color.r, color.g, color.b, color.a))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(to)
    }
  }
  

  /**
   * @return true if the color space gamma is near enough to be approximated as sRGB
   */
  def isGammaCloseToSRGB: Boolean = {
    try {
      Stats.onNativeCall()
      ColorSpace._nIsGammaCloseToSRGB(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return true if the color space gamma is linear
   */
  def isGammaLinear: Boolean = {
    try {
      Stats.onNativeCall()
      ColorSpace._nIsGammaLinear(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns true if the color space is sRGB. Returns false otherwise.</p>
   *
   * <p>This allows a little bit of tolerance, given that we might see small numerical error
   * in some cases: converting ICC fixed point to float, converting white point to D50,
   * rounding decisions on transfer function and matrix.</p>
   *
   * <p>This does not consider a 2.2f exponential transfer function to be sRGB.  While these
   * functions are similar (and it is sometimes useful to consider them together), this
   * function checks for logical equality.</p>
   */
  def isSRGB: Boolean = {
    try {
      Stats.onNativeCall()
      ColorSpace._nIsSRGB(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}