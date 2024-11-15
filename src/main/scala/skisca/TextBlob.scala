package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object TextBlob {
  /**
   * Returns a TextBlob built from a single run of text with x-positions and a single y value.
   * Returns null if glyphs is empty.
   *
   * @param glyphs glyphs drawn
   * @param xpos   array of x-positions, must contain values for all of the glyphs.
   * @param ypos   shared y-position for each glyph, to be paired with each xpos.
   * @param font   Font used for this run
   * @return new TextBlob or null
   */
  def makeFromPosH(glyphs: Array[Short], xpos: Array[Float], ypos: Float, font: Font): TextBlob = {
    try {
      assert(glyphs.length == xpos.length, "glyphs.length " + glyphs.length + " != xpos.length " + xpos.length)
      Stats.onNativeCall()
      val ptr = _nMakeFromPosH(glyphs, xpos, ypos, Native.getPtr(font))
      if (ptr == 0) {
        null
      } else {
        new TextBlob(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(font)
    }
  }

  /**
   * Returns a TextBlob built from a single run of text with positions.
   * Returns null if glyphs is empty.
   *
   * @param glyphs glyphs drawn
   * @param pos    array of positions, must contain values for all of the glyphs.
   * @param font   Font used for this run
   * @return new TextBlob or null
   */
  def makeFromPos(glyphs: Array[Short], pos: Array[Point], font: Font): TextBlob = {
    try {
      assert(glyphs.length == pos.length, "glyphs.length " + glyphs.length + " != pos.length " + pos.length)
      val floatPos = new Array[Float](pos.length * 2)
      for (i <- 0 until pos.length) {
        floatPos(i * 2) = pos(i)._x
        floatPos(i * 2 + 1) = pos(i)._y
      }
      Stats.onNativeCall()
      val ptr = _nMakeFromPos(glyphs, floatPos, Native.getPtr(font))
      if (ptr == 0) {
        null
      } else {
        new TextBlob(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(font)
    }
  }

  def makeFromRSXform(glyphs: Array[Short], xform: Array[RSXform], font: Font): TextBlob = {
    try {
      assert(glyphs.length == xform.length, "glyphs.length " + glyphs.length + " != xform.length " + xform.length)
      val floatXform = new Array[Float](xform.length * 4)
      for ((form, i) <- xform.zipWithIndex) {
        floatXform(i * 4) = form.scos
        floatXform(i * 4 + 1) = form.ssin
        floatXform(i * 4 + 2) = form.tx
        floatXform(i * 4 + 3) = form.ty
      }
      Stats.onNativeCall()
      val ptr = _nMakeFromRSXform(glyphs, floatXform, Native.getPtr(font))
      if (ptr == 0) {
        null
      } else {
        new TextBlob(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(font)
    }
  }

  @Nullable def makeFromData(data: Data): TextBlob = {
    try {
      Stats.onNativeCall()
      val ptr = _nMakeFromData(Native.getPtr(data))
      if (ptr == 0) {
        null
      } else {
        new TextBlob(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(data)
    }
  }

  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nBounds(ptr: Long): Rect

  @ApiStatus.Internal
  @native def _nGetUniqueId(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nGetIntercepts(ptr: Long, lower: Float, upper: Float, paintPtr: Long): Array[Float]

  @ApiStatus.Internal
  @native def _nMakeFromPosH(glyphs: Array[Short], xpos: Array[Float], ypos: Float, fontPtr: Long): Long

  @ApiStatus.Internal
  @native def _nMakeFromPos(glyphs: Array[Short], pos: Array[Float], fontPtr: Long): Long

  @ApiStatus.Internal
  @native def _nMakeFromRSXform(glyphs: Array[Short], xform: Array[Float], fontPtr: Long): Long

  @ApiStatus.Internal
  @native def _nSerializeToData(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nMakeFromData(dataPtr: Long): Long

  @ApiStatus.Internal
  @native def _nGetGlyphs(ptr: Long): Array[Short]

  @ApiStatus.Internal
  @native def _nGetPositions(ptr: Long): Array[Float]

  @ApiStatus.Internal
  @native def _nGetClusters(ptr: Long): Array[Int]

  @ApiStatus.Internal
  @native def _nGetTightBounds(ptr: Long): Rect

  @ApiStatus.Internal
  @native def _nGetBlockBounds(ptr: Long): Rect

  @ApiStatus.Internal
  @native def _nGetFirstBaseline(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nGetLastBaseline(ptr: Long): Float

  try Library.staticLoad()
}

class TextBlob @ApiStatus.Internal(ptr: Long) extends Managed(ptr, TextBlob._FinalizerHolder.PTR) {
  /**
   * Returns conservative bounding box. Uses Paint associated with each glyph to
   * determine glyph bounds, and unions all bounds. Returned bounds may be
   * larger than the bounds of all glyphs in runs.
   *
   * @return conservative bounding box
   */
  def getBounds: Rect = {
    try {
      Stats.onNativeCall()
      TextBlob._nBounds(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns a non-zero value unique among all text blobs.
   *
   * @return identifier for TextBlob
   */
  def getUniqueId: Int = {
    try {
      Stats.onNativeCall()
      TextBlob._nGetUniqueId(_ptr)
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
   * <p>Runs within the blob that contain SkRSXform are ignored when computing intercepts.</p>
   *
   * @param lowerBound lower line parallel to the advance
   * @param upperBound upper line parallel to the advance
   * @return intersections; may be null
   */
  @Nullable def getIntercepts(lowerBound: Float, upperBound: Float): Array[Float] = {
    getIntercepts(lowerBound, upperBound)
  }

  /**
   * <p>Returns the number of intervals that intersect bounds.
   * bounds describes a pair of lines parallel to the text advance.
   * The return array size is a multiple of two, and is at most twice the number of glyphs in
   * the the blob.</p>
   *
   * <p>Runs within the blob that contain SkRSXform are ignored when computing intercepts.</p>
   *
   * @param lowerBound lower line parallel to the advance
   * @param upperBound upper line parallel to the advance
   * @param paint      specifies stroking, PathEffect that affects the result; may be null
   * @return intersections; may be null
   */
  @Nullable def getIntercepts(lowerBound: Float, upperBound: Float, @Nullable paint: Paint): Array[Float] = {
    try {
      Stats.onNativeCall()
      TextBlob._nGetIntercepts(_ptr, lowerBound, upperBound, Native.getPtr(paint))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(paint)
    }
  }

  def serializeToData: Data = {
    try {
      Stats.onNativeCall()
      new Data(TextBlob._nSerializeToData(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return glyph indices for the whole blob
   */
  @NotNull def getGlyphs: Array[Short] = {
    try {
      Stats.onNativeCall()
      TextBlob._nGetGlyphs(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Return result depends on how blob was constructed.</p>
   *
   * <ul><li>makeFromPosH returns 1 float per glyph (x pos)
   * <li>makeFromPos returns 2 floats per glyph (x, y pos)
   * <li>makeFromRSXform returns 4 floats per glyph
   * </ul>
   *
   * <p>Blobs constructed by TextBlobBuilderRunHandler/Shaper default handler have 2 floats per glyph.</p>
   *
   * @return glyph positions for the blob if it was made with makeFromPos, null otherwise
   */
  @NotNull def getPositions: Array[Float] = {
    try {
      Stats.onNativeCall()
      TextBlob._nGetPositions(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Only works on TextBlobs that come from TextBlobBuilderRunHandler/Shaper default handler.
   *
   * @return utf-16 offsets of clusters that start the glyph
   * @throws IllegalArgumentException if TextBlob doesn’t have this information
   */
  @NotNull def getClusters: Array[Int] = {
    try {
      Stats.onNativeCall()
      val res = TextBlob._nGetClusters(_ptr)
      if (res == null) throw new IllegalArgumentException
      res
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Only works on TextBlobs that come from TextBlobBuilderRunHandler/Shaper default handler.
   *
   * @return tight bounds around all the glyphs in the TextBlob
   * @throws IllegalArgumentException if TextBlob doesn’t have this information
   */
  @NotNull def getTightBounds: Rect = {
    try {
      Stats.onNativeCall()
      val res = TextBlob._nGetTightBounds(_ptr)
      if (res == null) throw new IllegalArgumentException
      res
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Only works on TextBlobs that come from TextBlobBuilderRunHandler/Shaper default handler.
   *
   * @return tight bounds around all the glyphs in the TextBlob
   * @throws IllegalArgumentException if TextBlob doesn’t have this information
   */
  @NotNull def getBlockBounds: Rect = {
    try {
      Stats.onNativeCall()
      val res = TextBlob._nGetBlockBounds(_ptr)
      if (res == null) throw new IllegalArgumentException
      res
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Only works on TextBlobs that come from TextBlobBuilderRunHandler/Shaper default handler.
   *
   * @return first baseline in TextBlob
   * @throws IllegalArgumentException if TextBlob doesn’t have this information
   */
  @NotNull def getFirstBaseline: Float = {
    try {
      Stats.onNativeCall()
      val res = TextBlob._nGetFirstBaseline(_ptr)
      res
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Only works on TextBlobs that come from TextBlobBuilderRunHandler/Shaper default handler.
   *
   * @return last baseline in TextBlob
   * @throws IllegalArgumentException if TextBlob doesn’t have this information
   */
  @NotNull def getLastBaseline: Float = {
    try {
      Stats.onNativeCall()
      val res = TextBlob._nGetLastBaseline(_ptr)
      res
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}