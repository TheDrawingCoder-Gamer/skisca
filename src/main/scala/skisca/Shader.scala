package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object Shader { // Linear
  def makeLinearGradient(p0: Point, p1: Point, colors: Array[Int]): Shader = {
    makeLinearGradient(p0._x, p0._y, p1._x, p1
      ._y, colors)
  }

  def makeLinearGradient(x0: Float, y0: Float, x1: Float, y1: Float, colors: Array[Int]): Shader = {
    makeLinearGradient(x0, y0, x1, y1, colors, null, GradientStyle
      .DEFAULT)
  }

  def makeLinearGradient(p0: Point, p1: Point, colors: Array[Int], positions: Array[Float]): Shader = {
    makeLinearGradient(p0
      ._x, p0._y, p1._x, p1._y, colors, positions)
  }

  def makeLinearGradient(x0: Float, y0: Float, x1: Float, y1: Float, colors: Array[Int], positions: Array[Float]): Shader = {
    makeLinearGradient(x0, y0, x1, y1, colors, positions, GradientStyle
      .DEFAULT)
  }

  def makeLinearGradient(p0: Point, p1: Point, colors: Array[Int], positions: Array[Float], style: GradientStyle): Shader = {
    makeLinearGradient(p0
      ._x, p0._y, p1._x, p1._y, colors, positions, style)
  }

  def makeLinearGradient(x0: Float, y0: Float, x1: Float, y1: Float, colors: Array[Int], positions: Array[Float], style: GradientStyle): Shader = {
    assert(positions == null || colors.length == positions.length, "colors.length " + colors
      .length + "!= positions.length " + positions.length)
    Stats.onNativeCall()
    new Shader(_nMakeLinearGradient(x0, y0, x1, y1, colors, positions, style.tileMode.ordinal, style._getFlags, style
      ._getMatrixArray))
  }

  def makeLinearGradient(p0: Point, p1: Point, colors: Array[Color4f], cs: ColorSpace, positions: Array[Float], style: GradientStyle): Shader = {
    makeLinearGradient(p0
      ._x, p0._y, p1._x, p1._y, colors, cs, positions, style)
  }

  def makeLinearGradient(x0: Float, y0: Float, x1: Float, y1: Float, colors: Array[Color4f], cs: ColorSpace, positions: Array[Float], style: GradientStyle): Shader = {
    try {
      assert(positions == null || colors.length == positions.length, "colors.length " + colors
        .length + "!= positions.length " + positions.length)
      Stats.onNativeCall()
      new Shader(_nMakeLinearGradientCS(x0, y0, x1, y1, Color4f.flattenArray(colors), Native
        .getPtr(cs), positions, style
        .tileMode.ordinal, style._getFlags, style._getMatrixArray))
    } finally {
      ReferenceUtil.reachabilityFence(cs)
    }
  }

  // Radial
  def makeRadialGradient(center: Point, r: Float, colors: Array[Int]): Shader = {
    makeRadialGradient(center._x, center
      ._y, r, colors)
  }

  def makeRadialGradient(x: Float, y: Float, r: Float, colors: Array[Int]): Shader = {
    makeRadialGradient(x, y, r, colors, null, GradientStyle
      .DEFAULT)
  }

  def makeRadialGradient(center: Point, r: Float, colors: Array[Int], positions: Array[Float]): Shader = {
    makeRadialGradient(center
      ._x, center._y, r, colors, positions)
  }

  def makeRadialGradient(x: Float, y: Float, r: Float, colors: Array[Int], positions: Array[Float]): Shader = {
    makeRadialGradient(x, y, r, colors, positions, GradientStyle
      .DEFAULT)
  }

  def makeRadialGradient(center: Point, r: Float, colors: Array[Int], positions: Array[Float], style: GradientStyle): Shader = {
    makeRadialGradient(center
      ._x, center._y, r, colors, positions, style)
  }

  def makeRadialGradient(x: Float, y: Float, r: Float, colors: Array[Int], positions: Array[Float], style: GradientStyle): Shader = {
    assert(positions == null || colors.length == positions.length, "colors.length " + colors
      .length + "!= positions.length " + positions.length)
    Stats.onNativeCall()
    new Shader(_nMakeRadialGradient(x, y, r, colors, positions, style.tileMode.ordinal, style._getFlags, style
      ._getMatrixArray))
  }

  def makeRadialGradient(center: Point, r: Float, colors: Array[Color4f], cs: ColorSpace, positions: Array[Float], style: GradientStyle): Shader = {
    makeRadialGradient(center
      ._x, center._y, r, colors, cs, positions, style)
  }

  def makeRadialGradient(x: Float, y: Float, r: Float, colors: Array[Color4f], cs: ColorSpace, positions: Array[Float], style: GradientStyle): Shader = {
    try {
      assert(positions == null || colors.length == positions.length, "colors.length " + colors
        .length + "!= positions.length " + positions.length)
      Stats.onNativeCall()
      new Shader(_nMakeRadialGradientCS(x, y, r, Color4f.flattenArray(colors), Native.getPtr(cs), positions, style
        .tileMode.ordinal, style._getFlags, style._getMatrixArray))
    } finally {
      ReferenceUtil.reachabilityFence(cs)
    }
  }

  // Two-point Conical
  def makeTwoPointConicalGradient(p0: Point, r0: Float, p1: Point, r1: Float, colors: Array[Int]): Shader = {
    makeTwoPointConicalGradient(p0
      ._x, p0._y, r0, p1._x, p1._y, r1, colors)
  }

  def makeTwoPointConicalGradient(x0: Float, y0: Float, r0: Float, x1: Float, y1: Float, r1: Float, colors: Array[Int]): Shader = {
    makeTwoPointConicalGradient(x0, y0, r0, x1, y1, r1, colors, null, GradientStyle
      .DEFAULT)
  }

  def makeTwoPointConicalGradient(p0: Point, r0: Float, p1: Point, r1: Float, colors: Array[Int], positions: Array[Float]): Shader = {
    makeTwoPointConicalGradient(p0
      ._x, p0._y, r0, p1._x, p1._y, r1, colors, positions)
  }

  def makeTwoPointConicalGradient(x0: Float, y0: Float, r0: Float, x1: Float, y1: Float, r1: Float, colors: Array[Int], positions: Array[Float]): Shader = {
    makeTwoPointConicalGradient(x0, y0, r0, x1, y1, r1, colors, positions, GradientStyle
      .DEFAULT)
  }

  def makeTwoPointConicalGradient(p0: Point, r0: Float, p1: Point, r1: Float, colors: Array[Int], positions: Array[Float], style: GradientStyle): Shader = {
    makeTwoPointConicalGradient(p0
      ._x, p0._y, r0, p1._x, p1._y, r1, colors, positions, style)
  }

  def makeTwoPointConicalGradient(x0: Float, y0: Float, r0: Float, x1: Float, y1: Float, r1: Float, colors: Array[Int], positions: Array[Float], style: GradientStyle): Shader = {
    assert(positions == null || colors.length == positions.length, "colors.length " + colors
      .length + "!= positions.length " + positions.length)
    Stats.onNativeCall()
    new Shader(_nMakeTwoPointConicalGradient(x0, y0, r0, x1, y1, r1, colors, positions, style.tileMode.ordinal, style
      ._getFlags, style._getMatrixArray))
  }

  def makeTwoPointConicalGradient(p0: Point, r0: Float, p1: Point, r1: Float, colors: Array[Color4f], cs: ColorSpace, positions: Array[Float], style: GradientStyle): Shader = {
    makeTwoPointConicalGradient(p0
      ._x, p0._y, r0, p1._x, p1._y, r1, colors, cs, positions, style)
  }

  def makeTwoPointConicalGradient(x0: Float, y0: Float, r0: Float, x1: Float, y1: Float, r1: Float, colors: Array[Color4f], cs: ColorSpace, positions: Array[Float], style: GradientStyle): Shader = {
    try {
      assert(positions == null || colors.length == positions.length, "colors.length " + colors
        .length + "!= positions.length " + positions.length)
      Stats.onNativeCall()
      new Shader(_nMakeTwoPointConicalGradientCS(x0, y0, r0, x1, y1, r1, Color4f.flattenArray(colors), Native
        .getPtr(cs), positions, style.tileMode.ordinal, style._getFlags, style._getMatrixArray))
    } finally {
      ReferenceUtil.reachabilityFence(cs)
    }
  }

  // Sweep
  def makeSweepGradient(center: Point, colors: Array[Int]): Shader = {
    makeSweepGradient(center._x, center._y, colors)
  }

  def makeSweepGradient(x: Float, y: Float, colors: Array[Int]): Shader = {
    makeSweepGradient(x, y, 0, 360, colors, null, GradientStyle
      .DEFAULT)
  }

  def makeSweepGradient(center: Point, colors: Array[Int], positions: Array[Float]): Shader = {
    makeSweepGradient(center
      ._x, center._y, colors, positions)
  }

  def makeSweepGradient(x: Float, y: Float, colors: Array[Int], positions: Array[Float]): Shader = {
    makeSweepGradient(x, y, 0, 360, colors, positions, GradientStyle
      .DEFAULT)
  }

  def makeSweepGradient(center: Point, colors: Array[Int], positions: Array[Float], style: GradientStyle): Shader = {
    makeSweepGradient(center
      ._x, center._y, colors, positions, style)
  }

  def makeSweepGradient(x: Float, y: Float, colors: Array[Int], positions: Array[Float], style: GradientStyle): Shader = makeSweepGradient(x, y, 0, 360, colors, positions, style)

  def makeSweepGradient(center: Point, startAngle: Float, endAngle: Float, colors: Array[Int], positions: Array[Float], style: GradientStyle): Shader = {
    makeSweepGradient(center
      ._x, center._y, startAngle, endAngle, colors, positions, style)
  }

  def makeSweepGradient(x: Float, y: Float, startAngle: Float, endAngle: Float, colors: Array[Int], positions: Array[Float], style: GradientStyle): Shader = {
    assert(positions == null || colors.length == positions.length, "colors.length " + colors
      .length + "!= positions.length " + positions.length)
    Stats.onNativeCall()
    new Shader(_nMakeSweepGradient(x, y, startAngle, endAngle, colors, positions, style.tileMode.ordinal, style
      ._getFlags, style._getMatrixArray))
  }

  def makeSweepGradient(center: Point, startAngle: Float, endAngle: Float, colors: Array[Color4f], cs: ColorSpace, positions: Array[Float], style: GradientStyle): Shader = {
    makeSweepGradient(center
      ._x, center._y, startAngle, endAngle, colors, cs, positions, style)
  }

  def makeSweepGradient(x: Float, y: Float, startAngle: Float, endAngle: Float, colors: Array[Color4f], cs: ColorSpace, positions: Array[Float], style: GradientStyle): Shader = {
    try {
      assert(positions == null || colors.length == positions.length, "colors.length " + colors
        .length + "!= positions.length " + positions.length)
      Stats.onNativeCall()
      new Shader(_nMakeSweepGradientCS(x, y, startAngle, endAngle, Color4f.flattenArray(colors), Native
        .getPtr(cs), positions, style.tileMode.ordinal, style._getFlags, style._getMatrixArray))
    } finally {
      ReferenceUtil.reachabilityFence(cs)
    }
  }

  //
  def makeEmpty: Shader = {
    Stats.onNativeCall()
    new Shader(_nMakeEmpty)
  }

  def makeColor(color: Int): Shader = {
    Stats.onNativeCall()
    new Shader(_nMakeColor(color))
  }

  def makeColor(color: Color4f, space: ColorSpace): Shader = {
    try {
      Stats.onNativeCall()
      new Shader(_nMakeColorCS(color.r, color.g, color.b, color.a, Native.getPtr(space)))
    } finally {
      ReferenceUtil.reachabilityFence(space)
    }
  }

  def makeBlend(mode: BlendMode, dst: Shader, src: Shader): Shader = {
    try {
      Stats.onNativeCall()
      new Shader(_nMakeBlend(mode.ordinal, Native.getPtr(dst), Native.getPtr(src)))
    } finally {
      ReferenceUtil.reachabilityFence(dst)
      ReferenceUtil.reachabilityFence(src)
    }
  }

  @native def _nMakeWithColorFilter(ptr: Long, colorFilterPtr: Long): Long

  @native def _nMakeLinearGradient(x0: Float, y0: Float, x1: Float, y1: Float, colors: Array[Int], positions: Array[Float], tileType: Int, flags: Int, matrix: Array[Float]): Long

  @native def _nMakeLinearGradientCS(x0: Float, y0: Float, x1: Float, y1: Float, colors: Array[Float], colorSpacePtr: Long, positions: Array[Float], tileType: Int, flags: Int, matrix: Array[Float]): Long

  @native def _nMakeRadialGradient(x: Float, y: Float, r: Float, colors: Array[Int], positions: Array[Float], tileType: Int, flags: Int, matrix: Array[Float]): Long

  @native def _nMakeRadialGradientCS(x: Float, y: Float, r: Float, colors: Array[Float], colorSpacePtr: Long, positions: Array[Float], tileType: Int, flags: Int, matrix: Array[Float]): Long

  @native def _nMakeTwoPointConicalGradient(x0: Float, y0: Float, r0: Float, x1: Float, y1: Float, r1: Float, colors: Array[Int], positions: Array[Float], tileType: Int, flags: Int, matrix: Array[Float]): Long

  @native def _nMakeTwoPointConicalGradientCS(x0: Float, y0: Float, r0: Float, x1: Float, y1: Float, r1: Float, colors: Array[Float], colorSpacePtr: Long, positions: Array[Float], tileType: Int, flags: Int, matrix: Array[Float]): Long

  @native def _nMakeSweepGradient(x: Float, y: Float, startAngle: Float, endAngle: Float, colors: Array[Int], positions: Array[Float], tileType: Int, flags: Int, matrix: Array[Float]): Long

  @native def _nMakeSweepGradientCS(x: Float, y: Float, startAngle: Float, endAngle: Float, colors: Array[Float], colorSpacePtr: Long, positions: Array[Float], tileType: Int, flags: Int, matrix: Array[Float]): Long

  @native def _nMakeEmpty: Long

  @native def _nMakeColor(color: Int): Long

  @native def _nMakeColorCS(r: Float, g: Float, b: Float, a: Float, colorSpacePtr: Long): Long

  @native def _nMakeBlend(blendMode: Int, dst: Long, src: Long): Long

  try Library.staticLoad()
}

class Shader @ApiStatus.Internal(ptr: Long) extends RefCnt(ptr) {
  def makeWithColorFilter(f: ColorFilter): Shader = {
    try {
      new Shader(Shader._nMakeWithColorFilter(_ptr, Native.getPtr(f)))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(f)
    }
  }
}