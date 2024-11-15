package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object Region {
  enum Op extends Enum[Op] {
    case DIFFERENCE, INTERSECT, UNION, XOR, REVERSE_DIFFERENCE, REPLACE
  }

  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @native def _nMake: Long

  @native def _nGetFinalizer: Long

  @native def _nSet(ptr: Long, regoinPtr: Long): Boolean

  @native def _nIsEmpty(ptr: Long): Boolean

  @native def _nIsRect(ptr: Long): Boolean

  @native def _nIsComplex(ptr: Long): Boolean

  @native def _nGetBounds(ptr: Long): IRect

  @native def _nComputeRegionComplexity(ptr: Long): Int

  @native def _nGetBoundaryPath(ptr: Long, pathPtr: Long): Boolean

  @native def _nSetEmpty(ptr: Long): Boolean

  @native def _nSetRect(ptr: Long, left: Int, top: Int, right: Int, bottom: Int): Boolean

  @native def _nSetRects(ptr: Long, rects: Array[Int]): Boolean

  @native def _nSetRegion(ptr: Long, regionPtr: Long): Boolean

  @native def _nSetPath(ptr: Long, pathPtr: Long, regionPtr: Long): Boolean

  @native def _nIntersectsIRect(ptr: Long, left: Int, top: Int, right: Int, bottom: Int): Boolean

  @native def _nIntersectsRegion(ptr: Long, regionPtr: Long): Boolean

  @native def _nContainsIPoint(ptr: Long, x: Int, y: Int): Boolean

  @native def _nContainsIRect(ptr: Long, left: Int, top: Int, right: Int, bottom: Int): Boolean

  @native def _nContainsRegion(ptr: Long, regionPtr: Long): Boolean

  @native def _nQuickContains(ptr: Long, left: Int, top: Int, right: Int, bottom: Int): Boolean

  @native def _nQuickRejectIRect(ptr: Long, left: Int, top: Int, right: Int, bottom: Int): Boolean

  @native def _nQuickRejectRegion(ptr: Long, regionPtr: Long): Boolean

  @native def _nTranslate(ptr: Long, dx: Int, dy: Int): Unit

  @native def _nOpIRect(ptr: Long, left: Int, top: Int, right: Int, bottom: Int, op: Int): Boolean

  @native def _nOpRegion(ptr: Long, regionPtr: Long, op: Int): Boolean

  @native def _nOpIRectRegion(ptr: Long, left: Int, top: Int, right: Int, bottom: Int, regionPtr: Long, op: Int): Boolean

  @native def _nOpRegionIRect(ptr: Long, regionPtr: Long, left: Int, top: Int, right: Int, bottom: Int, op: Int): Boolean

  @native def _nOpRegionRegion(ptr: Long, regionPtrA: Long, regionPtrB: Long, op: Int): Boolean

  try Library.staticLoad()
}

class Region extends Managed(Region._nMake, Region._FinalizerHolder.PTR) {
  Stats.onNativeCall()

  def set(r: Region): Boolean = {
    try {
      Stats.onNativeCall()
      Region._nSet(_ptr, Native.getPtr(r))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(r)
    }
  }

  def isEmpty: Boolean = {
    try {
      Stats.onNativeCall()
      Region._nIsEmpty(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def isRect: Boolean = {
    try {
      Stats.onNativeCall()
      Region._nIsRect(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def isComplex: Boolean = {
    try {
      Stats.onNativeCall()
      Region._nIsComplex(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getBounds: IRect = {
    try {
      Stats.onNativeCall()
      Region._nGetBounds(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def computeRegionComplexity: Int = {
    try {
      Stats.onNativeCall()
      Region._nComputeRegionComplexity(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getBoundaryPath(p: Path): Boolean = {
    try {
      Stats.onNativeCall()
      Region._nGetBoundaryPath(_ptr, Native.getPtr(p))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(p)
    }
  }

  def setEmpty: Boolean = {
    try {
      Stats.onNativeCall()
      Region._nSetEmpty(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setRect(rect: IRect): Boolean = {
    try {
      Stats.onNativeCall()
      Region._nSetRect(_ptr, rect._left, rect._top, rect._right, rect._bottom)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setRects(rects: Array[IRect]): Boolean = {
    try {
      val arr = new Array[Int](rects.length * 4)
      for (i <- 0 until rects.length) {
        arr(i * 4) = rects(i)._left
        arr(i * 4 + 1) = rects(i)._top
        arr(i * 4 + 2) = rects(i)._right
        arr(i * 4 + 3) = rects(i)._bottom
      }
      Stats.onNativeCall()
      Region._nSetRects(_ptr, arr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setRegion(r: Region): Boolean = {
    try {
      Stats.onNativeCall()
      Region._nSetRegion(_ptr, Native.getPtr(r))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(r)
    }
  }

  def setPath(path: Path, clip: Region): Boolean = {
    try {
      Stats.onNativeCall()
      Region._nSetPath(_ptr, Native.getPtr(path), Native.getPtr(clip))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(path)
      ReferenceUtil.reachabilityFence(clip)
    }
  }

  def intersects(rect: IRect): Boolean = {
    try {
      Stats.onNativeCall()
      Region._nIntersectsIRect(_ptr, rect._left, rect._top, rect._right, rect._bottom)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def intersects(r: Region): Boolean = {
    try {
      Stats.onNativeCall()
      Region._nIntersectsRegion(_ptr, Native.getPtr(r))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(r)
    }
  }

  def contains(x: Int, y: Int): Boolean = {
    try {
      Stats.onNativeCall()
      Region._nContainsIPoint(_ptr, x, y)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def contains(rect: IRect): Boolean = {
    try {
      Stats.onNativeCall()
      Region._nContainsIRect(_ptr, rect._left, rect._top, rect._right, rect._bottom)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def contains(r: Region): Boolean = {
    try {
      Stats.onNativeCall()
      Region._nContainsRegion(_ptr, Native.getPtr(r))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(r)
    }
  }

  def quickContains(rect: IRect): Boolean = {
    try {
      Stats.onNativeCall()
      Region._nQuickContains(_ptr, rect._left, rect._top, rect._right, rect._bottom)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def quickReject(rect: IRect): Boolean = {
    try {
      Stats.onNativeCall()
      Region._nQuickRejectIRect(_ptr, rect._left, rect._top, rect._right, rect._bottom)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def quickReject(r: Region): Boolean = {
    try {
      Stats.onNativeCall()
      Region._nQuickRejectRegion(_ptr, Native.getPtr(r))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(r)
    }
  }

  def translate(dx: Int, dy: Int): Unit = {
    try {
      Stats.onNativeCall()
      Region._nTranslate(_ptr, dx, dy)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def op(rect: IRect, op: Region.Op): Boolean = {
    try {
      Stats.onNativeCall()
      Region._nOpIRect(_ptr, rect._left, rect._top, rect._right, rect._bottom, op.ordinal)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def op(r: Region, op: Region.Op): Boolean = {
    try {
      Stats.onNativeCall()
      Region._nOpRegion(_ptr, Native.getPtr(r), op.ordinal)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(r)
    }
  }

  def op(rect: IRect, r: Region, op: Region.Op): Boolean = {
    try {
      Stats.onNativeCall()
      Region._nOpIRectRegion(_ptr, rect._left, rect._top, rect._right, rect._bottom, Native.getPtr(r), op.ordinal)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(r)
    }
  }

  def op(r: Region, rect: IRect, op: Region.Op): Boolean = {
    try {
      Stats.onNativeCall()
      Region._nOpRegionIRect(_ptr, Native.getPtr(r), rect._left, rect._top, rect._right, rect._bottom, op.ordinal)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(r)
    }
  }

  def op(a: Region, b: Region, op: Region.Op): Boolean = {
    try {
      Stats.onNativeCall()
      Region._nOpRegionRegion(_ptr, Native.getPtr(a), Native.getPtr(b), op.ordinal)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(a)
      ReferenceUtil.reachabilityFence(b)
    }
  }
}