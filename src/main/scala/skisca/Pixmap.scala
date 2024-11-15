package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.IRect
import org.jetbrains.annotations.*

import java.nio.ByteBuffer

object Pixmap {
  def apply(): Pixmap = {
    Stats.onNativeCall()
    new Pixmap(_nMakeNull())
  }
  
  def make(info: ImageInfo, buffer: ByteBuffer, rowBytes: Int): Pixmap = {
    make(info, BufferUtil
      .getPointerFromByteBuffer(buffer), rowBytes)
  }

  def make(info: ImageInfo, addr: Long, rowBytes: Int): Pixmap = {
    try {
      val ptr = _nMake(info.width, info.height, info.colorInfo.colorType.ordinal, info.colorInfo.alphaType
                                                                                          .ordinal, Native
        .getPtr(info.colorInfo.colorSpace), addr, rowBytes)
      if (ptr == 0) throw new IllegalArgumentException("Failed to create Pixmap.")
      new Pixmap(ptr, true)
    } finally {
      ReferenceUtil.reachabilityFence(info.colorInfo.colorSpace)
    }
  }

  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @native def _nGetFinalizer: Long

  @native def _nMakeNull(): Long

  @native def _nMake(width: Int, height: Int, colorType: Int, alphaType: Int, colorSpacePtr: Long, pixelsPtr: Long, rowBytes: Int): Long

  @native def _nReset(ptr: Long): Unit

  @native def _nResetWithInfo(ptr: Long, width: Int, height: Int, colorType: Int, alphaType: Int, colorSpacePtr: Long, pixelsPtr: Long, rowBytes: Int): Unit

  @native def _nSetColorSpace(ptr: Long, colorSpacePtr: Long): Unit

  @native def _nExtractSubset(ptr: Long, subsetPtr: Long, l: Int, t: Int, r: Int, b: Int): Boolean

  @native def _nGetInfo(ptr: Long): ImageInfo

  @native def _nGetRowBytes(ptr: Long): Int

  @native def _nGetAddr(ptr: Long): Long

  // TODO methods flattening ImageInfo not included yet - use GetInfo() instead.
  @native def _nGetRowBytesAsPixels(ptr: Long): Int

  // TODO shiftPerPixel
  @native def _nComputeByteSize(ptr: Long): Int

  @native def _nComputeIsOpaque(ptr: Long): Boolean

  @native def _nGetColor(ptr: Long, x: Int, y: Int): Int

  @native def _nGetColor4f(ptr: Long, x: Int, y: Int): Color4f

  @native def _nGetAlphaF(ptr: Long, x: Int, y: Int): Float

  @native def _nGetAddrAt(ptr: Long, x: Int, y: Int): Long

  // methods related to C++ types(addr8/16/32/64, writable_addr8/16/32/64) not included - not needed
  @native def _nReadPixels(ptr: Long, width: Int, height: Int, colorType: Int, alphaType: Int, colorSpacePtr: Long, dstPixelsPtr: Long, dstRowBytes: Int): Boolean

  @native def _nReadPixelsFromPoint(ptr: Long, width: Int, height: Int, colorType: Int, alphaType: Int, colorSpacePtr: Long, dstPixelsPtr: Long, dstRowBytes: Int, srcX: Int, srcY: Int): Boolean

  @native def _nReadPixelsToPixmap(ptr: Long, dstPixmapPtr: Long): Boolean

  @native def _nReadPixelsToPixmapFromPoint(ptr: Long, dstPixmapPtr: Long, srcX: Int, srcY: Int): Boolean

  @native def _nScalePixels(ptr: Long, dstPixmapPtr: Long, samplingOptions: Long): Boolean

  @native def _nErase(ptr: Long, color: Int): Boolean

  @native def _nEraseSubset(ptr: Long, color: Int, l: Int, t: Int, r: Int, b: Int): Boolean

  try Library.staticLoad()
}

class Pixmap @ApiStatus.Internal(ptr: Long, managed: Boolean = true) extends Managed(ptr, Pixmap._FinalizerHolder
                                                                                         .PTR, managed) {


  def reset(): Unit = {
    Stats.onNativeCall()
    Pixmap._nReset(_ptr)
    ReferenceUtil.reachabilityFence(this)
  }

  def reset(info: ImageInfo, addr: Long, rowBytes: Int): Unit = {
    Stats.onNativeCall()
    Pixmap
      ._nResetWithInfo(_ptr, info.width, info.height, info.colorInfo.colorType.ordinal, info.colorInfo.alphaType
                                                                                                .ordinal, Native
        .getPtr(info.colorInfo.colorSpace), addr, rowBytes)
    ReferenceUtil.reachabilityFence(this)
    ReferenceUtil.reachabilityFence(info.colorInfo.colorSpace)
  }

  def reset(info: ImageInfo, buffer: ByteBuffer, rowBytes: Int): Unit = {
    reset(info, BufferUtil.getPointerFromByteBuffer(buffer), rowBytes)
  }

  def setColorSpace(colorSpace: ColorSpace): Unit = {
    Stats.onNativeCall()
    Pixmap._nSetColorSpace(_ptr, Native.getPtr(colorSpace))
    ReferenceUtil.reachabilityFence(this)
    ReferenceUtil.reachabilityFence(colorSpace)
  }

  def extractSubset(subsetPtr: Long, area: IRect): Boolean = {
    try {
      Stats.onNativeCall()
      Pixmap._nExtractSubset(_ptr, subsetPtr, area._left, area._top, area._right, area._bottom)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def extractSubset(buffer: ByteBuffer, area: IRect): Boolean = {
    extractSubset(BufferUtil
      .getPointerFromByteBuffer(buffer), area)
  }

  def getInfo: ImageInfo = {
    Stats.onNativeCall()
    try {
      Pixmap._nGetInfo(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getRowBytes: Int = {
    Stats.onNativeCall()
    try {
      Pixmap._nGetRowBytes(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getAddr: Long = {
    Stats.onNativeCall()
    try {
      Pixmap._nGetAddr(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getRowBytesAsPixels: Int = {
    Stats.onNativeCall()
    try {
      Pixmap._nGetRowBytesAsPixels(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def computeByteSize: Int = {
    Stats.onNativeCall()
    try {
      Pixmap._nComputeByteSize(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def computeIsOpaque: Boolean = {
    Stats.onNativeCall()
    try {
      Pixmap._nComputeIsOpaque(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getColor(x: Int, y: Int): Int = {
    Stats.onNativeCall()
    try {
      Pixmap._nGetColor(_ptr, x, y)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getColor4f(x: Int, y: Int): Color4f = {
    Stats.onNativeCall()
    try {
      Pixmap._nGetColor4f(_ptr, x, y)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getAlphaF(x: Int, y: Int): Float = {
    Stats.onNativeCall()
    try {
      Pixmap._nGetAlphaF(_ptr, x, y)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getAddr(x: Int, y: Int): Long = {
    Stats.onNativeCall()
    try {
      Pixmap._nGetAddrAt(_ptr, x, y)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def readPixels(info: ImageInfo, addr: Long, rowBytes: Int): Boolean = {
    Stats.onNativeCall()
    try {
      Pixmap
        ._nReadPixels(_ptr, info.width, info.height, info.colorInfo.colorType.ordinal, info.colorInfo.alphaType
                                                                                               .ordinal, Native
          .getPtr(info.colorInfo.colorSpace), addr, rowBytes)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(info.colorInfo.colorSpace)
    }
  }

  def readPixels(info: ImageInfo, addr: Long, rowBytes: Int, srcX: Int, srcY: Int): Boolean = {
    Stats.onNativeCall()
    try {
      Pixmap
        ._nReadPixelsFromPoint(_ptr, info.width, info.height, info.colorInfo.colorType.ordinal, info.colorInfo
                                                                                                        .alphaType
                                                                                                        .ordinal, Native
          .getPtr(info.colorInfo.colorSpace), addr, rowBytes, srcX, srcY)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(info.colorInfo.colorSpace)
    }
  }

  def readPixels(pixmap: Pixmap): Boolean = {
    Stats.onNativeCall()
    try {
      Pixmap._nReadPixelsToPixmap(_ptr, Native.getPtr(pixmap))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(pixmap)
    }
  }

  def readPixels(pixmap: Pixmap, srcX: Int, srcY: Int): Boolean = {
    Stats.onNativeCall()
    try {
      Pixmap._nReadPixelsToPixmapFromPoint(_ptr, Native.getPtr(pixmap), srcX, srcY)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(pixmap)
    }
  }

  def scalePixels(dstPixmap: Pixmap, samplingMode: SamplingMode): Boolean = {
    Stats.onNativeCall()
    try {
      Pixmap._nScalePixels(_ptr, Native.getPtr(dstPixmap), samplingMode.packed)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(dstPixmap)
    }
  }

  def erase(color: Int): Boolean = {
    Stats.onNativeCall()
    try {
      Pixmap._nErase(_ptr, color)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def erase(color: Int, subset: IRect): Boolean = {
    Stats.onNativeCall()
    try {
      Pixmap._nEraseSubset(_ptr, color, subset._left, subset._top, subset._right, subset._bottom)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getBuffer: ByteBuffer = BufferUtil.getByteBufferFromPointer(getAddr, computeByteSize)
  // TODO float erase methods not included
}