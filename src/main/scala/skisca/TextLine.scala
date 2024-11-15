package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import gay.menkissing.skisca.shaper.*
import org.jetbrains.annotations.*

object TextLine {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @NotNull
  @Contract("_, _ -> new") def make(text: String, font: Font): TextLine = {
    make(text, font, ShapingOptions.DEFAULT)
  }

  @NotNull
  @Contract("_, _, _, _ -> new") def make(text: String, font: Font, opts: ShapingOptions): TextLine = {
    try {
      val shaper = Shaper.makeShapeDontWrapOrReorder
      try {
        shaper.shapeLine(text, font, opts)
      } finally {
        if (shaper != null) shaper.close()
      }
    }
  }

  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nGetAscent(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nGetCapHeight(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nGetXHeight(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nGetDescent(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nGetLeading(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nGetWidth(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nGetHeight(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nGetTextBlob(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nGetGlyphs(ptr: Long): Array[Short]

  @ApiStatus.Internal
  @native def _nGetPositions(ptr: Long): Array[Float]

  @ApiStatus.Internal
  @native def _nGetRunPositions(ptr: Long): Array[Float]

  @ApiStatus.Internal
  @native def _nGetBreakPositions(ptr: Long): Array[Float]

  @ApiStatus.Internal
  @native def _nGetBreakOffsets(ptr: Long): Array[Int]

  @ApiStatus.Internal
  @native def _nGetOffsetAtCoord(ptr: Long, x: Float): Int

  @ApiStatus.Internal
  @native def _nGetLeftOffsetAtCoord(ptr: Long, x: Float): Int

  @ApiStatus.Internal
  @native def _nGetCoordAtOffset(ptr: Long, offset: Int): Float

  try Library.staticLoad()
}

class TextLine @ApiStatus.Internal(ptr: Long) extends Managed(ptr, TextLine._FinalizerHolder.PTR) {
  def getAscent: Float = {
    Stats.onNativeCall()
    try {
      TextLine._nGetAscent(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getCapHeight: Float = {
    Stats.onNativeCall()
    try {
      TextLine._nGetCapHeight(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getXHeight: Float = {
    Stats.onNativeCall()
    try {
      TextLine._nGetXHeight(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getDescent: Float = {
    Stats.onNativeCall()
    try {
      TextLine._nGetDescent(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getLeading: Float = {
    Stats.onNativeCall()
    try {
      TextLine._nGetLeading(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getWidth: Float = {
    Stats.onNativeCall()
    try {
      TextLine._nGetWidth(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getHeight: Float = {
    Stats.onNativeCall()
    try {
      TextLine._nGetHeight(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @Nullable def getTextBlob: TextBlob = {
    Stats.onNativeCall()
    try {
      val res = TextLine._nGetTextBlob(_ptr)
      if (res == 0) {
        null
      } else {
        new TextBlob(res)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getGlyphs: Array[Short] = {
    Stats.onNativeCall()
    try {
      TextLine._nGetGlyphs(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return [x0, y0, x1, y1, ...]
   */
  def getPositions: Array[Float] = {
    Stats.onNativeCall()
    try {
      TextLine._nGetPositions(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @param x coordinate in px
   * @return UTF-16 offset of glyph
   */
  def getOffsetAtCoord(x: Float): Int = {
    try {
      Stats.onNativeCall()
      TextLine._nGetOffsetAtCoord(_ptr, x)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @param x coordinate in px
   * @return UTF-16 offset of glyph strictly left of coord
   */
  def getLeftOffsetAtCoord(x: Float): Int = {
    try {
      Stats.onNativeCall()
      TextLine._nGetLeftOffsetAtCoord(_ptr, x)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @param offset UTF-16 character offset
   * @return glyph coordinate
   */
  def getCoordAtOffset(offset: Int): Float = {
    try {
      Stats.onNativeCall()
      TextLine._nGetCoordAtOffset(_ptr, offset)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns the number of intervals that intersect bounds.
   * bounds describes a pair of lines parallel to the text advance.
   * The return array size is a multiple of two, and is at most twice the number of glyphs in
   * the the blob.</p>
   *
   * @param lowerBound lower line parallel to the advance
   * @param upperBound upper line parallel to the advance
   * @return intersections; may be null
   */
  @Nullable def getIntercepts(lowerBound: Float, upperBound: Float): Array[Float] = {
    getIntercepts(lowerBound, upperBound, null)
  }

  /**
   * <p>Returns the number of intervals that intersect bounds.
   * bounds describes a pair of lines parallel to the text advance.
   * The return array size is a multiple of two, and is at most twice the number of glyphs in
   * the the blob.</p>
   *
   * @param lowerBound lower line parallel to the advance
   * @param upperBound upper line parallel to the advance
   * @param paint      specifies stroking, PathEffect that affects the result; may be null
   * @return intersections; may be null
   */
  @Nullable def getIntercepts(lowerBound: Float, upperBound: Float, @Nullable paint: Paint): Array[Float] = {
    try {
      val blob = getTextBlob
      try {
        if (blob == null) {
          null
        } else {
          blob.getIntercepts(lowerBound, upperBound, paint)
        }
      } finally {
        ReferenceUtil.reachabilityFence(this)
        ReferenceUtil.reachabilityFence(paint)
        if (blob != null) blob.close()
      }
    }
  }
}