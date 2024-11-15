package gay.menkissing.skisca.paragraph

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object ParagraphStyle {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nMake: Long

  @ApiStatus.Internal
  @native def _nEquals(ptr: Long, otherPtr: Long): Boolean

  @ApiStatus.Internal
  @native def _nGetStrutStyle(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nSetStrutStyle(ptr: Long, stylePtr: Long): Unit

  @ApiStatus.Internal
  @native def _nGetTextStyle(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nSetTextStyle(ptr: Long, textStylePtr: Long): Unit

  @ApiStatus.Internal
  @native def _nGetDirection(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nSetDirection(ptr: Long, direction: Int): Unit

  @ApiStatus.Internal
  @native def _nGetAlignment(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nSetAlignment(ptr: Long, align: Int): Unit

  @ApiStatus.Internal
  @native def _nGetMaxLinesCount(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nSetMaxLinesCount(ptr: Long, maxLines: Long): Unit

  @ApiStatus.Internal
  @native def _nGetEllipsis(ptr: Long): String

  @ApiStatus.Internal
  @native def _nSetEllipsis(ptr: Long, ellipsis: String): Unit

  @ApiStatus.Internal
  @native def _nGetHeight(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nSetHeight(ptr: Long, height: Float): Unit

  @ApiStatus.Internal
  @native def _nGetHeightMode(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nSetHeightMode(ptr: Long, v: Int): Unit

  @ApiStatus.Internal
  @native def _nGetEffectiveAlignment(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nIsHintingEnabled(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nDisableHinting(ptr: Long): Unit

  try Library.staticLoad()
}

class ParagraphStyle extends Managed(ParagraphStyle._nMake, ParagraphStyle._FinalizerHolder.PTR, true) {
  Stats.onNativeCall()

  @ApiStatus.Internal override def _nativeEquals(other: Native): Boolean = {
    try {
      Stats.onNativeCall()
      ParagraphStyle._nEquals(_ptr, Native.getPtr(other))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(other)
    }
  }

  def getStrutStyle: StrutStyle = {
    try {
      Stats.onNativeCall()
      new StrutStyle(ParagraphStyle._nGetStrutStyle(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setStrutStyle(s: StrutStyle): ParagraphStyle = {
    try {
      Stats.onNativeCall()
      ParagraphStyle._nSetStrutStyle(_ptr, Native.getPtr(s))
      this
    } finally {
      ReferenceUtil.reachabilityFence(s)
    }
  }

  def getTextStyle: TextStyle = {
    try {
      Stats.onNativeCall()
      new TextStyle(ParagraphStyle._nGetTextStyle(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setTextStyle(style: TextStyle): ParagraphStyle = {
    try {
      Stats.onNativeCall()
      ParagraphStyle._nSetTextStyle(_ptr, Native.getPtr(style))
      this
    } finally {
      ReferenceUtil.reachabilityFence(style)
    }
  }

  def getDirection: Direction = {
    try {
      Stats.onNativeCall()
      Direction.values(ParagraphStyle._nGetDirection(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setDirection(style: Direction): ParagraphStyle = {
    Stats.onNativeCall()
    ParagraphStyle._nSetDirection(_ptr, style.ordinal)
    this
  }

  def getAlignment: Alignment = {
    try {
      Stats.onNativeCall()
      Alignment.values(ParagraphStyle._nGetAlignment(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setAlignment(alignment: Alignment): ParagraphStyle = {
    Stats.onNativeCall()
    ParagraphStyle._nSetAlignment(_ptr, alignment.ordinal)
    this
  }

  def getMaxLinesCount: Long = {
    try {
      Stats.onNativeCall()
      ParagraphStyle._nGetMaxLinesCount(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setMaxLinesCount(count: Long): ParagraphStyle = {
    Stats.onNativeCall()
    ParagraphStyle._nSetMaxLinesCount(_ptr, count)
    this
  }

  def getEllipsis: String = {
    try {
      Stats.onNativeCall()
      ParagraphStyle._nGetEllipsis(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setEllipsis(ellipsis: String): ParagraphStyle = {
    Stats.onNativeCall()
    ParagraphStyle._nSetEllipsis(_ptr, ellipsis)
    this
  }

  def getHeight: Float = {
    try {
      Stats.onNativeCall()
      ParagraphStyle._nGetHeight(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setHeight(height: Float): ParagraphStyle = {
    Stats.onNativeCall()
    ParagraphStyle._nSetHeight(_ptr, height)
    this
  }

  def getHeightMode: HeightMode = {
    try {
      Stats.onNativeCall()
      HeightMode.values(ParagraphStyle._nGetHeightMode(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setHeightMode(behavior: HeightMode): ParagraphStyle = {
    Stats.onNativeCall()
    ParagraphStyle._nSetHeightMode(_ptr, behavior.ordinal)
    this
  }

  def getEffectiveAlignment: Alignment = {
    try {
      Stats.onNativeCall()
      Alignment.values(ParagraphStyle._nGetEffectiveAlignment(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def isHintingEnabled: Boolean = {
    try {
      Stats.onNativeCall()
      ParagraphStyle._nIsHintingEnabled(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def disableHinting: ParagraphStyle = {
    Stats.onNativeCall()
    ParagraphStyle._nDisableHinting(_ptr)
    this
  }
}