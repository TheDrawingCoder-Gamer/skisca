package gay.menkissing.skisca.paragraph

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object FontCollection {
  @native def _nMake: Long

  @native def _nGetFontManagersCount(ptr: Long): Long

  @native def _nSetAssetFontManager(ptr: Long, fontManagerPtr: Long): Long

  @native def _nSetDynamicFontManager(ptr: Long, fontManagerPtr: Long): Long

  @native def _nSetTestFontManager(ptr: Long, fontManagerPtr: Long): Long

  @native def _nSetDefaultFontManager(ptr: Long, fontManagerPtr: Long, defaultFamilyName: String): Long

  @native def _nGetFallbackManager(ptr: Long): Long

  @native def _nFindTypefaces(ptr: Long, familyNames: Array[String], fontStyle: Int): Array[Long]

  @native def _nDefaultFallbackChar(ptr: Long, unicode: Int, fontStyle: Int, locale: String): Long

  @native def _nDefaultFallback(ptr: Long): Long

  @native def _nSetEnableFallback(ptr: Long, value: Boolean): Long

  @native def _nGetParagraphCache(ptr: Long): Long

  try Library.staticLoad()
}

class FontCollection @ApiStatus.Internal(ptr: Long) extends RefCnt(ptr, true) {
  def this() = {
    this(FontCollection._nMake)
    Stats.onNativeCall()
  }

  def getFontManagersCount: Long = {
    try {
      Stats.onNativeCall()
      FontCollection._nGetFontManagersCount(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setAssetFontManager(fontMgr: FontMgr): FontCollection = {
    try {
      Stats.onNativeCall()
      FontCollection._nSetAssetFontManager(_ptr, Native.getPtr(fontMgr))
      this
    } finally {
      ReferenceUtil.reachabilityFence(fontMgr)
    }
  }

  def setDynamicFontManager(fontMgr: FontMgr): FontCollection = {
    try {
      Stats.onNativeCall()
      FontCollection._nSetDynamicFontManager(_ptr, Native.getPtr(fontMgr))
      this
    } finally {
      ReferenceUtil.reachabilityFence(fontMgr)
    }
  }

  def setTestFontManager(fontMgr: FontMgr): FontCollection = {
    try {
      Stats.onNativeCall()
      FontCollection._nSetTestFontManager(_ptr, Native.getPtr(fontMgr))
      this
    } finally {
      ReferenceUtil.reachabilityFence(fontMgr)
    }
  }

  def setDefaultFontManager(fontMgr: FontMgr): FontCollection = setDefaultFontManager(fontMgr, null)

  def setDefaultFontManager(fontMgr: FontMgr, defaultFamilyName: String): FontCollection = {
    try {
      Stats.onNativeCall()
      FontCollection._nSetDefaultFontManager(_ptr, Native.getPtr(fontMgr), defaultFamilyName)
      this
    } finally {
      ReferenceUtil.reachabilityFence(fontMgr)
    }
  }

  def getFallbackManager: FontMgr = {
    try {
      Stats.onNativeCall()
      val ptr = FontCollection._nGetFallbackManager(_ptr)
      if (ptr == 0) {
        null
      } else {
        new FontMgr(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def findTypefaces(familyNames: Array[String], style: FontStyle): Array[Typeface] = {
    try {
      Stats.onNativeCall()
      val ptrs = FontCollection._nFindTypefaces(_ptr, familyNames, style.value)
      val res = new Array[Typeface](ptrs.length)
      for (i <- 0 until ptrs.length) {
        res(i) = new Typeface(ptrs(i))
      }
      res
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def defaultFallback(unicode: Int, style: FontStyle, locale: String): Typeface = {
    try {
      Stats.onNativeCall()
      val ptr = FontCollection._nDefaultFallbackChar(_ptr, unicode, style.value, locale)
      if (ptr == 0) {
        null
      } else {
        new Typeface(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def defaultFallback: Typeface = {
    try {
      Stats.onNativeCall()
      val ptr = FontCollection._nDefaultFallback(_ptr)
      if (ptr == 0) {
        null
      } else {
        new Typeface(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setEnableFallback(value: Boolean): FontCollection = {
    Stats.onNativeCall()
    FontCollection._nSetEnableFallback(_ptr, value)
    this
  }

  def getParagraphCache: ParagraphCache = {
    try {
      Stats.onNativeCall()
      new ParagraphCache(this, FontCollection._nGetParagraphCache(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}