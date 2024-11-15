package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*

object ColorFilter {
  def makeComposed(outer: ColorFilter, inner: ColorFilter): ColorFilter = {
    try {
      Stats.onNativeCall()
      new ColorFilter(_nMakeComposed(Native.getPtr(outer), Native.getPtr(inner)))
    } finally {
      ReferenceUtil.reachabilityFence(outer)
      ReferenceUtil.reachabilityFence(inner)
    }
  }

  def makeBlend(color: Int, mode: BlendMode): ColorFilter = {
    Stats.onNativeCall()
    new ColorFilter(_nMakeBlend(color, mode.ordinal))
  }

  def makeMatrix(matrix: ColorMatrix): ColorFilter = {
    Stats.onNativeCall()
    new ColorFilter(_nMakeMatrix(matrix.mat))
  }

  def makeHSLAMatrix(matrix: ColorMatrix): ColorFilter = {
    Stats.onNativeCall()
    new ColorFilter(_nMakeHSLAMatrix(matrix.mat))
  }

  object _LinearToSRGBGammaHolder {
    val INSTANCE = new ColorFilter(_nGetLinearToSRGBGamma, false)
    Stats.onNativeCall()
  }

  def getLinearToSRGBGamma: ColorFilter = _LinearToSRGBGammaHolder.INSTANCE

  object _SRGBToLinearGammaHolder {
    val INSTANCE = new ColorFilter(_nGetSRGBToLinearGamma, false)
    Stats.onNativeCall()
  }

  def getSRGBToLinearGamma: ColorFilter = _SRGBToLinearGammaHolder.INSTANCE

  def makeLerp(dst: ColorFilter, src: ColorFilter, t: Float): ColorFilter = {
    try {
      new ColorFilter(_nMakeLerp(t, Native
        .getPtr(dst), Native.getPtr(src)))
    } finally {
      ReferenceUtil.reachabilityFence(dst)
      ReferenceUtil.reachabilityFence(src)
    }
  }

  def makeLighting(colorMul: Int, colorAdd: Int) = new ColorFilter(_nMakeLighting(colorMul, colorAdd))

  def makeHighContrast(grayscale: Boolean, mode: InversionMode, contrast: Float): ColorFilter = {
    new ColorFilter(_nMakeHighContrast(grayscale, mode
      .ordinal, contrast))
  }

  def makeTable(table: Array[Byte]): ColorFilter = {
    assert(table.length == 256, "Expected 256 elements, got " + table.length)
    new ColorFilter(_nMakeTable(table))
  }

  def makeTableARGB(a: Array[Byte], r: Array[Byte], g: Array[Byte], b: Array[Byte]): ColorFilter = {
    assert(a == null || a.length == 256, "Expected 256 elements in a[], got " + a.length)
    assert(r == null || r.length == 256, "Expected 256 elements in r[], got " + r.length)
    assert(g == null || g.length == 256, "Expected 256 elements in g[], got " + g.length)
    assert(b == null || b.length == 256, "Expected 256 elements in b[], got " + b.length)
    new ColorFilter(_nMakeTableARGB(a, r, g, b))
  }

  def makeOverdraw(colors: Array[Int]): ColorFilter = {
    assert(colors.length == 6, "Expected 6 elements, got " + colors.length)
    new ColorFilter(_nMakeOverdraw(colors(0), colors(1), colors(2), colors(3), colors(4), colors(5)))
  }

  object _LumaHolder {
    val INSTANCE = new ColorFilter(_nGetLuma, false)
    Stats.onNativeCall()
  }

  def getLuma: ColorFilter = _LumaHolder.INSTANCE

  @native def _nMakeComposed(outer: Long, inner: Long): Long

  @native def _nMakeBlend(color: Int, blendMode: Int): Long

  @native def _nMakeMatrix(rowMajor: Array[Float]): Long

  @native def _nMakeHSLAMatrix(rowMajor: Array[Float]): Long

  @native def _nGetLinearToSRGBGamma: Long

  @native def _nGetSRGBToLinearGamma: Long

  @native def _nMakeLerp(t: Float, dstPtr: Long, srcPtr: Long): Long

  @native def _nMakeLighting(colorMul: Int, colorAdd: Int): Long

  @native def _nMakeHighContrast(grayscale: Boolean, inversionMode: Int, contrast: Float): Long

  @native def _nMakeTable(table: Array[Byte]): Long

  @native def _nMakeTableARGB(a: Array[Byte], r: Array[Byte], g: Array[Byte], b: Array[Byte]): Long

  @native def _nMakeOverdraw(c0: Int, c1: Int, c2: Int, c3: Int, c4: Int, c5: Int): Long

  @native def _nGetLuma: Long

  Library.staticLoad()
}

class ColorFilter(ptr: Long, allowClose: Boolean = true) extends RefCnt(ptr, allowClose)