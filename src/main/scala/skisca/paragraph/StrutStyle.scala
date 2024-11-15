package gay.menkissing.skisca.paragraph

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object StrutStyle {
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
  @native def _nGetFontFamilies(ptr: Long): Array[String]

  @ApiStatus.Internal
  @native def _nSetFontFamilies(ptr: Long, families: Array[String]): Unit

  @ApiStatus.Internal
  @native def _nGetFontStyle(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nSetFontStyle(ptr: Long, value: Int): Unit

  @ApiStatus.Internal
  @native def _nGetFontSize(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nSetFontSize(ptr: Long, value: Float): Unit

  @ApiStatus.Internal
  @native def _nGetHeight(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nSetHeight(ptr: Long, value: Float): Unit

  @ApiStatus.Internal
  @native def _nGetLeading(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nSetLeading(ptr: Long, value: Float): Unit

  @ApiStatus.Internal
  @native def _nIsEnabled(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nSetEnabled(ptr: Long, value: Boolean): Unit

  @ApiStatus.Internal
  @native def _nIsHeightForced(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nSetHeightForced(ptr: Long, value: Boolean): Unit

  @ApiStatus.Internal
  @native def _nIsHeightOverridden(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nSetHeightOverridden(ptr: Long, value: Boolean): Unit

  try Library.staticLoad()
}

class StrutStyle @ApiStatus.Internal(ptr: Long) extends Managed(ptr, StrutStyle._FinalizerHolder.PTR, true) {
  def this() = {
    this(StrutStyle._nMake)
    Stats.onNativeCall()
  }

  @ApiStatus.Internal override def _nativeEquals(other: Native): Boolean = {
    try {
      Stats.onNativeCall()
      StrutStyle._nEquals(_ptr, Native.getPtr(other))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(other)
    }
  }

  def getFontFamilies: Array[String] = {
    try {
      Stats.onNativeCall()
      StrutStyle._nGetFontFamilies(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setFontFamilies(families: Array[String]): StrutStyle = {
    Stats.onNativeCall()
    StrutStyle._nSetFontFamilies(_ptr, families)
    this
  }

  def getFontStyle: FontStyle = {
    try {
      Stats.onNativeCall()
      new FontStyle(StrutStyle._nGetFontStyle(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setFontStyle(style: FontStyle): StrutStyle = {
    Stats.onNativeCall()
    StrutStyle._nSetFontStyle(_ptr, style.value)
    this
  }

  def getFontSize: Float = {
    try {
      Stats.onNativeCall()
      StrutStyle._nGetFontSize(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setFontSize(value: Float): StrutStyle = {
    Stats.onNativeCall()
    StrutStyle._nSetFontSize(_ptr, value)
    this
  }

  def getHeight: Float = {
    try {
      Stats.onNativeCall()
      StrutStyle._nGetHeight(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setHeight(value: Float): StrutStyle = {
    Stats.onNativeCall()
    StrutStyle._nSetHeight(_ptr, value)
    this
  }

  def getLeading: Float = {
    try {
      Stats.onNativeCall()
      StrutStyle._nGetLeading(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setLeading(value: Float): StrutStyle = {
    Stats.onNativeCall()
    StrutStyle._nSetLeading(_ptr, value)
    this
  }

  def isEnabled: Boolean = {
    try {
      Stats.onNativeCall()
      StrutStyle._nIsEnabled(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setEnabled(value: Boolean): StrutStyle = {
    Stats.onNativeCall()
    StrutStyle._nSetEnabled(_ptr, value)
    this
  }

  def isHeightForced: Boolean = {
    try {
      Stats.onNativeCall()
      StrutStyle._nIsHeightForced(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setHeightForced(value: Boolean): StrutStyle = {
    Stats.onNativeCall()
    StrutStyle._nSetHeightForced(_ptr, value)
    this
  }

  def isHeightOverridden: Boolean = {
    try {
      Stats.onNativeCall()
      StrutStyle._nIsHeightOverridden(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setHeightOverridden(value: Boolean): StrutStyle = {
    Stats.onNativeCall()
    StrutStyle._nSetHeightOverridden(_ptr, value)
    this
  }
}