package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object PathEffect {
  enum Style extends Enum[Style] {
    case

    /** translate the shape to each position */
    TRANSLATE,

    /** rotate the shape about its center */
    ROTATE,

    /** transform each point, and turn lines into curves */
    MORPH
  }

  def makePath1D(path: Path, advance: Float, phase: Float, style: PathEffect.Style): PathEffect = {
    try {
      Stats.onNativeCall()
      new PathEffect(_nMakePath1D(Native.getPtr(path), advance, phase, style.ordinal))
    } finally {
      ReferenceUtil.reachabilityFence(path)
    }
  }

  def makePath2D(matrix: Matrix33, path: Path): PathEffect = {
    try {
      Stats.onNativeCall()
      new PathEffect(_nMakePath2D(matrix.mat, Native.getPtr(path)))
    } finally {
      ReferenceUtil.reachabilityFence(path)
    }
  }

  def makeLine2D(width: Float, matrix: Matrix33): PathEffect = {
    Stats.onNativeCall()
    new PathEffect(_nMakeLine2D(width, matrix.mat))
  }

  def makeCorner(radius: Float): PathEffect = {
    Stats.onNativeCall()
    new PathEffect(_nMakeCorner(radius))
  }

  def makeDash(intervals: Array[Float], phase: Float): PathEffect = {
    Stats.onNativeCall()
    new PathEffect(_nMakeDash(intervals, phase))
  }

  def makeDiscrete(segLength: Float, dev: Float, seed: Int): PathEffect = {
    Stats.onNativeCall()
    new PathEffect(_nMakeDiscrete(segLength, dev, seed))
  }

  @native def _nMakeSum(firstPtr: Long, secondPtr: Long): Long

  @native def _nMakeCompose(outerPtr: Long, innerPtr: Long): Long

  @native def _nMakePath1D(pathPtr: Long, advance: Float, phase: Float, style: Int): Long

  @native def _nMakePath2D(matrix: Array[Float], pathPtr: Long): Long

  @native def _nMakeLine2D(width: Float, matrix: Array[Float]): Long

  @native def _nMakeCorner(radius: Float): Long

  @native def _nMakeDash(intervals: Array[Float], phase: Float): Long

  @native def _nMakeDiscrete(segLength: Float, dev: Float, seed: Int): Long

  Library.staticLoad()
}

class PathEffect @ApiStatus.Internal(ptr: Long) extends RefCnt(ptr, true) {
  def makeSum(second: PathEffect): PathEffect = {
    try {
      Stats.onNativeCall()
      new PathEffect(PathEffect._nMakeSum(_ptr, Native.getPtr(second)))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(second)
    }
  }

  def makeCompose(inner: PathEffect): PathEffect = {
    try {
      Stats.onNativeCall()
      new PathEffect(PathEffect._nMakeCompose(_ptr, Native.getPtr(inner)))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(inner)
    }
  }
}