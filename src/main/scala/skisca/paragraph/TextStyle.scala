package gay.menkissing.skisca.paragraph

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object TextStyle {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @native def _nGetFinalizer: Long

  @native def _nMake: Long

  @native def _nEquals(ptr: Long, otherPtr: Long): Boolean

  @native def _nAttributeEquals(ptr: Long, attribute: Int, otherPtr: Long): Boolean

  @native def _nGetColor(ptr: Long): Int

  @native def _nSetColor(ptr: Long, color: Int): Unit

  @native def _nGetForeground(ptr: Long): Long

  @native def _nSetForeground(ptr: Long, paintPtr: Long): Unit

  @native def _nGetBackground(ptr: Long): Long

  @native def _nSetBackground(ptr: Long, paintPtr: Long): Unit

  @native def _nGetDecorationStyle(ptr: Long): DecorationStyle

  @native def _nSetDecorationStyle(ptr: Long, underline: Boolean, overline: Boolean, lineThrough: Boolean, gaps: Boolean, color: Int, style: Int, thicknessMultiplier: Float): Unit

  @native def _nGetFontStyle(ptr: Long): Int

  @native def _nSetFontStyle(ptr: Long, fontStyle: Int): Unit

  @native def _nGetShadows(ptr: Long): Array[Shadow]

  @native def _nAddShadow(ptr: Long, color: Int, offsetX: Float, offsetY: Float, blurSigma: Double): Unit

  @native def _nClearShadows(ptr: Long): Unit

  @native def _nGetFontFeatures(ptr: Long): Array[FontFeature]

  @native def _nAddFontFeature(ptr: Long, name: String, value: Int): Unit

  @native def _nClearFontFeatures(ptr: Long): Unit

  @native def _nGetFontSize(ptr: Long): Float

  @native def _nSetFontSize(ptr: Long, size: Float): Unit

  @native def _nGetFontFamilies(ptr: Long): Array[String]

  @native def _nSetFontFamilies(ptr: Long, families: Array[String]): Unit

  @native def _nGetHeight(ptr: Long): java.lang.Float

  @native def _nSetHeight(ptr: Long, `override`: Boolean, height: Float): Unit

  @native def _nGetLetterSpacing(ptr: Long): Float

  @native def _nSetLetterSpacing(ptr: Long, letterSpacing: Float): Unit

  @native def _nGetWordSpacing(ptr: Long): Float

  @native def _nSetWordSpacing(ptr: Long, wordSpacing: Float): Unit

  @native def _nGetTypeface(ptr: Long): Long

  @native def _nSetTypeface(ptr: Long, typefacePtr: Long): Unit

  @native def _nGetLocale(ptr: Long): String

  @native def _nSetLocale(ptr: Long, locale: String): Unit

  @native def _nGetBaselineMode(ptr: Long): Int

  @native def _nSetBaselineMode(ptr: Long, mode: Int): Unit

  @native def _nGetFontMetrics(ptr: Long): FontMetrics

  @native def _nIsPlaceholder(ptr: Long): Boolean

  @native def _nSetPlaceholder(ptr: Long): Unit

  try Library.staticLoad()
}

class TextStyle @ApiStatus.Internal(ptr: Long) extends Managed(ptr, TextStyle._FinalizerHolder.PTR, true) {
  def this() = {
    this(TextStyle._nMake)
    Stats.onNativeCall()
  }

  @ApiStatus.Internal override def _nativeEquals(other: Native): Boolean = {
    try {
      Stats.onNativeCall()
      TextStyle._nEquals(_ptr, Native.getPtr(other))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(other)
    }
  }

  def equals(attribute: TextStyleAttribute, other: TextStyle): Boolean = {
    try {
      Stats.onNativeCall()
      TextStyle._nAttributeEquals(_ptr, attribute.ordinal, Native.getPtr(other))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(other)
    }
  }

  def getColor: Int = {
    try {
      Stats.onNativeCall()
      TextStyle._nGetColor(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setColor(color: Int): TextStyle = {
    Stats.onNativeCall()
    TextStyle._nSetColor(_ptr, color)
    this
  }

  @Nullable def getForeground: Paint = {
    try {
      Stats.onNativeCall()
      val ptr = TextStyle._nGetForeground(_ptr)
      if (ptr == 0) {
        null
      } else {
        new Paint(ptr, true)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setForeground(@Nullable paint: Paint): TextStyle = {
    try {
      Stats.onNativeCall()
      TextStyle._nSetForeground(_ptr, Native.getPtr(paint))
      this
    } finally {
      ReferenceUtil.reachabilityFence(paint)
    }
  }

  @Nullable def getBackground: Paint = {
    try {
      Stats.onNativeCall()
      val ptr = TextStyle._nGetBackground(_ptr)
      if (ptr == 0) {
        null
      } else {
        new Paint(ptr, true)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setBackground(@Nullable paint: Paint): TextStyle = {
    try {
      Stats.onNativeCall()
      TextStyle._nSetBackground(_ptr, Native.getPtr(paint))
      this
    } finally {
      ReferenceUtil.reachabilityFence(paint)
    }
  }

  def getDecorationStyle: DecorationStyle = {
    try {
      Stats.onNativeCall()
      TextStyle._nGetDecorationStyle(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setDecorationStyle(d: DecorationStyle): TextStyle = {
    Stats.onNativeCall()
    TextStyle
      ._nSetDecorationStyle(_ptr, d.underline, d.overline, d.lineThrough, d.gaps, d.color, d.lineStyle.ordinal, d
        .thicknessMultiplier)
    this
  }

  def getFontStyle: FontStyle = {
    try {
      Stats.onNativeCall()
      new FontStyle(TextStyle._nGetFontStyle(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setFontStyle(s: FontStyle): TextStyle = {
    Stats.onNativeCall()
    TextStyle._nSetFontStyle(_ptr, s.value)
    this
  }

  def getShadows: Array[Shadow] = {
    try {
      Stats.onNativeCall()
      TextStyle._nGetShadows(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def addShadow(s: Shadow): TextStyle = {
    Stats.onNativeCall()
    TextStyle._nAddShadow(_ptr, s.color, s.offsetX, s.offsetY, s.blurSigma)
    this
  }

  def addShadows(shadows: Array[Shadow]): TextStyle = {
    for (s <- shadows) {
      addShadow(s)
    }
    this
  }

  def clearShadows: TextStyle = {
    Stats.onNativeCall()
    TextStyle._nClearShadows(_ptr)
    this
  }

  def getFontFeatures: Array[FontFeature] = {
    try {
      Stats.onNativeCall()
      TextStyle._nGetFontFeatures(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def addFontFeature(f: FontFeature): TextStyle = {
    Stats.onNativeCall()
    TextStyle._nAddFontFeature(_ptr, f.feature, f.value)
    this
  }

  def addFontFeatures(FontFeatures: Array[FontFeature]): TextStyle = {
    for (s <- FontFeatures) {
      addFontFeature(s)
    }
    this
  }

  def clearFontFeatures: TextStyle = {
    Stats.onNativeCall()
    TextStyle._nClearFontFeatures(_ptr)
    this
  }

  def getFontSize: Float = {
    try {
      Stats.onNativeCall()
      TextStyle._nGetFontSize(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setFontSize(size: Float): TextStyle = {
    Stats.onNativeCall()
    TextStyle._nSetFontSize(_ptr, size)
    this
  }

  def getFontFamilies: Array[String] = {
    try {
      Stats.onNativeCall()
      TextStyle._nGetFontFamilies(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setFontFamily(family: String): TextStyle = setFontFamilies(Array[String](family))

  def setFontFamilies(families: Array[String]): TextStyle = {
    Stats.onNativeCall()
    TextStyle._nSetFontFamilies(_ptr, families)
    this
  }

  def getHeight: Option[Float] = {
    try {
      Stats.onNativeCall()
      Option(TextStyle._nGetHeight(_ptr)).map(_.floatValue())
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setHeight(height: Option[Float]): TextStyle = {
    Stats.onNativeCall()
    height match {
      case Some(h) => TextStyle._nSetHeight(_ptr, true, h)
      case None => TextStyle._nSetHeight(_ptr, false, 0)
    }
    this
  }

  def getLetterSpacing: Float = {
    try {
      Stats.onNativeCall()
      TextStyle._nGetLetterSpacing(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setLetterSpacing(letterSpacing: Float): TextStyle = {
    Stats.onNativeCall()
    TextStyle._nSetLetterSpacing(_ptr, letterSpacing)
    this
  }

  def getWordSpacing: Float = {
    try {
      Stats.onNativeCall()
      TextStyle._nGetWordSpacing(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setWordSpacing(wordSpacing: Float): TextStyle = {
    Stats.onNativeCall()
    TextStyle._nSetWordSpacing(_ptr, wordSpacing)
    this
  }

  def getTypeface: Typeface = {
    try {
      Stats.onNativeCall()
      val ptr = TextStyle._nGetTypeface(_ptr)
      if (ptr == 0) {
        null
      } else {
        new Typeface(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setTypeface(typeface: Typeface): TextStyle = {
    try {
      Stats.onNativeCall()
      TextStyle._nSetTypeface(_ptr, Native.getPtr(typeface))
      this
    } finally {
      ReferenceUtil.reachabilityFence(typeface)
    }
  }

  def getLocale: String = {
    try {
      Stats.onNativeCall()
      TextStyle._nGetLocale(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setLocale(locale: String): TextStyle = {
    Stats.onNativeCall()
    TextStyle._nSetLocale(_ptr, locale)
    this
  }

  def getBaselineMode: BaselineMode = {
    try {
      Stats.onNativeCall()
      BaselineMode.values(TextStyle._nGetBaselineMode(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setBaselineMode(baseline: BaselineMode): TextStyle = {
    Stats.onNativeCall()
    TextStyle._nSetBaselineMode(_ptr, baseline.ordinal)
    this
  }

  def getFontMetrics: FontMetrics = {
    try {
      Stats.onNativeCall()
      TextStyle._nGetFontMetrics(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def isPlaceholder: Boolean = {
    try {
      Stats.onNativeCall()
      TextStyle._nIsPlaceholder(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setPlaceholder: TextStyle = {
    Stats.onNativeCall()
    TextStyle._nSetPlaceholder(_ptr)
    this
  }
}