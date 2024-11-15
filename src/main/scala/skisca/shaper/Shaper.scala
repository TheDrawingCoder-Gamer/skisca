package gay.menkissing.skisca.shaper

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

import java.util.*

/**
 * Shapes text using HarfBuzz and places the shaped text into a
 * client-managed buffer.
 */
object Shaper {
  @NotNull
  @Contract("-> new") def makePrimitive: Shaper = {
    Stats.onNativeCall()
    new Shaper(_nMakePrimitive)
  }

  @NotNull
  @Contract("-> new") def makeShaperDrivenWrapper: Shaper = {
    makeShaperDrivenWrapper(null)
  }

  @NotNull
  @Contract("_ -> new") def makeShaperDrivenWrapper(@Nullable fontMgr: FontMgr): Shaper = {
    try {
      Stats.onNativeCall()
      new Shaper(_nMakeShaperDrivenWrapper(Native.getPtr(fontMgr)))
    } finally {
      ReferenceUtil.reachabilityFence(fontMgr)
    }
  }

  @NotNull
  @Contract("-> new") def makeShapeThenWrap: Shaper = {
    makeShapeThenWrap(null)
  }

  @NotNull
  @Contract("_ -> new") def makeShapeThenWrap(@Nullable fontMgr: FontMgr): Shaper = {
    try {
      Stats.onNativeCall()
      new Shaper(_nMakeShapeThenWrap(Native.getPtr(fontMgr)))
    } finally {
      ReferenceUtil.reachabilityFence(fontMgr)
    }
  }

  @NotNull
  @Contract("-> new") def makeShapeDontWrapOrReorder: Shaper = {
    makeShapeDontWrapOrReorder(null)
  }

  @NotNull
  @Contract("_ -> new") def makeShapeDontWrapOrReorder(@Nullable fontMgr: FontMgr): Shaper = {
    try {
      Stats.onNativeCall()
      new Shaper(_nMakeShapeDontWrapOrReorder(Native.getPtr(fontMgr)))
    } finally {
      ReferenceUtil.reachabilityFence(fontMgr)
    }
  }

  /**
   * <p>Only works on macOS</p>
   *
   * <p>WARN broken in m87 https://bugs.chromium.org/p/skia/issues/detail?id=10897</p>
   *
   * @return Shaper on macOS, throws UnsupportedOperationException elsewhere
   */
  @NotNull
  @Contract("-> new") def makeCoreText: Shaper = {
    Stats.onNativeCall()
    val ptr = _nMakeCoreText
    if (ptr == 0) throw new UnsupportedOperationException("CoreText not available")
    new Shaper(ptr)
  }

  @NotNull
  @Contract("-> new") def make: Shaper = {
    make(null)
  }

  @NotNull
  @Contract("_ -> new") def make(@Nullable fontMgr: FontMgr): Shaper = {
    try {
      Stats.onNativeCall()
      new Shaper(_nMake(Native.getPtr(fontMgr)))
    } finally {
      ReferenceUtil.reachabilityFence(fontMgr)
    }
  }

  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @native def _nGetFinalizer: Long

  @native def _nMakePrimitive: Long

  @native def _nMakeShaperDrivenWrapper(fontMgrPtr: Long): Long

  @native def _nMakeShapeThenWrap(fontMgrPtr: Long): Long

  @native def _nMakeShapeDontWrapOrReorder(fontMgrPtr: Long): Long

  @native def _nMakeCoreText: Long

  @native def _nMake(fontMgrPtr: Long): Long

  @native def _nShapeBlob(ptr: Long, text: String, fontPtr: Long, opts: ShapingOptions, width: Float, offsetX: Float, offsetY: Float): Long

  @native def _nShapeLine(ptr: Long, text: String, fontPtr: Long, opts: ShapingOptions): Long

  @native def _nShape(ptr: Long, textPtr: Long, fontIter: java.util.Iterator[FontRun], bidiIter: java.util.Iterator[BidiRun], scriptIter: java.util.Iterator[ScriptRun], langIter: java.util.Iterator[LanguageRun], opts: ShapingOptions, width: Float, runHandler: RunHandler): Unit

  try Library.staticLoad()
}

class Shaper @ApiStatus.Internal(ptr: Long) extends Managed(ptr, Shaper._FinalizerHolder.PTR, true) {
  @Nullable
  @Contract("_, _ -> new") def shape(text: String, font: Font): TextBlob = {
    shape(text, font, ShapingOptions
      .DEFAULT, Float.PositiveInfinity, Point.ZERO)
  }

  @Nullable
  @Contract("_, _, _ -> new") def shape(text: String, font: Font, width: Float): TextBlob = {
    shape(text, font, ShapingOptions
      .DEFAULT, width, Point.ZERO)
  }

  @Nullable
  @Contract("_, _, _, _ -> new") def shape(text: String, font: Font, width: Float, @NotNull offset: Point): TextBlob = {
    shape(text, font, ShapingOptions
      .DEFAULT, width, offset)
  }

  @Nullable
  @Contract("_, _, _, _, _ -> new") def shape(text: String, @NotNull font: Font, @NotNull opts: ShapingOptions, width: Float, @NotNull offset: Point): TextBlob = {
    try {
      assert(opts != null, "Can’t Shaper::shape with opts == null")
      assert(font != null, "Can’t Shaper::shape with font == null")
      Stats.onNativeCall()
      val ptr = Shaper._nShapeBlob(_ptr, text, Native.getPtr(font), opts, width, offset._x, offset._y)
      if (0 == ptr) {
        null
      } else {
        new TextBlob(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(font)
    }
  }

  @NotNull
  @Contract("_, _, _ -> new") def shapeLine(text: String, @NotNull font: Font, @NotNull opts: ShapingOptions): TextLine = {
    try {
      assert(opts != null, "Can’t Shaper::shapeLine with opts == null")
      assert(font != null, "Can’t Shaper::shapeLine with font == null")
      Stats.onNativeCall()
      new TextLine(Shaper._nShapeLine(_ptr, text, Native.getPtr(font), opts))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(font)
    }
  }

  @NotNull
  @Contract("_, _, _ -> new") def shapeLine(text: String, font: Font): TextLine = {
    shapeLine(text, font, ShapingOptions
      .DEFAULT)
  }

  @NotNull
  @Contract("_, _, _, _, _ -> this") def shape(text: String, font: Font, @NotNull opts: ShapingOptions, width: Float, runHandler: RunHandler): Shaper = {
    try {
      val textUtf8 = new ManagedString(text)
      val fontIter = new FontMgrRunIterator(textUtf8, false, font, opts)
      val bidiIter = new IcuBidiRunIterator(textUtf8, false, if (opts.leftToRight) {
        java.text.Bidi.DIRECTION_LEFT_TO_RIGHT
      } else {
        java.text.Bidi.DIRECTION_RIGHT_TO_LEFT
      })
      val scriptIter = new HbIcuScriptRunIterator(textUtf8, false)
      try {
        val langIter = new TrivialLanguageRunIterator(text, Locale.getDefault.toLanguageTag)
        shape(textUtf8, fontIter, bidiIter, scriptIter, langIter, opts, width, runHandler)
      } finally {
        if (textUtf8 != null) textUtf8.close()
        if (fontIter != null) fontIter.close()
        if (bidiIter != null) bidiIter.close()
        if (scriptIter != null) scriptIter.close()
      }
    }
  }

  @NotNull
  @Contract("_, _, _, _, _, _, _ -> this") def shape(@NotNull text: String, @NotNull fontIter: java.util.Iterator[FontRun], @NotNull bidiIter: java.util.Iterator[BidiRun], @NotNull scriptIter: java.util.Iterator[ScriptRun], @NotNull langIter: java.util.Iterator[LanguageRun], @NotNull opts: ShapingOptions, width: Float, @NotNull runHandler: RunHandler): Shaper = {
    try {
      val textUtf8 = new ManagedString(text)
      try {
        shape(textUtf8, fontIter, bidiIter, scriptIter, langIter, opts, width, runHandler)
      } finally {
        if (textUtf8 != null) textUtf8.close()
      }
    }
  }

  @NotNull
  @Contract("_, _, _, _, _, _, _ -> this") def shape(@NotNull textUtf8: ManagedString, @NotNull fontIter: java.util.Iterator[FontRun], @NotNull bidiIter: java.util.Iterator[BidiRun], @NotNull scriptIter: java.util.Iterator[ScriptRun], @NotNull langIter: java.util.Iterator[LanguageRun], @NotNull opts: ShapingOptions, width: Float, @NotNull runHandler: RunHandler): Shaper = {
    try {
      assert(fontIter != null, "FontRunIterator == null")
      assert(bidiIter != null, "BidiRunIterator == null")
      assert(scriptIter != null, "ScriptRunIterator == null")
      assert(langIter != null, "LanguageRunIterator == null")
      assert(opts != null, "Can’t Shaper::shape with opts == null")
      Stats.onNativeCall()
      Shaper._nShape(_ptr, Native.getPtr(textUtf8), fontIter, bidiIter, scriptIter, langIter, opts, width, runHandler)
      this
    } finally {
      ReferenceUtil.reachabilityFence(textUtf8)
    }
  }
}