package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object PixelRef {
  @ApiStatus.Internal
  @native def _nGetWidth(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nGetHeight(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nGetRowBytes(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nGetGenerationId(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nNotifyPixelsChanged(ptr: Long): Unit

  @ApiStatus.Internal
  @native def _nIsImmutable(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nSetImmutable(ptr: Long): Unit

  try Library.staticLoad()
}

class PixelRef @ApiStatus.Internal(ptr: Long) extends RefCnt(ptr) {
  def getWidth: Int = {
    try {
      Stats.onNativeCall()
      PixelRef._nGetWidth(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getHeight: Int = {
    try {
      Stats.onNativeCall()
      PixelRef._nGetHeight(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getRowBytes: Long = {
    try {
      Stats.onNativeCall()
      PixelRef._nGetRowBytes(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns a non-zero, unique value corresponding to the pixels in this
   * pixelref. Each time the pixels are changed (and notifyPixelsChanged is
   * called), a different generation ID will be returned.
   */
  def getGenerationId: Int = {
    try {
      Stats.onNativeCall()
      PixelRef._nGetGenerationId(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Call this if you have changed the contents of the pixels. This will in-
   * turn cause a different generation ID value to be returned from
   * getGenerationID().
   */
  def notifyPixelsChanged: PixelRef = {
    Stats.onNativeCall()
    PixelRef._nNotifyPixelsChanged(_ptr)
    this
  }

  /**
   * Returns true if this pixelref is marked as immutable, meaning that the
   * contents of its pixels will not change for the lifetime of the pixelref.
   */
  def isImmutable: Boolean = {
    try {
      Stats.onNativeCall()
      PixelRef._nIsImmutable(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Marks this pixelref is immutable, meaning that the contents of its
   * pixels will not change for the lifetime of the pixelref. This state can
   * be set on a pixelref, but it cannot be cleared once it is set.
   */
  def setImmutable: PixelRef = {
    Stats.onNativeCall()
    PixelRef._nSetImmutable(_ptr)
    this
  }
}