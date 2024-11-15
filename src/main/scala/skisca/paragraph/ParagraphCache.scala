package gay.menkissing.skisca.paragraph

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object ParagraphCache {
  @ApiStatus.Internal
  @native def _nAbandon(ptr: Long): Unit

  @ApiStatus.Internal
  @native def _nReset(ptr: Long): Unit

  @ApiStatus.Internal
  @native def _nUpdateParagraph(ptr: Long, paragraphPtr: Long): Boolean

  @ApiStatus.Internal
  @native def _nFindParagraph(ptr: Long, paragraphPtr: Long): Boolean

  @ApiStatus.Internal
  @native def _nPrintStatistics(ptr: Long): Unit

  @ApiStatus.Internal
  @native def _nSetEnabled(ptr: Long, value: Boolean): Unit

  @ApiStatus.Internal
  @native def _nGetCount(ptr: Long): Int

  try Library.staticLoad()
}

class ParagraphCache @ApiStatus.Internal(@ApiStatus.Internal val _owner: FontCollection, ptr: Long) extends Native(ptr) {
  def abandon(): Unit = {
    try {
      _validate()
      Stats.onNativeCall()
      ParagraphCache._nAbandon(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def reset(): Unit = {
    try {
      _validate()
      Stats.onNativeCall()
      ParagraphCache._nReset(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def updateParagraph(paragraph: Paragraph): Boolean = {
    try {
      _validate()
      Stats.onNativeCall()
      ParagraphCache._nUpdateParagraph(_ptr, Native.getPtr(paragraph))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(paragraph)
    }
  }

  def findParagraph(paragraph: Paragraph): Boolean = {
    try {
      _validate()
      Stats.onNativeCall()
      ParagraphCache._nFindParagraph(_ptr, Native.getPtr(paragraph))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(paragraph)
    }
  }

  def printStatistics(): Unit = {
    try {
      _validate()
      Stats.onNativeCall()
      ParagraphCache._nPrintStatistics(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setEnabled(value: Boolean): Unit = {
    try {
      _validate()
      Stats.onNativeCall()
      ParagraphCache._nSetEnabled(_ptr, value)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getCount: Int = {
    try {
      _validate()
      Stats.onNativeCall()
      ParagraphCache._nGetCount(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @ApiStatus.Internal def _validate(): Unit = {
    try {
      if (Native
        .getPtr(_owner) == 0) {
        throw new IllegalStateException("ParagraphCache needs owning FontCollection to be alive")
      }
    } finally {
      ReferenceUtil.reachabilityFence(_owner)
    }
  }
}