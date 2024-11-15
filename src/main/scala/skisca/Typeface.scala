package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

import java.util.*

object Typeface {
  /**
   * @return the default normal typeface, which is never null
   */
    @NotNull def makeDefault: Typeface = {
      Stats.onNativeCall()
      new Typeface(_nMakeDefault)
    }

  /**
   * Creates a new reference to the typeface that most closely matches the
   * requested name and style. This method allows extended font
   * face specifiers as in the {@link FontStyle} type. Will never return null.
   *
   * @param name  May be null. The name of the font family
   * @param style The style of the typeface
   * @return reference to the closest-matching typeface
   */
  @NotNull def makeFromName(name: String, style: FontStyle): Typeface = {
    Stats.onNativeCall()
    val ptr = _nMakeFromName(name, style.value)
    if (0 == ptr) {
      makeDefault
    } else {
      new Typeface(ptr)
    }
  }

  /**
   * @return a new typeface given a file
   * @throws IllegalArgumentException If the file does not exist, or is not a valid font file
   */
  @NotNull def makeFromFile(path: String): Typeface = {
    makeFromFile(path, 0)
  }

  /**
   * @return a new typeface given a file
   * @throws IllegalArgumentException If the file does not exist, or is not a valid font file
   */
  @NotNull def makeFromFile(path: String, index: Int): Typeface = {
    Stats.onNativeCall()
    val ptr = _nMakeFromFile(path, index)
    if (ptr == 0) throw new IllegalArgumentException("Failed to create Typeface from path=\"" + path + "\" index=" + index)
    new Typeface(ptr)
  }

  /**
   * @return a new typeface given a Data
   * @throws IllegalArgumentException If the data is null, or is not a valid font file
   */
  @NotNull def makeFromData(data: Data): Typeface = {
    makeFromData(data, 0)
  }

  /**
   * @return a new typeface given a Data
   * @throws IllegalArgumentException If the data is null, or is not a valid font file
   */
  @NotNull def makeFromData(data: Data, index: Int): Typeface = {
    try {
      Stats.onNativeCall()
      val ptr = _nMakeFromData(Native.getPtr(data), index)
      if (ptr == 0) throw new IllegalArgumentException("Failed to create Typeface from data " + data)
      new Typeface(ptr)
    } finally {
      ReferenceUtil.reachabilityFence(data)
    }
  }

  @ApiStatus.Internal
  @native def _nGetFontStyle(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nIsFixedPitch(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nGetVariations(ptr: Long): Array[FontVariation]

  @ApiStatus.Internal
  @native def _nGetVariationAxes(ptr: Long): Array[FontVariationAxis]

  @ApiStatus.Internal
  @native def _nGetUniqueId(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nEquals(ptr: Long, otherPtr: Long): Boolean

  @ApiStatus.Internal
  @native def _nMakeDefault: Long

  @ApiStatus.Internal
  @native def _nMakeFromName(name: String, fontStyle: Int): Long

  @ApiStatus.Internal
  @native def _nMakeFromFile(path: String, index: Int): Long

  @ApiStatus.Internal
  @native def _nMakeFromData(dataPtr: Long, index: Int): Long

  @ApiStatus.Internal
  @native def _nMakeClone(ptr: Long, variations: Array[FontVariation], collectionIndex: Int): Long

  @ApiStatus.Internal
  @native def _nGetUTF32Glyphs(ptr: Long, uni: Array[Int]): Array[Short]

  @ApiStatus.Internal
  @native def _nGetUTF32Glyph(ptr: Long, unichar: Int): Short

  @ApiStatus.Internal
  @native def _nGetGlyphsCount(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nGetTablesCount(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nGetTableTags(ptr: Long): Array[Int]

  @ApiStatus.Internal
  @native def _nGetTableSize(ptr: Long, tag: Int): Long

  @ApiStatus.Internal
  @native def _nGetTableData(ptr: Long, tag: Int): Long

  @ApiStatus.Internal
  @native def _nGetUnitsPerEm(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nGetKerningPairAdjustments(ptr: Long, glyphs: Array[Short]): Array[Int]

  @ApiStatus.Internal
  @native def _nGetFamilyNames(ptr: Long): Array[FontFamilyName]

  @ApiStatus.Internal
  @native def _nGetFamilyName(ptr: Long): String

  @ApiStatus.Internal
  @native def _nGetBounds(ptr: Long): Rect

  try Library.staticLoad()
}

class Typeface @ApiStatus.Internal(ptr: Long) extends RefCnt(ptr, true) {
  /**
   * @return the typeface’s intrinsic style attributes
   */
  def getFontStyle: FontStyle = {
    try {
      Stats.onNativeCall()
      new FontStyle(Typeface._nGetFontStyle(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return true if {@link # getFontStyle ( )} has the bold bit set
   */
  def isBold: Boolean = {
    getFontStyle.getWeight >= FontWeight.SEMI_BOLD
  }

  /**
   * @return true if {@link # getFontStyle ( )} has the italic bit set
   */
  def isItalic: Boolean = {
    getFontStyle.getSlant ne FontSlant.UPRIGHT
  }

  /**
   * This is a style bit, advance widths may vary even if this returns true.
   *
   * @return true if the typeface claims to be fixed-pitch
   */
  def isFixedPitch: Boolean = {
    try {
      Stats.onNativeCall()
      Typeface._nIsFixedPitch(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * It is possible the number of axes can be retrieved but actual position cannot.
   *
   * @return the variation coordinates describing the position of this typeface in design variation space, null if there’s no variations
   */
  @Nullable def getVariations: Array[FontVariation] = {
    try {
      Stats.onNativeCall()
      Typeface._nGetVariations(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * It is possible the number of axes can be retrieved but actual position cannot.
   *
   * @return the variation coordinates describing the position of this typeface in design variation space, null if there’s no variations
   */
  @Nullable def getVariationAxes: Array[FontVariationAxis] = {
    try {
      Stats.onNativeCall()
      Typeface._nGetVariationAxes(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return a 32bit value for this typeface, unique for the underlying font data. Never 0
   */
  def getUniqueId: Int = {
    try {
      Stats.onNativeCall()
      Typeface._nGetUniqueId(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return true if the two typefaces reference the same underlying font, treating null as the default font
   */
  @ApiStatus.Internal override def _nativeEquals(other: Native): Boolean = {
    try {
      Typeface
        ._nEquals(_ptr, Native.getPtr(other))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(other)
    }
  }

  /**
   * Return a new typeface based on this typeface but parameterized as specified in the
   * variation. If the variation does not supply an argument for a parameter
   * in the font then the value from this typeface will be used as the value for that argument.
   *
   * @return same typeface if variation already matches, new typeface otherwise
   * @throws IllegalArgumentException on failure
   */
  def makeClone(variation: FontVariation): Typeface = {
    makeClone(Array[FontVariation](variation), 0)
  }

  /**
   * Return a new typeface based on this typeface but parameterized as specified in the
   * variations. If the variations does not supply an argument for a parameter
   * in the font then the value from this typeface will be used as the value for that argument.
   *
   * @return same typeface if all variation already match, new typeface otherwise
   * @throws IllegalArgumentException on failure
   */
  def makeClone(variations: Array[FontVariation]): Typeface = {
    makeClone(variations, 0)
  }

  /**
   * Return a new typeface based on this typeface but parameterized as specified in the
   * variations. If the variations does not supply an argument for a parameter
   * in the font then the value from this typeface will be used as the value for that argument.
   *
   * @return same typeface if all variation already match, new typeface otherwise
   * @throws IllegalArgumentException on failure
   */
  def makeClone(variations: Array[FontVariation], collectionIndex: Int): Typeface = {
    try {
      if (variations.length == 0) return this
      Stats.onNativeCall()
      val ptr = Typeface._nMakeClone(_ptr, variations, collectionIndex)
      if (ptr == 0) {
        throw new IllegalArgumentException("Failed to clone Typeface " + this + " with " + variations.mkString("Array(", ",", ")"))
      }
      new Typeface(ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Given a string, returns corresponding glyph ids.
   *
   * @return the corresponding glyph ids for each character.
   */
  def getStringGlyphs(s: String): Array[Short] = {
    getUTF32Glyphs(s.codePoints.toArray)
  }

  /**
   * Given an array of UTF32 character codes, return their corresponding glyph IDs.
   *
   * @return the corresponding glyph IDs for each character.
   */
  def getUTF32Glyphs(uni: Array[Int]): Array[Short] = {
    try {
      Stats.onNativeCall()
      Typeface._nGetUTF32Glyphs(_ptr, uni)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * This is a short-cut for calling {@link # getUTF32Glyphs ( int [ ] )}.
   *
   * @return the glyph that corresponds to the specified unicode code-point (in UTF32 encoding). If the unichar is not supported, returns 0
   */
  def getUTF32Glyph(unichar: Int): Short = {
    try {
      Stats.onNativeCall()
      Typeface._nGetUTF32Glyph(_ptr, unichar)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return the number of glyphs in the typeface
   */
  def getGlyphsCount: Int = {
    try {
      Stats.onNativeCall()
      Typeface._nGetGlyphsCount(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return the number of tables in the font
   */
  def getTablesCount: Int = {
    try {
      Stats.onNativeCall()
      Typeface._nGetTablesCount(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return the list of table tags in the font
   */
  def getTableTags: Array[String] = {
    try {
      Stats.onNativeCall()
      java.util.Arrays.stream(Typeface._nGetTableTags(_ptr)).mapToObj(FourByteTag.toString).toArray(Array.ofDim[String])
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Given a table tag, return the size of its contents, or 0 if not present
   */
  def getTableSize(tag: String): Long = {
    try {
      Stats.onNativeCall()
      Typeface._nGetTableSize(_ptr, FourByteTag.fromString(tag))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Return an immutable copy of the requested font table, or null if that table was
   * not found.
   *
   * @param tag The table tag whose contents are to be copied
   * @return an immutable copy of the table's data, or null
   */
  @Nullable def getTableData(tag: String): Data = {
    try {
      Stats.onNativeCall()
      val ptr = Typeface._nGetTableData(_ptr, FourByteTag.fromString(tag))
      if (ptr == 0) {
        null
      } else {
        new Data(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return the units-per-em value for this typeface, or zero if there is an error
   */
  def getUnitsPerEm: Int = {
    try {
      Stats.onNativeCall()
      Typeface._nGetUnitsPerEm(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Given a run of glyphs, return the associated horizontal adjustments.
   * Adjustments are in "design units", which are integers relative to the
   * typeface's units per em (see {@link # getUnitsPerEm ( )}).
   *
   * Some typefaces are known to never support kerning. Calling this with null,
   * if it returns null then it will always return null (no kerning) for all
   * possible glyph runs. If it returns int[0], then it *may* return non-null
   * adjustments for some glyph runs.
   *
   * @return adjustment array (one less than glyphs), or null if no kerning should be applied
   */
  @Nullable def getKerningPairAdjustments(glyphs: Array[Short]): Array[Int] = {
    try {
      Stats.onNativeCall()
      Typeface._nGetKerningPairAdjustments(_ptr, glyphs)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return all of the family names specified by the font
   */
  def getFamilyNames: Array[FontFamilyName] = {
    try {
      Stats.onNativeCall()
      Typeface._nGetFamilyNames(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return the family name for this typeface. The language of the name is whatever the host platform chooses
   */
  def getFamilyName: String = {
    try {
      Stats.onNativeCall()
      Typeface._nGetFamilyName(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Return a rectangle (scaled to 1-pt) that represents the union of the bounds of all
   * of the glyphs, but each one positioned at (0,). This may be conservatively large, and
   * will not take into account any hinting or other size-specific adjustments.
   */
  def getBounds: Rect = {
    try {
      Stats.onNativeCall()
      Typeface._nGetBounds(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}