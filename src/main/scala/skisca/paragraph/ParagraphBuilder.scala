package gay.menkissing.skisca.paragraph

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object ParagraphBuilder {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @native def _nMake(paragraphStylePtr: Long, fontCollectionPtr: Long): Long

  @native def _nGetFinalizer: Long

  @native def _nPushStyle(ptr: Long, textStylePtr: Long): Unit

  @native def _nPopStyle(ptr: Long): Unit

  @native def _nAddText(ptr: Long, text: String): Unit

  @native def _nAddPlaceholder(ptr: Long, width: Float, height: Float, alignment: Int, baselineMode: Int, baseline: Float): Unit

  @native def _nBuild(ptr: Long): Long

  try Library.staticLoad()
}

class ParagraphBuilder(style: ParagraphStyle, fc: FontCollection) extends Managed(ParagraphBuilder
  ._nMake(Native.getPtr(style), Native.getPtr(fc)), ParagraphBuilder._FinalizerHolder.PTR, true) {
  Stats.onNativeCall()
  ReferenceUtil.reachabilityFence(style)
  ReferenceUtil.reachabilityFence(fc)
  @ApiStatus.Internal var _text: ManagedString = null

  def pushStyle(style: TextStyle): ParagraphBuilder = {
    try {
      Stats.onNativeCall()
      ParagraphBuilder._nPushStyle(_ptr, Native.getPtr(style))
      this
    } finally {
      ReferenceUtil.reachabilityFence(style)
    }
  }

  def popStyle: ParagraphBuilder = {
    Stats.onNativeCall()
    ParagraphBuilder._nPopStyle(_ptr)
    this
  }

  def addText(text: String): ParagraphBuilder = {
    Stats.onNativeCall()
    ParagraphBuilder._nAddText(_ptr, text)
    if (_text == null) {
      _text = new ManagedString(text)
    } else {
      _text.append(text)
    }
    this
  }

  def addPlaceholder(style: PlaceholderStyle): ParagraphBuilder = {
    Stats.onNativeCall()
    ParagraphBuilder
      ._nAddPlaceholder(_ptr, style.width, style.height, style.alignment.ordinal, style.baselineMode
                                                                                                .ordinal, style
        .baseline)
    this
  }

  def build: Paragraph = {
    try {
      Stats.onNativeCall()
      val paragraph = new Paragraph(ParagraphBuilder._nBuild(_ptr), _text)
      _text = null
      paragraph
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}