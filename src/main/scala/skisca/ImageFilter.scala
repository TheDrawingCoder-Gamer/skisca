package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

import java.util

object ImageFilter {
  def makeArithmetic(k1: Float, k2: Float, k3: Float, k4: Float, enforcePMColor: Boolean, bg: ImageFilter, fg: ImageFilter, crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeArithmetic(k1, k2, k3, k4, enforcePMColor, Native.getPtr(bg), Native.getPtr(fg), crop))
    } finally {
      ReferenceUtil.reachabilityFence(bg)
      ReferenceUtil.reachabilityFence(fg)
    }
  }

  def makeBlend(blendMode: BlendMode, bg: ImageFilter, fg: ImageFilter, crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeBlend(blendMode.ordinal, Native.getPtr(bg), Native.getPtr(fg), crop))
    } finally {
      ReferenceUtil.reachabilityFence(bg)
      ReferenceUtil.reachabilityFence(fg)
    }
  }

  def makeBlur(sigmaX: Float, sigmaY: Float, mode: FilterTileMode): ImageFilter = makeBlur(sigmaX, sigmaY, mode, null, null)

  def makeBlur(sigmaX: Float, sigmaY: Float, mode: FilterTileMode, input: ImageFilter, crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeBlur(sigmaX, sigmaY, mode.ordinal, Native.getPtr(input), crop))
    } finally {
      ReferenceUtil.reachabilityFence(input)
    }
  }

  def makeColorFilter(f: ColorFilter, input: ImageFilter, crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeColorFilter(Native.getPtr(f), Native.getPtr(input), crop))
    } finally {
      ReferenceUtil.reachabilityFence(f)
      ReferenceUtil.reachabilityFence(input)
    }
  }

  def makeCompose(outer: ImageFilter, inner: ImageFilter): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeCompose(Native.getPtr(outer), Native.getPtr(inner)))
    } finally {
      ReferenceUtil.reachabilityFence(outer)
      ReferenceUtil.reachabilityFence(inner)
    }
  }

  def makeDisplacementMap(x: ColorChannel, y: ColorChannel, scale: Float, displacement: ImageFilter, color: ImageFilter, crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeDisplacementMap(x.ordinal, y.ordinal, scale, Native.getPtr(displacement), Native
        .getPtr(color), crop))
    } finally {
      ReferenceUtil.reachabilityFence(displacement)
      ReferenceUtil.reachabilityFence(color)
    }
  }

  def makeDropShadow(dx: Float, dy: Float, sigmaX: Float, sigmaY: Float, color: Int): ImageFilter = makeDropShadow(dx, dy, sigmaX, sigmaY, color, null, null)

  def makeDropShadow(dx: Float, dy: Float, sigmaX: Float, sigmaY: Float, color: Int, input: ImageFilter, crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeDropShadow(dx, dy, sigmaX, sigmaY, color, Native.getPtr(input), crop))
    } finally {
      ReferenceUtil.reachabilityFence(input)
    }
  }

  def makeDropShadowOnly(dx: Float, dy: Float, sigmaX: Float, sigmaY: Float, color: Int): ImageFilter = makeDropShadowOnly(dx, dy, sigmaX, sigmaY, color, null, null)

  def makeDropShadowOnly(dx: Float, dy: Float, sigmaX: Float, sigmaY: Float, color: Int, input: ImageFilter, crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeDropShadowOnly(dx, dy, sigmaX, sigmaY, color, Native.getPtr(input), crop))
    } finally {
      ReferenceUtil.reachabilityFence(input)
    }
  }

  def makeImage(image: Image): ImageFilter = {
    val r = Rect.makeWH(image.getWidth, image.getHeight)
    makeImage(image, r, r, SamplingMode.DEFAULT)
  }

  def makeImage(image: Image, src: Rect, dst: Rect, mode: SamplingMode): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeImage(Native.getPtr(image), src._left, src._top, src._right, src._bottom, dst._left, dst
        ._top, dst._right, dst._bottom, mode.packed))
    } finally {
      ReferenceUtil.reachabilityFence(image)
    }
  }

  def makeMagnifier(r: Rect, zoomAmount: Float, inset: Float, mode: SamplingMode, input: ImageFilter, crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeMagnifier(r._left, r._top, r._right, r._bottom, zoomAmount, inset, mode.packed, Native
        .getPtr(input), crop))
    } finally {
      ReferenceUtil.reachabilityFence(input)
    }
  }

  def makeMatrixConvolution(kernelW: Int, kernelH: Int, kernel: Array[Float], gain: Float, bias: Float, offsetX: Int, offsetY: Int, tileMode: FilterTileMode, convolveAlpha: Boolean, input: ImageFilter, crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeMatrixConvolution(kernelW, kernelH, kernel, gain, bias, offsetX, offsetY, tileMode
        .ordinal, convolveAlpha, Native.getPtr(input), crop))
    } finally {
      ReferenceUtil.reachabilityFence(input)
    }
  }

  def makeMatrixTransform(matrix: Matrix33, mode: SamplingMode, input: ImageFilter): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeMatrixTransform(matrix.mat, mode.packed, Native.getPtr(input)))
    } finally {
      ReferenceUtil.reachabilityFence(input)
    }
  }

  def makeMerge(filters: Array[ImageFilter], crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      val filterPtrs = new Array[Long](filters.length)
      util.Arrays.setAll(filterPtrs, (i: Int) => Native.getPtr(filters(i)))
      new ImageFilter(_nMakeMerge(filterPtrs, crop))
    } finally {
      ReferenceUtil.reachabilityFence(filters)
    }
  }

  def makeOffset(dx: Float, dy: Float, input: ImageFilter, crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeOffset(dx, dy, Native.getPtr(input), crop))
    } finally {
      ReferenceUtil.reachabilityFence(input)
    }
  }

  // public static ImageFilter makePicture(Picture picture, Rect target) {
  //     Native.onNativeCall();
  //     return new ImageFilter(_nMakePicture(Native.pointer(picture), target.left, target.top, target.right, target.bottom));
  // }
  def makeTile(src: Rect, dst: Rect, input: ImageFilter): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeTile(src._left, src._top, src._right, src._bottom, dst._left, dst._top, dst._right, dst
        ._bottom, Native.getPtr(input)))
    } finally {
      ReferenceUtil.reachabilityFence(input)
    }
  }

  def makeDilate(rx: Float, ry: Float, input: ImageFilter, crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeDilate(rx, ry, Native.getPtr(input), crop))
    } finally {
      ReferenceUtil.reachabilityFence(input)
    }
  }

  def makeErode(rx: Float, ry: Float, input: ImageFilter, crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeErode(rx, ry, Native.getPtr(input), crop))
    } finally {
      ReferenceUtil.reachabilityFence(input)
    }
  }

  def makeDistantLitDiffuse(x: Float, y: Float, z: Float, lightColor: Int, surfaceScale: Float, kd: Float, input: ImageFilter, crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeDistantLitDiffuse(x, y, z, lightColor, surfaceScale, kd, Native.getPtr(input), crop))
    } finally {
      ReferenceUtil.reachabilityFence(input)
    }
  }

  def makePointLitDiffuse(x: Float, y: Float, z: Float, lightColor: Int, surfaceScale: Float, kd: Float, input: ImageFilter, crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakePointLitDiffuse(x, y, z, lightColor, surfaceScale, kd, Native.getPtr(input), crop))
    } finally {
      ReferenceUtil.reachabilityFence(input)
    }
  }

  def makeSpotLitDiffuse(x0: Float, y0: Float, z0: Float, x1: Float, y1: Float, z1: Float, falloffExponent: Float, cutoffAngle: Float, lightColor: Int, surfaceScale: Float, kd: Float, input: ImageFilter, crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeSpotLitDiffuse(x0, y0, z0, x1, y1, z1, falloffExponent, cutoffAngle, lightColor, surfaceScale, kd, Native
        .getPtr(input), crop))
    } finally {
      ReferenceUtil.reachabilityFence(input)
    }
  }

  def makeDistantLitSpecular(x: Float, y: Float, z: Float, lightColor: Int, surfaceScale: Float, ks: Float, shininess: Float, input: ImageFilter, crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeDistantLitSpecular(x, y, z, lightColor, surfaceScale, ks, shininess, Native
        .getPtr(input), crop))
    } finally {
      ReferenceUtil.reachabilityFence(input)
    }
  }

  def makePointLitSpecular(x: Float, y: Float, z: Float, lightColor: Int, surfaceScale: Float, ks: Float, shininess: Float, input: ImageFilter, crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakePointLitSpecular(x, y, z, lightColor, surfaceScale, ks, shininess, Native
        .getPtr(input), crop))
    } finally {
      ReferenceUtil.reachabilityFence(input)
    }
  }

  def makeSpotLitSpecular(x0: Float, y0: Float, z0: Float, x1: Float, y1: Float, z1: Float, falloffExponent: Float, cutoffAngle: Float, lightColor: Int, surfaceScale: Float, ks: Float, shininess: Float, input: ImageFilter, crop: IRect): ImageFilter = {
    try {
      Stats.onNativeCall()
      new ImageFilter(_nMakeSpotLitSpecular(x0, y0, z0, x1, y1, z1, falloffExponent, cutoffAngle, lightColor, surfaceScale, ks, shininess, Native
        .getPtr(input), crop))
    } finally {
      ReferenceUtil.reachabilityFence(input)
    }
  }

  @native def _nMakeArithmetic(k1: Float, k2: Float, k3: Float, k4: Float, enforcePMColor: Boolean, bg: Long, fg: Long, crop: IRect): Long

  @native def _nMakeBlend(blendMode: Int, bg: Long, fg: Long, crop: IRect): Long

  @native def _nMakeBlur(sigmaX: Float, sigmaY: Float, tileMode: Int, input: Long, crop: IRect): Long

  @native def _nMakeColorFilter(colorFilterPtr: Long, input: Long, crop: IRect): Long

  @native def _nMakeCompose(outer: Long, inner: Long): Long

  @native def _nMakeDisplacementMap(xChan: Int, yChan: Int, scale: Float, displacement: Long, color: Long, crop: IRect): Long

  @native def _nMakeDropShadow(dx: Float, dy: Float, sigmaX: Float, sigmaY: Float, color: Int, input: Long, crop: IRect): Long

  @native def _nMakeDropShadowOnly(dx: Float, dy: Float, sigmaX: Float, sigmaY: Float, color: Int, input: Long, crop: IRect): Long

  @native def _nMakeImage(image: Long, l0: Float, t0: Float, r0: Float, b0: Float, l1: Float, t1: Float, r1: Float, b1: Float, samplingMode: Long): Long

  @native def _nMakeMagnifier(l: Float, t: Float, r: Float, b: Float, zoomAmount: Float, inset: Float, samplingMode: Long, input: Long, crop: IRect): Long

  @native def _nMakeMatrixConvolution(kernelW: Int, kernelH: Int, kernel: Array[Float], gain: Float, bias: Float, offsetX: Int, offsetY: Int, tileMode: Int, convolveAlpha: Boolean, input: Long, crop: IRect): Long

  @native def _nMakeMatrixTransform(matrix: Array[Float], samplingMode: Long, input: Long): Long

  @native def _nMakeMerge(filters: Array[Long], crop: IRect): Long

  @native def _nMakeOffset(dx: Float, dy: Float, input: Long, crop: IRect): Long

  @native def _nMakePicture(picture: Long, l: Float, t: Float, r: Float, b: Float): Long

  @native def _nMakeTile(l0: Float, t0: Float, r0: Float, b0: Float, l1: Float, t1: Float, r1: Float, b1: Float, input: Long): Long

  @native def _nMakeDilate(rx: Float, ry: Float, input: Long, crop: IRect): Long

  @native def _nMakeErode(rx: Float, ry: Float, input: Long, crop: IRect): Long

  @native def _nMakeDistantLitDiffuse(x: Float, y: Float, z: Float, lightColor: Int, surfaceScale: Float, kd: Float, input: Long, crop: IRect): Long

  @native def _nMakePointLitDiffuse(x: Float, y: Float, z: Float, lightColor: Int, surfaceScale: Float, kd: Float, input: Long, crop: IRect): Long

  @native def _nMakeSpotLitDiffuse(x0: Float, y0: Float, z0: Float, x1: Float, y1: Float, z1: Float, falloffExponent: Float, cutoffAngle: Float, lightColor: Int, surfaceScale: Float, kd: Float, input: Long, crop: IRect): Long

  @native def _nMakeDistantLitSpecular(x: Float, y: Float, z: Float, lightColor: Int, surfaceScale: Float, ks: Float, shininess: Float, input: Long, crop: IRect): Long

  @native def _nMakePointLitSpecular(x: Float, y: Float, z: Float, lightColor: Int, surfaceScale: Float, ks: Float, shininess: Float, input: Long, crop: IRect): Long

  @native def _nMakeSpotLitSpecular(x0: Float, y0: Float, z0: Float, x1: Float, y1: Float, z1: Float, falloffExponent: Float, cutoffAngle: Float, lightColor: Int, surfaceScale: Float, ks: Float, shininess: Float, input: Long, crop: IRect): Long

  Library.staticLoad()
}

class ImageFilter @ApiStatus.Internal(ptr: Long) extends RefCnt(ptr, true) {
}