package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object FontStyleSet {
  def makeEmpty: FontStyleSet = {
    Stats.onNativeCall()
    new FontStyleSet(_nMakeEmpty)
  }

  @native def _nMakeEmpty: Long

  @native def _nCount(ptr: Long): Int

  @native def _nGetStyle(ptr: Long, index: Int): Int

  @native def _nGetStyleName(ptr: Long, index: Int): String

  @native def _nGetTypeface(ptr: Long, index: Int): Long

  @native def _nMatchStyle(ptr: Long, style: Int): Long

  try Library.staticLoad()
}

class FontStyleSet @ApiStatus.Internal(ptr: Long) extends RefCnt(ptr) {
  def count: Int = {
    try {
      Stats.onNativeCall()
      FontStyleSet._nCount(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getStyle(index: Int): FontStyle = {
    try {
      Stats.onNativeCall()
      new FontStyle(FontStyleSet._nGetStyle(_ptr, index))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getStyleName(index: Int): String = {
    try {
      Stats.onNativeCall()
      FontStyleSet._nGetStyleName(_ptr, index)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getTypeface(index: Int): Typeface = {
    try {
      Stats.onNativeCall()
      val ptr = FontStyleSet._nGetTypeface(_ptr, index)
      if (ptr == 0) {
        null
      } else {
        new Typeface(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def matchStyle(style: FontStyle): Typeface = {
    try {
      Stats.onNativeCall()
      val ptr = FontStyleSet._nMatchStyle(_ptr, style.value)
      if (ptr == 0) {
        null
      } else {
        new Typeface(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}