package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object MaskFilter {
  def makeBlur(mode: FilterBlurMode, sigma: Float): MaskFilter = makeBlur(mode, sigma, true)

  def makeBlur(mode: FilterBlurMode, sigma: Float, respectCTM: Boolean): MaskFilter = {
    Stats.onNativeCall()
    new MaskFilter(_nMakeBlur(mode.ordinal, sigma, respectCTM))
  }

  def makeShader(s: Shader): MaskFilter = {
    try {
      Stats.onNativeCall()
      new MaskFilter(_nMakeShader(Native.getPtr(s)))
    } finally {
      ReferenceUtil.reachabilityFence(s)
    }
  }

  def makeTable(table: Array[Byte]): MaskFilter = {
    Stats.onNativeCall()
    new MaskFilter(_nMakeTable(table))
  }

  def makeGamma(gamma: Float): MaskFilter = {
    Stats.onNativeCall()
    new MaskFilter(_nMakeGamma(gamma))
  }

  def makeClip(min: Int, max: Int): MaskFilter = {
    Stats.onNativeCall()
    new MaskFilter(_nMakeClip(min.toByte, max.toByte))
  }

  @native def _nMakeBlur(mode: Int, sigma: Float, respectCTM: Boolean): Long

  @native def _nMakeShader(shaderPtr: Long): Long

  // public static native long _nMakeEmboss(float sigma, float x, float y, float z, int pad, int ambient, int specular);
  @native def _nMakeTable(table: Array[Byte]): Long

  @native def _nMakeGamma(gamma: Float): Long

  @native def _nMakeClip(min: Byte, max: Byte): Long

  try Library.staticLoad()
}

class MaskFilter @ApiStatus.Internal(ptr: Long) // public MaskFilter makeEmboss(float blurSigma, float lightDirectionX, float lightDirectionY, float lightDirectionZ, int lightPad, int lightAmbient, int lightSpecular) {
//     Native.onNativeCall();
//     return new MaskFilter(_nMakeEmboss(blurSigma, lightDirectionX, lightDirectionY, lightDirectionZ, lightPad, lightAmbient, lightSpecular));
// }
  extends RefCnt(ptr) {
}